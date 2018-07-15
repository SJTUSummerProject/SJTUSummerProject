package sjtusummerproject.codemicroservice.Controller;


import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sjtusummerproject.codemicroservice.Service.GenerateCodeService;
import sjtusummerproject.codemicroservice.Service.RedisAnswerUuidManageService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value="/Code")
public class CodeController {

    @Autowired
    GenerateCodeService generateCodeService;

    @Autowired
    RedisAnswerUuidManageService redisAnswerUuidManageService;

    @GetMapping(value="/Generate")
    @ResponseBody
    public HashMap<String,Object> GenerateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* 获得hashmap： 包括 code图片 + code-answer*/
        HashMap<String,Object> res = generateCodeService.GetCode();
        ByteOutputStream byteOutputStream = new ByteOutputStream();
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 输出图象到页面
        ImageIO.write((BufferedImage)res.get("image"), "JPG", responseOutputStream);
        String answer = (String)res.get("code-ans");
        // 将answer存入Redis中，10分钟有效
        redisAnswerUuidManageService.AddAnswerUuidRedis(answer.toLowerCase(), "1");
        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
    }

    @PostMapping(value="/Validate")
    @ResponseBody
    public boolean ValidateCode(HttpServletRequest request, HttpServletResponse response){
        System.out.println("in validate code");
        String Answer = request.getParameter("answer").toLowerCase();

        /* Cookie 里有Uuid */
        String redisAnswer = redisAnswerUuidManageService.QueryAnswerRedis(Answer);
        /* Redis 里有Uuid对应的answer */
        if(redisAnswer != null) return true;
        else return false;
    }
}
