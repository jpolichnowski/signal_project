package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] AlertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {
        AlertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (AlertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    AlertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert",
                            "resolved"); // 100 columns limit - Style Guide Violation
                }
            } else {
                // Lambda -> lambda - lowerCamelCase Style Guide Violation
                double lambda = 0.1; // Average rate (alerts per period),
                                     // adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    AlertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert",
                            "triggered"); // 100 columns limit - Style Guide Violation
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " +
                    patientId); // 100 columns limit - Style Guide Violation
            e.printStackTrace();
        }
    }
}
