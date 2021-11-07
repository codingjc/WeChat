package cn.codingjc.wechat.model;

/**
 * @author shenjicheng
 * @create 2021/11/5 11:25 下午
 */
public class AccessToken {

    private String accessToken;
    private Long expiresTime;

    public AccessToken(String accessToken, String expiresIn) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + Integer.parseInt(expiresIn) * 1000;
    }

    public boolean isExpire(){
        return System.currentTimeMillis() - this.expiresTime > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Long expiresTime) {
        this.expiresTime = expiresTime;
    }
}
