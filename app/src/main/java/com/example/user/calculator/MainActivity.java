package com.example.user.calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.Menu;
import android.support.v7.app.AppCompatDelegate;


import com.example.user.calculator.ReversePolishEntry.ParseComputation;
import com.example.user.calculator.ReversePolishEntry.ReversePolishEntry;
import com.example.user.calculator.Functions.SearchPointInDigit;
import com.example.user.calculator.Functions.SkeplFunction;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Activity process;
    private TextView resulttext;
    private TextView resultEnd;
    private TextView expression;
    private boolean color=true;
    private static final String OPERATORS = "÷/*-+";
    private static final String Digit = "0123456789";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = this.getSharedPreferences("Preference", MODE_PRIVATE);
        if (preferences.getBoolean("appTheme", true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        setContentView(R.layout.activity_simple);
        resulttext = findViewById(R.id.result);
        expression = findViewById(R.id.expression);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_activity,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_change_theme:
                change_theme(item);
                return true;
            case R.id.action_about:
                Intent intent=new Intent(this,Info.class);
                startActivity(intent);
                return true;
            case R.id.action_feedback:
                Intent Intent =new Intent(this, FeedbackActivity.class);
                startActivity(Intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_delenie:
            case R.id.button_divide:
            case R.id.button_addition:
            case R.id.button_multiple:
                operations(view);
                break;

            case R.id.button_delete_all:
                clearAll();
                break;

            case R.id.button_delete:
                clear();
                break;

            case R.id.button_equally:
                calculate();
                break;

            case R.id.button_skepls:
                skepls(view);
                break;

            case R.id.button_point:
                point();
                break;

            case R.id.button_one:
            case R.id.button_two:
            case R.id.button_three:
            case R.id.button_four:
            case R.id.button_five:
            case R.id.button_six:
            case R.id.button_seven:
            case R.id.button_eight:
            case R.id.button_nine:
            case R.id.button_zero:
                numbers(view);
                break;
        }
    }

    public void operations(View view) {

        String ms = expression.getText().toString();
        if (ms.equals("")){
            if(view.getId()==R.id.button_divide){
                ms+="-";
                expression.append(ms);
                return;
            }
            else
                return;
        }
        String posledniy = ms.substring(ms.length() - 1);
       if (posledniy.equals("."))
            return;
       else if(posledniy.equals("-") && ms.length()==1)
           return;
       else if (posledniy.equals("(")){
           if(view.getId()==R.id.button_divide){
               ms+="-";
               expression.setText(ms);
               return;
           }
           else
               return;
       }
       else if(Digit.contains(posledniy)){
           expression.append(((Button) view).getText().toString());
           return;
       }
        if (OPERATORS.contains(posledniy)){
           String predposledniy=ms.substring(ms.length()-2,ms.length()-1);
           if(posledniy.equals("-") && predposledniy.equals("-"))
               return;
           else if(posledniy.equals("-") && OPERATORS.contains(predposledniy))
               return;
           else if(view.getId()==R.id.button_divide){
                if(OPERATORS.contains(predposledniy))
                    return;
                else{
                    ms+="-";
                    expression.setText(ms);
                }
                return;
            }
            ms=ms.substring(0,ms.length()-1);
            expression.setText(ms);
            expression.append(((Button) view).getText().toString());
            return;
        }
        expression.append(((Button) view).getText().toString());
    }

    public void clearAll() {
        expression.setText("");
        resulttext.setText("");
    }

    public void clear() {
        String ms = expression.getText().toString();
        if(ms.equals(""))
            return;
        expression.setText(ms.substring(0, ms.length() - 1));
    }

    public void point() {

        SearchPointInDigit point = new SearchPointInDigit();
        String ms = expression.getText().toString();

        if (ms.equals("")) {
            ms += "0.";
            expression.append(ms);
            return;
        }
        String poslElement = ms.substring(ms.length() - 1);

        if (poslElement.equals("(")) {
            ms += "0.";
            expression.setText((ms));
            return;
        }
        if (poslElement.equals(")")) {
            ms += "*0.";
            expression.append((ms));
            return;
        }
        if (OPERATORS.contains(poslElement) || poslElement.equals(".")) {
            return;
        }
        if (!(point.searchPoint(ms)) && Digit.contains(poslElement)) {
            return;
        }
        expression.append(".");
        return;
    }

    public void skepls(View view) {

        String ms = expression.getText().toString();
        if (!ms.equals("")) {
            SkeplFunction skeplFunction = new SkeplFunction();
            String poslElement = ms.substring(ms.length() - 1);
            if (poslElement.equals("(")) {
                expression.append("(");
                return;
            }
            else if (poslElement.equals(".")) {
                return;
            }
            else if (poslElement.equals(")")) {
                if(skeplFunction.closeSkepl(ms)){
                    expression.append(")");
                }
                else
                    expression.append("*(");
                return;
            }
            else if (Digit.contains(poslElement) && !skeplFunction.skeplsSearch(ms)) {
                expression.append("*(");
                return;
            }
            else if (Digit.contains(poslElement) && skeplFunction.skeplsSearch(ms)) {
                expression.append(")");
                return;
            }
            expression.append("(");
            return;
        }
        else {
            expression.append("(");
        }

    }

    public void numbers(View view) {

        String ms = expression.getText().toString();
        if (!ms.equals("")) {
            String posledniy = ms.substring(ms.length() - 1);
            if (posledniy.equals(")")) {
                ms += "*";
                expression.append(ms);
            }
        }
        expression.append(((Button) view).getText().toString());

        return;
    }

    public void calculate() {

        String ms = expression.getText().toString();
        if(ms.equals(""))
            return;
        ReversePolishEntry reversePolishEntry = new ReversePolishEntry();
        ParseComputation parseComputation = new ParseComputation();

        ArrayList<String> temp = reversePolishEntry.parseExpression(ms);
        if(reversePolishEntry.isAnswer())
            resulttext.setText(parseComputation.calculate(temp));
        else
            resulttext.setText("Неверное выражение!");

    }

    public void change_theme(MenuItem item){
        if (preferences.getBoolean("appTheme", true)) {
            item.setTitle(getString(R.string.change_theme));
            preferences.edit().putBoolean("appTheme", false).apply();
        } else {
            item.setTitle(getString(R.string.change_theme));
            preferences.edit().putBoolean("appTheme", true).apply();
        }
        recreate();
    }

}