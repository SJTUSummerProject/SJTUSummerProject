package com.sjtusummerproject.picturemicroservice.Controller;

import com.sjtusummerproject.picturemicroservice.DataModel.Domain.PictureEntity;
import com.sjtusummerproject.picturemicroservice.Service.ManagePictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping(value = "/Picture")
public class PictureController {
    @Autowired
    ManagePictureService managePictureService;

    @Value("${pictureservice.url}")
    String baseUrl;

    @RequestMapping(value = "/Save")
    @ResponseBody
    public String save(@RequestParam(value = "uuid",required = false)UUID uuid,@RequestParam(value = "img",required = false)byte[] picture){
        managePictureService.save(uuid, picture);
        return baseUrl+"/Query?uuid="+uuid;
    }

    @RequestMapping(value = "/Query")
    @ResponseBody
    public String query(@RequestParam(value = "uuid",required = false)UUID uuid, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        // os write的为图片的二进制流
        os.write(managePictureService.query(uuid).getBase64());
        os.flush();
        os.close();
        return "success";
    }
}
