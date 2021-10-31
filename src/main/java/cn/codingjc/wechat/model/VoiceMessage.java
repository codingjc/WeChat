package cn.codingjc.wechat.model;

import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 10:44 上午
 */
public class VoiceMessage extends BaseMessage{
    private String mediaId;
    public VoiceMessage(Map<String, String> resultMap, String mediaId) {
        super(resultMap);
        this.setMsgType("voice");
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
