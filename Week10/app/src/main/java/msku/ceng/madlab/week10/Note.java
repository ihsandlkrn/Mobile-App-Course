package msku.ceng.madlab.week10;

import java.util.Date;

public class Note {
    private String header;
    private Date date;
    public String filePath;

    public void setHeader(String header) {
        this.header = header;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getHeader() {
        return header;
    }

    public Date getDate() {
        return date;
    }

    public String getFilePath() {
        return filePath;
    }
}
