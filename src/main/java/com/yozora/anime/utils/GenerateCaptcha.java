package com.yozora.anime.utils;



import lombok.Data;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public  class GenerateCaptcha {
    private static final int WIDTH = 120; // 图像宽度
    private static final int HEIGHT = 40; // 图像高度
    private static final int FONT_SIZE = 20; // 字体大小


    public Map<String, String> generateCaptcha() throws IOException {

        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        // 设置画布背景为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // 添加随机干扰线
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            g.setColor(getRandomColor(random));
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.draw(new Line2D.Float(x1, y1, x2, y2));
        }

        // 生成验证码
       String captchaText = "";
        for (int i = 0; i < 6; i++) { // 生成6个字符长度的验证码
            char c = (char) (random.nextInt(26) + 'a'); // 取随机小写字母
            captchaText += c;
        }

        // 绘制验证码文字，使用随机颜色
        g.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
        for (int i = 0; i < captchaText.length(); i++) {
            g.setColor(getRandomColor(random));
            g.drawString(String.valueOf(captchaText.charAt(i)), i * 20, 25);
        }

        Map<String,String> ret=new HashMap<>();
        ret.put("img",bufferedImageToBase64(image));
        ret.put("captcha",captchaText);


        // 清理资源
        g.dispose();
        return ret;
    }

    private static Color getRandomColor(Random random) {
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }

    private static String bufferedImageToBase64(BufferedImage image) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", outputStream);
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        outputStream.close();
        return "data:image/png;base64,"+base64;
    }

}
