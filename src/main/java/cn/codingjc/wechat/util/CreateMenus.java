package cn.codingjc.wechat.util;

import cn.codingjc.wechat.common.RestClient;
import cn.codingjc.wechat.model.*;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author shenjicheng
 * @create 2021/11/6 8:18 下午
 */
public class CreateMenus {

    private static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        // 菜单对象
        Button button = new Button();
        // 第一个一级菜单
        button.getButton().add(new ClickButton("一级点击", "1"));
        // 第二个二级菜单
        button.getButton().add(new ViewButton("百度", "http://www.baidu.com"));
        // 第三个一级菜单
        SubButton subButton = new SubButton("子菜单");
        subButton.getSub_button().add(new PhotoOrAlbumButton("拍照", "31"));
        subButton.getSub_button().add(new ClickButton("点击", "32"));
        subButton.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));

        String token = RestClient.getToken();
        String url = String.format(CREATE_MENU_URL, token);


        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(JSON.toJSONString(button), headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, formEntity, String.class);
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
    }
}
