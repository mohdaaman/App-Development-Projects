package com.example.qna;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvTime, tvScore, tvQuestion, tvAnswer;
    Button btnCorrect, btnWrong;

    int score = 0;
    int correctAnswer;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link XML with Java
        tvTime = findViewById(R.id.tvTime);
        tvScore = findViewById(R.id.tvScore);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvAnswer = findViewById(R.id.tvAnswer);

        btnCorrect = findViewById(R.id.btnCorrect);
        btnWrong = findViewById(R.id.btnWrong);

        generateQuestion();
        startTimer();

        btnCorrect.setOnClickListener(v -> checkAnswer(true));
        btnWrong.setOnClickListener(v -> checkAnswer(false));
    }

    // Generate random question
    void generateQuestion() {
        Random random = new Random();

        int a = random.nextInt(50);
        int b = random.nextInt(50);

        correctAnswer = a + b;

        tvQuestion.setText(a + " + " + b);

        // Randomly decide whether answer shown is correct or wrong
        if (random.nextBoolean()) {
            tvAnswer.setText("= " + correctAnswer);
        } else {
            tvAnswer.setText("= " + (correctAnswer + random.nextInt(5) + 1));
        }
    }

    // Check user choice
    void checkAnswer(boolean userSaysCorrect) {
        boolean isAnswerCorrect =
                tvAnswer.getText().toString().equals("= " + correctAnswer);

        if (userSaysCorrect == isAnswerCorrect) {
            score++;
        } else {
            score--;
        }

        tvScore.setText("SCORE: " + score);
        generateQuestion();
    }

    // Timer logic
    void startTimer() {
        timer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTime.setText("TIME: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTime.setText("TIME: 0");
                btnCorrect.setEnabled(false);
                btnWrong.setEnabled(false);
            }
        }.start();
    }
}
