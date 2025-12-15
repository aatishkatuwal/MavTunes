import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.*;
import java.util.regex.*;

public class Buffer {
    private final Queue<String> buffer = new LinkedList<>();
    private final int capacity = 15;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    public void produce(String item) throws InterruptedException {
        lock.lock();
        try {
            while (buffer.size() == capacity) {
                notFull.await();
            }
            buffer.offer(item);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public String consume() throws InterruptedException {
        lock.lock();
        try {
            while (buffer.isEmpty()) {
                notEmpty.await();
            }
            String item = buffer.poll();
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}
