package mav.shan.common.utils.generationImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Watermark {
    public static void addWatermark(String imagePath, String watermarkText, String outputImagePath) throws IOException, IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        Graphics2D g2d = image.createGraphics();

        g2d.setFont(new Font("微软雅黑", Font.BOLD, 100));
        g2d.setColor(Color.GREEN);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        // 获取字体度量信息
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(watermarkText);
        int textHeight = fontMetrics.getHeight();

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        // 左上角
        g2d.drawString(watermarkText, 10, textHeight);

        // 右上角
        g2d.drawString(watermarkText, imageWidth - textWidth - 10, textHeight);

        // 左下角
        g2d.drawString(watermarkText, 10, imageHeight - 10);

        // 右下角
        g2d.drawString(watermarkText, imageWidth - textWidth - 10, imageHeight - 10);

        // 中间
        int centerX = (imageWidth - textWidth) / 2;
        int centerY = (imageHeight + textHeight) / 2;
        g2d.drawString(watermarkText, centerX, centerY);

        g2d.dispose();

        ImageIO.write(image, "PNG", new File(outputImagePath));
    }

    public static void main(String[] args) throws IOException {
        addWatermark("Z:\\initTemplate\\payment-system\\payment-common\\src\\main\\resources\\images\\cf9efc788e92c9bfca1feda49ad17d9.jpg", "内部资料", "output.png");
    }
}
