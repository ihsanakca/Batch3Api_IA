package com.kraft;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        /**
         *  //Which method would you use to learn if a string is a rotation of another string?
         *     //rotation of car->car, arc, rca
         *     //sarı->sarı,arıs,rısa,ısar  -->en baştaki harfin en sona geçmesiyle oluşan yeni kelime..sıra bozulmuyor
         *     //ve en fazla harf sayısı kadar oluyor
         */

        System.out.println(isRotation("sarı", "ısar"));
        System.out.println(isRotation2("sarı", "ısar"));

    }

    public static boolean isRotation(String str,String str1){
        List<String> list=new ArrayList<>();
        for (int i = 0; i <str.length() ; i++) {
          str=  str.substring(1)+str.substring(0,1);
          list.add(str);
        }
        System.out.println("list = " + list);
        return list.contains(str1);
    }

    public static boolean isRotation2(String str,String str1){
        if (str.length()!=str1.length()) return false;
        return (str+str).contains(str1);
    }

    //sarı->sarı,arıs,rısa,ısar
    //sarısarı
}