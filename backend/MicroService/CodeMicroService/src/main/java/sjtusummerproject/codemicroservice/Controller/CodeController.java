package sjtusummerproject.codemicroservice.Controller;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import sjtusummerproject.codemicroservice.Service.GenerateCodeService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value="/Code")
public class CodeController {

    @Autowired
    GenerateCodeService generateCodeService;

    @GetMapping(value="/Generate")
    @ResponseBody
    public HashMap<String,Object> GenerateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in generate code");
        UUID uuid = UUID.randomUUID();
        Cookie cookie = new Cookie("CodeUUID",uuid.toString());
        response.addCookie(cookie);
        /* 获得hashmap： 包括 code图片 + code-answer*/
        HashMap<String,Object> res = generateCodeService.GetCode();
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 输出图象到页面
        ImageIO.write((BufferedImage)res.get("image"), "JPG", responseOutputStream);
        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
    }

    @PostMapping(value="/Validate")
    @ResponseBody
    public String ValidateCode(HttpServletRequest request, HttpServletResponse response){
        System.out.println("in validate code");
        return null;
    }
}
