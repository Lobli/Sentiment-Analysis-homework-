package com.company;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    static ArrayList<SmartWord> makeVocabulary (ArrayList<ArrayList<Integer>> LineArray, ArrayList<Integer> PossibilityArray){
        ArrayList<SmartWord> Vocabulary = new ArrayList<>();
        for (int i=0; i<LineArray.size();i++){

            ArrayList<Integer> CurrentLine = LineArray.get(i);
            // 0 or 1
            int PossibilityForCurrentLine = PossibilityArray.get(i);
            addWordsToVocabularyFromLine(CurrentLine,Vocabulary,PossibilityForCurrentLine);
        }
        return Vocabulary;
    }

    static ArrayList<SmartWord> addWordsToVocabularyFromLine (ArrayList<Integer> CurrentLine,ArrayList<SmartWord> Vocabulary,int PossibilityForCurrentLine){
        for (int j=0; j<CurrentLine.size();j++) {
            if (Vocabulary.size() != 0){
               TryToAddWordToVocabulary(Vocabulary, CurrentLine.get(j),PossibilityForCurrentLine);
            }
            else {
                int word = CurrentLine.get(j);
                AddNewWordToVocabulary(Vocabulary,word,PossibilityForCurrentLine);
            }
        }
        return Vocabulary;
    }

    static ArrayList<SmartWord> TryToAddWordToVocabulary (ArrayList<SmartWord> Vocabulary,int Word,int PossibilityForCurrentLine) {
        boolean contains=false;
        int current=0;
        for (int k = 0; k < Vocabulary.size(); k++) {
            // if the word is in the vocabulary
            if (Vocabulary.get(k).WordID == Word) {
                contains= true;
                current=k;
                break;
            }
        }
        // if the word is in the vocabulary ->
        if (contains==true) {
            if (PossibilityForCurrentLine == 1) {
                Vocabulary.get(current).incrementPos();
            }
            if (PossibilityForCurrentLine == 0){
                Vocabulary.get(current).incrementNeg();
            }
        }
        // if the word is NOT in the vocabulary ->
        else {
            AddNewWordToVocabulary(Vocabulary,Word, PossibilityForCurrentLine);
        }
        return Vocabulary;
    }

    static ArrayList<SmartWord> AddNewWordToVocabulary (ArrayList <SmartWord> Vocabulary, int Word,int PossibilityForCurrentLine){
        SmartWord word = new SmartWord(Word);
        if (PossibilityForCurrentLine == 1) {
            word.incrementPos();
        }
        if (PossibilityForCurrentLine == 0){
            word.incrementNeg();
        }
        Vocabulary.add(word);

        return Vocabulary;
    }

    //Compares the possibility value that the line is positive or negative and writes out the solution
    static void PrintLineSolution(double PosLinePos,double NegLinePos) {
        if (PosLinePos==NegLinePos){
            System.out.println(1);
        }
        if(PosLinePos<NegLinePos){
            System.out.println(0);
        }
        if(PosLinePos>NegLinePos){
            System.out.println(1);
        }
    }

    static void PrintLineSolutionWithDetails(double PosLinePos,double NegLinePos) {
        int value = Double.compare(PosLinePos,NegLinePos);
        if (value==0){
            System.out.print("Same value : ");
            System.out.println(0);
            System.out.println();
        }
        if(value<0){
            System.out.print("Neg is higher : ");
            System.out.println(0);
            System.out.println();
        }
        if(value>0){
            System.out.print("Pos is higher : ");
            System.out.println(1);
            System.out.println();
        }
    }

    static void calculateSolution (ArrayList<ArrayList<Integer>> UnknownLineArray,ArrayList<SmartWord> Vocabulary, ArrayList<Integer> PossibilityArray) {

        int AllPos=0;
        int AllNeg=0;

        int AllPosLine=0;
        int AllNegLine=0;

        //Sum of the positive and negative values
        for(int i=0; i<Vocabulary.size();i++){
            AllPos+= Vocabulary.get(i).PosValue;
            AllNeg+= Vocabulary.get(i).NegValue;
        }

        //Sum of the positive and negative lines
        for(int i=0; i<PossibilityArray.size();i++){
            if(PossibilityArray.get(i)==1){
            AllPosLine+=1;
            }
            if(PossibilityArray.get(i)==0) {
                AllNegLine += 1;
            }
        }

        /*
        System.out.println("Allpos: "+AllPos);
        System.out.println("Allneg: "+AllNeg);

        System.out.println("AllPosLine: "+AllPosLine);
        System.out.println("AllNegLine: "+AllNegLine+"\n");
        */

        for (int i=0;i<UnknownLineArray.size();i++){
            ArrayList<Integer> PosValuesInCurrentLine = new ArrayList<>();
            ArrayList<Integer> NegValuesInCurrentLine = new ArrayList<>();
            ArrayList<Integer> CurrentLine = UnknownLineArray.get(i);

            /*itt kigyujtom, hogy az egyes szavak amik benne vannak
             a sorban hanyszor fordulnak elo a tanitomintaban a
             pozitiv vagy a negativ sorokban es csinalok egy pozitiv ertekes tombot (PosValuesInCurrentLine)
             meg egy negativosat (PosValuesInCurrentLine)
            */

            for(int j=0;j<CurrentLine.size();j++){
                boolean contains=false;
                int CurrentWord = CurrentLine.get(j);
                if(contains==false) {
                    for (int k = 0; k < Vocabulary.size(); k++) {
                        if (CurrentWord == Vocabulary.get(k).WordID) {
                            contains=true;
                            SmartWord EqualSmartWord = Vocabulary.get(k);
                            // How many times was a word in positive or negative lines?
                            PosValuesInCurrentLine.add(EqualSmartWord.PosValue);
                            NegValuesInCurrentLine.add(EqualSmartWord.NegValue);
                            break;
                        }
                    }
                }
                if(contains==false){
                    // if we can't find the word int the vocabulary it was 0 times in pos/neg lines

                    PosValuesInCurrentLine.add(0);
                    NegValuesInCurrentLine.add(0);
                }
            }

            //calculate the possibility that a line is positive/negative (allPositiveLines/allLines) and allNegativeLines/allLines
            double PositivePossibilityForLine =(double)AllPosLine/PossibilityArray.size();
            double NegativePossibilityForLine =(double)AllNegLine/PossibilityArray.size();

            /*
            System.out.println("PositivePossibilityForLine: "+PositivePossibilityForLine);
            System.out.println("NegativePossibilityForLine: "+ NegativePossibilityForLine);
            */

                //calculate pos/neg value for current line
                for(int j = 0; j < CurrentLine.size(); j++){

                    double CurrentWordPos= (double)(PosValuesInCurrentLine.get(j)+1)/(Vocabulary.size()+AllPos);
                    double CurrentWordNeg= (double)(NegValuesInCurrentLine.get(j)+1)/(Vocabulary.size()+AllNeg);

                    /*
                    System.out.println("\n"+"CurrentWord:"+ CurrentLine.get(j));
                    System.out.println("CurrentWordPos: "+CurrentWordPos);
                    System.out.println("CurrentWordNeg: "+ CurrentWordNeg+"\n");
                    */

                    PositivePossibilityForLine = PositivePossibilityForLine*CurrentWordPos;
                    NegativePossibilityForLine = NegativePossibilityForLine*CurrentWordNeg;
                }

                /*
                System.out.println((i+1)+". ROW :");
                //System.out.println("PositivePossibilityForAllLines: "+PositivePossibilityForAll+" NegativePossibilityForAllLines: "+NegativePossibilityForAll);
                System.out.println("PosValue:"+PositivePossibilityForLine);
                System.out.println("NegValue:"+NegativePossibilityForLine);
                */

                //compare the values and than print out the solution
                PrintLineSolution(PositivePossibilityForLine,NegativePossibilityForLine);
                //PrintLineSolutionWithDetails(PositivePossibilityForLine,NegativePossibilityForLine);
        }
    }

    public static void main(String[] args) {

        InputReader reader = new InputReader();

        //array of the lines that we learn
        ArrayList<ArrayList<Integer>> KnownLineArray = reader.readKnownLines();


        //array of the possibilities that we learn
        ArrayList<Integer> PossibilityArray = reader.readPossibilities();

        //array of the known words
        ArrayList<SmartWord> Vocabulary;

        // Make the Vocabulary from the known lines and possibilities
        Vocabulary=makeVocabulary(KnownLineArray,PossibilityArray);
        ArrayList<ArrayList<Integer>> UnknownLineArray = reader.readUnknownLines();

        /*
        System.out.print("KnownLineArray:");
        System.out.println(KnownLineArray+"\n");

        System.out.print("PossibilityArray: ");
        System.out.println(PossibilityArray+"\n");

        System.out.print("UnknownLineArray:");
        System.out.println(UnknownLineArray+"\n");


        System.out.println("\n"+"Vocabulary:");
        for (int i = 0; i< Vocabulary.size(); i++){
            Vocabulary.get(i).print();
        }
        System.out.println();
        */


        calculateSolution(UnknownLineArray,Vocabulary,PossibilityArray);

    }
}