package com.neuedu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ThirdStepMapper extends Mapper<LongWritable,Text,Text,Text> {
    /*<中年武师,何思豪>	1
      <中年武师,大汉>	1
      <中年武师,孙伏虎>	3
      <中年武师,尉迟连>	1
      <中年武师,杨宾>	4
      <中年武师,胡斐>	4*/
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\\t+");//[<中年武师,何思豪>	,1]
        String num = split[1];//1
        String name = split[0].substring(1, split[0].length() - 1);//中年武师,何思豪
        String[] split1 = name.split(",");
        String name1=split1[0];//中年武师
        String name2=split1[1];//何思豪
        context.write(new Text(name1),new Text(name2+","+num));
    }
}
