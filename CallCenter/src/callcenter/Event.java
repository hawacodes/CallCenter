package callcenter;

/**
 *
 * @author BeyBus
 */
public class Event {

    Customer customer;
    int type;
    double time;

    public Event(Customer customer, int type, double time) {
        this.customer = customer;
        this.type = type;
        this.time = time;

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Event{" + "customer=" + customer + ", type=" + type + ", time=" + time + '}';
    }

    

}
