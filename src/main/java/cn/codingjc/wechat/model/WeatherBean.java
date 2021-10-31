package cn.codingjc.wechat.model;

import lombok.Data;

import java.util.List;

/**
 * @author shenjicheng
 * @create 2021/10/31 9:12 下午
 */
@Data
public class WeatherBean {

    private String date;
    private Data data;
    private CityInfo cityInfo;
    private String time;
    private String message;
    private Integer status;

    public static class Data{
        private String shidu;
        private Integer pm25;
        private Integer pm10;
        private String quality;
        private String wendu;
        private String ganmao;
        private List<Forecast> forecast;
        private Forecast yesterday;

        public String getShidu() {
            return shidu;
        }

        public void setShidu(String shidu) {
            this.shidu = shidu;
        }

        public Integer getPm25() {
            return pm25;
        }

        public void setPm25(Integer pm25) {
            this.pm25 = pm25;
        }

        public Integer getPm10() {
            return pm10;
        }

        public void setPm10(Integer pm10) {
            this.pm10 = pm10;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getWendu() {
            return wendu;
        }

        public void setWendu(String wendu) {
            this.wendu = wendu;
        }

        public String getGanmao() {
            return ganmao;
        }

        public void setGanmao(String ganmao) {
            this.ganmao = ganmao;
        }

        public List<Forecast> getForecast() {
            return forecast;
        }

        public void setForecast(List<Forecast> forecast) {
            this.forecast = forecast;
        }

        public Forecast getYesterday() {
            return yesterday;
        }

        public void setYesterday(Forecast yesterday) {
            this.yesterday = yesterday;
        }
    }

    public static class Forecast{
        private String date;
        private String high;
        private String low;
        private String ymd;
        private String week;
        private String sunrise;
        private String sunset;
        private String api;
        private String fx;
        private String fl;
        private String type;
        private String notice;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getHigh() {
            return high;
        }

        public void setHigh(String high) {
            this.high = high;
        }

        public String getLow() {
            return low;
        }

        public void setLow(String low) {
            this.low = low;
        }

        public String getYmd() {
            return ymd;
        }

        public void setYmd(String ymd) {
            this.ymd = ymd;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getSunrise() {
            return sunrise;
        }

        public void setSunrise(String sunrise) {
            this.sunrise = sunrise;
        }

        public String getSunset() {
            return sunset;
        }

        public void setSunset(String sunset) {
            this.sunset = sunset;
        }

        public String getApi() {
            return api;
        }

        public void setApi(String api) {
            this.api = api;
        }

        public String getFx() {
            return fx;
        }

        public void setFx(String fx) {
            this.fx = fx;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }
    }

    public static class CityInfo{
        private String city;
        private String cityKey;
        private String parent;
        private String updateTime;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCityKey() {
            return cityKey;
        }

        public void setCityKey(String cityKey) {
            this.cityKey = cityKey;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
