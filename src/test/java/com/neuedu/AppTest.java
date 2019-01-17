package com.neuedu;

import static org.junit.Assert.assertTrue;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.DicLibrary;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.junit.Test;

import java.io.*;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws IOException {
        File file = new File("C:\\Users\\Administrator\\Desktop\\jinyong_all_person.txt");
        InputStream in=new FileInputStream(file);
        byte[] b=new byte[1000000];
        in.read(b);
        in.close();
        String s = new String(b);
        String substring = s.substring(1);
        String[] split = substring.split("\r\n");
        for(String name:split){
            DicLibrary.insert(DicLibrary.DEFAULT, name, "n", 100000);
        }

        String str="前面三辆柯镇恶囚车中分别监禁的是三个男子，都作书生打扮，一个是白发老者，两个是中年人。后面四辆囚车中坐的是女子，最后一辆囚车中是个少妇，怀中抱着个女婴，女婴啼哭不休。她母亲温言相呵，女婴只是大哭。囚车旁一清兵恼了，伸腿在车上踢了一脚，喝道：“再哭，再哭，老子踢死你！”那女婴一惊，哭得更加响了。";
        Result result = NlpAnalysis.parse(str);
        List<Term> termList=result.getTerms();
        for(Term term:termList){
            System.out.println(term.getName()+":"+term.getNatureStr());
        }

    }
}
