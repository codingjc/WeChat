package cn.codingjc.wechat.common.excel;

import cn.codingjc.wechat.model.CityBean;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.metadata.ReadSheet;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * 项目启动扫描cityId
 * @author coding_jc
 * @date 2021/11/7
 */
@Component
public class CityIdConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        CityDataListener cityDataListener = new CityDataListener();
        Resource resource = new ClassPathResource("citycode.xlsx");
        ExcelReader read = EasyExcel.read(resource.getInputStream(), CityBean.class, cityDataListener).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        read.read(readSheet);
        read.finish();
//        System.out.println("cityDataListener.date = " + CityDataListener.date);
    }
}
