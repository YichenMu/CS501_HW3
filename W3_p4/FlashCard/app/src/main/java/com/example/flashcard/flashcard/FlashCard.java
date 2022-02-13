package com.example.flashcard.flashcard;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.databinding.ActivityFlashCardBinding;

public class FlashCard extends AppCompatActivity {

    private ActivityFlashCardBinding binding;
    private Operand currentOperand;
    private int score;
    private int totalQuestionNum;
    private int operandBound;
    private int count;
    private String curAnswer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        totalQuestionNum = 10;
        operandBound = NumberConstant.mediumBound;
        curAnswer = "";

        binding = ActivityFlashCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListener();
    }

    @Override
    public void onConfigurationChanged (Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        binding = ActivityFlashCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListener();
    }

    private void setListener() {
        final Button submitButton = binding.submit;
        final Button generateButton = binding.generate;
        final EditText answer = binding.answer;
        final TextView num1 = binding.num1;
        final TextView num2 = binding.num2;

        displayQuestion(num1, num2);
        init();

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateButton.setEnabled(false);
                binding.hardSwitch.setEnabled(false);
                binding.answer.setEnabled(true);
                newQuestion();
                displayQuestion(num1, num2);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                judge(answer);
                if (count == totalQuestionNum) {
                    String scoreString = score + " out of " + totalQuestionNum;
                    Toast.makeText(getApplicationContext(), scoreString, Toast.LENGTH_LONG).show();
                    reset();
                } else {
                    newQuestion();
                    displayQuestion(num1, num2);
                }
            }
        });

        answer.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                curAnswer = answer.getText().toString();
                if (!answer.getText().toString().equals("")) {
                    submitButton.setEnabled(true);
                } else {
                    submitButton.setEnabled(false);
                }
            }
        });

        binding.hardSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    operandBound = NumberConstant.hardBound;
                } else {
                    operandBound = NumberConstant.mediumBound;
                }
            }
        });
    }

    private void reset() {
        score = 0;
        count = 0;
        currentOperand = null;
        binding.answer.setEnabled(false);
        binding.generate.setEnabled(true);
        binding.hardSwitch.setEnabled(true);
        displayQuestion(binding.num1, binding.num2);
    }

    private void init() {
        if (count != 0) {
            binding.generate.setEnabled(false);
            binding.hardSwitch.setEnabled(false);
        } else {
            binding.answer.setEnabled(false);
        }
        if (operandBound == NumberConstant.hardBound) {
            binding.hardSwitch.setChecked(true);
        }
        if (curAnswer.equals("")){
            binding.submit.setEnabled(false);
        }
        binding.answer.setText(curAnswer);
    }

    private void judge(EditText answer) {
        try {
            if (Integer.valueOf(answer.getText().toString()) == currentOperand.getResult()) {
                score++;
            }
        } catch (Exception e) {

        }
        answer.setText("");
    }

    private void newQuestion() {
        currentOperand = FlashCardRandom.getOperand(operandBound);
        count++;
    }

    private void displayQuestion(TextView num1, TextView num2) {
        if (currentOperand != null) {
            num1.setText(currentOperand.getNum1() + "");
            num2.setText(currentOperand.getOperator() + "" + currentOperand.getNum2());
        } else {
            num1.setText("");
            num2.setText("");
        }
    }
}
