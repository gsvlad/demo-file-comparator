package com.wix.demo;

import com.wix.demo.algo.FileComparator;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2
                || !new File(args[0]).isFile()
                || !new File(args[1]).isFile()) {

            System.out.println("Wrong params: 2 valid text files required");
            return;
        }

        new FileComparator().startComparing(args[0], args[1]);
    }
}
