package cn.codingjc.wechat.common.excel;

import cn.codingjc.wechat.model.CityBean;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel监听器
 * @author coding_jc
 * @date 2021/11/7
 */
public class CityDataListener extends AnalysisEventListener<CityBean> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CityDataListener.class);

    public static Map<String, String> date = new HashMap<>();

    @Override
    public void invoke(CityBean cityBean, AnalysisContext analysisContext) {
        String content = cityBean.getContent();
        String[] splitData = content.split(",");
        date.put(splitData[1], splitData[0]);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
