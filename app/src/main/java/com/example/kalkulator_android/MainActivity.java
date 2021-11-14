package com.example.kalkulator_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    TextView result;
    boolean activeOperation = false;
    String regOperations = "[*-+^/]";
    Pattern patternOperations = Pattern.compile(regOperations);
    char [] operators = "*-+^√/".toCharArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.TextView_result);
        result.setText("0");
    }

    public void clear(View view) {
        result.setText("0");
        activeOperation = false;
    }

    public void backspace(View view) {
        String actualResult = result.getText().toString();

        if(actualResult.length() > 0 && !actualResult.equals("0")) {
            if(patternOperations.matcher(actualResult.substring(actualResult.length()-1,actualResult.length())).find()) {
                activeOperation = false;
            }

            result.setText(actualResult.substring(0,actualResult.length()-1));

            if(result.getText().toString().equals(""))
                result.setText("0");
        }
    }

    public void percent(View view) {

    }

    public void reversion(View view) {

    }

    public void sqrt(View view) {

    }

    public void sqr(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();

        if(activeOperation) {
            tempOperation();
        }

        activeOperation = true;
        result.setText(result.getText().toString() + "^");
    }

    public void division(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();

        if(activeOperation) {
            tempOperation();
        }

        activeOperation = true;
        result.setText(result.getText().toString() + "/");
    }

    public void number(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();
        String number = tempButton.getText().toString();

        if(actualResult.equals("0") && !number.equals("0")) {
            result.setText(number);
        } else if(!actualResult.equals("0")) {
            result.setText(result.getText().toString() + number);
        }
    }

    public void multiplication(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();

        if(activeOperation) {
            tempOperation();
        }

        activeOperation = true;
        result.setText(result.getText().toString() + "*");
    }

    public void subtraction(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();

        if(activeOperation) {
            tempOperation();
        }

        activeOperation = true;
        result.setText(result.getText().toString() + "-");
    }

    public void addition(View view) {
        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();

        if(activeOperation) {
            tempOperation();
        }

        activeOperation = true;
        result.setText(result.getText().toString() + "+");
    }

    public void opposition(View view) {
        String actualResult = result.getText().toString();
        float res = Float.parseFloat(actualResult) * -1;

        result.setText(Float.toString(res));
    }

    public void floating_point(View view) {

    }

    public void equation(View view) {

    }

    public boolean tempOperation() {
        String actualResult = result.getText().toString();
        char usedOperator = '0';
        int operatorIndex = -1;

        for(char operator : operators) {
            operatorIndex = actualResult.indexOf(operator);

            if(operatorIndex != -1) {
                usedOperator = operator;
                break;
            }
        }
        if(operatorIndex != -1) {
            String left = actualResult.substring(0, operatorIndex);
            String right = actualResult.substring(operatorIndex + 1);

            // *-+^/
            switch (usedOperator) {
                case '*': {
                    result.setText(Double.toString(Double.parseDouble(left) * Double.parseDouble(right)));
                    break;
                }
                case '-': {
                    result.setText(Double.toString(Double.parseDouble(left) - Double.parseDouble(right)));
                    break;
                }
                case '+': {
                    result.setText(Double.toString(Double.parseDouble(left) + Double.parseDouble(right)));
                    break;
                }
                case '^': {
                    result.setText(Double.toString(Math.pow(Double.parseDouble(left),Double.parseDouble(right))));
                    break;
                }
                case '/': {
                    result.setText(Double.toString(Double.parseDouble(left) / Double.parseDouble(right)));
                    break;
                }
                default:
                    break;
            }
            return true;
        }
        return false;
    }
}