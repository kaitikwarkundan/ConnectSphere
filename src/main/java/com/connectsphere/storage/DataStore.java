package com.connectsphere.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DataStore {
    private static final String FILE_PATH = "data/network.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void save(Object data) throws IOException {
        new File("data").mkdirs();
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(data, writer);
            System.out.println("✅ Data saved to " + FILE_PATH);
        }
    }
}