import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello World!");
        List<String> citylist = openfile();
        dirhasfile("1");
    }

    public static List<String> openfile(){
        File file = new File("/Users/sky/Desktop/软件工程导论/SJTUSummerProject/SJTUSummerProject/backend/MicroService/TicketMicroService/city.txt");
        BufferedReader reader = null;
        List<String> resList = new LinkedList<String>();
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.replace(',',' ');
                tempString = tempString.replace('\'',' ');
                tempString = tempString.trim();
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                resList.add(tempString);
                line++;
            }
            reader.close();
            System.out.println("遍历list");
            for(String tmpString : resList){
                System.out.println(tmpString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return resList;
    }

    public static boolean dirhasfile(String filename){
        String dirName = "/Users/sky/Desktop/软件工程导论/SJTUSummerProject/SJTUSummerProject/backend/MicroService/TicketMicroService/src/main/resources/演唱会信息/有";
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        File dirFile = new File(dirName);
        //如果dir对应的文件不存在，或者不是一个文件夹则退出
        if (!dirFile.exists() || (!dirFile.isDirectory())) {
            System.out.println("List失败！找不到目录：" + dirName);
        }

        //列出文件夹下所有的文件
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                System.out.println(files[i].getAbsolutePath() + " 是文件！");
                File infofile = new File(files[i].getAbsolutePath());
                BufferedReader reader = null;
                try {
                    System.out.println("以行为单位读取文件内容，一次读一整行：");
                    reader = new BufferedReader(new FileReader(infofile));
                    String tempString = null;
                    int line = 1;
                    // 一次读入一行，直到读入null为文件结束
                    while ((tempString = reader.readLine()) != null) {
                        tempString = tempString.replace(',',' ');
                        tempString = tempString.replace('\'',' ');
                        tempString = tempString.trim();
                        // 显示行号
                        System.out.println("line " + line + ": " + tempString);

                        line++;
                    }
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e1) {
                        }
                    }
                }

            } else if (files[i].isDirectory()) {
                System.out.println(files[i].getAbsolutePath() + " 是目录！");
            }
        }
        return false;
    }
}
