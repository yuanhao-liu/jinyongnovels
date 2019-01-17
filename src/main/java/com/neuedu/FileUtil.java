package com.neuedu;

import java.io.File;

/**
 * Created by bee on 3/25/17.
 */
public class FileUtil {

    public static boolean deleteDir(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            for (File f : dir.listFiles()) {
                if (f.isDirectory()) {
                    deleteDir(f.getAbsolutePath());
                } else {
                    f.delete();
                }
            }
            dir.delete();
            return true;
        } else {
            System.out.println("文件(夹)不存在!");
            return false;
        }
    }

}


