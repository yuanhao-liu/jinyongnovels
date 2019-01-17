package com.neuedu;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FourStepReducer extends Reducer<Text, Text, Text, Text> {
    //{中年武师,List<0.1*0.2,0.1*005,,,,,#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286>}
    //输出：中年武师 新权重#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        double sum=0;
        String str="";
        for(Text text:values){
            if(!text.toString().startsWith("#")){
                sum+=Double.parseDouble(text.toString());
            }
            if(text.toString().startsWith("#")){
                str=text.toString();//#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
            }
        }
       String str1= String.valueOf(sum)+str;
        context.write(key,new Text(str1));

    }
}

