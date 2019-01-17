package com.neuedu;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SecondStepMapper extends Mapper<LongWritable,Text,Text,IntWritable> {
    //胡斐	胡斐	商老太	平阿四	商宝震	胡斐
    //去重后：胡斐  商老太 平阿四 商宝震
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] split = value.toString().split("\t");
        Set<String> nameset=new HashSet<>();
        for(String name:split){
            nameset.add(name);
        }
        for(String name:nameset){
            for(String name1:nameset){
                if(!name.equals(name1)){
                    context.write(new Text("<"+name+","+name1+">"),new IntWritable(1));
                }
            }
        }
        /*String[] strs=new String[nameset.size()];
        int index=0;
        for(String i:nameset){
            strs[index]=i;
            index++;
        }
        //strs=(胡斐  商老太 平阿四 商宝震)
        for (int i = 0; i <strs.length ; i++) {
            for (int j = 0; j <strs.length ; j++) {
                if(i != j){
                    context.write(new Text("<"+strs[i]+","+strs[j]+">"),new IntWritable(1));
                }
            }
        }*/
    }
}
