import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestAPI {
    public static Configuration conf;

    static {
        //使用HBaseConfiguration 的单例方法实例化
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "192.168.0.102,192.168.0.101,192.168.0.103");
        conf.set("hbase.zookeeper.property.clientPort", "2181");
    }

    public static boolean isTableExist(String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        //在 HBase 中管理、访问表需要先创建 HBaseAdmin 对象
        HBaseAdmin admin = new HBaseAdmin(conf);
        return admin.tableExists(tableName);
    }

    public static void createTable(String tableName, String... columnFamily) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        //判断表是否存
        if (isTableExist(tableName)) {
            System.out.println("表" + tableName + "已存在");
            //System.exit(0);
        } else {
            //创建表属性对象,表名需要转字节 HTableDescriptor
            HTableDescriptor descriptor = new HTableDescriptor(TableName.valueOf(tableName));
            //创建多个列族
            for (String cf : columnFamily) {
                descriptor.addFamily(new HColumnDescriptor(cf));
            }
            //根据对表的配置，创建表
            admin.createTable(descriptor);
            System.out.println("表" + tableName + "创建成功！");
        }
    }

    public static void dropTable(String tableName) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
        HBaseAdmin admin = new HBaseAdmin(conf);
        if (isTableExist(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
            System.out.println("表" + tableName + "删除成功！");
        } else {
            System.out.println("表" + tableName + "不存在！");
        }
    }

    public static void addRowData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        //创建HTable 对象
        HTable hTable = new HTable(conf, tableName);
        //向表中插入数据
        Put put = new Put(Bytes.toBytes(rowKey));
        //向 Put 对象中组装数据
        put.add(Bytes.toBytes(columnFamily), Bytes.toBytes(column),
                Bytes.toBytes(value));
        hTable.put(put);
        hTable.close();
        System.out.println("插入数据成功");
    }

    public static void deleteMultiRow(String tableName, String... rows) throws IOException {
        HTable hTable = new HTable(conf, tableName);
        List<Delete> deleteList = new ArrayList<Delete>();
        for (String row : rows) {
            Delete delete = new Delete(Bytes.toBytes(row));
            deleteList.add(delete);
        }
        hTable.delete(deleteList);
        hTable.close();
    }

    public static void getAllRows(String tableName) throws IOException {
        HTable hTable = new HTable(conf, tableName); //得到用于扫描 region 的对象
        Scan scan = new Scan(); //使用HTable 得到resultcanner 实现类的对象
        ResultScanner resultScanner = hTable.getScanner(scan);
        for(Result result : resultScanner){
            Cell[] cells = result.rawCells();
            for(Cell cell : cells){ //得到 rowkey
         System.out.println(" 行键 :"
        +Bytes.toString(CellUtil.cloneRow(cell))); //得到列族
        System.out.println(" 列族 "+
        Bytes.toString(CellUtil.cloneFamily(cell)));
        System.out.println(" 列 " +
                        Bytes.toString(CellUtil.cloneQualifier(cell))); System.out.println(" 值"+
                Bytes.toString(CellUtil.cloneValue(cell)));
    }
}
}

    public static void getRow(String tableName, String rowKey) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        //get.setMaxVersions();//显示所有版本
        //get.setTimeStamp();//显示指定时间戳的版本
        Result result = table.get(get);
        for(Cell cell : result.rawCells()) {
            System.out.println(" 行键 :" +
                    Bytes.toString(result.getRow()));
            System.out.println(" 列族 " +
                    Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.println(" 列" +
                    Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println(" 值" +
                    Bytes.toString(CellUtil.cloneValue(cell)));
            System.out.println("时间戳:" + cell.getTimestamp());
        }}

    public static void getRowQualifier(String tableName, String rowKey, String family, String qualifier) throws IOException{
        HTable table = new HTable(conf, tableName);
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        Result result = table.get(get);
        for(Cell cell : result.rawCells()){
            System.out.println(" 行键 :"
            +
            Bytes.toString(result.getRow())); System.out.println(" 列族 "+
            Bytes.toString(CellUtil.cloneFamily(cell))); System.out.println(" 列"+
            Bytes.toString(CellUtil.cloneQualifier(cell))); System.out.println(" 值"+
            Bytes.toString(CellUtil.cloneValue(cell))); } 
    }

public static void main(String args[]){

        }

        }
