package cn.codingjc.wechat.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 10:15 上午
 */
@XStreamAlias("xml")
public class BaseMessage {

    @XStreamAlias("FromUserName")
    private String fromUserName;
    @XStreamAlias("ToUserName")
    private String toUserName;
    @XStreamAlias("CreateTime")
    private String createTime;
    @XStreamAlias("MsgType")
    private String msgType;

    public BaseMessage(Map<String, String> resultMap) {
        this.fromUserName = resultMap.get("ToUserName");
        this.toUserName = resultMap.get("FromUserName");
        this.createTime = System.currentTimeMillis()/1000 + "";
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "fromUserName='" + fromUserName + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", createTime='" + createTime + '\'' +
                ", msgType='" + msgType + '\'' +
                '}';
    }
}
