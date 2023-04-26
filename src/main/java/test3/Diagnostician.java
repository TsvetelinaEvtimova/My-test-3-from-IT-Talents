package test3;

import java.time.LocalDateTime;

public class Diagnostician extends Thread {
    private int id;
    private int age;
    private CarShop carShop;

    public Diagnostician(String name, int age) {
        this.age = age;
        setName(name);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDId() {
        return id;
    }

    public void setCarShop(CarShop carShop) {
        this.carShop = carShop;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order o = this.carShop.getOrder();
                System.out.println(this.getName() + " get " + o.getId() + "order");
                Thread.sleep(5000);
                o.setService(this.carShop.getRandomService());
                o.setDiagnostician(this);
                o.setDiagnosed_at(LocalDateTime.now());
                DBManager.getInstance().editOrder(o);
                if (o.getService().getType().equals("РЕМОНТ")) {
                    this.carShop.addOrderForRepair(o);
                } else {
                    this.carShop.addOrderForSupport(o);
                }
            } catch (InterruptedException e) {
                System.out.println("Problem in diagnostician!");
            }
        }
    }
}
