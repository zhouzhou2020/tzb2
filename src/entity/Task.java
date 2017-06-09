package entity;

/**
 * Created by Yan on 2017/3/18.
 */
public class Task {
    private String createMan;

    private String id;

    private String createDate;

    private String BeiZhu;

    private String[] upUrl;

    private String downUrl;

    private String taskName;

    public Task() {
    }

    public Task(String createMan, String id, String createDate, String beiZhu, String[] upUrl, String downUrl, String taskName) {
        this.createMan = createMan;
        this.id = id;
        this.createDate = createDate;
        BeiZhu = beiZhu;
        this.upUrl = upUrl;
        this.downUrl = downUrl;
        this.taskName = taskName;
    }

    public String getCreateMan() {
        return createMan;
    }

    public void setCreateMan(String createMan) {
        this.createMan = createMan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getBeiZhu() {
        return BeiZhu;
    }

    public void setBeiZhu(String beiZhu) {
        BeiZhu = beiZhu;
    }

    public String[] getUpUrl() {
        return upUrl;
    }

    public void setUpUrl(String[] upUrl) {
        this.upUrl = upUrl;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
