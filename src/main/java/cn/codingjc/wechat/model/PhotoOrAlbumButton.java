package cn.codingjc.wechat.model;

import java.util.List;

/**
 * @author shenjicheng
 * @create 2021/11/6 12:11 上午
 */
public class PhotoOrAlbumButton extends AbstractButton{
    private String type = "photo";
    private String key;
    private List<AbstractButton> sub_button;

    public PhotoOrAlbumButton(String name, String key) {
        super(name);
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<AbstractButton> getSub_button() {
        return sub_button;
    }

    public void setSub_button(List<AbstractButton> sub_button) {
        this.sub_button = sub_button;
    }
}
