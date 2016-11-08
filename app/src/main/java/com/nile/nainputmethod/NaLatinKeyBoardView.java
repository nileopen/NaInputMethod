package com.nile.nainputmethod;

import android.annotation.TargetApi;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.util.AttributeSet;

/**
 * @actor:taotao
 * @DATE: 16/9/13
 */
public class NaLatinKeyBoardView extends KeyboardView{
    static final int KEYCODE_OPTIONS = -100;
    public NaLatinKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NaLatinKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NaLatinKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected boolean onLongPress(Keyboard.Key key) {
        if (key.codes[0] == Keyboard.KEYCODE_CANCEL) {
            getOnKeyboardActionListener().onKey(KEYCODE_OPTIONS, null);
            return true;
        } else {
            return super.onLongPress(key);
        }
    }
}
