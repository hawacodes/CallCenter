package callcenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author BeyBus
 */
public class CallCenter {

    public static double clock;
    public static ArrayList<Event> FEL = new ArrayList();
    public static ArrayList<Customer> callsInOnHoldQueue = new ArrayList<>();
    public static Customer customer;
    public static int numCustomer;
    public static int arrival = 1;
    public static int endAliceCall = 2;
    public static int endBobCall = 3;
    public static double utilAlice;
    public static double utilBob;
    public static int numQueueOnHold;
    public static int numInAlice;
    public static int numInBob;
    public static double answerQueueWaiting;
    public static double numWaited;
    public static double idleAlice;
    public static double idleBob;
    public static double totTime;
    public static double answerTimeAlice;
    public static double answerTimeBob;
    public static int interArrivalTime;


    public static void initialization(ArrayList FEL, Customer customer) {
        clock = 0.0;
        answerQueueWaiting = 0;
        Event newEvent = new Event(customer, arrival, 0);
        //First customer arrives to the system
        FEL.add(newEvent); // add it to the future event list

    }

    public static int calculateInterArrivalTime() { //calculate interarrivaltime
        Random rnd = new Random();
        int interArrivalRDA = rnd.nextInt(100) + 1;
        int interTime;
        //Asignment of random variables between 1-100(Range is calculated by cumulative probability)
        if (interArrivalRDA >= 1 && interArrivalRDA <= 25) {

            interTime = 1;
        } else if (interArrivalRDA >= 26 && interArrivalRDA <= 65) {

            interTime = 2;
        } else if (interArrivalRDA >= 66 && interArrivalRDA <= 85) {
            interTime = 3;

        } else {
            interTime = 4;
        }

        return interTime;
    }

    public static int calculateAliceServiceTime() { //calculate interarrivaltime
        Random rnd = new Random();
        int serviceAliceRDA = rnd.nextInt(100) + 1;
        int serviceAlice;
        //Asignment of random variables between 1-100(Range is calculated by cumulative probability)
        if (serviceAliceRDA >= 1 && serviceAliceRDA <= 30) {

            serviceAlice = 2;
        } else if (serviceAliceRDA >= 31 && serviceAliceRDA <= 58) {

            serviceAlice = 3;
        } else if (serviceAliceRDA >= 59 && serviceAliceRDA <= 83) {
            serviceAlice = 4;

        } else {
            serviceAlice = 5;
        }
        return serviceAlice;
    }

    public static int calculateBobServiceTime() { //calculate interarrivaltime
        Random rnd = new Random();
        int serviceBobRDA = rnd.nextInt(100) + 1;
        int serviceBob;
        //Asignment of random variables between 1-100(Range is calculated by cumulative probability)
        if (serviceBobRDA >= 1 && serviceBobRDA <= 35) {

            serviceBob = 3;
        } else if (serviceBobRDA >= 36 && serviceBobRDA <= 60) {

            serviceBob = 4;
        } else if (serviceBobRDA >= 61 && serviceBobRDA <= 80) {
            serviceBob = 5;

        } else {
            serviceBob = 6;
        }
        return serviceBob;
    }

    public static void callCenterArrival(ArrayList FEL, Customer customer) {

        if (numInAlice == 1 && numInBob == 1) {
            numQueueOnHold++;
            numWaited++;
            callsInOnHoldQueue.add(customer);
        } else if (numInAlice == 1 && numInBob == 0) {
            numInBob = 1;
            scheduleEndBobCall(FEL, customer);
        } else {
            numInAlice = 1;
            scheduleEndAliceCall(FEL, customer);
        }

        Customer c = new Customer(calculateInterArrivalTime() + clock);
        if (numCustomer > c.getCustomerNumber()) {
            scheduleArrival(FEL, c);
        }
        //schedule new arrival for the next customer

    }

    public static void callCenterAliceDeparture(ArrayList FEL, Customer customer) {
        if (numQueueOnHold > 0) { //If there is someone in the queue then take it
            numQueueOnHold--;
            Customer newCall = callsInOnHoldQueue.remove(0);
            scheduleEndAliceCall(FEL, newCall); // get first in the line
            answerQueueWaiting += clock - customer.getEnterTime(); //enter time for queue
        } else {
            numInAlice = 0; //update state
            idleAlice += Math.abs(clock - customer.getEnterTime());
        }
    }

