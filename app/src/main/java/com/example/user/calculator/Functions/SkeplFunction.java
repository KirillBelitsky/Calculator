package com.example.user.calculator.Functions;

public class SkeplFunction {

    private static final String OPERATOR="+-*/";

    public boolean skeplsSearch(String ms){
        int flag=ms.length()-1;
        String poslElement = ms.substring(ms.length() - 1);
        while (flag!=0)
        {
            if(poslElement.equals("("))
                return true;
            flag--;
            poslElement=ms.substring(flag,flag+1);
        }
        poslElement=ms.substring(0,1);
        if(poslElement.equals("("))
            return true;
        return false;
    }

    public boolean closeSkepl(String ms){

        int open=0,close=0;
        String temp;
        for(int i=0;i<ms.length()-1;i++){
            temp=ms.substring(i,i+1);
            if(temp.equals("("))
                open++;
            else if(temp.equals(")"))
                close++;
        }
        if(open>close+1)
            return true;
        else return false;
    }
}