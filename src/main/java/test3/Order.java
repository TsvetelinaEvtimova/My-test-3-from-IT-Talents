package test3;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private Car car;
    private int id;
    private LocalDateTime registered_at;
    private Diagnostician diagnostician;
    private LocalDateTime diagnosed_at;
    private Service service;
    private LocalDateTime repaired_at;
    private Mechanic mechanic;
    private LocalDateTime closed_at;

    public Diagnostician getDiagnostician() {
        return this.diagnostician;
    }

    public int getId() {
        return this.id;
    }

    public LocalDateTime getDiagnosed_at() {
        return this.diagnosed_at;
    }

    public Service getService() {
        return this.service;
    }

    public LocalDateTime getRepaired_at() {
        return this.repaired_at;
    }

    public Mechanic getMechanic() {
        return this.mechanic;
    }

    public LocalDateTime getClosed_at() {
        return this.closed_at;
    }

    public void setDiagnostician(Diagnostician diagnostician) {
        this.diagnostician = diagnostician;
    }

    public void setDiagnosed_at(LocalDateTime diagnosed_at) {
        this.diagnosed_at = diagnosed_at;
    }

    public void setRepaired_at(LocalDateTime repaired_at) {
        this.repaired_at = repaired_at;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public void setClosed_at(LocalDateTime closed_at) {
        this.closed_at = closed_at;
    }

    public Order(Car car, LocalDateTime registered_at) {
        this.car = car;
        this.registered_at = registered_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Car getCar() {
        return this.car;
    }

    public LocalDateTime getRegistered_at() {
        return this.registered_at;
    }
}
