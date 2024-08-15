package utils;

import models.User;

import java.io.*;
import java.util.List;

public class SerializationUtil {

    public static void serializeUsers(List<User> users, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(users);
        }
    }

    public static List<User> deserializeUsers(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<User>) ois.readObject();
        }
    }
}