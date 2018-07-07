package com.example.user.calculator.ReversePolishEntry;

import java.util.ArrayList;
import java.util.Stack;

public class ParseComputation {

    public static String calculate(ArrayList<String> ms){

        java.util.Stack<Double> stack=new Stack<>();
        for(String x:ms){
            if(x.equals("+")){
                stack.push(stack.pop()+stack.pop());
            }
            else if(x.equals("-")){
                double a=stack.pop();
                double b=stack.pop();
                stack.push(b-a);
            }
            else if(x.equals("*")){
                stack.push(stack.pop()*stack.pop());
            }
            else if(x.equals("/")){
                double a=stack.pop();
                double b=stack.pop();
                stack.push(b/a);
            }
            else if (x.equals("u-")) stack.push(-stack.pop());
            else stack.push(Double.valueOf(x));
        }
        return stack.pop().toString();
    }
}