package com.example.peter.geoquiz;

/**
 * Created by Peter on 14.08.2018.
 */

public class Quastion {
    private int mTextResId;
    private boolean mAswerTrue;

    public Quastion(){}

    public Quastion(int mTextResId, boolean answerTrue) {
        this.mTextResId = mTextResId;
        this.mAswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAswerTrue() {
        return mAswerTrue;
    }

    public void setAswerTrue(boolean aswerTrue) {
        mAswerTrue = aswerTrue;
    }
}
