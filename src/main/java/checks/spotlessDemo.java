package de.hsbi.prog2;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;  // unused import

public class SpotlessDemo {
    public static void main(String[]args){
        List<String> names=new ArrayList<>();
        names.add("Alice");
        names.add(   "Bob"   );
        for(String n:names){
            System.out.println(  "Hello, "+n  );
        }
    }

    private static int    add(int a,   int b){
        return a+b;}
}