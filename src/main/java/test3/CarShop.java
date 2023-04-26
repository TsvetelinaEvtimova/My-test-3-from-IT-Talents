package test3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CarShop {
    private List<Service> services = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Order> ordersForRepair = new ArrayList<>();
    private List<Order> ordersForSupport = new ArrayList<>();
    private List<Diagnostician> diagnosticians = new ArrayList<>();
    private List<Mechanic> mechanics = new ArrayList<>();
    private List<Order> readyOrders = new ArrayList<>();
    private Dispatcher dispatcher;
    private final Object firstLock = new Object();
    private final Object secondLock = new Object();
    private final Object thirdLock = new Object();

    public CarShop() {
        this.services = DBManager.getInstance().addServices();
    }

    public void addOrder(Order o) {
        synchronized (firstLock) {
            this.orders.add(o);
            firstLock.notifyAll();
        }
    }

    public void addOrderForRepair(Order o) {
        synchronized (secondLock) {
            this.ordersForRepair.add(o);
            secondLock.notifyAll();
        }
    }

    public void addOrderForSupport(Order o) {
        synchronized (secondLock) {
            this.ordersForSupport.add(o);
            secondLock.notifyAll();
        }
    }

    public void addReadyOrder(Order o) {
        synchronized (thirdLock) {
            this.readyOrders.add(o);
            thirdLock.notifyAll();
        }
    }

    public Order getOrder() {
        synchronized (firstLock) {
            if (this.orders.size() == 0) {
                try {
                    firstLock.wait();
                } catch (InterruptedException e) {
                    System.out.println("oops");
                }
            }
            Order o = this.orders.get(this.orders.size() - 1);
            this.orders.remove(o);
            firstLock.notifyAll();
            return o;
        }
    }

    public Order getOrderForRepair() {
        synchronized (secondLock) {
            if (this.ordersForRepair.size() == 0) {
                try {
                    secondLock.wait();
                } catch (InterruptedException e) {
                    System.out.println("oops");
                }
            }
            Order o = this.ordersForRepair.get(this.ordersForRepair.size() - 1);
            this.ordersForRepair.remove(o);
            secondLock.notifyAll();
            return o;
        }
    }

    public Order getReadyOrder() {
        synchronized (thirdLock) {
            if (this.readyOrders.size() == 0) {
                try {
                    thirdLock.wait();
                } catch (InterruptedException e) {
                    System.out.println("oops");
                }
            }
            Order o = this.readyOrders.get(this.readyOrders.size() - 1);
            this.readyOrders.remove(o);
            thirdLock.notifyAll();
            return o;
        }
    }

    public Order getOrderForSupport() {
        synchronized (secondLock) {
            if (this.ordersForSupport.size() == 0) {
                try {
                    secondLock.wait();
                } catch (InterruptedException e) {
                    System.out.println("oops");
                }
            }
            Order o = this.ordersForSupport.get(this.ordersForSupport.size() - 1);
            this.ordersForSupport.remove(o);
            secondLock.notifyAll();
            return o;
        }
    }

    public void hireDiagnostician(Diagnostician diagnostician) {
        this.diagnosticians.add(diagnostician);
        diagnostician.setCarShop(this);
        DBManager.getInstance().saveDiagnostician(diagnostician);
        diagnostician.start();
    }

    public void hireMechanic(Mechanic mechanic) {
        this.mechanics.add(mechanic);
        mechanic.setCarShop(this);
        DBManager.getInstance().saveMechanic(mechanic);
        mechanic.start();
    }

    public void hireDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        dispatcher.setCarShop(this);
        dispatcher.start();
    }

    public Service getRandomService() {
        return this.services.get(new Random().nextInt(this.services.size()));
    }
}
