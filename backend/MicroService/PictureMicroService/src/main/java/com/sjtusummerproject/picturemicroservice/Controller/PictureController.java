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
import sun.misc.BASE64Decoder;

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

    @RequestMapping(value = "/Query")
    @ResponseBody
    public void query(@RequestParam(value = "uuid",required = false)String uuid, HttpServletResponse response) throws IOException {
        byte[] img = managePictureService.query(uuid).getBase64();
        response.setHeader("Content-Type","image/jpeg");
        OutputStream outputStream= response.getOutputStream();
        outputStream.write(img);
        outputStream.flush();
        outputStream.close();
    }
}
