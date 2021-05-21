package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class AnswerActivity extends AppCompatActivity {

    private TextView mTextViewAnswer;
    Button buttonReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        mTextViewAnswer=findViewById(R.id.textAnswer);
        buttonReturn=findViewById(R.id.button_return);

        String answer = getIntent().getStringExtra(Constants.QUESTION_ANSWER);
        if (answer !=null){
            mTextViewAnswer.setText(answer);
        }

    }

    public void returnButton(View view) {
        finish();
        //ابدأ نشاطًا باستخدام الرسوم المتحركة
        CustomIntent.customType(AnswerActivity.this,"left-to-right");
    }
}