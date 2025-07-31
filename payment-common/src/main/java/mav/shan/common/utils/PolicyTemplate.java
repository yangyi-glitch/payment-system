package mav.shan.common.utils;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;

@Slf4j
public class PolicyTemplate {

    public static byte[] ptf(String str, String policyNo) {
        String inputFileName = "policytemplate/未命名1_加水印.pdf";

        FileOutputStream os = null;
        PdfStamper ps = null;
        PdfReader reader = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            // 2 读取pdf模板
            reader = new PdfReader(inputFileName);
            // 3 根据模板生成一个新的pdf
            ps = new PdfStamper(reader, bos);
            // 4 获取pdf表单
            AcroFields form = ps.getAcroFields();
            // 5 给表单添加中文字体
            BaseFont bf = BaseFont.createFont("policytemplate/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            form.addSubstitutionFont(bf);
            // 6 填充模板表单数据并设置字体
            form.setField("name", "杨溢");
            form.setField("age", "24");
            form.setField("sex", "男");
            ps.setFormFlattening(true);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (reader != null) {
                    reader.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }

    public static void main(String[] args) {
        byte[] byteStr = ptf("杨溢", "1234");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("保单.pdf");
            fileOutputStream.write(byteStr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


