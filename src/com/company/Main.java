/*
* Twin Primes Calculator
* Copyright 2017 Miles Bainbridge
* Provided under the MIT License - license details in the accompanying LICENSE file.
*
* */

package com.company;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Welcome to the Twin Prime generator.");
        //Get the maximum number.
        int maxNum = getInput();


        System.out.println("Calculating prime numbers.");


        //List for twin primes
        Map<Integer,Integer> twinPrimes = new HashMap<>();

        //Find all prime numbers in the range
        ArrayList<Integer> knownPrimes = findPrimes(maxNum);

        //Find all twin primes among the known primes
        ArrayList<String> pairList = findTwins(knownPrimes, twinPrimes);

        //Save the output file
        saveFile(pairList);


        //If you would like to display all twin prime pairs in the range on-screen, un-comment the following line.
        //displayTwins(twinPrimes);


    }

    private static int getInput(){
        int maxNum = 0;

        System.out.print("Enter the maximum number: ");
        Scanner input = new Scanner(System.in);
        //input.nextLine();
        if(input.hasNextInt())
            maxNum = input.nextInt();
        else{
            while(!input.hasNextInt()){
                System.out.print("\nPlease enter a whole number greater than 2: ");
                input.nextLine();
                if(input.hasNextInt())
                    maxNum = input.nextInt();
            }
        }

        if(maxNum < 3){
            System.out.println("\nThe number must be greater than 2.");
            return getInput();
        }

        return maxNum;
    }

    private static ArrayList<Integer> findPrimes(int maxNum){

        //list for known primes
        ArrayList<Integer> knownPrimes = new ArrayList<>();

        //progressMarker is used to display progress while calculating large numbers of primes.
        int progressMarker = maxNum / 80;

        //setup progress display
        if(maxNum > 1000000){
            System.out.print("Start");
            for(int i = 0; i < 72; i++)
                System.out.print(" ");
            System.out.print("End\n");
            System.out.print("|");
            for(int i = 0; i < 78; i++)
                System.out.print(" ");
            System.out.print("|\n");
        }


        long startTime = System.nanoTime();
        //populate knownPrime list
        knownPrimes.add(3);
        for(int i = 3; i <= maxNum; i+=2){
            int srt = (int) Math.sqrt(i);

            //output progress bar
            if(maxNum > 1000000 && i % progressMarker == (progressMarker - 1))
                System.out.print("•");

            //find primes
            for(int j = 2; j <= srt; j++){
                if((i % j) == 0){
                    //not a prime
                    break;
                }
                else if(j == srt){
                    //This is a prime number.
                    knownPrimes.add(i);
                }

            }
        }
        System.out.println("\nAll prime numbers up to " + maxNum + " have been calculated.  ");
        System.out.println(knownPrimes.size() + " total prime numbers found.");
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //divide by 1000000 to get milliseconds.
        double seconds = (double) duration / (double) 1000000000;
        System.out.println("Finding primes took " + seconds + " seconds." );

        return knownPrimes;
    }

    private static ArrayList<String> findTwins(ArrayList<Integer> knownPrimes, Map<Integer,Integer> twinPrimes){


        System.out.println("Now finding all twin primes in the range.");

        long startTime = System.nanoTime();

        //The following code finds the twin primes, then creates comma-separated strings to be used in the output file.
        ArrayList<String> pairList = new ArrayList<>();
        for(int i = 0; i < (knownPrimes.size() - 1); i++){
            Integer first = new Integer(knownPrimes.get(i));
            Integer second = new Integer(knownPrimes.get(i+1));

            if(second.intValue() - first.intValue() == 2){
                twinPrimes.put(first,second);
                String pair = first + ", " + second;
                pairList.add(pair);
            }

        }
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        double seconds = (double) duration / (double) 1000000000;

        System.out.println("Found " + pairList.size() + " twin prime pairs.");
        System.out.println("Finding twin prime pairs took " + seconds + " seconds." );

        return pairList;
    }

    private static void saveFile(ArrayList<String> pairList){
        //Save the twin primes to the CSV output file.
        try{
            Path file = Paths.get("outputFile.csv");
            System.out.println("Now saving the twin primes to " + file.toAbsolutePath().toString());
            Files.write(file, pairList, Charset.forName("UTF-8"));
        }catch(IOException e){
            System.out.println("An error occurred while attempting to save the file:\n" + e);
        }

    }

    private static void displayTwins(Map<Integer,Integer> twinPrimes){
        //Display all twin prime pairs
        Set keys = twinPrimes.keySet();
        ArrayList<Integer> keyList = new ArrayList<Integer>(keys);
        Collections.sort(keyList);

        for(int i = 0; i < keys.size(); i++){
            //System.out.println(keyList.get(i));
            //System.out.println(twinPrimes.get(keyList.get(i)));

            Integer twin1 = keyList.get(i);
            Integer twin2 = twinPrimes.get(twin1);

            long t1Val = twin1.intValue();
            long t2Val = twin2.intValue();

            //This displays an interesting property of twin primes.
            System.out.println(t1Val + " * " + t2Val + " % 9 = " + ((t1Val * t2Val) % 9) );
            if((t1Val * t2Val) % 9 != 8 )
                System.out.println("Exception found: " + t1Val + ", " + t2Val);
        }
    }

}
