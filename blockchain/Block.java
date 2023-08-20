package blockchain;


abstract class Block implements BlockDeque{
    private final long id;
    private final long timeStamp;
    private long timeStart;
    private final String hashPreviousBlock;
    private String hashBlock;
    private long random;
    private String letters;
    Block (long id, long timeStamp, String hashPreviousBlock) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.hashPreviousBlock = hashPreviousBlock;
    }

    public void setHashBlock(String input) {
        hashBlock = Sha.applySha256(input);
    }
    public void setLetters(String letters) {
        this.letters = letters;
    }
    public String getLetters() {
        return letters;
    }
    public void setRandom(long random) {
        this.random = random;
    }
    public void setTimeStart(long timeStart) {
        this.timeStart = timeStart;
    }
    public Long getTimeStart() {
        return timeStart;
    }
    public long getRandom() {
        return random;
    }

    public String getHashPreviousBlock() {
        return hashPreviousBlock;
    }

    public String getHashBlock() {
        return hashBlock;
    }

    public long getId() {
        return id;
    }
    public long getTimeStamp() {
        return timeStamp;
    }


}
