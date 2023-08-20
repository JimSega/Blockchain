package blockchain;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public interface Letter {
    ArrayList<String> lettersArray = new ArrayList<>();
    Semaphore semaphore = new Semaphore(1, true);

    default void addLetter(String letter) {
        try {
            semaphore.acquire();
            lettersArray.add(Thread.currentThread().getName().replaceAll("pool-1-thread-", "")
                    .replaceAll("pool-2-thread-", "") + " : " + letter);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            semaphore.release();
        }
    }

    default String getAllLetters() {
        StringBuilder sendData = new StringBuilder();
        try {
            semaphore.acquire();
            for (String particular: lettersArray) {
                sendData.append("\n").append(particular);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            semaphore.release();
        }
        return sendData.toString();
    }

    default void clear(int fromIndex, int toIndex) {
        try {
            semaphore.acquire();
            lettersArray.subList(fromIndex, toIndex).clear();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            semaphore.release();
        }

    }
}
