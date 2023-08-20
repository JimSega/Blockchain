package blockchain;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class BlockStore extends BlockFactory implements BlockDeque, Letter{
    static ReadWriteLock lock = new ReentrantReadWriteLock();
    static Lock writeLock = lock.writeLock();

    static volatile private long uuidSimple = 0; //Checking the UUID of messages is very primitive,
    // but such a check is enough to complete the test task.

    public synchronized static long getUuidSimple() {
        return uuidSimple++;
    }


    @Override
    Block createBlock(Long id) {
        Letter letter = new BlockStore();
        long timeStart = new Date().getTime();
        int numberLetters = 0;
        String hashPreviousBlock;
        hashPreviousBlock = (id == 1) ? "0" : blockDeque.peekFirst().getHashBlock();
        BlockChain blockChain = null;
        long random = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        boolean start = true;
        String startZero = "0";
        String letters = null;
        synchronized (BlockStore.class) {
            startZero = (quantityRepeatZero == 0) ? "" : startZero.repeat(quantityRepeatZero);
        }
        while(start) {
            if (!blockDeque.isEmpty() && blockDeque.peekFirst().getId() != id - 1) {
                return null;
            } else {

                letters = (letter.getAllLetters().isEmpty()) ? "no messages" : letter.getAllLetters();
                numberLetters = lettersArray.size();
                blockChain = new BlockChain(id, new Date().getTime(), hashPreviousBlock);
                blockChain.setTimeStart(timeStart);
                blockChain.setHashBlock(id + blockChain.getTimeStamp() + hashPreviousBlock + random +
                        letters + "miner" + Thread.currentThread().getName()
                        .replaceAll("pool-1-thread-", "")
                        .replaceAll("pool-2-thread-", "") + " gets 100 VC");
                if(blockChain.getHashBlock().startsWith(startZero)) {

                    start = false;



                } else {
                    random = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

                    if (new Date().getTime() % 1000 == 0) {
                        try {
                            Message message = new Message(new Date() + " " + Math.random() + " random message",
                                    Long.toString(getUuidSimple()),
                                    "c:\\Users\\Администратор\\IdeaProjects\\Blockchain (Java)\\KeyPair\\privateKey");
                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                            outputStream.write(message.getList().get(0));
                            outputStream.write(message.getList().get(1));
                            if (new VerifyMessage()
                                    .verifySignature(outputStream.toByteArray(), message.getList().get(2),
                                            "c:\\Users\\Администратор\\IdeaProjects\\Blockchain (Java)\\KeyPair\\publicKey")
                             && uuidSimple >= message.getUuid()) {
                                outputStream.close();
                                letter.addLetter(new String(message.getList().get(0)));
                            } else {
                                outputStream.close();
                                letter.addLetter("Could not verify the signature.");
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }

        writeLock.lock();
        if(!blockDeque.isEmpty() && blockDeque.peekFirst().getId() != id - 1) {
            writeLock.unlock();
            return null;
        } else {
            synchronized (BlockStore.class) {

                quantityRepeatZero = (blockChain.getTimeStamp() - blockChain.getTimeStart() < 100) ?
                        ++quantityRepeatZero : (blockChain.getTimeStamp() - blockChain.getTimeStart() > 200) ?
                        --quantityRepeatZero : quantityRepeatZero;
            }

            try {
                Message message = new Message("Yho! I created previous block ",
                        Long.toString(getUuidSimple()),
                        "c:\\Users\\Администратор\\IdeaProjects\\Blockchain (Java)\\KeyPair\\privateKey");
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                outputStream.write(message.getList().get(0));
                outputStream.write(message.getList().get(1));
                if (new VerifyMessage()
                        .verifySignature(outputStream.toByteArray(), message.getList().get(2),
                                "c:\\Users\\Администратор\\IdeaProjects\\Blockchain (Java)\\KeyPair\\publicKey")
                && uuidSimple >= message.getUuid()) {
                    outputStream.close();
                    letter.addLetter(new String(message.getList().get(0)));
                } else {
                    outputStream.close();
                    letter.addLetter("Could not verify the signature.");}

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            blockChain.setLetters(letters);
            blockChain.setRandom(random);
            blockDeque.push(blockChain);
            letter.clear(0, numberLetters);
            writeLock.unlock();
            return blockChain;
        }
    }

}
