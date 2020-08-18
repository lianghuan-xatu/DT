package com.xatu.weibo.dao;


import com.google.inject.internal.cglib.core.$Predicate;
import com.sun.org.apache.bcel.internal.generic.ANEWARRAY;
import com.xatu.weibo.constants.Constants;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.ByteStringer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.file.tfile.TFile;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import javax.sql.ConnectionPoolDataSource;
import java.io.IOException;
import java.io.PushbackInputStream;
import java.util.ArrayList;

/**
 *
 *
 *
 * 发布微博
 * 删除微博
 *
 * 关注用户
 * 取关用户
 *
 * 获取用户微博详情
 * 获取用户的初始化页面
 */
public class HBaseDao
{
    //发布微博
    public static void publishWeiBo(String uid,String content) throws IOException {

        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);

        //操作微博内容表
        //获取微博内容表对象
        Table contentTable = connection.getTable(TableName.valueOf(Constants.CONTENT_TABLE));
        //获取当前系统时间
        long ts = System.currentTimeMillis();

        //获取rowkey
        String rowKey = uid + "_" + ts;
        //创建Put对象
        Put put = new Put(Bytes.toBytes(rowKey));
        //给Put对象赋值
        put.addColumn(Bytes.toBytes(Constants.CONTENT_TABLE_CF),Bytes.toBytes("content"),Bytes.toBytes(content));
        //执行插入数据操作
        contentTable.put(put);


