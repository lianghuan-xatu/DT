package com.xatu.weibo.utils;

import com.xatu.weibo.constants.Constants;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class HBaseUtil {

    //创建命名空间
    private static void createNameSpace(String nameSpace) throws IOException {

        //获取连接
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取Admin对象
        Admin admin = connection.getAdmin();
        //构建名称空间描述器
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(nameSpace).build();
        //创建命名空间
        admin.createNamespace(namespaceDescriptor);
        //关闭资源
        admin.close();
    }

    //判断表是否存在
    private static boolean isTableExist(String tableName) throws IOException {
        //获取连接
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取Admin对象
        Admin admin = connection.getAdmin();
        try {
            boolean tableExists = admin.tableExists(TableName.valueOf(tableName));
            return tableExists;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //关闭资源
            admin.close();
        }
        //返回结果
        return false;
    }

    //创建表
    private static void createTable(String tableName,int versions,String... cfs) throws IOException {

        //判断是否传入列族信息
        if(cfs.length < 0) {
            System.out.println("请添加列族信息");
            return;
        }

        //判断表是否存在
        boolean tableExist = isTableExist(tableName);
        if(tableExist){
            System.out.println("该表已存在");
            return;
        }

        //获取Connection连接
        Connection connection = ConnectionFactory.createConnection(Constants.CONFIGURATION);
        //获取Admin对象
        Admin admin = connection.getAdmin();
        //创建表描述器
        HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
        //循环添加列族信息

        for (String cf : cfs) {
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(cf);
            //设置版本
            columnDescriptor.setMaxVersions(versions);
            descriptor.addFamily(columnDescriptor);
        }

        //创建表操作
        admin.createTable(descriptor);
        //关闭资源
        admin.close();

    }

}
