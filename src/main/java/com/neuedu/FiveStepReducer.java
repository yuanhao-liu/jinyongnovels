package com.neuedu;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiveStepReducer extends Reducer<Text, Text, Text, Text> {
//<丁同,List(@权重,12#xxx,13#yy,300#李文秀,#李三:0.250;李文秀:0.500;老头子:0.125;霍元龙:0.125)>
    Map<String,String> mapGlobalName2Label = new HashMap<>();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        Map<String,Double> mapName2Weight = new HashMap<>();
        Map<String,String> mapName2Label = new HashMap<>();

        String peopleRelation = "";//李三:0.250;李文秀:0.500;老头子:0.125;霍元龙:0.125
        String peoplePR = "";//权重
        List<String> labelPeopleNameList = new ArrayList<>();
        for(Text text : values)
        {
            String content = text.toString();
            if(content.startsWith("#"))
            {
                peopleRelation = content.replace("#","");
            }
            else if(content.startsWith("@"))
            {
                peoplePR = content.replace("@","");
            }
            else
            {
                //List[100#尹克西,250#完颜萍]
                labelPeopleNameList.add(content);
            }
        }

        String[] arrNameWeight = peopleRelation.split(";");
        for(String string : arrNameWeight)
        {
            //小龙女:0.018648018648018648
            String[] split = string.split(":");
            mapName2Weight.put(split[0],Double.parseDouble(split[1]));
        }

        Map<String,Double> mapLabel2Weight =  new HashMap<>();
        for(String string : labelPeopleNameList)
        {
            //100#尹克西
            String[] split = string.split("#");
            mapName2Label.put(split[1],split[0]);

//            7  zhangsan 0.3
//            7  wangwu   0.4
            String label = split[0];
            if(mapGlobalName2Label.containsKey(split[1]))
            {
                label = mapGlobalName2Label.get(split[1]);
            }

            if(!mapLabel2Weight.containsKey(label))
            {
                mapLabel2Weight.put(label,mapName2Weight.get(split[1]));
            }
            else
            {
                Double weight = mapLabel2Weight.get(label);
//                System.out.println(peopleRelation);
//                System.out.println(mapName2Weight);
//                System.out.println(labelPeopleNameList);

                if(mapName2Weight.containsKey(split[1]))
                    weight += mapName2Weight.get(split[1]);
                mapLabel2Weight.put(label,weight);
            }



        }

        //比较主要人物周围的人，找到关系最密切的那个，将主要人物的标签label更新成那个人的label
        double maxWeight = 0;
        String newLabel = "";
        for(Map.Entry<String,Double> entry : mapLabel2Weight.entrySet())
        {
            double weight = entry.getValue();
            if(maxWeight < weight)
            {
                maxWeight = weight;
                newLabel = entry.getKey();
            }
        }

        mapGlobalName2Label.put(key.toString(),newLabel);

        Text keyWrite = new Text(newLabel + "#" + key.toString());
        Text valueWrite = new Text(peoplePR + "#" + peopleRelation);
        context.write(keyWrite,valueWrite);
    }
}

