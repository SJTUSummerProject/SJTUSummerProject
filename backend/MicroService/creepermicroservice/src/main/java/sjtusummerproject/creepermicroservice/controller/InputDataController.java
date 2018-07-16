package sjtusummerproject.creepermicroservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class InputDataController {
    @GetMapping(value="/InputData")
    @ResponseBody
    public String InputData(){
        List<String> list = openFileService.getfilestring();
        inputDataService.inputdata(list);
        return "ok";
    }
}
