package com.example.user.calculator.Functions;

public class SkeplSearch {

    private static final String OPERATOR="+-*/";

    public boolean skeplsSearch(String ms){
        int flag=ms.length();
        String poslElement=ms.substring(ms.length()-1);
        while (flag!=0)
        {
            if(poslElement.equals("("))
                return true;
            flag--;
            poslElement=ms.substring(flag,flag+1);
        }
        return false;
    }
}