package com.xatu.video;


import java.io.PrintStream;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ETLDriver
        implements Tool
{
    private Configuration configuration;

    public int run(String[] args)
            throws Exception
    {
        Job job = Job.getInstance(this.configuration);

        job.setJarByClass(ETLDriver.class);

        job.setMapperClass(ETLMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.setInputPaths(job, new Path[] { new Path(args[0]) });
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        boolean result = job.waitForCompletion(true);

        return result ? 0 : 1;
    }

    public void setConf(Configuration conf)
    {
        this.configuration = conf;
    }

    public Configuration getConf()
    {
        return this.configuration;
    }

    public static void main(String[] args)
    {
        Configuration configuration = new Configuration();
        try
        {
            int run = ToolRunner.run(configuration, new ETLDriver(), args);

            System.out.println(run);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
