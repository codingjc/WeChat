package cn.codingjc.wechat.controller;

import cn.codingjc.wechat.service.WeChatService;
import cn.codingjc.wechat.util.CheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 9:14 上午
 */
@RestController
public class WeChatController {

    @Autowired
    WeChatService weChatService;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/verify_wx_token")
    public void vertifyWeChat(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        try {
            request.setCharacterEncoding("UTF-8");
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

            out = response.getWriter();
            if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
                out.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    @PostMapping("/verify_wx_token")
    public void acceptMsg(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            Map<String, String> resultMap = weChatService.parseXml(request);
            System.out.println(resultMap);

            String result = weChatService.getResponse(resultMap);

            String msg = "<xml>\n" +
                    "  <ToUserName><![CDATA[" + resultMap.get("FromUserName") + "]]></ToUserName>\n" +
                    "  <FromUserName><![CDATA[" + resultMap.get("ToUserName") + "]]></FromUserName>\n" +
                    "  <CreateTime>" + System.currentTimeMillis()/1000 + "</CreateTime>\n" +
                    "  <MsgType><![CDATA[text]]></MsgType>\n" +
                    "  <Content><![CDATA[你好!]]></Content>\n" +
                    "</xml>";
            out.write(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
