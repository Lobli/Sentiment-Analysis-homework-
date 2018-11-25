package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputReader {

    public Scanner scanner;
    public ArrayList<ArrayList<Integer>> IntLines = new ArrayList<>();
    public ArrayList<ArrayList<Integer>> IntLines2 = new ArrayList<>();


    public ArrayList<ArrayList<Integer>> readKnownLines() {
        scanner = new Scanner(System.in);

        for (int i=0; i<80000; i++) {
            String stringInput = scanner.nextLine();
            if (stringInput.isEmpty()==false) {

                ArrayList<Integer> IntLine;
                IntLine = createIntArrayFromLine(stringInput);

                IntLines.add(IntLine);
            }
            else{
                ArrayList<Integer> IntLine= new ArrayList<Integer>();
                //-2 means it is an empty line
                IntLine.add(-2);
                IntLines.add(IntLine);
            }
        }
        return IntLines;
    }


    public ArrayList<Integer> readPossibilities() {
        ArrayList<Integer> Possibilities= new ArrayList<>();
        for (int i=0;i<80000;i++) {
            String stringInput = scanner.nextLine();
            int Possibility = Integer.parseInt(stringInput);
            Possibilities.add(Possibility);
        }
        return Possibilities;
    }

    public ArrayList<ArrayList<Integer>> readUnknownLines() {

        for (int i=0; i<20000; i++) {
            String stringInput = scanner.nextLine();

            ArrayList<Integer> IntLine = new ArrayList<>();
            if (stringInput.isEmpty()==false){
                IntLine = createIntArrayFromLine(stringInput);
            }
            IntLines2.add(IntLine);
        }
        return IntLines2;
    }


    public ArrayList<Integer> createIntArrayFromLine(String s){
        ArrayList<Integer> IntegerLine = new ArrayList<Integer>();
        List<String> stringLine = Arrays.asList(s.split("\\s+"));

        for (String str : stringLine)
            IntegerLine.add(Integer.parseInt(str));

        return IntegerLine;
    }
}