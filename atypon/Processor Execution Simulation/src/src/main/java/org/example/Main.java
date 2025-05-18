package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        Scanner scan = new Scanner(System.in);
        String []input = scan.nextLine().strip().split(" ");
        int processors = Integer.valueOf(input[0]);
        int clockCycles = Integer.valueOf(input[1]);
        String filePath = input[2];

        Simulator simulator = new Simulator(processors,clockCycles,filePath);
        simulator.run();

    }
}


