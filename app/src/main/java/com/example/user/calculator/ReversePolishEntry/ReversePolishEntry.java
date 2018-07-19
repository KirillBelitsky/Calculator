package com.example.user.calculator.ReversePolishEntry;

import java.util.ArrayList;
import java.util.Stack;
import java.util.StringTokenizer;

public class ReversePolishEntry {

    private static final String OPERATORS = "+-*/^√";
    public static boolean flag = true;
    public static boolean answer = true;
    private static final String delimiters = "()" + OPERATORS;


    private static int  priority(String ms) {

        if (ms.equals("("))
            return 1;
        if (ms.equals("+") || ms.equals("-"))
            return 2;
        if (ms.equals("*") || ms.equals("/"))
            return 3;
        return 4;
    }

    private static boolean isDelimiter(String str) {
        if(str.length()!=1)
            return false;
        for (int i = 0; i < delimiters.length(); i++) {
            if (str.charAt(0) == delimiters.charAt(i))
                return true;
        }
        return false;
    }

    private static boolean isOperator(String token) {
        if (token.equals("u-")) return true;
        for (int i = 0; i < OPERATORS.length(); i++) {
            if (token.charAt(0) == OPERATORS.charAt(i)) return true;
        }
        return false;
    }

    public boolean isAnswer() {
        return answer;
    }

    public static ArrayList<String> parseExpression(String ms) {
        int digit=0;
        ArrayList<String> List = new ArrayList<String>();
        Stack<String> stack = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(ms, delimiters, true);
        String prev = "";
        String curr = "";
        while (tokenizer.hasMoreTokens()) {
            curr = tokenizer.nextToken();
            if (!tokenizer.hasMoreTokens() && isOperator(curr)) {
                System.out.println("Некорректное выражение.");
                flag = false;
                answer=false;
                return List;
            }
            if(!tokenizer.hasMoreTokens() && curr.equals("(")){
                flag=false;
                answer=false;
                return List;
            }
            if (curr.equals(" ")) continue;
            else if (isDelimiter(curr)) {
                if (curr.equals("(")) stack.push(curr);
                else if (curr.equals(")")) {
                    while (!stack.peek().equals("(")) {
                        List.add(stack.pop());
                        if (stack.isEmpty()) {
                            //    System.out.println("Скобки не согласованы.");
                            flag = false;
                            answer=false;
                            return List;
                        }
                    }
                    stack.pop();
                   // if (!stack.isEmpty() ) {
                    //    List.add(stack.pop());
                   // }
                }
                else {
                    if (curr.equals("-") && (prev.equals("") || (isDelimiter(prev)  && !prev.equals(")")))) {
                        // унарный минус
                        curr = "u-";
                    }
                    else {
                        while (!stack.isEmpty() && (priority(curr) <= priority(stack.peek()))) {
                            List.add(stack.pop());
                        }

                    }
                    stack.push(curr);
                }

            }

            else {
                digit++;
                List.add(curr);
            }
            prev = curr;
        }

        while (!stack.isEmpty()) {
            if (isOperator(stack.peek())) List.add(stack.pop());
            else if(stack.peek().equals("(")) stack.pop();
            else {
                System.out.println("Скобки не согласованы.");
                flag = false;
                answer=false;
                return List;
            }
        }
        answer=true;
        return List;
    }

}