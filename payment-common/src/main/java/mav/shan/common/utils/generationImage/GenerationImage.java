package mav.shan.common.utils.generationImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class GenerationImage {
    // 生成二维码
    private static BufferedImage generateQRCode(String text, int size) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

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

        // 获取字体度量信息
        FontMetrics fm = g2d.getFontMetrics();

        // 姓名 && 公司名称
        String nameText = "姓名: " + name;
        String companyText = "公司: " + company;
        g2d.drawString(nameText, 50, 60);
        g2d.drawString(companyText, width - 50 - fm.stringWidth(companyText), 60);
        g2d.drawLine(50, 70, width - 50, 70);

        // 姓名 && 公司名称
        g2d.drawString(nameText, 50, 110);
        g2d.drawString(companyText, width - 50 - fm.stringWidth(companyText), 110);
        g2d.drawLine(50, 120, width - 50, 120);

        // 姓名 && 公司名称
        g2d.drawString(nameText, 50, 160);
        g2d.drawString(companyText, width - 50 - fm.stringWidth(companyText), 160);
        g2d.drawLine(50, 170, width - 50, 170);

        BufferedImage seal = ImageIO.read(GenerationImage.class.getResource("/images/OIP-C.png"));
        // 设置印章绘制位置（比如右上角，y=30）
        int sealX = width - seal.getWidth() - 50; // 距离右边 50px
        int sealY = 30; // y 坐标
        // 绘制印章图片
        g2d.drawImage(seal, sealX, sealY, null);

//        // 生成二维码
//        BufferedImage qrCode = generateQRCode("https://www.example.com/profile/" + name, 150);
//        // 绘制二维码到图片上
//        g2d.drawImage(qrCode, 125, 150, null);

        g2d.dispose();

        // 保存图片
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        ImageIO.write(image, "PNG", outputStream);
//        byte[] imageBytes = outputStream.toByteArray();
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
