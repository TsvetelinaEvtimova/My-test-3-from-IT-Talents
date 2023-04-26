package test3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static DBManager instance = new DBManager();
    private static final String IP = "localhost";
    private static final String PORT = "3306";
    private static final String USER = "root";
    private static final String PASS = "root";
    private static final String DB_NAME = "s15_test3_carshop";
    private Connection connection;

    private DBManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://" + IP + ":" + PORT + "/" + DB_NAME, USER, PASS);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }
    }

    public static DBManager getInstance() {
        return instance;
    }

    public List<Service> addServices() {
        try {
            List<Service> services = new ArrayList<>();
            String sql = "SELECT id, type, name, price, duration FROM services";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service s = new Service();
                s.setId(rs.getInt("id"));
                s.setType(rs.getString("type"));
                s.setName(rs.getString("name"));
                s.setPrice(rs.getInt("price"));
                s.setDuration(rs.getInt("duration"));
                services.add(s);
            }
            return services;
        } catch (SQLException e) {
            System.out.println("problem");
            return null;
        }
    }

    public void checkCar(Car car) {
        try {
            String sql = "SELECT licence_plate FROM cars WHERE licence_plate = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, car.getLicence_plate());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                checkPhoneNumber(car);
            } else {
                saveCar(car);
            }
        } catch (SQLException e) {
            System.out.println("problem");
        }
    }

    private void checkPhoneNumber(Car car) {
        try {
            String sql = "SELECT phone_number FROM cars WHERE licence_plate = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, car.getLicence_plate());
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (!car.getPhone_number().equals(rs.getString("phone_number"))) {
                editPhoneNumber(car);
            }
        } catch (SQLException e) {
            System.out.println("problem");
        }
    }

    private void editPhoneNumber(Car car) {
        String sql = "UPDATE cars SET phone_number = ? WHERE licence_plate = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, car.getPhone_number());
            ps.setString(2, car.getLicence_plate());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("oops");
        }
    }

    private void saveCar(Car car) {
        String sql = "INSERT INTO cars (licence_plate, model, owner_name, year_of_production, phone_number, warranty) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, car.getLicence_plate());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getOwner_name());
            ps.setInt(4, car.getYear_of_production());
            ps.setString(4, car.getPhone_number());
            ps.setBoolean(4, car.isWarranty());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            car.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.out.println("Can't save this car!");
        }
    }

    public void saveOrder(Order order) {
        String sql = "INSERT INTO orders (car_id, registered_at) VALUES (?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getCar().getId());
            ps.setObject(2, order.getRegistered_at());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            order.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.out.println("Can't save this order!");
        }
    }

    public void saveDiagnostician(Diagnostician d) {
        String sql = "INSERT INTO diagnosticians (name, age) VALUES (?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, d.getName());
            ps.setInt(2, d.getAge());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            d.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.out.println("Can't save diagnostician!");
        }
    }

    public void saveMechanic(Mechanic m) {
        String sql = "INSERT INTO workers (name, age) VALUES (?, ?)";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, m.getName());
            ps.setInt(2, m.getAge());
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            keys.next();
            m.setId(keys.getInt(1));
        } catch (SQLException e) {
            System.out.println("Can't save mechanic!");
        }
    }

    public void editOrder(Order o) {
        String sql = "UPDATE orders SET diagnosed_at = ? AND diagnosed_by = ? AND service_id WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setObject(1, o.getDiagnosed_at());
            ps.setInt(2, o.getDiagnostician().getDId());
            ps.setInt(3, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Order edit failed!");
        }
    }

    public void secondEditOrder(Order o) {
        String sql = "UPDATE orders SET repaired_at = ? AND repaired_by = ? AND service_id WHERE id = ?";
        try {
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setObject(1, o.getRepaired_at());
            ps.setInt(2, o.getMechanic().getMId());
            ps.setInt(3, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Order second edit failed!");
        }
    }

    public void closeOrder(Order o) {
        String sql = "UPDATE orders SET closed_at = ? WHERE id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1, o.getClosed_at());
            ps.setInt(2, o.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("There is a problem with closing order");
        }
    }

    public void getOrdersFor30Days() {
        // брой поръчки, които са заявени (регистрирани) за последните 30 дни (5т)
        String sql = "SELECT COUNT(id) AS total FROM orders WHERE registered_at > (DATE_SUB(NOW(), INTERVAL 30 DAY)";
    }

    public void getNumberOfOrdersType() {
        //брой поръчки, които са за услуги тип РЕМОНТ и брой поръчки, които са за услуги тип ПОДДРЪЖКА
        String sql1 = """
                SELECT COUNT(o.id) AS totalRepair
                FROM orders AS o
                JOIN services AS s
                ON (p.service_id = s.id)
                WHERE s.service_id = ?
                """;
        String sql2 = "like sql1";
    }

    public void getDiagnosticianNameWithMostReadyCars() {
        // името на диагностика, извършил най-много диагностики на автомобили (5т)
        String sql = """
                SELECT d.name , COUNT(o.id) AS total
                FROM diagnosticians AS d
                JOIN orders AS o
                ON (o.diagnosed_by = d.id)
                GROUP BY total, d.name
                ORDER BY total
                LIMIT 1
                """;
    }

    public void getLicencePlateWith3OrMoreServices() {
        //регистрационният номер на всички автомобили с поне 3 извършени услуги (5т)
        String sql = """
                SELECT c.licence_plate , COUNT(o.service_id) AS services
                FROM cars AS c
                JOIN orders AS o
                ON (o.car_id = c.id)
                GROUP BY services, d.name
                HAVING services >= 3;
                """;
    }

    public void getNumberOfCarsWithWarranty() {
        //брой поръчки за автомобили в гаранция (5т)
        String sql = """
                SELECT COUNT(id) AS total
                FROM orders 
                WHERE warranty = 1
                """;

    }

    public void getSumOfAllServices() {
        //обща сума на извършените услуги (ако услугата е извършена върху автомобил в гаранция, сумата за услугата е 0) (5т)
        String sql = """
                SELECT SUM(s.price) AS total
                FROM services AS s
                JOIN orders AS o
                ON (o.service_id = s.id)
                JOIN cars AS c
                ON (o.car_id = c.id)
                WHERE c.warranty = 0
                """;
    }
}
