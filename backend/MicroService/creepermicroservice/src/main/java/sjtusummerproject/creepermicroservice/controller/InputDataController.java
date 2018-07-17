package sjtusummerproject.creepermicroservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sjtusummerproject.creepermicroservice.Service.ServiceImpl.InputDataServiceImpl;
import sjtusummerproject.creepermicroservice.Service.ServiceImpl.OpenFileServiceImpl;

import java.util.List;



public class InputDataController {
    @Autowired
    InputDataServiceImpl inputDataService;

    @Autowired
    OpenFileServiceImpl openFileService;

    @GetMapping(value="/InputData")
    @ResponseBody
    public String InputData(){
        List<String> list = openFileService.getfilestring();
        inputDataService.inputdata(list);
        return "ok";
    }
}
