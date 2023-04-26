package test3;

import java.time.LocalDateTime;

public class Receiver {
    private CarShop carShop;
    private String name;

    public Receiver(String name, CarShop carShop) {
        this.name = name;
        this.carShop = carShop;
    }

    public void exceptCar(Car car) {
        DBManager.getInstance().checkCar(car);
        Order order = new Order(car, LocalDateTime.now());
        DBManager.getInstance().saveOrder(order);
        this.carShop.addOrder(order);
    }
}
