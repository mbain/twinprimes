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

        //list for known primes
        ArrayList<Integer> knownPrimes = new ArrayList<>();

        //List for twin primes
        Map<Integer,Integer> twinPrimes = new HashMap<>();

        //progressMarker is used to display progress while calculating large numbers of primes.
        int progressMarker = maxNum / 80;

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
        System.out.println("Now finding all twin primes in the range.");

        //The following code finds the twin primes, then creates comma-separated strings to be used in the output file.
        List<String> pairList = new ArrayList<>();
        for(int i = 0; i < (knownPrimes.size() - 1); i++){
            Integer first = new Integer(knownPrimes.get(i));
            Integer second = new Integer(knownPrimes.get(i+1));

            if(second.intValue() - first.intValue() == 2){
                twinPrimes.put(first,second);
                String pair = first + ", " + second;
                pairList.add(pair);
            }

        }

        System.out.println("Found " + pairList.size() + " twin prime pairs.");

        //Save the twin primes to the CSV output file.
        try{
            Path file = Paths.get("outputFile.csv");
            System.out.println("Now saving the twin primes to " + file.toAbsolutePath().toString());
            Files.write(file, pairList, Charset.forName("UTF-8"));
        }catch(IOException e){
            System.out.println("An error occurred while attempting to save the file:\n" + e);
        }

        //If you wish to output the twin prime pairs to STDOUT, un-comment the following code:
        /*Set keys = twinPrimes.keySet();
        ArrayList<Integer> keyList = new ArrayList<Integer>(keys);

        for(int i = 0; i < keys.size(); i++){
            //System.out.println(keyList.get(i));
            //System.out.println(twinPrimes.get(keyList.get(i)));

            Integer twin1 = keyList.get(i);
            Integer twin2 = twinPrimes.get(twin1);

            long t1Val = twin1.intValue();
            long t2Val = twin2.intValue();

            //This displays a unique property of twin primes.
            System.out.println(t1Val + " * " + t2Val + " % 9 = " + ((t1Val * t2Val) % 9) );
            if((t1Val * t2Val) % 9 != 8 )
                System.out.println("Exception found: " + t1Val + ", " + t2Val);
        }*/
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
}
