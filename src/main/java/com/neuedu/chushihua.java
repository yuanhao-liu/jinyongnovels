package com.neuedu;

import it.uniroma1.dis.wsngroup.gexf4j.core.Node;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chushihua {
    public static void main(String[] args) throws IOException {
        File file = new File("output13/part-r-00000");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        File file1 = new File("output13/part-r-00001");
        FileWriter fileWriter = new FileWriter(file1);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String line = bufferedReader.readLine();//丁同	0.008290701427018436#李三:0.250;李文秀:0.500;老头子:0.125;霍元龙:0.125
        Integer i=1;
        while (line != null)
        {
            line=i.toString()+"#"+line;
            i++;
            bufferedWriter.write(line);
            bufferedWriter.newLine();
            line = bufferedReader.readLine();
        }

    }
}
