package entity;

/**
 * Created by wangshunyao on 2017/5/5.
 */
public class Item {
    private String path;
    private byte[] content;

    public Item(String path, String content) {
        this(path, content.getBytes());
    }

    public Item(String path, byte[] content) {
        this.path = path;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
