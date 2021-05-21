package com.barmej.guesstheanswer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import maes.tech.intentanim.CustomIntent;

public class QuestionActivity extends AppCompatActivity {

    private TextView mTextViewQuestion;
    private  String[] QUESTIONS ;
    private static final boolean[] ANSWERS = {
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            false,
            false,
            true,
            true,
            false,
            true
    };

    private  String[] ANSWERS_DETAILS ;

    private String mCurrentQuestion,mCurrentAnswerDetail;
    private boolean mCurrentAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_LANGUAGE,MODE_PRIVATE);
        String language =sharedPreferences.getString(Constants.LANGUAGE_KEY,"");
        if (!language.equals("")){
            LocaleHelper.setLocale(QuestionActivity.this,language);
        }
        setContentView(R.layout.activity_main);

        mTextViewQuestion=findViewById(R.id.text_View_question);
        QUESTIONS = getResources().getStringArray(R.array.questions);
        ANSWERS_DETAILS =getResources().getStringArray(R.array.answers_details);

        showNewQuestion();

    }

    private void showNewQuestion(){
        Random random =new Random();
        int randomQuestionIndex =random.nextInt(QUESTIONS.length);
        mCurrentQuestion = QUESTIONS[randomQuestionIndex];
        mCurrentAnswerDetail = ANSWERS_DETAILS[randomQuestionIndex];
        mCurrentAnswer = ANSWERS[randomQuestionIndex];
        mTextViewQuestion.setText(mCurrentQuestion);
    }

    public void onChangeDetailQuestion(View view) {
        showNewQuestion();
    }

    public void onTrueClicked(View view){
        if (mCurrentAnswer == true){
            Toast.makeText(this, "الاجابه صحيحه", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        }else{
            Toast.makeText(this, "الاجابه خطأ", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(QuestionActivity.this,AnswerActivity.class);
            intent.putExtra(Constants.QUESTION_ANSWER,mCurrentAnswerDetail);
            startActivity(intent);
            CustomIntent.customType(QuestionActivity.this,"right-to-left");
        }
    }

    public void onFalseClicked(View view){
        if (mCurrentAnswer == false){
            Toast.makeText(this, "الاجابه صحيحة", Toast.LENGTH_SHORT).show();
            showNewQuestion();
        }  else{
            Toast.makeText(this, "الاجابه خطأ", Toast.LENGTH_SHORT).show();
            Intent intent =new Intent(QuestionActivity.this,AnswerActivity.class);
            intent.putExtra(Constants.QUESTION_ANSWER,mCurrentAnswerDetail);
          //  startActivity(intent);
            // ابدأ نشاطًا باستخدام الرسوم المتحركة 1
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //ابدأ نشاطًا باستخدام الرسوم المتحركة 2
          // CustomIntent.customType(QuestionActivity.this,"right-to-left");
        }

    }

    public void onShareQuestion(View view) {
        Intent intent =new Intent(QuestionActivity.this,ShareActivity.class);
        intent.putExtra(Constants.QUESTION,mCurrentQuestion);
        startActivity(intent);
        CustomIntent.customType(QuestionActivity.this,"right-to-left");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // return super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuChangeLanguage:{
                showLanguageDialog();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showLanguageDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_language)
                .setItems(R.array.Languages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item) {
                        String language = "ar";
                        switch (item) {
                            case 0:
                                language = "ar";
                                break;
                            case 1:
                                language = "en";
                                break;
                            case 2:
                                language = "fr";
                                break;
                        }
                        saveLanguage(language);

                        LocaleHelper.setLocale(QuestionActivity.this, language);
                        Intent intent = new Intent(getApplicationContext(),QuestionActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .create();
        dialog.show();
    }

    private void saveLanguage(String language){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_LANGUAGE,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.LANGUAGE_KEY,language);
        editor.apply();
    }


}