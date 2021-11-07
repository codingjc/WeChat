package cn.codingjc.wechat;

import cn.codingjc.wechat.model.WeatherBean;
import com.alibaba.fastjson.JSON;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechatApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://t.weather.itboy.net/api/weather/city/101210101";
        ResponseEntity<WeatherBean> responseEntity = restTemplate.getForEntity(url, WeatherBean.class);
        System.out.println(JSON.toJSONString(responseEntity.getBody()));
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
