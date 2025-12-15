import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Producer implements Runnable {
    private final Buffer buffer;
    private final String[] data = {
            "Elephant", "giraffe", "Computer", "tv", "Television",
            "bananas", "Sunshine", "Rainbow", "Notebook", "Charging",
            "tornado", "Avocado", "goddess", "Paradise", "Calendar"
    };
    private final String color = "\u001B[35m"; // Purple
    private final Pattern validPattern = Pattern.compile("^[A-Z][a-zA-Z]{4,}$");

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {
        try {
            for (String item : data) {
                Matcher matcher = validPattern.matcher(item);
                if (matcher.matches()) {
                    buffer.produce(item);
                    System.out.println(color + "[Producer] Produced: " + item + "\u001B[0m");
                } else {
                    System.out.println(color + "[Producer] Skipped invalid string: " + item + "\u001B[0m");
                }
                Thread.sleep(100); // simulate time delay
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
