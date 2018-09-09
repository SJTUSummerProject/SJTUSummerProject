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
    public InputStream scale(MultipartFile image) {
        try {
            int height = 400;
            int width = 300;
            double ratio = 0.0; // 缩放比例
            BufferedImage bi = ImageIO.read(image.getInputStream());

            Image itemp = bi.getScaledInstance(300, 400, bi.SCALE_SMOOTH);//bi.SCALE_SMOOTH  选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
            // 计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                double   ratioHeight = (new Integer(height)).doubleValue()/ bi.getHeight();
                double   ratioWhidth = (new Integer(width)).doubleValue()/ bi.getWidth();
                if(ratioHeight>ratioWhidth){
                    ratio= 1/ratioHeight;//ratioHeight;
                }else{
                    ratio= 1/ratioWhidth;//ratioWhidth;
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform//仿射转换
                        .getScaleInstance(ratio, ratio), null);//返回表示剪切变换的变换
                itemp = op.filter(bi, null);//转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。
            }
            //补白
            BufferedImage bbImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);//构造一个类型为预定义图像类型之一的 BufferedImage。
            Graphics2D g = bbImage.createGraphics();//创建一个 Graphics2D，可以将它绘制到此 BufferedImage 中。
            g.setColor(Color.white);//控制颜色
            g.fillRect(0, 0, width, height);// 使用 Graphics2D 上下文的设置，填充 Shape 的内部区域。
            if (width == itemp.getWidth(null))
                g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                        itemp.getWidth(null), itemp.getHeight(null),
                        Color.white, null);
            else
                g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                        itemp.getWidth(null), itemp.getHeight(null),
                        Color.white, null);
            g.dispose();
            itemp = bbImage;

            //输出
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write((BufferedImage)itemp, "jpg", imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());
            return is;
            //ImageIO.write((BufferedImage) itemp, "JPEG", new File(result));      //输出压缩图片
        } catch (IOException e) {
            e.printStackTrace();
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
