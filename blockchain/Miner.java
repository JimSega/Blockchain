package blockchain;

public class Miner implements Runnable, BlockDeque{

    @Override
    public void run() {
        BlockStore blockStore= new BlockStore();
        while (blockDeque.size() < 15) {
            Block blockChain = (blockDeque.isEmpty()) ? blockStore.orderBlock(1L) :
                    blockStore.orderBlock((blockDeque.peekFirst().getId() + 1));
        }

    }
}
