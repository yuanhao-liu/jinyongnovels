package com.neuedu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class SecondStepReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    //{<胡斐 ,商老太>,List(1,1)}
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        for(IntWritable num:values){
            sum+=num.get();
        }
        context.write(key,new IntWritable(sum));
    }
}

