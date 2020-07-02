package com.xatu.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.ssl.FileBasedKeyStoresFactory;
import org.junit.Test;
import sun.nio.ch.FileKey;

import java.io.File;
import java.io.FileInputStream;
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

}
