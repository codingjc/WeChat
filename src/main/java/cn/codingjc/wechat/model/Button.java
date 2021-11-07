package cn.codingjc.wechat.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenjicheng
 * @create 2021/11/5 11:55 下午
 */
public class Button {

    private List<AbstractButton> button = new ArrayList<>();

    public List<AbstractButton> getButton() {
        return button;
    }

    public void setButton(List<AbstractButton> button) {
        this.button = button;
    }

}
