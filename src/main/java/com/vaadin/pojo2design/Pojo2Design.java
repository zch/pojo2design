package com.vaadin.pojo2design;

import com.github.javaparser.ParseException;

import java.io.File;
import java.io.IOException;

/**
 * Created by jonatan on 15/09/15.
 */
public class Pojo2Design {

    public static void main(String[] args) {
        try {
            System.out.println(new Generator(new File(args[0])).generate());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
