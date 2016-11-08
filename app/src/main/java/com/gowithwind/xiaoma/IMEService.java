package com.gowithwind.xiaoma;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

public class IMEService extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private static final String TAG = "IMEService";
    public final static int Key_0 = 0;
    public final static int Key_1 = 1;
    public final static int Key_2 = 2;
    public final static int Key_3 = 3;
    public final static int Key_4 = 4;
    public final static int Key_5 = 5;
    public final static int Key_6 = 6;
    public final static int Key_7 = 7;
    public final static int Key_8 = 8;
    public final static int Key_9 = 9;
    public final static int Key_P = 10;
    public final static int Key_Up = 11;
    public final static int Key_Down = 12;
    public final static int Key_Left = 13;
    public final static int Key_Right = 14;
    public final static int Key_Del = 15;
    public final static int Key_Nums = Key_Del + 1;

    public final String[] NumKeyStrs = {
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."
    };

    private View view;
    KeyboardView kv;

    @Override
    public View onCreateInputView() {
        view = getLayoutInflater().inflate(R.layout.latin_keyboatd_layout1, null);
        final Keyboard keyboard = new Keyboard(this, R.xml.latin_keymap1);
        kv = (KeyboardView) view.findViewById(R.id.keyboard_view);
        kv.setKeyboard(keyboard);
        kv.setEnabled(true);
        kv.setPreviewEnabled(false);
        kv.setOnKeyboardActionListener(this);
        return view;
    }


    public void input(String s) {
        if (getCurrentInputConnection() != null) {
            getCurrentInputConnection().commitText(s, 1);
        }
    }

    public void backspace() {
//        getCurrentInputConnection().deleteSurroundingText(1, 0);
        keyDownUp(KeyEvent.KEYCODE_DEL);
    }


    private void onKeyHandler(int key) {
        if (key >= Key_0 && key <= Key_P) {
            input(NumKeyStrs[key]);
        } else if (key == Key_Del) {
            backspace();
        } else if (key == Key_Up) {
            up();
        } else if (key == Key_Down) {
            down();
        } else if (key == Key_Left) {
            left();
        } else if (key == Key_Right) {
            right();
        }
    }

    private void up() {
        keyDownUp(KeyEvent.KEYCODE_DPAD_UP);
    }

    private void down() {
        keyDownUp(KeyEvent.KEYCODE_DPAD_DOWN);
    }

    private void left() {
        keyDownUp(KeyEvent.KEYCODE_DPAD_LEFT);
    }

    private void right() {
        keyDownUp(KeyEvent.KEYCODE_DPAD_RIGHT);
    }

    private void keyDownUp(int keyEventCode) {
        if (getCurrentInputConnection() != null) {
            getCurrentInputConnection().sendKeyEvent(
                    new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
            getCurrentInputConnection().sendKeyEvent(
                    new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
        }
    }

    @Override
    public void onPress(int primaryCode) {
        Log.e(TAG, "onPress primaryCode=" + primaryCode);
    }

    @Override
    public void onRelease(int primaryCode) {
        Log.e(TAG, "onRelease primaryCode=" + primaryCode);
    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.e(TAG, "onKey primaryCode=" + primaryCode + ",keycodes=" + keyCodes);
        onKeyHandler(primaryCode);
    }

    @Override
    public void onText(CharSequence text) {
        Log.e(TAG, "onPress text=" + text);
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
}
