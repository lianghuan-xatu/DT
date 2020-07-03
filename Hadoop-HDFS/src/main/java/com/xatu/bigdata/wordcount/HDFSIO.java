package com.xatu.bigdata.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.ssl.FileBasedKeyStoresFactory;
import org.junit.Test;
import sun.nio.ch.FileKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HDFSIO {


    /**
     * IO上传
     */
    @Test
    public void putFileToHDFS() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //获取输入流
        FileInputStream fileInputStream = new FileInputStream(new File("d:/IOTest2.txt"));

        //获取输出流
        FSDataOutputStream fsDataOutputStream = fileSystem.create(new Path("/0326/liang/IOTest.txt"));
        //流的对拷
        IOUtils.copyBytes(fileInputStream,fsDataOutputStream,conf);

        //关闭资源
        IOUtils.closeStream(fileInputStream);
        IOUtils.closeStream(fsDataOutputStream);

        fileSystem.close();
        System.out.println("over");
    }


    /**
     * 从HDFS上下载文件到本地
     */
    @Test
    public void getFileFromHDFS() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //获取输入流
        FSDataInputStream fileInputStream = fileSystem.open(new Path("/0326/liang/IOTest.txt"));
        //获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/IOTest2.txt"));

        //流的对拷
        IOUtils.copyBytes(fileInputStream,fileOutputStream,conf);

        //关闭资源
        IOUtils.closeStream(fileInputStream);
        IOUtils.closeStream(fileOutputStream);

        fileSystem.close();
        System.out.println("over");
    }

    /**
     * 下载第一块
     */
    @Test
    public void readFileSeek1() throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //获取输入流
        FSDataInputStream fileInputStream = fileSystem.open(new Path("/0326/liang/IOTest.txt"));
        //获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/IOTest2.txt"));

        //流的对拷(只拷128m)
        byte[] buf = new byte[1024];
        for(int i = 0;i < 1024 * 128;i++) {
            fileInputStream.read(buf);
            fileOutputStream.write(buf);
        }
        //关闭资源
        IOUtils.closeStream(fileInputStream);
        IOUtils.closeStream(fileOutputStream);

        fileSystem.close();
        System.out.println("over");
    }


    /**
     * 下载第二块
     */
    @Test
    public void readFileSeek2 () throws URISyntaxException, IOException, InterruptedException {
        Configuration conf = new Configuration();
        //conf.set("fs.defaultFS","hdfs://192.168.0.101:9000");
        //获取HDFS客户端对象
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://192.168.0.101:9000"),conf,"root");

        //获取输入流
        FSDataInputStream fileInputStream = fileSystem.open(new Path("/0326/liang/IOTest.txt"));
        //获取输出流
        FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/IOTest2.txt"));

        //设置指定读取的起点
        fileInputStream.seek(1024*1024*128);

        //流的对拷
        IOUtils.copyBytes(fileInputStream,fileOutputStream,conf);
        //关闭资源
        IOUtils.closeStream(fileInputStream);
        IOUtils.closeStream(fileOutputStream);

        fileSystem.close();
        System.out.println("over");
    }


}
