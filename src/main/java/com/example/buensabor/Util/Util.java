package com.example.buensabor.Util;

import java.io.File;

public class Util {

    public static void deleteTemp(){
        String folderPath = new File("").getAbsolutePath() + "/src/main/resources/Temp";
        File folder = new File(folderPath);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();

            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        }
    }
}
