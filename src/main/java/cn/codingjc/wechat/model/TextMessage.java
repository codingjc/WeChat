package cn.codingjc.wechat.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 10:19 上午
 */
@XStreamAlias("xml")
public class TextMessage extends BaseMessage{

    @XStreamAlias("Content")
    private String content;

    public TextMessage(Map<String, String> resultMap, String content) {
        super(resultMap);
        this.setMsgType("text");
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "content='" + content + '\'' +
                "} " + super.toString();
    }
}
