package com.company;

public class SmartWord {

    int WordID;
    int PosValue;
    int NegValue;

    SmartWord (int id) {
        WordID=id;
        PosValue =0;
        NegValue =0;
    }

    void incrementPos() {
        PosValue +=1;
    }

    void incrementNeg(){
        NegValue +=1;
    }

    void print() {
        System.out.print("WordID: "+WordID+" ");
        System.out.print("Pos: "+PosValue +" ");
        System.out.print("Neg: "+ NegValue +"\n");
    }
}