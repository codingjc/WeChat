package cn.codingjc.wechat.model;

/**
 * @author shenjicheng
 * @create 2021/11/5 11:54 下午
 */
public abstract class AbstractButton {
    public String name;

    public AbstractButton(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
