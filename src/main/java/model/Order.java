package model;

/**
 * Created by Arun on 30-05-2015.
 */
public class Order {

    private int id;
    private String user;
    private String destination;
    private String status;
    private int shipfee;
    private int total;

    public void setId(int id) {
        this.id = id;
    }

    public Order() {

    }

    public Order(int id, String user, String destination, String status, int shipfee, int total) {
        this.id = id;
        this.user = user;
        this.destination = destination;
        this.status = status;
        this.shipfee = shipfee;
        this.total = total;
    }

    public void setUser(String user) {
        this.user = user;

    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShipfee(int shipfee) {
        this.shipfee = shipfee;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;

    }

    public String getUser() {
        return user;
    }

    public String getDestination() {
        return destination;
    }

    public String getStatus() {
        return status;
    }

    public int getShipfee() {
        return shipfee;
    }

    public int getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", user=" + user + ", destination="
                + destination + ", status=" + status + ", shipfee=" + shipfee
                + ", total=" + total + "]";
    }

    public String getNextStatus(){
        switch (status) {
            case "processing":
                return "shipped";
            case "shipped":
                return "delivered";
            default:
                return "delivered";
        }
    }


}
