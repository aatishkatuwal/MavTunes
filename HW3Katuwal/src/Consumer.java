public class Consumer implements Runnable {
    private final Buffer buffer;
    private final String name;
    private final String color;

    public Consumer(Buffer buffer, String name, String color) {
        this.buffer = buffer;
        this.name = name;
        this.color = color;
    }

    public void run() {
        try {
            while (true) {
                String item = buffer.consume();
                int len = item.length();
                String reversed = new StringBuilder(item).reverse().toString();
                System.out.println(color + "[" + name + "] Consumed: " + item + ", Length: " + len + ", Reversed: " + reversed + "\u001B[0m");
                Thread.sleep(150); // simulate time delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
