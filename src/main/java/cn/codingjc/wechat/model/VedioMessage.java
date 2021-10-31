package cn.codingjc.wechat.model;

import java.util.Map;

/**
 * @author shenjicheng
 * @create 2021/10/31 10:45 上午
 */
public class VedioMessage extends BaseMessage{
    private String title;
    private String mediaId;
    private String description;

    public VedioMessage(Map<String, String> resultMap, String title, String mediaId, String description) {
        super(resultMap);
        this.setMsgType("vedio");
        this.mediaId = mediaId;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
