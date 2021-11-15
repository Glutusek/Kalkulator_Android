/**
 *  @author Kamil Wieczorek
 *  @version %I%, %G%
 *
 *  Calculator
 */
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
    char [] operators = "+-*/.".toCharArray();
    boolean error = false;

    String [] buttonsToTurnOff = {
            "button_percent",
            "button_reverse",
            "button_sqrt",
            "button_sqr",
            "button_division",
            "button_multiplication",
            "button_subtraction",
            "button_addition",
            "button_opposite",
            "button_dot",
            "button_equation",
            "button_BKSP",
            "button_1",
            "button_2",
            "button_3",
            "button_4",
            "button_5",
            "button_6",
            "button_7",
            "button_8",
            "button_9",
            "button_0"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.TextView_result);
        result.setText("0");
    }

    /**
     * Clearing the result
     * @param view Clicked button
     */
    public void clear(View view) {
        result.setText("0");
        activeOperation = false;
        error = false;

        for(String ID : buttonsToTurnOff) {
            int tileID = getResources().getIdentifier(ID, "id", getPackageName());
            Button tempButton = findViewById(tileID);
            tempButton.setEnabled(true);
        }
    }

    /**
     * Clearing the last character of the result
     * @param view Clicked button
     */
    public void backspace(View view) {
        String actualResult = result.getText().toString();

        if(actualResult.length() > 0 && !actualResult.equals("0")) {
            result.setText(actualResult.substring(0,actualResult.length()-1));

            if(result.getText().toString().equals(""))
                result.setText("0");
        }
    }

    /**
     * Writing a number into the result
     * @param view Clicked button
     */
    public void number(View view) {
        if(error) {
            for(String ID : buttonsToTurnOff) {
                int tileID = getResources().getIdentifier(ID, "id", getPackageName());
                Button tempButton = findViewById(tileID);
                tempButton.setEnabled(true);
            }
            error = true;
        }

        Button tempButton = findViewById(view.getId());
        String actualResult = result.getText().toString();
        String number = tempButton.getText().toString();

        if(actualResult.equals("0") && !number.equals("0")) {
            result.setText(number);
        } else if(!actualResult.equals("0")) {
            result.setText(result.getText().toString() + number);
        }
    }

    /**
     * Writing "+" into the result
     * @param view Clicked button
     */
    public void addition(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("+");
        }
    }

    /**
     * Writing "-" into the result
     * @param view Clicked button
     */
    public void subtraction(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("-");
        }
    }

    /**
     * Writing "*" into the result
     * @param view Clicked button
     */
    public void multiplication(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("*");
        }
    }

    /**
     * Writing "/" into the result
     * @param view Clicked button
     */
    public void division(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator("/");
        }
    }

    /**
     * Writing "%" into the result
     * @param view Clicked button
     */
    public void percent(View view) {
        String actualResult = result.getText().toString();

        if(actualResult.lastIndexOf(".") != actualResult.length()-1) {

        }
    }

    /**
     * Reversing given result and writing into the result
     * @param view Clicked button
     * @exception Exception If could not count properly
     */
    public void reversion(View view) {
        String actualResult = result.getText().toString();
        try {
            double num = Double.parseDouble(actualResult);
            result.setText(Double.toString(1.0 / num));
        } catch (Exception e) {
            result.setText("Błędne dane");
            error = true;

            for(String ID : buttonsToTurnOff) {
                int tileID = getResources().getIdentifier(ID, "id", getPackageName());
                Button tempButton = findViewById(tileID);
                tempButton.setEnabled(false);
            }
        }
    }

    /**
     * Writing square root of given number into the result
     * @param view Clicked button
     * @exception Exception If could not count properly
     */
    public void sqrt(View view) {
        String actualResult = result.getText().toString();
        try {
            double num = Double.parseDouble(actualResult);

            if(num < 0)
                throw new Exception();

            result.setText(Double.toString(Math.sqrt(num)));
        } catch (Exception e) {
            result.setText("Błędne dane");
            error = true;

            for(String ID : buttonsToTurnOff) {
                int tileID = getResources().getIdentifier(ID, "id", getPackageName());
                Button tempButton = findViewById(tileID);
                tempButton.setEnabled(false);
            }
        }
    }

    /**
     * Writing square of given number into the result
     * @param view Clicked button
     * @exception Exception If could not count properly
     */
    public void sqr(View view) {
        String actualResult = result.getText().toString();
        try {
            double num = Double.parseDouble(actualResult);
            result.setText(Double.toString(Math.pow(num, 2)));
        } catch (Exception e) {
            result.setText("Błędne dane");
            error = true;

            for(String ID : buttonsToTurnOff) {
                int tileID = getResources().getIdentifier(ID, "id", getPackageName());
                Button tempButton = findViewById(tileID);
                tempButton.setEnabled(false);
            }
        }
    }

    /**
     * Changing positive number into negative number (or vice versa)
     * @param view Clicked button
     */
    public void opposition(View view) {
        String actualResult = result.getText().toString();

        if(actualResult.indexOf("-") == 0)
            result.setText(actualResult.substring(1));
        else
            result.setText("-" + actualResult);
    }

    /**
     * Writing "." into the result
     * @param view Clicked button
     */
    public void floating_point(View view) {
        if(!result.getText().toString().equals("0")) {
            writeOperator(".");
        }
    }

    /**
     * Counting given sequence and writing into the result
     * @param view Clicked button
     */
    public void equation(View view) {
        String actualResult = result.getText().toString();

        List<Integer> operatorIndexes = new ArrayList<>();
        List<Character> actualOperators = new ArrayList<>();

        Map<Character, Integer> operatorPriorities = new HashMap<Character, Integer>(){{
            put('+', 1);
            put('-', 1);
            put('*', 2);
            put('/', 2);
        }};

        int idx = -1;
        boolean negativeNum = false;

        if(actualResult.substring(0,1).equals("-"))
            negativeNum = true;

        for(char ch : actualResult.toCharArray()) {
            idx++;

            if(negativeNum) {
                negativeNum = false;
                continue;
            }

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

        Toast.makeText(getApplicationContext(), numbers.toString(), Toast.LENGTH_SHORT).show();

        if(numbers.size() >= 2) {
            for(int priority = 2; priority >= 1; priority--) {

                int actualOperatorNum = 0;

                for(char op : actualOperators) {
                    if(operatorPriorities.get(op) == priority) {
                        String tempNum = "";

                        switch (priority) {
                            case 2: {
                                if(op == '*') {
                                    tempNum = multiply(numbers.get(actualOperatorNum), numbers.get(actualOperatorNum+1));
                                } else {
                                    double a = Double.parseDouble(numbers.get(actualOperatorNum));
                                    double b = Double.parseDouble(numbers.get(actualOperatorNum+1));

                                    try {
                                        if(b == 0)
                                            throw new Exception();

                                        tempNum = Double.toString(a / b);
                                    } catch (Exception e) {
                                        result.setText("Błędne dane");
                                        error = true;

                                        for(String ID : buttonsToTurnOff) {
                                            int tileID = getResources().getIdentifier(ID, "id", getPackageName());
                                            Button tempButton = findViewById(tileID);
                                            tempButton.setEnabled(false);
                                        }
                                    }
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
                    }
                    actualOperatorNum++;
                }
            }
            if(!error)
                result.setText(numbers.get(0));
        }
    }

    /**
     * Writing an operator into the result
     * @param op Given operator to write
     */
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

    /**
     * Adding both numbers
     * @param left First number
     * @param right Second number
     * @return Sum of both numbers
     */
    private String add(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a + b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }

    /**
     * Subtracting both numbers
     * @param left First number
     * @param right Second number
     * @return Subtraction of both numbers
     */
    private String subtract(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a - b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }

    /**
     * Multiplacting both numbers
     * @param left First number
     * @param right Second number
     * @return Multiplication of both numbers
     */
    private String multiply(String left, String right) {
        double a = Double.parseDouble(left);
        double b = Double.parseDouble(right);

        String tempStr = Double.toString(a * b);
        tempStr = tempStr.substring(0, tempStr.indexOf('.'));

        return tempStr;
    }
}
