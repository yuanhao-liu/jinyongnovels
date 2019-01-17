package com.neuedu;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class ThirdStepReducer extends Reducer<Text, Text, Text, Text> {
    //{中年武师,List<何思豪,1 大汉,1 孙伏虎,3 尉迟连,1 杨宾,4 胡斐,4>}
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sum=0;
        String str="";
        Map<String,Double> map=new TreeMap<>();
        for(Text i:values){
            String[] split = i.toString().split(",");
            sum+=Integer.parseInt(split[1]);
            map.put(split[0],Double.parseDouble(split[1]));
        }
        for(Map.Entry<String,Double> entry:map.entrySet()){
            map.put(entry.getKey(),entry.getValue()/sum);
        }
        for(Map.Entry<String,Double> entry:map.entrySet()){
            str+=entry.getKey()+":"+String.format("%.3f", entry.getValue())+";";
        }
        str=str.substring(0,str.length()-1);
        context.write(key,new Text(str));
    }
}

