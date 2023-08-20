package blockchain;

import java.util.function.*;

class BlockValidate {
    public static BiFunction<Block, Block, Boolean> blockValidate = (block, previousBlock) -> previousBlock
            .getHashBlock()
            .equals(block.getHashPreviousBlock());

}
