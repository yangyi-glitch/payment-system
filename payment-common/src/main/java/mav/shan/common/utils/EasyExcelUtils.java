package mav.shan.common.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class EasyExcelUtils {
    public static <T, O> void write(HttpServletResponse response, String sheetName, Class<T> exportCless, List<O> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }
        try {
            List<T> dataListT = new ArrayList<>();
            for (O o : dataList) {
                T t = exportCless.getConstructor().newInstance();
                BeanUtil.copyProperties(o, t);
                dataListT.add(t);
            }
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=" + sheetName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), exportCless)
                    .sheet(sheetName)
                    .doWrite(dataListT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
