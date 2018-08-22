package com.example.peter.geoquiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {
    private Button mTrueButton, mFalseButton, mCheatButton;
    private ImageButton mNextButton, mBackButton;
    private TextView mQuastionsTextView;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuizActivity";
    public static final String EXTRA_ASWER_IS_TRUE = "geoquizanswe";
    public static final int REQUEST_CODE_CHEAT = 0;
    public static final String EXTRA_ASWER_SHOWN = "answer_shown";

    private Quastion[] mQuastionBank = new Quastion[]{
      new Quastion(R.string.quastion_oceans,true),
      new Quastion(R.string.quastion_mediast,false),
      new Quastion(R.string.quastion_africa, false),
      new Quastion(R.string.quastion_amiricas, true),
      new Quastion(R.string.quastion_asia, true),
    };

    private int mCurrentIndex = 0;

    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuastionsTextView = findViewById(R.id.quastion_text);

        mQuastionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuastionBank.length;
                updateQuestion();
            }
        });
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);
        mNextButton = findViewById(R.id.next_button);
        mBackButton = findViewById(R.id.back_button);
        mCheatButton =findViewById(R.id.cheat_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1)%mQuastionBank.length;
                updateQuestion();
            }
        });
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex != 0){
                    mCurrentIndex = (mCurrentIndex - 1);
                    updateQuestion();
                }


            }
        });
        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt("KEY_INDEX", 0);
        }
        updateQuestion();
        mTrueButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
              //  Toast.makeText(QuizActivity.this,R.string.correct_toast,Toast.LENGTH_SHORT).show();
                checkAnswer(true);
            }
        });

      mFalseButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //  Toast.makeText(QuizActivity.this,R.string.incorrect_toast,Toast.LENGTH_SHORT).show();
              checkAnswer(false);
          }
      });

      mCheatButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            //  Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
              boolean answerIsTrue = mQuastionBank[mCurrentIndex].isAswerTrue();
              Intent intent = newIntent(QuizActivity.this, answerIsTrue);
              startActivityForResult(intent, REQUEST_CODE_CHEAT);
          }
      });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("KEY_INDEX", mCurrentIndex);

    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuastionBank[mCurrentIndex].isAswerTrue();
        int messageResId = 0;
        if(mIsCheater){
            messageResId = R.string.judgment_toast;
        }else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void updateQuestion(){
        int question = mQuastionBank[mCurrentIndex].getTextResId();
        mQuastionsTextView.setText(question);
    }

    public static Intent newIntent(Context packageContext, boolean answerTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ASWER_IS_TRUE, answerTrue);
        return intent;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if(requestCode == REQUEST_CODE_CHEAT){
            if(data != null){
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
        }
    }
}
