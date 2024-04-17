package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;
// class name should start with a capital letter - UpperCamelCase Style Guide Violation
public class FileOutputStrategy implements OutputStrategy {

    private String BaseDirectory;

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    public FileOutputStrategy(String baseDirectory) {

        this.BaseDirectory = baseDirectory;
    }

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(BaseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k ->  // 100 columns limit - Style Guide Violation
                Paths.get(BaseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), // 100 columns limit - Style Guide Violation
                        StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                    patientId, timestamp, label, data); // 100 columns limit - Style Guide Violation
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}