    public static void callCenterBobDeparture(ArrayList FEL, Customer customer) {
        if (numQueueOnHold > 0) { //If there is someone in the queue then take it
            numQueueOnHold--;
            Customer newCall = callsInOnHoldQueue.remove(0);
            scheduleEndBobCall(FEL, newCall); // get first in the line
            answerQueueWaiting += clock - customer.getEnterTime(); //enter time for queue
        } else {
            numInBob = 0; //update state
            idleBob += Math.abs(clock - customer.getEnterTime());
        }

    }

    public static void scheduleEndAliceCall(ArrayList FEL, Customer customer) {
        answerTimeAlice = calculateAliceServiceTime(); //calculate alice service time
        utilAlice += answerTimeAlice; //time Alice is active
        Event endCall = new Event(customer, endAliceCall, answerTimeAlice + clock);//create a new event
        FEL.add(endCall);// add that event into future event list
    }

    public static void scheduleEndBobCall(ArrayList FEL, Customer customer) {
        answerTimeBob = calculateBobServiceTime(); //calculate Bo service time
        utilBob += answerTimeBob; //time Bob is active
        Event endCall = new Event(customer, endBobCall, answerTimeBob + clock);//create a new event
        FEL.add(endCall);// add that event into future event list
    }

    public static void scheduleArrival(ArrayList FEL, Customer customer) {
        Event nextArrival = new Event(customer, arrival, customer.getEnterTime());
        //get entertime for customer and create a new arrival event
        FEL.add(nextArrival); //add it to future event list
    }
/*
    static class Sort implements Comparator<Event> {
        //sorting for future event list according to time

        @Override
        public int compare(Event o1, Event o2) {
            return (Double.compare(o1.getTime(), o2.getTime()));
        }

    }
*/
    public static void printList() { //for printing snapshots
        String str = " ";
        for (int i = 0; i < callsInOnHoldQueue.size(); i++) {
            str += callsInOnHoldQueue.get(i).getCustomerNumber() + " ";
        }

        System.out.println("Clock: " + clock + " States: " + numQueueOnHold + " " + numInAlice + " "
                + numInBob + "Customer in queue: " + str + "Fel: " + FEL);
    }

    public static void statistics() { //print statistics

        /*Average caller delay (hold time) waiting for Alice or Bob to answer
         Percentage of callers whose calls go on hold
         Average caller delay (hold time) waiting for the callers whose calls go on hold
         Alice’s idle time percentage
         Bob’s idle time percentage. 
         */
        System.out.println("Average caller delay (hold time) waiting for Alice or Bob to answer: " + (double) answerQueueWaiting / numCustomer);
        System.out.println("Percentage of callers whose calls go on hold: " + (double) (numWaited / numCustomer) * 100);
        System.out.println("Average caller delay (hold time) waiting for the callers whose calls go on hold: " + answerQueueWaiting / numWaited);
        System.out.println("Alice’s idle time percentage" + (double) (idleAlice / totTime) * 100);
        System.out.println("Bob’s idle time percentage" + (double) (idleBob / totTime) * 100);
        System.out.println("Util Alice"+ (utilAlice / totTime)*100 + "Util Bob"+ (utilBob /totTime)*100);
        System.out.println("Total time a customer spends in system" + totTime /numCustomer);
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter number of calls: ");
        numCustomer = scan.nextInt(); //get user input
        Customer premier = new Customer(calculateInterArrivalTime());
        //create the first customer
        initialization(FEL, premier);
        int i = 0;
        while (FEL.size() > 0) { 

            Event newEvent = (Event) FEL.get(0); //imminent event
            FEL.remove(0);//delete first event in the list
            clock = newEvent.getTime(); //time iteration
            customer = newEvent.getCustomer();
            

            int type = newEvent.getType(); //get type :can be 1,2,3
            //arrival, end Alice , end Bob
            switch (type) {
                case 1:
                    callCenterArrival(FEL, customer);
                    i++;
                    break;
                case 2:
                    callCenterAliceDeparture(FEL, customer);
                    break;
                case 3:
                    callCenterBobDeparture(FEL, customer);
                    break;

            }
            Collections.sort(FEL, Comparator.comparing((Event p) -> p.getTime())
                    .thenComparing(p -> p.getType()));//sorting
            totTime=clock;
            //printList();
        }
        statistics();
        
        System.out.println("");
        
        
    }

}
