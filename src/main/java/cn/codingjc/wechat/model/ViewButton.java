package cn.codingjc.wechat.model;

/**
 * @author shenjicheng
 * @create 2021/11/6 12:01 上午
 */
public class ViewButton extends AbstractButton{

    private String type = "view";

    private String url;

    public ViewButton(String name, String url) {
        super(name);
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
