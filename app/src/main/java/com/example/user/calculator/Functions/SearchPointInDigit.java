package com.example.user.calculator.Functions;

public class SearchPointInDigit {

    private static final String OPERATOR = "÷/*-+";

    public boolean searchPoint(String ms){

        String lastChar =ms.substring(ms.length()-1);
        int flag=ms.length()-1;
        while(flag!=0) {
            if(OPERATOR.contains(lastChar) || lastChar.equals("("))
                return true;
            if(lastChar.equals("."))
                return false;
            flag--;
            lastChar=ms.substring(flag,flag+1);
        }
        return true;
    }
}