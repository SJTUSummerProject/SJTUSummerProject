package sjtusummerproject.ticketmicroservice.Utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

/**
 * 裁剪、缩放图片工具类
 */
public class ImgUtils {

    /**
     * 缩放图片方法
     */
    public ByteArrayOutputStream scale(MultipartFile image) {
        try {
            int height = 400;
            int width = 300;
            double ratio = 0.0; // 缩放比例
            Image srcImg = ImageIO.read(image.getInputStream());
            BufferedImage buffImg = null;
            buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            buffImg.getGraphics().drawImage(
                    srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                    0, null);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write((BufferedImage) buffImg, "jpg", imOut);
            return bs;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * 裁剪图片方法
     * @param bufferedImage 图像源
     * @param startX 裁剪开始x坐标
     * @param startY 裁剪开始y坐标
     * @param endX 裁剪结束x坐标
     * @param endY 裁剪结束y坐标
     * @return
     */
    public static BufferedImage cropImage(BufferedImage bufferedImage, int startX, int startY, int endX, int endY) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        if (startX == -1) {
            startX = 0;
        }
        if (startY == -1) {
            startY = 0;
        }
        if (endX == -1) {
            endX = width - 1;
        }
        if (endY == -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX - startX, endY - startY, 4);
        for (int x = startX; x < endX; ++x) {
            for (int y = startY; y < endY; ++y) {
                int rgb = bufferedImage.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }
}
//
//        import java.awt.image.BufferedImage;
//        import java.io.File;
//        import java.io.IOException;
//        import javax.imageio.ImageIO;
//        import com.etoak.util.ImgUtils;
//
//public class Test {
//    public static void main(String[] args) throws IOException {
//        String path="C:/1.jpg";    //输入图片  测试要在C盘放一张图片1.jpg
//        ImgUtils.scale("C:/1.jpg","C:/yasuo.jpg", 180, 240, true);//等比例缩放  输出缩放图片
//
//        File newfile=new File("C:/yasuo.jpg");
//        BufferedImage bufferedimage=ImageIO.read(newfile);
//        int width = bufferedimage.getWidth();
//        int height = bufferedimage.getHeight();
//        //目标将图片裁剪成 宽240，高160
//        if (width > 240) {
//            /*开始x坐标              开始y坐标             结束x坐标                     结束y坐标*/
//            bufferedimage=ImgUtils.cropImage(bufferedimage,(int) ((width - 240) / 2),0,(int) (width - (width-240) / 2),(int) (height)
//            );
//            if (height > 160) {
//                bufferedimage=ImgUtils.cropImage(bufferedimage,0,(int) ((height - 160) / 2),240,(int) (height - (height - 160) / 2)
//                );
//            }
//        }else{
//            if (height > 160) {
//                bufferedimage=ImgUtils.cropImage(bufferedimage,0,(int) ((height - 160) / 2),(int) (width),(int) (height - (height - 160) / 2)
//                );
//            }
//        }
//        ImageIO.write(bufferedimage, "jpg", new File("C:/caijian.jpg"));    //输出裁剪图片
//    }
//}
