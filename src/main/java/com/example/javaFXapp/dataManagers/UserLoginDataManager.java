package com.example.javaFXapp.dataManagers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserLoginDataManager {
    public static void main(String[] args) {
        createFileIfNotExists();
    }
    private static final String USER_DATA_FILENAME = "user_data.csv";

    private static void createFileIfNotExists() {
        File file = new File(USER_DATA_FILENAME);
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println("Файл " + USER_DATA_FILENAME + " создан.");
                } else {
                    System.out.println("Не удалось создать файл " + USER_DATA_FILENAME);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> readUserDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILENAME))) {
            HashMap<String, String> data = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    data.put(parts[0], parts[1]);
                }
            }
            return data;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updateUserDataFile(HashMap<String, String> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILENAME))) {
            for (Map.Entry<String, String> entry : data.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}