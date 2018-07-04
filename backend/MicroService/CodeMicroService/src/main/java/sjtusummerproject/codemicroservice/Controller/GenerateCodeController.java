package sjtusummerproject.codemicroservice.Controller;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

@RestController
@RequestMapping(value="/Code")
public class GenerateCodeController {

    @GetMapping(value="/users")
    @ResponseBody
    public JSONArray findAll(){
        return JSONArray.fromObject();
    }
}