        //操作微博收件箱
        //获取用户关系对象
        Table relationTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));

        //获取当前表的fans列族数据
        Get get = new Get(Bytes.toBytes(uid));
        get.addFamily(Bytes.toBytes(Constants.RELATION_TABLE_CF2));
        Result result = relationTable.get(get);

        //创建一个集合用于存放微博内容表的Put对象
        ArrayList<Put> inboxPuts = new ArrayList<Put>();

        Cell[] cells = result.rawCells();
        for (Cell cell : cells) {
            //构建微博收件箱表的Put对象
            Put inboxPut = new Put(CellUtil.cloneQualifier(cell));
            //给收件箱Put对象赋值
            inboxPut.addColumn(Bytes.toBytes(Constants.INBOX_TABLE_CF),Bytes.toBytes(uid),Bytes.toBytes(rowKey));
            //将收件箱表的Put对象放入集合
            inboxPuts.add(inboxPut);
        }

        //判断是否有粉丝
        if(inboxPuts.size() > 0) {
            //获取收件箱对象
            Table inboxTable = connection.getTable(TableName.valueOf(Constants.INBOX_TABLE));
            //执行收件箱表数据插入操作
            inboxTable.put(inboxPuts);
        }

        //关闭资源
        contentTable.close();
        relationTable.close();
        connection.close();
    }

    //关注用户
    public static void addAttends(String uid,String... attends) throws IOException {
        //校验是否添加了待关注的人
        if(attends.length <= 0) {
            System.out.println("请添加待关注的人");
            return;
        }
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //操作用户关系表
        Table table = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        Table contentTable = connection.getTable(TableName.valueOf(Constants.CONTENT_TABLE));

        //创建集合存放用户关系表的Put对象
        ArrayList<Put> relationPuts = new ArrayList<Put>();
        //创建操作者的Put对象
        Put uidPut = new Put(Bytes.toBytes(uid));
        //循环创建被关注者的Put对象
        for (String attend : attends) {
            uidPut.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF1),Bytes.toBytes(attend),Bytes.toBytes(attend));
            //创建被关注者的Put对象
            Put attendPut = new Put(Bytes.toBytes(attend));
            attendPut.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF2),Bytes.toBytes(uid),Bytes.toBytes(uid));
            //将被关注者的Put对象添加至集合
            relationPuts.add(attendPut);
        }
        //执行用户关系表的插入数据操作
        table.put(uidPut);
        table.put(relationPuts);
        //操作收件箱表
        //获取微博内容Put对象
        //获取收件箱的Put对象
        //循环attends，获取每个被关注者近期发布微博内容
        Put inboxPut = new Put(Bytes.toBytes(uid));
        for (String attend : attends) {
            //获取当前被关注者微博内容
            //对获取的微博进行遍历
            Scan scan = new Scan(Bytes.toBytes(attend+"_"),Bytes.toBytes(attend+"|"));
            ResultScanner scanner = contentTable.getScanner(scan);
            //定义一个时间戳
            long l = System.currentTimeMillis();

            for (Result result : scanner) {
                //赋值inboxPut
                inboxPut.addColumn(Bytes.toBytes(Constants.INBOX_TABLE_CF),Bytes.toBytes(attend),l++,result.getRow());
            }
        }
        //判断Put对象是否为空
        if(!inboxPut.isEmpty()) {
            Table inboxTable = connection.getTable(TableName.valueOf(Constants.INBOX_TABLE));
            inboxTable.put(inboxPut);
            inboxTable.close();
        }
        //关闭资源
        table.close();
        contentTable.close();
        connection.close();

    }

    //取关
    public static void deleteAttends(String uid,String... dels) throws IOException {
        //获取连接
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取用户关系表对象
        Table relationTable = connection.getTable(TableName.valueOf(Constants.RELATION_TABLE));
        //创建集合，用于存放用户关系表Delete对象
        ArrayList<Delete> deletes = new ArrayList<Delete>();
        //创建操作者Delete对象
        Delete uidDelete = new Delete(Bytes.toBytes(uid));
        //循环创建被取关者的delete对象
        for (String del : dels) {
            //给操作者Delete对象赋值
            uidDelete.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF1),Bytes.toBytes(del));
            //创建被取关者的Delete对象
            Delete delDelete = new Delete(Bytes.toBytes(del));
            //给被取关者Delete对象赋值
            delDelete.addColumn(Bytes.toBytes(Constants.RELATION_TABLE_CF2),Bytes.toBytes(uid));
            //将被取关者的Delete对象添加至集合
            deletes.add(delDelete);

        }
        //执行删除
        relationTable.delete(deletes);
        relationTable.delete(uidDelete);

        //执行收件箱对象
        //获取收件箱表对象
        Table inboxTable = connection.getTable(TableName.valueOf(Constants.INBOX_TABLE));
        //创建操作者的Delete对象
        Delete inboxDelete = new Delete(Bytes.toBytes(uid));
        //给操作者对象循环赋值
        for (String del : dels) {
            inboxDelete.addColumn(Bytes.toBytes(Constants.INBOX_TABLE_CF),Bytes.toBytes(del));
        }
        inboxTable.delete(inboxDelete);

        relationTable.close();
        inboxTable.close();
        connection.close();

    }

    //获取个人初始化页面数据
    public static void getInit(String uid) throws IOException {
        //获取Connection连接对象
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取收件箱表对象
        Table inboxTable = connection.getTable(TableName.valueOf(Constants.INBOX_TABLE));
        //获取微博内容表对象
        Table contentTable = connection.getTable(TableName.valueOf(Constants.CONTENT_TABLE));
        //创建收件箱表Get对象，并获取数据
        Get inboxGet = new Get(Bytes.toBytes(uid));
        inboxGet.setMaxVersions();
        Result result = inboxTable.get(inboxGet);
        //遍历获取数据
        for (Cell cell : result.rawCells()) {
            //构建微博内容表Get对象
            Get get = new Get(CellUtil.cloneValue(cell));
            //获取该Get对象的数据内容
            Result result1 = contentTable.get(get);
            //获取内容打印结果  
            for (Cell rawCell : result1.rawCells()) {
                System.out.println("RK"+Bytes.toString(CellUtil.cloneRow(rawCell)));
                System.out.println("RF"+Bytes.toString(CellUtil.cloneFamily(rawCell)));
                System.out.println("CN"+Bytes.toString(CellUtil.cloneQualifier(rawCell)));
                System.out.println("Value"+Bytes.toString(CellUtil.cloneValue(rawCell)));
            }
        }
        //关闭资源
        contentTable.close();
        inboxTable.close();
        connection.close();

    }

    //获取某个人所有微博详情
    public static void getWeiBo(String uid) throws IOException {
        //获取连接对象
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取微博内容表对象
        Table contentTable = connection.getTable(TableName.valueOf(Constants.CONTENT_TABLE));
        //构建Scan对象
        Scan scan = new Scan();
        //构建过滤器
        new RowFilter()
        //获取数据
        ResultScanner scanner = contentTable.getScanner(scan);
        //解析数据打印
        for (Result result : scanner) {
            for (Cell rawCell : result.rawCells()) {
                System.out.println("RK"+Bytes.toString(CellUtil.cloneRow(rawCell)));
                System.out.println("RF"+Bytes.toString(CellUtil.cloneFamily(rawCell)));
                System.out.println("CN"+Bytes.toString(CellUtil.cloneQualifier(rawCell)));
                System.out.println("Value"+Bytes.toString(CellUtil.cloneValue(rawCell)));
            }
        }


        //关闭资源
    }


}
