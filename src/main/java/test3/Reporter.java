package test3;

public class Reporter extends Thread {
    private CarShop carShop;

    public Reporter(CarShop carShop) {
        this.carShop = carShop;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000 * 60 * 60 * 24 * 31);//1 month
            stat1();
            stat2();
            stat3();
            stat4();
            stat5();
            stat6();
        } catch (InterruptedException e) {
            System.out.println("There is a problem with reporter!");
        }
    }

    private void stat6() {
        //обща сума на извършените услуги (ако услугата е извършена върху автомобил в гаранция, сумата за услугата е 0) (5т)
        DBManager.getInstance().getSumOfAllServices();
    }

    private void stat5() {
        //брой поръчки за автомобили в гаранция (5т)
        DBManager.getInstance().getNumberOfCarsWithWarranty();
    }

    private void stat4() {
        //регистрационният номер на всички автомобили с поне 3 извършени услуги (5т)
        DBManager.getInstance().getLicencePlateWith3OrMoreServices();
    }

    private void stat3() {
        // името на диагностика, извършил най-много диагностики на автомобили (5т)
        DBManager.getInstance().getDiagnosticianNameWithMostReadyCars();
    }

    private void stat2() {
        //брой поръчки, които са за услуги тип РЕМОНТ и брой поръчки, които са за услуги тип ПОДДРЪЖКА
        DBManager.getInstance().getNumberOfOrdersType();
    }

    private void stat1() {
        // брой поръчки, които са заявени (регистрирани) за последните 30 дни (5т)
        DBManager.getInstance().getOrdersFor30Days();
    }
}
