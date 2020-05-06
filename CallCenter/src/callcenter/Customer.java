/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package callcenter;

/**
 *
 * @author BeyBus
 */
public class Customer {

    int customerNumber;
    static int count = 0;
    double enterTime;
    double enterOnHold;

    Customer(double enterTime) {
        count++;
        customerNumber = count;
        this.enterTime = enterTime;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public double getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(double enterTime) {
        this.enterTime = enterTime;
    }

    public double getEnterOnHold() {
        return enterOnHold;
    }

    public void setEnterOnHold(double enterOnHold) {
        this.enterOnHold = enterOnHold;
    }

    @Override
    public String toString() {
        return "" + customerNumber;
    }

}
