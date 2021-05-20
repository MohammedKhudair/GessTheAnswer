package com.barmej.guesstheanswer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    EditText mEditTextٍShareDetail;
    private String mQuestionText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        mEditTextٍShareDetail=findViewById(R.id.editTextٍShareDetail);

        mQuestionText = getIntent().getStringExtra("question");

        SharedPreferences sharedPreferences = getSharedPreferences("appPref",MODE_PRIVATE);
            mEditTextٍShareDetail.setText(sharedPreferences.getString("shareTitle",""));



    }

    public void ShareQuestion(View view) {
      String questionTitle =  mEditTextٍShareDetail.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,questionTitle + "\n" + mQuestionText);
        intent.setType("text/plain");
        startActivity(intent);

        SharedPreferences sharedPreferences = getSharedPreferences("appPref",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("shareTitle",questionTitle);
        editor.apply();
    }
}