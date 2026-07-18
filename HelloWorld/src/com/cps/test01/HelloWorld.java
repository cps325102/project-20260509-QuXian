package com.cps.test01;

import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int num=3;
        string str=switch (num){
            case 1,2,3->{
                yield"一"
            }
            case 4,5,6->{
                yield"四"
            }
            default -> {
                yield"其他"
            }
        }
        System.out.println(str);
    }
}