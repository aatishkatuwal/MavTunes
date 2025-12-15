public class HW3 {
    public static void main(String[] args) {
        Buffer buffer = new Buffer();

        Thread producer = new Thread(new Producer(buffer));
        Thread consumer1 = new Thread(new Consumer(buffer, "Consumer-1", "\u001B[32m")); // Green
        Thread consumer2 = new Thread(new Consumer(buffer, "Consumer-2", "\u001B[34m")); // Blue
        Thread consumer3 = new Thread(new Consumer(buffer, "Consumer-3", "\u001B[31m")); // Red

        producer.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }
}
