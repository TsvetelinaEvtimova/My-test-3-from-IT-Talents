package test3;

public class Car {
    private int id;
    private String licence_plate;
    private String model;
    private String owner_name;
    private int year_of_production;
    private String phone_number;
    private boolean warranty;

    public Car(String licence_plate, String model, String owner_name, int year_of_production, String phone_number, boolean warranty) {
        this.licence_plate = licence_plate;
        this.model = model;
        this.owner_name = owner_name;
        this.year_of_production = year_of_production;
        this.phone_number = phone_number;
        this.warranty = warranty;
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public String getModel() {
        return model;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public int getYear_of_production() {
        return year_of_production;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public boolean isWarranty() {
        return warranty;
    }
}
