package test3;

import java.util.Random;

public class Demo {
    public static void main(String[] args) {
        CarShop carShop = new CarShop();
        Receiver receiver = new Receiver("Receiver", carShop);
        for (int i = 0; i < 5; i++) {
            receiver.exceptCar(new Car(getRandomLicencePlate(), "Golf", getRandomName(), 2009, getRandomPhoneNumber(), getRandomWarranty()));
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                receiver.exceptCar(new Car(getRandomLicencePlate(), "Golf", getRandomName(), 2009, getRandomPhoneNumber(), getRandomWarranty()));
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                receiver.exceptCar(new Car(getRandomLicencePlate(), "Golf", getRandomName(), 2009, getRandomPhoneNumber(), getRandomWarranty()));
            }
        }
        Diagnostician d1 = new Diagnostician(getRandomName(), getRandomAge());
        Diagnostician d2 = new Diagnostician(getRandomName(), getRandomAge());
        carShop.hireDiagnostician(d1);
        carShop.hireDiagnostician(d2);

        for (int i = 0; i < 2; i++) {
            Mechanic m1 = new Mechanic(getRandomName(), getRandomAge(), "РЕМОНТ");
            Mechanic m2 = new Mechanic(getRandomName(), getRandomAge(), "ПОДДРЪЖКА");
            carShop.hireMechanic(m1);
            carShop.hireMechanic(m2);
        }

        Dispatcher dispatcher = new Dispatcher("Dispatcher");
        carShop.hireDispatcher(dispatcher);

        Reporter reporter = new Reporter(carShop);
        reporter.setDaemon(true);
        reporter.start();
    }


    private static String getRandomName() {
        String[] names = {"Krasi", "Tsveti", "Hristo", "Kami", "Zori", "Mario", "Gabriel", "Radostin", "Georgi", "Joan", "Mitko", "Bobi", "Kali"};
        return names[new Random().nextInt(names.length)];
    }

    private static int getRandomAge() {
        return new Random().nextInt(20, 50);
    }

    private static String getRandomLicencePlate() {
        int random = new Random().nextInt(1000, 9999);
        String licence = "CB" + random + "TB";
        return licence;
    }

    private static String getRandomPhoneNumber() {
        int random = new Random().nextInt(100000, 999999);
        String number = "0878" + random;
        return number;
    }

    private static boolean getRandomWarranty() {
        return new Random().nextBoolean();
    }
}
