package com.app.flexfusion.repositories;

public class WaterIntakeCalculator {
    static double adjustedCalories;

    public static double calculateWaterIntake(double weight, double targetWeight, int age, double height, boolean isMale, double activityFactor) {
        double bmr = calculateBMR(weight, age, height, isMale);
        double adjustedBMR = bmr * activityFactor;
        adjustedCalories = adjustedBMR - (weight - targetWeight) * 7700; // Assuming 7700 kcal for 1 kg of weight loss
        double waterIntakeLiters = weight * 0.03; // Convert weight from kg to liters (1 kg = 1 liter)
        return Math.round(waterIntakeLiters * 100.0) / 100.0;
    }

    private static double calculateBMR(double weight, int age, double height, boolean isMale) {
        double bmr;
        if (isMale) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }
        return Math.round(bmr * 100.0) / 100.0;
    }

    public static double[] calculateDailyActivityTime(double currentWeight, double targetWeight, int numberOfDays, double caloriesBurnedPerHourForExercise, double caloriesBurnedPerHourForWalk) {
        // Calculate daily calorie deficit needed for weight loss
        double dailyCalorieDeficit = (currentWeight - targetWeight) * 7700 / numberOfDays;

        // Ensure non-negative calorie deficit
        dailyCalorieDeficit = Math.max(dailyCalorieDeficit, 0);

        // Calculate daily exercise time needed
        double exerciseTime = 0;
        if (caloriesBurnedPerHourForExercise != 0) {
            exerciseTime = dailyCalorieDeficit / caloriesBurnedPerHourForExercise;
        }
        // Ensure non-negative exercise time
        exerciseTime = Math.max(exerciseTime, 0);

        // Calculate daily walk time needed
        double walkTime = 0;
        if (caloriesBurnedPerHourForWalk != 0) {
            walkTime = dailyCalorieDeficit / caloriesBurnedPerHourForWalk;
        }
        // Ensure non-negative walk time
        walkTime = Math.max(walkTime, 0);

        // Return the exercise and walk times as an array
        return new double[]{Math.round(exerciseTime * 100.0) / 100.0, Math.round(walkTime * 100.0) / 100.0};
    }

    public static double calculateBMR(double weight, double height, int age, boolean isMale) {
        double bmr;
        if (isMale) {
            bmr = 66 + (13.7 * weight) + (5 * height) - (6.8 * age);
        } else {
            bmr = 655 + (9.6 * weight) + (1.8 * height) - (4.7 * age);
        }
        return Math.round(bmr * 100.0) / 100.0;
    }


}
