package com.xatu.bigdata;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSClient {

    public static void main(String args[]) throws IOException, URISyntaxException, InterruptedException {
        
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");
        
        //在HDFS上出创建路径
        fileSystem.mkdirs(new Path("/0326/liang"));
        
        //关闭资源
        fileSystem.close();
        System.out.println("over");

    }
    @Test
    public void testCopyFromLocalFIle() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //上传
        fileSystem.copyFromLocalFile(new Path("d:/IOTest.txt"),new Path("/0326/liang/IOTest.txt"));

        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 测试配置副本数量优先级
     * @throws URISyntaxException
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testCopyFromLocalFIle2() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("dfs.replication","2");
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //上传
        fileSystem.copyFromLocalFile(new Path("d:/IOTest.txt"),new Path("/0326/liang/IOTest.txt"));

        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 文件下载
     */
    @Test
    public void testCopyToLocalFIle() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //上传
        fileSystem.copyToLocalFile(new Path("/0326/liang/IOTest.txt"),new Path("d:/IOTest.txt"));
        //fileSystem.copyToLocalFile(false,new Path("/0326/liang/IOTest.txt"),new Path("d:/IOTest.txt"),true);

        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 文件删除
     */
    @Test
    public void testDelete() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //删除
        fileSystem.delete(new Path("/0326/liang/IOTest.txt"),true);
        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 文件更名
     */
    @Test
    public void testRename() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //文件更名
        fileSystem.rename(new Path("/0326/liang/IOTest.txt"),new Path("/0326/liang/IOTest2.txt"));
        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 查看文件详情
     */
    @Test
    public void testListFiles() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //查看文件详情
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/0326/liang/"), true);
        while(locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            //文件名称
            System.out.println(next.getPath().getName());
            //文件权限
            System.out.println(next.getPermission());
            //文件长度
            System.out.println(next.getLen());
            //块信息
            BlockLocation[] blockLocations = next.getBlockLocations();
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                for (String host : hosts) {
                    System.out.println(host);
                }
            }
            System.out.println("-----------------------------------");
        }
        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }


    /**
     * 判断文件还是文件名
     */
    @Test
    public void tetListStatus() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //判断文件还是文件名
        FileStatus[] fileStatuses = fileSystem.listStatus(new Path("/0326/liang/"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()){
                System.out.println("文件");
                System.out.println(fileStatus.getPath().getName());
            }else{
                System.out.println("文件夹");
                System.out.println(fileStatus.getPath().getName());
            }

        }
        //关闭资源
        fileSystem.close();
        System.out.println("over");
    }

}
