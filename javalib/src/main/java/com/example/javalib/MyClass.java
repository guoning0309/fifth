package com.example.javalib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;


public class MyClass {
    public static void main(String[] args) throws IOException{
        System.out.println("kkkkkk");
        Document doc = Jsoup.connect("https://www.usd-cny.com/bankofchina.htm").get();
        System.out.println("run:" +  doc.title());
    }

}