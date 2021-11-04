package cn.codingjc.wechat.common;

/**
 * @author shenjicheng
 * @create 2021/11/4 11:09 下午
 */
public enum Holiday {
    YUANDAN("元旦", Constant.YUANDAN),
    CHUNJIE("除夕", Constant.CHUNJIE),
    QINGMIN("清明", Constant.QINGMIN),
    WUYI("五一", Constant.WUYI),
    DUANWU("端午", Constant.DUANWU),
    ZHONGQIU("中秋", Constant.ZHONGQIU),
    GUOQING("国庆", Constant.GUOQING),
    ;

    private String name;
    private String day;

    Holiday(String name, String day) {
        this.name = name;
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
