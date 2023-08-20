package blockchain;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){
        System.out.println();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        for (int i = 1; i <= 12; i++) {
            try {
                executorService.submit(new Miner());
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        try {
            while (BlockDeque.blockDeque.size() < 15) {
                Thread.sleep(500); //Do not exit main method until you print 5 blocks!
                // Output is checked right after exiting main method.
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        executorService.shutdown();

    }
}
