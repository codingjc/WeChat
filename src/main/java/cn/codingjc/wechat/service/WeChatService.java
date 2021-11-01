package cn.codingjc.wechat.service;

import cn.codingjc.wechat.common.Constant;
import cn.codingjc.wechat.model.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author shenjicheng
 * @create 2021/10/31 9:27 上午
 */
@Service
public class WeChatService {

    @Autowired
    RestTemplate restTemplate;

    public Map<String, String> parseXml(HttpServletRequest request) throws Exception {
        HashMap<String, String> result = new HashMap<>(16);
        InputStream inputStream = request.getInputStream();
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            result.put(element.getName(), element.getStringValue());
        }
        return result;
    }

    /**
     * 获取响应
     * @param resultMap
     * @return
     */
    public String getResponse(Map<String, String> resultMap) {
        BaseMessage baseMessage = null;
        String msgType = resultMap.get("MsgType");

        switch (msgType) {
            case "text":
                baseMessage = dealTextMessage(resultMap);
                break;
            case "image":
                break;
            case "voice":
                break;
            case "video":
                break;
            case "shortvideo":
                break;
            case "location":
                break;
            default:
                break;
        }

        return beanToXml(baseMessage);
    }

    private String beanToXml(BaseMessage baseMessage){
        XStream xStream = new XStream();
        xStream.processAnnotations(TextMessage.class);
        xStream.processAnnotations(ImageMessage.class);
        xStream.processAnnotations(VoiceMessage.class);
        xStream.processAnnotations(VedioMessage.class);
        return xStream.toXML(baseMessage);
    }

    private BaseMessage dealTextMessage(Map<String, String> resultMap) {
        String content = resultMap.get("Content");
        String resp = "播主还在改代码😂";
        if (content.contains("天气") || content.contains("萧山") || content.contains("杭州")) {
            resp = queryWeather(content);
        }
        TextMessage textMessage = new TextMessage(resultMap, resp);
        return textMessage;
    }

    /**
     * 查询天气
     * @param content
     * @return
     */
    private String queryWeather(String content) {
        String result = "";
        if (content.contains("杭州")) {
            result = queryWeatherByCity("hangzhou");
        } else if (content.contains("嘉兴")) {
            result = queryWeatherByCity("jiaxing");
        }else if (content.contains("萧山")) {
            result = queryWeatherByCity("xiaoshan");
        } else {
            result = queryWeatherByCity("hangzhou");
        }
        return result;
    }

    private String queryWeatherByCity(String key){
        StringBuilder result = new StringBuilder();
        String cityId = "";
        switch (key){
            case "hangzhou":
                cityId = Constant.HANGZHOU;
                break;
            case "jiaxing":
                cityId = Constant.JIAXING;
                break;
            case "xiaoshan":
                cityId = Constant.XIAOSHAN;
                break;
            default:
                break;
        }
        String url = "http://t.weather.itboy.net/api/weather/city/" + cityId;
        ResponseEntity<WeatherBean> responseEntity = restTemplate.getForEntity(url, WeatherBean.class);
        System.out.println(responseEntity.getBody());
        WeatherBean weatherBean = responseEntity.getBody();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dayStr = "";
        int day = date.getDay();
        WeatherBean.Forecast today = weatherBean.getData().getForecast().get(0);
        result.append("今天是" + sdf.format(date) + ", 星期" + getDaystr(day) + "\n");
        result.append(weatherBean.getCityInfo().getCity() + "今日天气：" + today.getType() + "\n");
        result.append("☁️【️最低温度】:" + today.getLow() + "\n");
        result.append("🌡【最高温度】:" + today.getHigh() + "\n");
        result.append("🌄【日出时间】:" + today.getSunrise() + "\n");
        result.append("🌞【日落时间】:" + today.getSunset() + "\n");
        result.append("\n");

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, 1);
        Date tomorrow = c.getTime();//这是明天

        WeatherBean.Forecast nextDay = weatherBean.getData().getForecast().get(1);
        result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
        result.append(weatherBean.getCityInfo().getCity() + "明日天气：" + nextDay.getType() + "\n");
        result.append("☁️【️最低温度】:" + nextDay.getLow() + "\n");
        result.append("🌡【最高温度】:" + nextDay.getHigh() + "\n");
        result.append("🌄【日出时间】:" + nextDay.getSunrise() + "\n");
        result.append("🌞【日落时间】:" + nextDay.getSunset() + "\n");
        result.append("\n");
        result.append(nextDay.getNotice() + "😄");
        return result.toString();
    }


    public String getDaystr(int day){
        String dayStr = "";
        switch (day){
            case 0: dayStr = "日";
                break;
            case 1: dayStr = "一";
                break;
            case 2: dayStr = "二";
                break;
            case 3: dayStr = "三";
                break;
            case 4: dayStr = "四";
                break;
            case 5: dayStr = "五";
                break;
            case 6: dayStr = "六";
                break;
        }
        return dayStr;
    }
}
