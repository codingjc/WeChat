package cn.codingjc.wechat.model;

import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 10:41 上午
 */
public class ImageMessage extends BaseMessage{

    private String mediaId;

    public ImageMessage(Map<String, String> resultMap, String mediaId) {
        super(resultMap);
        this.setMsgType("image");
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
