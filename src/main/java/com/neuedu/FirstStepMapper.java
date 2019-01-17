package com.neuedu;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FirstStepMapper extends Mapper<LongWritable,Text,LongWritable,Text> {
    Set<String> nameset=new HashSet<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        File file = new File("person/jinyong_all_person.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();

        while (line != null)
        {
            DicLibrary.insert(DicLibrary.DEFAULT, line);
            nameset.add(line);
            line = bufferedReader.readLine();
        }

        /*File file = new File("person/jinyong_all_person.txt");
        InputStream in=new FileInputStream(file);
        byte[] b=new byte[1000000];
        in.read(b);
        in.close();
        String s = new String(b);
        s = s.substring(1);
        String[] split = s.split("\r\n");
        for(String name:split){
            namelist.add(name);
            DicLibrary.insert(DicLibrary.DEFAULT, name);
        }*/

    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String s1 = value.toString();
//        s1 = s1.replace("道", "");

        Result result = DicAnalysis.parse(s1);
        List<Term> termList=result.getTerms();
        StringBuilder stringBuilder = new StringBuilder();
        for(Term term:termList){
            if(nameset.contains(term.getName())){
                stringBuilder.append(term.getName()+"\t");
            }
        }
        context.write(key,new Text(stringBuilder.toString()));
    }
}
