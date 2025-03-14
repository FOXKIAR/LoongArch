package cn.foxkiar.loongarch.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base {
    public static String encoder(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String encoder(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    public static String decoder(String str) {
        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
    }

    public static byte[] decoderToBytes(String str) {
        return Base64.getDecoder().decode(str);
    }

    public static <T extends Serializable> byte[] serializable(T object) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
        objectOutputStream.writeObject(object);
        return byteOutputStream.toByteArray();
    }

    public static Object deserializable(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(bytesInputStream);
        return objectInputStream.readObject();
    }
}
