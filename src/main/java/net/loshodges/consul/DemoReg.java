package net.loshodges.consul;

public class DemoReg {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Main hit, sleeping for 20s...");
        System.out.println("run curl localhost:8500/health/service/{serviceId} to verify");

        Thread.sleep(20 * 1000);
        System.out.println("Main exiting, sleeping for 20s...");

    }
}
