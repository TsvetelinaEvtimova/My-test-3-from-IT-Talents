package test3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class Dispatcher extends Thread {
    private CarShop carShop;

    public Dispatcher(String name) {
        setName(name);
    }

    public void setCarShop(CarShop carShop) {
        this.carShop = carShop;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Order o = this.carShop.getReadyOrder();
                System.out.println(this.getName() + " get order " + o.getId());
                Thread.sleep(1000);
                o.setClosed_at(LocalDateTime.now());
                DBManager.getInstance().closeOrder(o);
                File file = new File(o.getCar().getLicence_plate() + "-" + o.getClosed_at() + ".txt");
                String text = "Car " + o.getCar().getLicence_plate() + " is registered_at " + o.getRegistered_at() + "... and all data for order";
                Files.writeString(file.toPath(), text);
            } catch (InterruptedException e) {
                System.out.println("oop");
            } catch (IOException e) {
                System.out.println("Can't save file");
            }
        }
    }
}
