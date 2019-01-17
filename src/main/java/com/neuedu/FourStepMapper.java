package com.neuedu;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FourStepMapper extends Mapper<LongWritable,Text,Text,Text> {
    /*输入：中年武师	0.1#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
    输出：
    何思豪 0.1*0.071
    大汉 0.1*0.071
    孙伏虎 0.1 *0.214
    尉迟连 0.1*0.071
    杨宾 0.1*0.286
    胡斐 0.1*0.286
    中年武师	#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286*/

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");//0:中年武师 1:0.1#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
        //String str=split[0]+"\t"+split[1].substring(3);//中年武师	#何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
        context.write(new Text(split[0]),new Text(split[1].substring(3)));

        String[] split1 = split[1].split("#");//0:0.1    1:何思豪:0.071;大汉:0.071;孙伏虎:0.214;尉迟连:0.071;杨宾:0.286;胡斐:0.286
        double quanzhong=Double.parseDouble(split1[0]);//0.1
        String[] split2 = split1[1].split(";");//何思豪:0.071  大汉:0.071  孙伏虎:0.214  尉迟连:0.071  杨宾:0.286  胡斐:0.286
        for(String s1:split2){
            String[] split3 = s1.split(":");
            //String s2=split3[0]+"\t"+Double.parseDouble(split3[1])*quanzhong; //何思豪 0.071*0.1
            context.write(new Text(split3[0]),new Text(String.valueOf(Double.parseDouble(split3[1])*quanzhong)));
        }

    }
}
