package com.csc;
import java.io.*;
import java.util.ArrayList;

public class CheeseAnalyzer {
    String file = "src\\cheese_data.csv"; //read in file
    BufferedReader reader = null; //initialize reader
    String line = "";
    ArrayList<ArrayList<String>> storeData = new ArrayList<>(); //arraylist to store data from csv
    ArrayList<String> cheeseID = new ArrayList<>(); //not sure how efficient this was, create list for all data
    ArrayList<String> manuProvCode = new ArrayList<>();
    ArrayList<String> manuTypeEn = new ArrayList<>();
    ArrayList<String> moisturePercent = new ArrayList<>();
    ArrayList<String> flavorEnt = new ArrayList<>();
    ArrayList<String> charEnt = new ArrayList<>();
    ArrayList<String> organic = new ArrayList<>();
    ArrayList<String> catTypeEn = new ArrayList<>();
    ArrayList<String> milkTypeEn = new ArrayList<>();
    ArrayList<String> milkTreatTypeEn = new ArrayList<>();
    ArrayList<String> rindType = new ArrayList<>();
    ArrayList<String> cheeseName = new ArrayList<>();
    ArrayList<String> fatLevel = new ArrayList<>();

    int numOfPasteurized = 0;
    int numOfRaw = 0;
    int numOfMoistOrganic = 0;
    int numOfCow = 0;
    int numOfEwe = 0;
    int numOfGoat = 0;
    int numOfBuffalo = 0;
    //buff = 0, cow = 1, ewe = 2, goat = 3
    int[] milkTypeCount = new int[4];
    String mostCommonAnimal = "";
    int maxCount = 0;
    int idxOfCount = 0;



    void intializeList(){
        storeData.add(cheeseID);
        storeData.add(manuProvCode);
        storeData.add(manuTypeEn);
        storeData.add(moisturePercent);
        storeData.add(flavorEnt);
        storeData.add(charEnt);
        storeData.add(organic);
        storeData.add(catTypeEn);
        storeData.add(milkTypeEn);
        storeData.add(milkTreatTypeEn);
        storeData.add(rindType);
        storeData.add(cheeseName);
        storeData.add(fatLevel);
    }

    void calcMilkTreatmentType(){
        for (String s : milkTreatTypeEn) {
            if (s.equals("Pasteurized")) {
                numOfPasteurized++;
            }
            if (s.equals("Raw Milk")) {
                numOfRaw++;
            }
        }
    }

    void calcAnimalType(){
        for (String s : milkTypeEn) {
            if (s.contains("Cow")) {
                if (s.contains("Buffalo")) {
                    numOfBuffalo++;
                } else {
                    numOfCow++;
                }
            }

            if (s.contains("Ewe")) {
                numOfEwe++;
            }

            if (s.contains("Goat")) {
                numOfGoat++;
            }
        }
    }

    void calcMoistOrganic(){
        for (int i = 0; i < organic.size(); i++) {
            if (organic.get(i).equals("1")) {
                try {
                    double percent = Double.parseDouble(moisturePercent.get(i));
                    if (percent > 41.0) {
                        numOfMoistOrganic++;
                    }
                } catch (NumberFormatException e) {
                }

            }
        }
    }

    void calcAnimalIdx() {
        for (int i = 0; i < milkTypeCount.length; i++) {
            if (milkTypeCount[i] > maxCount) {
                maxCount = milkTypeCount[i];
                idxOfCount = i;
            }
        }
    }

    void calcMostCommonAnimal() {
        if (idxOfCount == 0) {
            mostCommonAnimal = "Buffalo Cow";
        }

        if (idxOfCount == 1) {
            mostCommonAnimal = "Cow";
        }

        if (idxOfCount == 2) {
            mostCommonAnimal = "Ewe";
        }

        if (idxOfCount == 3) {
            mostCommonAnimal = "Goat";
        }
    }

    void setAnimalCounts(){
        milkTypeCount[0] = numOfBuffalo;
        milkTypeCount[1] = numOfCow;
        milkTypeCount[2] = numOfEwe;
        milkTypeCount[3] = numOfGoat;
    }

    public static void main(String[] args) {
        CheeseAnalyzer cheeseAnalyzer = new CheeseAnalyzer();
        String file = "src\\cheese_data.csv"; //read in file
        BufferedReader reader = null; //initialize reader
        String line = "";
        cheeseAnalyzer.intializeList();
        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skip header first line
            while ((line = reader.readLine()) != null) { //iterate through lines
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //store data in array
                for (int i = 0; i < cheeseAnalyzer.storeData.size(); i++) {
                    cheeseAnalyzer.storeData.get(i).add(data[i]); //add data to appropriate list
                }
            }
        } catch (Exception e) { //catch exception
            e.printStackTrace();
        } finally {
            try {
                reader.close(); //close reader
            } catch (IOException e) { //catch exception
                throw new RuntimeException(e);
            }
        }

        cheeseAnalyzer.calcMilkTreatmentType();
        cheeseAnalyzer.calcMoistOrganic();
        cheeseAnalyzer.calcAnimalType();
        cheeseAnalyzer.setAnimalCounts();
        cheeseAnalyzer.calcAnimalIdx();
        cheeseAnalyzer.calcMostCommonAnimal();

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write("'Am I a Muenster for Making Cheese Puns?' Output File");
            writer.append("\n Number of cheeses that use pasteurized milk: " + cheeseAnalyzer.numOfPasteurized);
            writer.append("\n Number of cheeses that use raw milk: " + cheeseAnalyzer.numOfRaw);
            writer.append("\n Number of organic cheeses that have a moisture percentage greater than 41.0%: "
                            + cheeseAnalyzer.numOfMoistOrganic);
            writer.append("\n Type of animal milk that most of the cheeses come from in Canada: "
                            + cheeseAnalyzer.mostCommonAnimal);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
