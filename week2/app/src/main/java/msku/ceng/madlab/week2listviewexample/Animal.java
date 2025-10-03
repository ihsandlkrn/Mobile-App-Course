package msku.ceng.madlab.week2listviewexample;

public class Animal {
    private String type;
    private int picId;

    public Animal(int picId, String type) {
        this.picId = picId;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getPicId() {
        return picId;
    }

    public Animal(String type, int picId) {
        this.type = type;
        this.picId = picId;
    }
}
