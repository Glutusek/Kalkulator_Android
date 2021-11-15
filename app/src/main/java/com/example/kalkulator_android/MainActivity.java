package com.example.kalkulator_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    TextView result;
    boolean activeOperation = false;
    char [] operators = "+-*/^.".toCharArray();

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
            result.setText(actualResult.substring(0,actualResult.length()-1));

            if(result.getText().toString().equals(""))
                result.setText("0");
        }
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

    public void addition(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("+");
        }
    }

    public void subtraction(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("-");
        }
    }

    public void multiplication(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("*");
        }
    }

    public void division(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("/");
        }
    }

    public void percent(View view) {
        String actualResult = result.getText().toString();

        if(actualResult.lastIndexOf(".") != actualResult.length()-1) {

        }
    }

    public void reversion(View view) {

    }

    public void sqrt(View view) {

    }

    public void sqr(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("^");
        }
    }

    public void opposition(View view) {

    }

    public void floating_point(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator(".");
        }
    }

    public void equation(View view) {
        String actualResult = result.getText().toString();

        List<Integer> operatorIndexes = new ArrayList<>();
        List<Character> actualOperators = new ArrayList<>();

        Map<Character, Integer> operatorPriorities = new HashMap<Character, Integer>(){{
            put('+', 1);
            put('-', 1);
            put('*', 2);
            put('/', 2);
            put('^', 3);
        }};

        int idx = -1;

        for(char ch : actualResult.toCharArray()) {
            idx++;
            for(char op : operators) {
                if(ch == op) {
                    operatorIndexes.add(idx);

                    switch (op) {
                        case '+':
                            actualOperators.add('+');
                            break;
                        case '-':
                            actualOperators.add('-');
                            break;
                        case '*':
                            actualOperators.add('*');
                            break;
                        case '/':
                            actualOperators.add('/');
                            break;
                        case '^':
                            actualOperators.add('^');
                            break;
                    }
                }
            }
        }

        List<String> numbers = new ArrayList<>();

        numbers.add(actualResult.substring(0, operatorIndexes.get(0)));

        for(int i = 0; i < operatorIndexes.size()-1; i++) {
            numbers.add(actualResult.substring(
                            operatorIndexes.get(i)+1,
                            operatorIndexes.get(i+1)
            ));
        }

        numbers.add(actualResult.substring(
                operatorIndexes.get(operatorIndexes.size()-1)+1
        ));

        if(numbers.size() >= 1) {
            for(int priority = 3; priority >= 1; priority--) {

                int actualOperatorNum = 0;

                for(char op : actualOperators) {
                    if(operatorPriorities.get(op) == priority) {
                        String tempNum = "";

                        switch (priority) {
                            case 3:
                                tempNum = power(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                break;
                            case 2: {
                                if(op == '*') {
                                    tempNum = multiply(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                } else {
                                    tempNum = divide(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                }
                                break;
                            }
                            case 1: {
                                if(op == '+') {
                                    tempNum = add(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                } else {
                                    tempNum = subtract(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                }
                                break;
                            }
                        }
                        numbers.set(actualOperatorNum, tempNum);
                        numbers.remove(actualOperatorNum + 1);
                        Toast.makeText(getApplicationContext(), numbers.toString(), Toast.LENGTH_SHORT).show();
                    }
                    actualOperatorNum++;
                }
            }
        }
    }

    private void writeOperator(String op) {
        String actualResult = result.getText().toString();

        for(char ch : operators) {
            if(actualResult.lastIndexOf(ch) == actualResult.length()-1) {
                actualResult = actualResult.substring(0, actualResult.length()-1);
                break;
            }
        }

        result.setText(actualResult + op);
    }

    private String add(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a + b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }

    private String subtract(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a - b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }

    private String multiply(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a * b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }

    private String divide(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        if(b != 0) {
            return Double.toString(a / b);
        }
        else
            return "";
    }

    private String power(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(Math.pow(a, b));
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }
}