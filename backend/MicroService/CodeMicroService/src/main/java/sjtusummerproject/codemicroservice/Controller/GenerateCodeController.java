package sjtusummerproject.codemicroservice.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import net.sf.json.JSONObject;
import net.sf.json.JSONArray;
import sjtusummerproject.codemicroservice.Service.GenerateCodeService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping(value="/Code")
public class GenerateCodeController {

    @Autowired
    GenerateCodeService generateCodeService;

    @GetMapping(value="/Generate")
    @ResponseBody
    public HashMap<String, Object> GenerateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("in generate code controller");
        HashMap<String,Object> res = generateCodeService.GetCode();
        System.out.println("after generate code controller");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        // 输出图象到页面
        ImageIO.write((BufferedImage)res.get("image"), "JPG", responseOutputStream);

        // 以下关闭输入流！
        responseOutputStream.flush();
        responseOutputStream.close();
        return res;
    }
}
