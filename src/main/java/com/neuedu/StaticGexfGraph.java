package com.neuedu;

import it.uniroma1.dis.wsngroup.gexf4j.core.*;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.Attribute;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeClass;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeList;
import it.uniroma1.dis.wsngroup.gexf4j.core.data.AttributeType;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.GexfImpl;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.StaxGraphWriter;
import it.uniroma1.dis.wsngroup.gexf4j.core.impl.data.AttributeListImpl;
import org.ansj.library.DicLibrary;


import java.io.*;
import java.util.*;


public class StaticGexfGraph {

    public static void main(String[] args) throws IOException {
        Gexf gexf = new GexfImpl();
        Calendar date = Calendar.getInstance();

        gexf.getMetadata()
                .setLastModified(date.getTime())
                .setCreator("Gephi.org")
                .setDescription("A Web network");
        gexf.setVisualization(true);

        Graph graph = gexf.getGraph();
        graph.setDefaultEdgeType(EdgeType.UNDIRECTED).setMode(Mode.STATIC);

        AttributeList attrList = new AttributeListImpl(AttributeClass.NODE);
        graph.getAttributeLists().add(attrList);

        Attribute attUrl = attrList.createAttribute("class", AttributeType.INTEGER, "Class");
        Attribute attIndegree = attrList.createAttribute("pageranks", AttributeType.DOUBLE, "PageRank");

        File file = new File("output13/part-r-00000");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = bufferedReader.readLine();//乔峰	0.014886361943334443#令狐冲:0.250;杨过:0.250;胡斐:0.250;郭靖:0.250
        List<String> list=new ArrayList<>();
        Map<String,Node> nodemap=new HashMap<>();
        int i=0;
        while (line != null)
        {
            String[] split = line.split("\t");//[0]乔峰   [1]0.014886361943334443#令狐冲:0.250;杨过:0.250;胡斐:0.250;郭靖:0.250
            String[] split1 = split[1].split("#");//[0]0.014886361943334443    [1]令狐冲:0.250;杨过:0.250;胡斐:0.250;郭靖:0.250
            Node node1 = graph.createNode(String.valueOf(i));
            node1
                    .setLabel(split[0])
                    .getAttributeValues()
                    .addValue(attUrl, "3")
                    .addValue(attIndegree, split1[0]);

            nodemap.put(split[0],node1);
            list.add(line);
            i++;
            line = bufferedReader.readLine();
        }
        int j=0;
        for(String line1:list){
            String[] split = line1.split("\t");//[0]乔峰   [1]0.014886361943334443#令狐冲:0.250;杨过:0.250;胡斐:0.250;郭靖:0.250
            String node1=split[0];
            String[] split1 = split[1].split("#");//[0]0.014886361943334443    [1]令狐冲:0.250;杨过:0.250;胡斐:0.250;郭靖:0.250
            String[] split2 = split1[1].split(";");//令狐冲:0.250
            for(String line3:split2){
                String[] split3 = line3.split(":");
                nodemap.get(node1).connectTo(String.valueOf(j), nodemap.get(split3[0])).setWeight(Float.parseFloat(split3[1]));
                j++;
            }
        }

        StaxGraphWriter graphWriter = new StaxGraphWriter();
        File f = new File("static_graph_sample.gexf");
        Writer out;
        try {
            out =  new FileWriter(f, false);
            graphWriter.writeToStream(gexf, out, "UTF-8");
            System.out.println(f.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

