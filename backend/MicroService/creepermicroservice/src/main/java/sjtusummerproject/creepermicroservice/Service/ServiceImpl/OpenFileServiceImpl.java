package sjtusummerproject.creepermicroservice.Service.ServiceImpl;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Service
public class OpenFileServiceImpl {
    public List<String> getfilestring(){
        BufferedReader reader = null;
        List<String> resList = new LinkedList<String>();
        try {
            File file = org.springframework.util.ResourceUtils.getFile("classpath:city.txt");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                tempString = tempString.replace(',',' ');
                tempString = tempString.replace('\'',' ');
                tempString = tempString.trim();
                resList.add(tempString);
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
        return resList;
    }
}
