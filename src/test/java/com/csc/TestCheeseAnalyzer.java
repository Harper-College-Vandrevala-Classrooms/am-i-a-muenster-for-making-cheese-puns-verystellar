package com.csc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestCheeseAnalyzer {

    CheeseAnalyzer cheese;

    @BeforeEach
    void setUp() {;
        cheese = new CheeseAnalyzer();
        String file = "src\\cheese_data.csv"; //read in file
        BufferedReader reader = null; //initialize reader
        String line = "";
        cheese.intializeList();
        try {
            reader = new BufferedReader(new FileReader(file));
            reader.readLine(); //skip header first line
            while ((line = reader.readLine()) != null) { //iterate through lines
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //store data in array
                for (int i = 0; i < cheese.storeData.size(); i++) {
                    cheese.storeData.get(i).add(data[i]); //add data to appropriate list
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

        cheese.calcMilkTreatmentType();
        cheese.calcMoistOrganic();
        cheese.calcAnimalType();
        cheese.setAnimalCounts();
        cheese.calcAnimalIdx();
        cheese.calcMostCommonAnimal();
    }

    // Numbers were calculated by hand through excel using the unique and countif functions

    @Test
    void testZero() {
        assertEquals(cheese.mostCommonAnimal,"Cow");
    }
    @Test
    void testOne() {
        assertEquals(cheese.cheeseID.size(), 1042);
    }

    @Test
    void testTwo() {
        assertEquals(cheese.numOfPasteurized,800);
    }

    @Test
    void testThree() {
        assertEquals(cheese.numOfRaw,115);
    }

    @Test
    void testFour() {
        assertEquals(cheese.numOfEwe,69);
    }

    @Test
    void testFive() {
        assertEquals(cheese.numOfCow,761);
    }

    @Test
    void testSix() {
        assertEquals(cheese.numOfBuffalo,2);
    }

}
