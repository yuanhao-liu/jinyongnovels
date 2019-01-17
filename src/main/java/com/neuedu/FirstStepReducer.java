package com.neuedu;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class FirstStepReducer extends Reducer<LongWritable, Text, Text, NullWritable> {
    //<偏移量，List（‘只有一个元素’）>
    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        Text strNameLine = values.iterator().next();
        if(!strNameLine.toString().equals(""))
        {
            context.write(strNameLine,NullWritable.get());
        }
    }
}

