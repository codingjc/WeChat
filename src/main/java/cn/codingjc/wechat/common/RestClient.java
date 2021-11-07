package cn.codingjc.wechat.common;

import cn.codingjc.wechat.model.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author shenjicheng
 * @create 2021/11/5 10:52 下午
 */
@Component
public class RestClient {

    private static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    private static final String APP_ID = "123";

    private static final String APP_SECURITY = "123";

    private static AccessToken token;

    @Autowired
    private RestTemplate restTemplate;

    public static String getToken(){
        if (token == null || token.isExpire()) {
            getAccessToken();
        }
        return token.getAccessToken();
    }

    private static void getAccessToken(){
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(TOKEN_URL, APP_ID, APP_SECURITY);
        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
        String access_token = result.getString("access_token");
        String expires_in = result.getString("expires_in");
        if (StringUtils.isEmpty(access_token)) {
        } else {
            token = new AccessToken(access_token, expires_in);
        }
    }

    public static void main(String[] args) {
//        StopWatch sw = new StopWatch("token");
//        String url = String.format(TOKEN_URL, APP_ID, APP_SECURITY);
////        System.out.println("url = " + url);
//        RestTemplate restTemplate = new RestTemplate();
//        sw.start("token");
//        JSONObject result = restTemplate.getForObject(url, JSONObject.class);
//        System.out.println(result);
//        sw.stop();
//        sw.prettyPrint();

        // 菜单对象
        Button button = new Button();
        // 第一个一级菜单
        button.getButton().add(new ClickButton("一级点击", "1"));
        // 第二个二级菜单
        button.getButton().add(new ViewButton("一级跳转", "http://www.baidu.com"));
        // 第三个一级菜单
        SubButton subButton = new SubButton("子菜单");
        subButton.getSub_button().add(new PhotoOrAlbumButton("拍照", "31"));
        subButton.getSub_button().add(new ClickButton("点击", "32"));
        subButton.getSub_button().add(new ViewButton("网易新闻", "http://news.163.com"));
        button.getButton().add(subButton);
        System.out.println(JSON.toJSONString(button));
    }

}
