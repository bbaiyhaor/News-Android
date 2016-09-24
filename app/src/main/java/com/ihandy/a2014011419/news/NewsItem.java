package com.ihandy.a2014011419.news;

/**
 * Created by byr on 2016/9/1.
 */
public class NewsItem {
    private long index;
    private String imageUrl;
    private String title;
    private String publishDate;
    private String source;
    private int readTimes;
    private String preview;
    private String contentUrl;

    private static NewsItem defaultNewsItem = new NewsItem(123456789123456789L, "http://p0.ifengimg.com/a/2016_37/dd5e8778778bfa3_size142_w900_h524.jpg", "G20",
            "2016-09-05", "byr", 0, "", "http://news.ifeng.com/a/20160904/49896539_0.shtml?_zbs_baidu_dk#p=1");

    public static NewsItem getDefaultNewsItem(){
        return defaultNewsItem;
    }
    public NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl) {
        this.index = index;
        this.imageUrl = imageUrl;
        this.title = title;
        this.publishDate = publishDate;
        this.source = source;
        this.readTimes = readTimes;
        this.preview = preview;
        this.contentUrl = contentUrl;
    }
    public String getContentUrl() {
        return contentUrl;
    }

    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(int readTimes) {
        this.readTimes = readTimes;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}