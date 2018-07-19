package com.sjtusummerproject.picturemicroservice.Controller;

import com.sjtusummerproject.picturemicroservice.DataModel.Domain.PictureEntity;
import com.sjtusummerproject.picturemicroservice.Service.ManagePictureService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
    public String save(@RequestParam(value = "img",required = false)String picture){
        try{
            UUID uuid = UUID.randomUUID();
            JSONObject jsonObject = JSONObject.fromObject(picture);
            byte[] img = (byte[]) JSONObject.toBean(jsonObject, byte[].class);
            System.out.print(1);
            managePictureService.save(uuid.toString(), img);
            return baseUrl+"/Picture/Query?uuid="+uuid;
        }
        catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value = "/Query")
    @ResponseBody
    public void query(@RequestParam(value = "uuid",required = false)String uuid, HttpServletResponse response) throws IOException {
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        // os write的为图片的二进制流
        os.write(managePictureService.query(uuid).getBase64());
        os.flush();
        os.close();
    }
}
