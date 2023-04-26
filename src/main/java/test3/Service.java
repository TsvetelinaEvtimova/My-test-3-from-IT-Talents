package test3;

public class Service {
    private int id;
    private String type;
    private String name;
    private int price;
    private int duration;

    public int getDuration() {
        return this.duration;
    }

    public String getType() {
        return this.type;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
