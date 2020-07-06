package com.xatu.bigdata.inputformat;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

public class WholeRecordReader extends RecordReader<Text, BytesWritable> {


    FileSplit fileSplit;    Configuration configuration;

    BytesWritable v = new BytesWritable();
    boolean isProgress = true;
    Text k = new Text();

    public void initialize(InputSplit inputSplit, TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
        //初始化
        this.fileSplit = (FileSplit) inputSplit;
        configuration = taskAttemptContext.getConfiguration();

    }

    public boolean nextKeyValue() throws IOException, InterruptedException {
        //核心业务逻辑
        if(isProgress == true) {
            //获取fs对象
            Path path = fileSplit.getPath();
            FileSystem fileSystem = path.getFileSystem(configuration);

            //获取输入流
            FSDataInputStream fsDataInputStream = fileSystem.open(path);
            byte[] buf = new byte[(int)fileSplit.getLength()];
            //拷贝
            IOUtils.readFully(fsDataInputStream,buf,0,buf.length);
            //封装v
            v.set(buf,0,buf.length);
            //封装k
            k.set(path.toString());
            //关闭资源
            IOUtils.closeStream(fsDataInputStream);

            isProgress = false;
            return true;
        }

        return true;
    }

    public Text getCurrentKey() throws IOException, InterruptedException {

        return k;
    }

    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return v;
    }

    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    public void close() throws IOException {

    }
}
