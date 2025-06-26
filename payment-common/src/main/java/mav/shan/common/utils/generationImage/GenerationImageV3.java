package mav.shan.common.utils.generationImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class GenerationImageV3 {

    // 创建个人信息图片
    public static void createPersonalImage(String name, String company, String outputPath) throws IOException, WriterException {
        int width = 600;
        int height = 900;

        // 创建空白图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        // 设置抗锯齿
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 填充背景色
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 绘制边框
        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, width - 1, height - 1);

        // 绘制文字
        g2d.setFont(new Font("微软雅黑", Font.BOLD, 20));
        g2d.setColor(Color.BLACK);
        String title = "京吉保单";
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int titleWidth = fontMetrics.stringWidth(title);
        int x = (width - titleWidth) / 2; // 居中 X 坐标
        int y = 60; // Y 坐标保持不变
        g2d.drawString(title, x, y);
        g2d.drawLine(40, 70, width - 40, 70);

        g2d.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        g2d.drawString("小车单号: " + "41152323433242", 50, 90);
        g2d.drawString("车辆名称: " + "梅德塞思5.0", 50, 130);
        g2d.drawString("车辆价值: " + "20000万", 50, 170);
        g2d.drawString("车架号: " + "6666", 50, 210);
        g2d.drawString("始发城市: " + "地球", 50, 250);
        g2d.drawString("目的城市: " + "火星", 50, 290);
        g2d.drawString("录单时间: " + LocalDate.now(), 50, 330);
        g2d.drawString("录单人: " + "王五", 300, 350);
        // 绘制印章图片
        BufferedImage seal = ImageIO.read(GenerationImageV3.class.getResource("/images/OIP-C.png"));
        g2d.drawImage(seal, 250, 200, null);
        g2d.dispose();
        ImageIO.write(image, "PNG", new File(outputPath));
        System.out.println("图片已生成: " + outputPath);
    }

    public static void main(String[] args) {
        try {
            createPersonalImage("张三", "阿里巴巴集团", "personal_card.png");
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }
}
