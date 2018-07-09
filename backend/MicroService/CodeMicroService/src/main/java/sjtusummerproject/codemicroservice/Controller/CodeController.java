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
        // 将uuid与answer存入Redis中，24小时有效
        redisAnswerUuidManageService.AddAnswerUuidRedis(uuid.toString(),(String)res.get("code-ans"));
        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
        return null;
    }

    @PostMapping(value="/Validate")
    @ResponseBody
    public String ValidateCode(HttpServletRequest request, HttpServletResponse response){
        System.out.println("in validate code");
        String Answer = request.getParameter("answer");
        Cookie[] cookies = request.getCookies();
        String Uuid = new String();
        for(int i=0; i<cookies.length; i++){
            if(cookies[i].getName().equals("CodeUUID")){
                Uuid = cookies[i].getValue();
            }
        }

        /* Cookie 里有Uuid */
        if(Uuid.length()!=0){
            String redisAnswer = redisAnswerUuidManageService.QueryAnswerRedis(Uuid);
            /* Redis 里有Uuid对应的answer */
            if(redisAnswer != null){
                /* 用户输入的验证码是对的 */
                if(redisAnswer.equals(Answer)){
                    return "ok";
                }
                /* 用户输入的验证码是错的 */
                else{
                    return "wrong";
                }
            }
            /* Redis 李敏啊没有Uuid对应的answer，说明验证码已经过期 */
            else{
                return "code has expired";
            }
        }
        /* Cookie 里没有Uuid */
        else{
            System.out.println("Uuid是空的");
            return "no cookie";
        }
    }
}
