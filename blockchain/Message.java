package blockchain;

import java.io.File;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

public class Message{
    private final List<byte[]> list;
    private final long uuid;


    public Message(String data, String uuid, String keyFile) throws Exception{
        this.uuid = Long.parseLong(uuid);
        list = new ArrayList<>();
        list.add(data.getBytes());
        list.add(uuid.getBytes());
        list.add(sign(data + uuid, keyFile));
    }
    public byte[] sign(String data, String keyFile) throws Exception{
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(getPrivate(keyFile));
        rsa.update(data.getBytes());
        return rsa.sign();
    }

    public PrivateKey getPrivate(String filename) throws Exception {
        byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public List<byte[]> getList () {
        return list;
    }

    public Long getUuid() {
    return uuid;
    }

}
