package cn.codingjc.wechat.service;

import cn.codingjc.wechat.common.Constant;
import cn.codingjc.wechat.common.Holiday;
import cn.codingjc.wechat.common.excel.CityDataListener;
import cn.codingjc.wechat.model.*;
import com.thoughtworks.xstream.XStream;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shenjicheng
 * @create 2021/10/31 9:27 上午
 */
@Service
public class WeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatService.class);

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
        if (content.contains("倒计时")) {
            resp = queryHoliday();
        }
        else if (content.contains("天气") || content.contains("萧山") || content.contains("杭州")) {
            resp = queryWeather(content);
        }
        TextMessage textMessage = new TextMessage(resultMap, resp);
        return textMessage;
    }

    /**
     * 假期倒计时
     * @return
     */
    private static String queryHoliday() {
        StringBuilder result = new StringBuilder();
        List<Holiday> holidays = Stream.of(Holiday.YUANDAN, Holiday.CHUNJIE, Holiday.QINGMIN, Holiday.WUYI, Holiday.DUANWU, Holiday.ZHONGQIU
                , Holiday.GUOQING).collect(Collectors.toList());
        Date now = new Date();
        LocalDate localDate = LocalDate.now();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        result.append("😄你好，摸鱼人！工作再累，一定不要忘记摸鱼哦！");
        result.append("\n");
        result.append("\n");
        result.append("今天是" + localDate.getYear() + "年" + localDate.getMonthValue() + "月" + localDate.getDayOfMonth() + "日,");
        result.append("\n");
        int value = localDate.getDayOfWeek().getValue();
        if (6 - value >= 0) {
            result.append("距离本周周末还有" + (6 - value) + "天！");
            result.append("\n");
        } else {
            result.append("快来拥抱美好的周末吧！");
            result.append("\n");
        }
        for (int i = 0; i < holidays.size(); i++) {
            Holiday holiday = holidays.get(i);
            String name = holiday.getName();
            String day = holiday.getDay();
            Date future = null;
            try {
                future = sdf.parse(day);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int diffDays = getDiffDays(now, future);
            result.append("距离" + name + "假期还有" + diffDays + "天！");
            if (i != holidays.size() - 1) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String a = "嘉兴天气";
        String[] aa = a.split("天气");
        System.out.println(aa[0]);
    }

    /**
     * 查询天气
     * @param content
     * @return
     */
    private String queryWeather(String content) {
        String result = "";
        result = queryWeatherByCity(content);
        return result;
    }

    private String queryWeatherByCity(String key){
        LOGGER.info("CityDataListener date size {}", CityDataListener.date.size());
        LOGGER.info("CityDataListener key is {}", key);

        StringBuilder result = new StringBuilder();
        String cityId = "";
        /*switch (key){
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
        }*/
        if (Constant.WEATHER.equals(key)) {
            cityId = Constant.HANGZHOU;
        }
        String[] splitKey = key.split(Constant.WEATHER);
        if (Constant.WEATHER.equals(key) || CityDataListener.date.containsKey(splitKey[0])) {
            if (!Constant.WEATHER.equals(key) ) {
                cityId = CityDataListener.date.get(splitKey[0]);
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
//            result.append("\n");
            result.append(nextDay.getNotice() + "😄");
            result.append("\n");
            result.append("\n");

            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 2);
            Date secord = c.getTime();//这是第二天
            WeatherBean.Forecast secordDay = weatherBean.getData().getForecast().get(2);
//            result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
            result.append("星期" + getDaystr(secord.getDay()) + weatherBean.getCityInfo().getCity() + "" + "天气：" + secordDay.getType() + "\n");
            result.append("☁️【️最低温度】:" + secordDay.getLow() + "\n");
            result.append("🌡【最高温度】:" + secordDay.getHigh() + "\n");
            result.append("🌄【日出时间】:" + secordDay.getSunrise() + "\n");
            result.append("🌞【日落时间】:" + secordDay.getSunset() + "\n");
            result.append(secordDay.getNotice() + "😄");
            result.append("\n");
            result.append("\n");

            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 3);
            Date third = c.getTime();//这是第三天
            WeatherBean.Forecast thirdDay = weatherBean.getData().getForecast().get(3);
//            result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
            result.append("星期" + getDaystr(third.getDay()) + weatherBean.getCityInfo().getCity() + "" + "天气：" + thirdDay.getType() + "\n");
            result.append("☁️【️最低温度】:" + thirdDay.getLow() + "\n");
            result.append("🌡【最高温度】:" + thirdDay.getHigh() + "\n");
            result.append("🌄【日出时间】:" + thirdDay.getSunrise() + "\n");
            result.append("🌞【日落时间】:" + thirdDay.getSunset() + "\n");
            result.append(thirdDay.getNotice() + "😄");
            result.append("\n");
            result.append("\n");

            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 4);
            Date fourth = c.getTime();//这是第四天
            WeatherBean.Forecast fourthDay = weatherBean.getData().getForecast().get(3);
//            result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
            result.append("星期" + getDaystr(fourth.getDay()) + weatherBean.getCityInfo().getCity() + "" + "天气：" + fourthDay.getType() + "\n");
            result.append("☁️【️最低温度】:" + fourthDay.getLow() + "\n");
            result.append("🌡【最高温度】:" + fourthDay.getHigh() + "\n");
            result.append("🌄【日出时间】:" + fourthDay.getSunrise() + "\n");
            result.append("🌞【日落时间】:" + fourthDay.getSunset() + "\n");
            result.append(fourthDay.getNotice() + "😄");
            result.append("\n");
            result.append("\n");

            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 5);
            Date five = c.getTime();//这是第无天
            WeatherBean.Forecast fiveDay = weatherBean.getData().getForecast().get(3);
//            result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
            result.append("星期" + getDaystr(five.getDay()) + weatherBean.getCityInfo().getCity() + "" + "天气：" + fiveDay.getType() + "\n");
            result.append("☁️【️最低温度】:" + fiveDay.getLow() + "\n");
            result.append("🌡【最高温度】:" + fiveDay.getHigh() + "\n");
            result.append("🌄【日出时间】:" + fiveDay.getSunrise() + "\n");
            result.append("🌞【日落时间】:" + fiveDay.getSunset() + "\n");
            result.append(fiveDay.getNotice() + "😄");
            result.append("\n");
            result.append("\n");

            c.setTime(date);
            c.add(Calendar.DAY_OF_MONTH, 6);
            Date six = c.getTime();//这是第无天
            WeatherBean.Forecast sixDay = weatherBean.getData().getForecast().get(3);
//            result.append("明日是" + sdf.format(tomorrow) + ", 星期" + getDaystr(tomorrow.getDay()) + "\n");
            result.append("星期" + getDaystr(six.getDay()) + weatherBean.getCityInfo().getCity() + "" + "天气：" + sixDay.getType() + "\n");
            result.append("☁️【️最低温度】:" + sixDay.getLow() + "\n");
            result.append("🌡【最高温度】:" + sixDay.getHigh() + "\n");
            result.append("🌄【日出时间】:" + sixDay.getSunrise() + "\n");
            result.append("🌞【日落时间】:" + sixDay.getSunset() + "\n");
            result.append(sixDay.getNotice() + "😄");
            result.append("\n");

            return result.toString();
        } else {
            result.append("小番正在努力寻找该城市的天气( ╯□╰ )");
            result.append("\n");
            return result.toString();
        }
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

    public static int getDiffDays(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("getDiffDays param is null!");
        }
        long diff = (endDate.getTime() - beginDate.getTime())
                / (1000 * 60 * 60 * 24);
        int days = new Long(diff).intValue();
        return days;
    }

}
