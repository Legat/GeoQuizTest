package com.example.peter.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ASWER_IS_TRUE = "geoquizanswe";
    public static final String EXTRA_ASWER_SHOWN = "answer_shown";
    private boolean newAnswerIsTrue;
    private Button mShowAnswer;
    private TextView mAnswerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mShowAnswer = findViewById(R.id.show_btn);
        mAnswerText = findViewById(R.id.answer_txt);
        newAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ASWER_IS_TRUE, false);

        mShowAnswer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(newAnswerIsTrue){
                    mAnswerText.setText(R.string.true_buttom);
                }else{
                    mAnswerText.setText(R.string.false_bitton);
                }
                setAnswerShownResult(true);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswer.getWidth() / 2;
                    int cy = mShowAnswer.getHeight() / 2;
                    float radius = mShowAnswer.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswer, cx, cy, radius, 0);
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnswerText.setVisibility(View.VISIBLE);
                            mShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                }else{
                    mAnswerText.setVisibility(View.VISIBLE);
                    mShowAnswer.setVisibility(View.INVISIBLE);
                }
        }
    });
    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        data.putExtra(EXTRA_ASWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK,data);
    }

    public static boolean wasAnswerShown(Intent result){
        return result.getBooleanExtra(EXTRA_ASWER_SHOWN, false);
    }



}
