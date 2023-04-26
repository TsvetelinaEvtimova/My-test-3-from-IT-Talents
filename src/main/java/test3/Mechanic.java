package test3;

import java.time.LocalDateTime;

public class Mechanic extends Thread {
    private int id;
    private int age;
    private String serviceType;
    private CarShop carShop;

    public Mechanic(String name, int age, String serviceType) {
        setName(name);
        this.age = age;
        this.serviceType = serviceType;
    }

    public void setCarShop(CarShop carShop) {
        this.carShop = carShop;
    }

    public int getMId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order o;
                if (this.serviceType.equals("РЕМОНТ")) {
                    o = this.carShop.getOrderForRepair();
                    System.out.println(this.getName() + " get order for РЕМОНТ");
                } else {
                    o = this.carShop.getOrderForSupport();
                    System.out.println(this.getName() + " get order for ПОДДРЪЖКА");
                }
                Thread.sleep(o.getService().getDuration());
                o.setMechanic(this);
                o.setRepaired_at(LocalDateTime.now());
                DBManager.getInstance().secondEditOrder(o);
                this.carShop.addReadyOrder(o);
            } catch (InterruptedException e) {
                System.out.println("Problem in mechanic!");
            }
        }
    }
}
