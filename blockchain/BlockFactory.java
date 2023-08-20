package blockchain;


abstract class BlockFactory {
    abstract Block createBlock(Long id);
    volatile static int quantityRepeatZero = 0;
    Block orderBlock (Long id) {
        Block block = createBlock(id);
        if (block == null) {
            return null;
        } else {
            synchronized (BlockStore.class) {
                long timeGenerate = block.getTimeStamp() - block.getTimeStart();
                String increment = (timeGenerate < 100) ? "N was increased to " + quantityRepeatZero :
                        (timeGenerate > 200) ? "N was decreased by 1" : "N stays the same";
                System.out.printf("""
                                Block:
                                Created by miner%s
                                miner%s gets 100 VC
                                Id: %d
                                Timestamp: %d
                                Magic number: %d
                                Hash of the previous block:
                                %s
                                Hash of the block:
                                %s
                                Block data: %s
                                Block was generating for %d seconds
                                %s

                                """,
                        Thread.currentThread().getName().replaceAll("pool-1-thread-", "")
                                .replaceAll("pool-2-thread-", ""),
                        Thread.currentThread().getName().replaceAll("pool-1-thread-", "")
                                .replaceAll("pool-2-thread-", ""),
                        id, block.getTimeStamp(), block.getRandom(), block.getHashPreviousBlock(), block.getHashBlock(),
                        block.getLetters(), timeGenerate / 1000, increment);
            }
            return block;
        }
    }
}
