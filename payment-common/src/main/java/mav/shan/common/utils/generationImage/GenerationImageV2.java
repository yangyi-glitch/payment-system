package mav.shan.common.utils.generationImage;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.util.CollectionUtils;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerationImageV2 {


    // 当前行Y轴坐标
    private static int y = 0;
    // 出现换行时下一行Y坐标
    private static int nextY = 0;
    // 图片宽度
    private static int width = 842;
    // 图片高度
    private static int height = 594;
    // 旧图片高度，用于长度扩展
    private static int oldHeight = 592;
    // 下一项坐标间隔
    private static int xNextSize = 200;
    // 下一行坐标间隔
    private static int yNextSize = 20;
    // 边框
    private static int broder = 21;
    private static int x = broder;
    // 项目名称前序号像素大小
    private static int itemNumSize = 32;

    public static void main(String[] args) throws IOException {
        Long t1 = System.currentTimeMillis();
        Map<String, String> data = new HashMap<>(16);
        data.put("姓名", "病人");
        data.put("门诊类型", "门诊");
        data.put("送检科室", "急诊科");
        data.put("标本号", "167");
        data.put("性别", "男");
        data.put("病历号", "W001121");
        // 标本类型为血清的检查项目类名称
        List<String> serumItems = new ArrayList();
        serumItems.add("校验项目名称");
        serumItems.add("英文名称");
        serumItems.add("检验结果");
        serumItems.add("参考范围");
        serumItems.add("单位");
        // 标本类型为血清的检查项目类数据
        List<Map<String, String>> serumData = new ArrayList();
        Map<String, String> serumDataMap = new HashMap();
        for (int i = 0; i < 10; i++) {
            serumDataMap = new HashMap();
            serumDataMap.put("1", "校验项目名称A" + i + 1);
            serumDataMap.put("2", "英文名称A" + i + 1);
            serumDataMap.put("3", "检验结果A" + i + 1);
            serumDataMap.put("4", "参考范围A" + i + 1);
            serumDataMap.put("5", "单位A" + i + 1);
            serumData.add(serumDataMap);
        }
        // 图片最底部数据
        List<Map<String, String>> tailList = new ArrayList();
        Map<String, String> tailMap = new HashMap<>();
        tailMap.put("送检日期", "2018-12-14 15:26:06");
        tailList.add(tailMap);
        Map<String, String> tailMap1 = new HashMap<>();
        tailMap1.put("检验时间", "2018-12-14 15:26:15");
        tailList.add(tailMap1);
        Map<String, String> tailMap2 = new HashMap<>();
        tailMap2.put("校验者", "./图片/name.png");
        tailList.add(tailMap2);
        Map<String, String> tailMap3 = new HashMap<>();
        tailMap3.put("审核者", "./图片/name.png");
        tailList.add(tailMap3);
        getImage(data, serumItems, serumData, tailList);
        Long t2 = System.currentTimeMillis();
        System.out.printf("时间：" + (t2 - t1));
    }

    public static void getImage(Map<String, String> data, List<String> items, List<Map<String, String>> itemData,
                                List<Map<String, String>> tailList) throws IOException {
        // 创建一个画布对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        BufferedImage newImage = null;
        Graphics2D g2d = image.createGraphics();

        // 在画布上绘制图像元素
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(Color.BLACK);
        // 标题
        String str = "XXXX市人民医院检验报告单";
        Font font = new Font("宋体", Font.BOLD, 26);
        Map<String, Integer> juZhong = getImageJuZhong(g2d, font, str, width, height);
        y = 40;
        g2d.setFont(font);
        g2d.drawString(str, juZhong.get("x"), y);
        Font font1 = new Font("宋体", Font.PLAIN, 16);
        y += yNextSize + 10;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            // 填入数据
            str = entry.getKey() + "：" + entry.getValue();
            // 想图片中添加数据
            draw(g2d, font1, str);
        }
        // 画横线
        g2d.drawLine(broder, y += 10, width - broder, y);
        if (!CollectionUtils.isEmpty(itemData)) {
            // 项目名称需要空出下面具体项目的需要位置
            x = broder + itemNumSize;
            y = y + yNextSize;
            // 计算项目名称所需要的位置大小
            xNextSize = (width - itemNumSize - broder * 2) / items.size();
            // 平均数差出来的值，放入第一项中，防止出现多个项目名称除不开的情况，多出来的这个值放在第一位数据中
            int ys = (width - itemNumSize - broder * 2) % items.size();

            for (int i = 0; i < items.size(); i++) {
                // 将项目名称写入图像
                // 第一个项需要将差值放入
                if (i == 0 && ys != 0) {
                    xNextSize = xNextSize + ys;
                }
                // 第二个项之后需要将差值减掉
                if (i == 1 && ys != 0) {
                    xNextSize = xNextSize - ys;
                }
                // 写入项目名称
                draw(g2d, font1, items.get(i));
                // 超出页面长度，扩宽图片长度，按理说每个写入都需要带这么一段代码，但有些基本项一般不会超出长度，有一些就不写了
                if (y >= height - 60) {
                    // 这里新增的高度应该空出尾部图片的高度
                    height = y + yNextSize + 60;
                    newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                    // 复制原始图像到新图像
                    WritableRaster originalRaster = image.getRaster();
                    WritableRaster resizedRaster = newImage.getRaster();
                    int[] pixelData = new int[width * height];
                    resizedRaster.setDataElements(0, 0, width, height, originalRaster.getDataElements(0, 0, width
                            , y, pixelData));
                    oldHeight = height;
                    image = newImage;
                    g2d = newImage.createGraphics();
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(0, y, width, height);
                    g2d.setColor(Color.BLACK);
                }
            }
            // 画横线
            g2d.drawLine(broder, y - 10, width - broder, y - 10);
            // 处理具体项目数据
            y += 10;
            for (int i = 1; i <= itemData.size(); i++) {
                Map<String, String> item = itemData.get(i - 1);
                x = broder;
                int copyXnextSize = xNextSize;
                xNextSize = itemNumSize;
                // 写入项目前的序号，一般也不会超，所以不增加扩容代码
                draw(g2d, font1, String.valueOf(i));
                xNextSize = copyXnextSize;
                // 放入项目数据
                for (int j = 1; j <= item.size(); j++) {
                    // 第一个项需要将差值放入
                    if (j == 1 && ys != 0) {
                        xNextSize = xNextSize + ys;
                    }
                    // 第二个项之后需要将差值减掉
                    if (j == 2 && ys != 0) {
                        xNextSize = xNextSize - ys;
                    }
                    // 写入项目
                    draw(g2d, font1, item.get(String.valueOf(j)));
                    // 超出页面长度，扩宽图片长度
                    if (y >= height - 60) {
                        height = y + yNextSize + 60;
                        newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        // 复制原始图像到新图像
                        WritableRaster originalRaster = image.getRaster();
                        WritableRaster resizedRaster = newImage.getRaster();
                        int[] pixelData = new int[width * height];
                        resizedRaster.setDataElements(0, 0, width, height, originalRaster.getDataElements(0, 0, width
                                , y, pixelData));
                        oldHeight = height;
                        image = newImage;
                        g2d = newImage.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, y, width, height);
                        g2d.setColor(Color.BLACK);
                    }
                }
            }

        }
        // 处理最后的项目，先生成一个图片，然后用覆盖的方式方法哦要返回的图片中
        // 高度默认50，如果不够进行线下扩容
        BufferedImage tail = new BufferedImage(width, 50, BufferedImage.TYPE_INT_RGB);
        BufferedImage newTail = null;
        Graphics2D g2d1 = tail.createGraphics();
        g2d1.setColor(Color.WHITE);
        g2d1.fillRect(0, 0, width, 50);
        g2d1.setColor(Color.BLACK);
        Font font2 = new Font("宋体", Font.PLAIN, 16);
        g2d1.setFont(font2);
        // 画横线
        g2d1.drawLine(broder, 10, width - broder, 10);
        x = broder;
        y = 30;
        xNextSize = 250;
        if (!CollectionUtils.isEmpty(tailList)) {
            for (int i = 0; i < tailList.size(); i++) {
                Map<String, String> tailMap = tailList.get(i);
                for (Map.Entry<String, String> entry : tailMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    str = entry.getKey() + ":" + entry.getValue();
                    // 处理图片，如果按照实际情况，应该会有一个特殊标识说明他是图片，但实验数据里没有，就这么写了
                    if (key.equals("校验者") || key.equals("审核者")) {
                        // 这里为了达到图片上的效果的宽度也需要进行计算，就是数据的长度，上面的为了保持公证都是一个定好的数据
                        FontMetrics fm = g2d.getFontMetrics(font2);
                        // 这里 + 10 是为了让图片和文字之间有一个间隔，在写入数据方法内部判断是否换行时 -10，所以这里加10
                        int i1 = fm.stringWidth(key) + 10;
                        int oldNextX = xNextSize;
                        xNextSize = i1;
                        // 覆盖图片
                        BufferedImage name = ImageIO.read(new File(value));
                        // 写入名称
                        draw(g2d1, font2, key);
                        // 写入图片
                        g2d1.drawImage(name, x, 15, 30, 30, null);
                        // 这里加30是未了让x坐标移动到图片的后面，应该还需要对宽度是否需要换行进行判断，但在这里就不进行判断了
                        x += 30;
                        xNextSize = oldNextX;
                    } else {
                        FontMetrics fm = g2d.getFontMetrics(font2);
                        // 这里 + 10 是为了让图片和文字之间有一个间隔，在写入数据方法内部判断是否换行时 -10，所以这里加10
                        int i1 = fm.stringWidth(str) + 10;
                        int oldNextX = xNextSize;
                        xNextSize = i1;
                        // 处理其他数据
                        draw(g2d1, font2, str);
                        xNextSize = oldNextX;
                    }
                    // 扩容
                    if (y >= height - 60) {
                        // 这里新增的高度就正常一行高度即可
                        height = y + yNextSize;
                        newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                        // 复制原始图像到新图像
                        WritableRaster originalRaster = image.getRaster();
                        WritableRaster resizedRaster = newImage.getRaster();
                        int[] pixelData = new int[width * height];
                        resizedRaster.setDataElements(0, 0, width, height, originalRaster.getDataElements(0, 0, width
                                , y, pixelData));
                        oldHeight = height;
                        image = newImage;
                        g2d = newImage.createGraphics();
                        g2d.setColor(Color.WHITE);
                        g2d.fillRect(0, y, width, height);
                        g2d.setColor(Color.BLACK);
                    }
                }
            }

        }
        // 将生成的尾部图片覆盖到结果图片中
        g2d.drawImage(tail, 0, height - 50, width, 50, null);
        // 保存生成的图像到文件
        File outputFile = new File("iamge.png");

        try {
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        // 释放资源
        g2d.dispose();
        g2d1.dispose();
    }

    /**
     * 写数据
     *
     * @param g2d
     * @param font1 样式
     * @param str   数据
     */
    private static void draw(Graphics2D g2d, Font font1, String str) {
        BufferedImage a = null;
        // 超出长度切割文字进行换行
        List<String> strs = getStrWith(g2d, font1, str, xNextSize);
        // 读取换行文字
        for (int i = 0; i < strs.size(); i++) {
            // data内容
            g2d.setFont(font1);
            if (ObjectUtil.isNotNull(strs.get(i))) {
                // 只有一个数据说明不需要换行
                if (i == 0) {
                    g2d.drawString(strs.get(i), x, y);
                } else {
                    // 产生了换行，y轴坐标需要向下移动
                    nextY = y + yNextSize * i;
                    g2d.drawString(strs.get(i), x, nextY);
                }
            }
        }
        // 判断是否需要移动到下一行展示，这个下一行是整个数据的下一行，不是换行数据
        if (x + xNextSize >= width - broder) {
            // 如果换行下一行数据有值，表示这一样数据发生了换行，下一行数据需要再换行数据的下一行展示
            if (0 != nextY) {
                y = nextY + yNextSize;
                nextY = 0;
            } else {
                // 没有换行，正常换一行就行
                y += yNextSize;
                nextY = 0;
            }
            // 将x轴重置到最开始
            x = broder;
        } else {
            // 不需要换行，将x轴移动到下一个数据的起始为止
            x += xNextSize;
        }
    }

    /**
     * 根据文字样式和图片大小计算居中位置坐标
     *
     * @param g2d    图片
     * @param font   文字样式
     * @param str    文字
     * @param width  宽
     * @param height 高
     * @return X轴和Y轴坐标
     **/
    private static Map<String, Integer> getImageJuZhong(Graphics2D g2d, Font font, String str, int width, int height) {
        Map<String, Integer> map = new HashMap<>();
        FontMetrics fm = g2d.getFontMetrics(font);
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (height - (ascent + descent)) / 2 + ascent;

        /**获取起始x轴*/
        int strLen = fm.stringWidth(str);
        int x = (width - strLen) / 2;
        map.put("x", x);
        map.put("y", y);
        return map;
    }

    /**
     * 对超出长度的字符串进行切割
     *
     * @param g2d
     * @param font
     * @param str
     * @param width
     * @date 下午2:18 10/5/2024
     **/
    private static List<String> getStrWith(Graphics2D g2d, Font font, String str, int width) {
        // 循环字符串，每次添加判断长度是否超过指定宽度，超过就截取，然后放入新的集合汇总
        List<String> strs = new ArrayList<>();
        FontMetrics fm = g2d.getFontMetrics(font);
        String s = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            int strLen = fm.stringWidth(s);
            // 这里减10是未了流出一定的空白，防止数据连一起
            if (strLen > width - 10) {
                strs.add(s);
                s = String.valueOf(c);
            } else {
                s = s + c;
            }
        }
        strs.add(s);
        return strs;
    }
}
