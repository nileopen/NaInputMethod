package com.nile.nainputmethod;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import com.gowithwind.xiaoma.R;

/**
 * @actor:taotao
 * @DATE: 16/9/13
 */
public class NaLatinSoftKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {
    private NaKeyBoard mNaKeyBoard;
    private KeyboardView mInputView;
    private NaCandidateView mCandidateView;

    private StringBuilder mComposing = new StringBuilder();
    private boolean mCompletionOn;
    private boolean mCapsLock;
    private String mWordSeparators;
    private boolean mPredictionOn = false;

    @Override
    public void onInitializeInterface() {
        super.onInitializeInterface();
        mNaKeyBoard = new NaKeyBoard(this, R.xml.latin_keymap);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWordSeparators = getResources().getString(R.string.word_separators);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateInputView() {
        mInputView = (KeyboardView) getLayoutInflater().inflate(R.layout.latin_keyboatd_layout, null);
        mInputView.setOnKeyboardActionListener(this);
        mInputView.setKeyboard(mNaKeyBoard);
        return mInputView;
    }

//    @Override
//    public View onCreateCandidatesView() {
//        mCandidateView = new NaCandidateView(this);
//        mCandidateView.setService(this);
//        return mCandidateView;
//    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        Log.e("NakeY", "onKey code=" + primaryCode);
//        if (isWordSeparator(primaryCode)) {
//            // Handle separator
//            if (mComposing.length() > 0) {
//                commitTyped(getCurrentInputConnection());
//            }
//            sendKey(primaryCode);
////            updateShiftKeyState(getCurrentInputEditorInfo());
//        } else if (primaryCode == NaKeyBoard.KEYCODE_DELETE) {
//            handleBackspace();
//        } else if (primaryCode == NaKeyBoard.KEYCODE_UP) {
//
//        } else if (primaryCode == NaKeyBoard.KEYCODE_UP) {
//
//        } else if (primaryCode == NaKeyBoard.KEYCODE_LEFT) {
//
//        } else if (primaryCode == NaKeyBoard.KEYCODE_RIGHT) {
//
//        } else {
//            handleCharacter(primaryCode, keyCodes);
//        }
    }

    /**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }

    private void handleCharacter(int primaryCode, int[] keyCodes) {
        if (isInputViewShown()) {
            if (mInputView.isShifted()) {
                primaryCode = Character.toUpperCase(primaryCode);
            }
        }
        if (isAlphabet(primaryCode) && mPredictionOn) {
            mComposing.append((char) primaryCode);
            getCurrentInputConnection().setComposingText(mComposing, 1);
//            updateShiftKeyState(getCurrentInputEditorInfo());
//            updateCandidates();
        } else {
            getCurrentInputConnection().commitText(
                    String.valueOf((char) primaryCode), 1);
        }
    }

    private void sendKey(int keyCode) {
        switch (keyCode) {
            case '\n':
                keyDownUp(KeyEvent.KEYCODE_ENTER);
                break;
            default:
                if (keyCode >= '0' && keyCode <= '9') {
                    keyDownUp(keyCode - '0' + KeyEvent.KEYCODE_0);
                } else {
                    getCurrentInputConnection().commitText(String.valueOf((char) keyCode), 1);
                }
                break;
        }
    }

    /**
     * Helper function to commit any text being composed in to the editor.
     */
    private void commitTyped(InputConnection inputConnection) {
        if (mComposing.length() > 0) {
            inputConnection.commitText(mComposing, mComposing.length());
            mComposing.setLength(0);
//            updateCandidates();
        }
    }

    private String getWordSeparators() {
        return mWordSeparators;
    }

    private boolean isWordSeparator(int code) {
        String separators = getWordSeparators();
        return separators.contains(String.valueOf((char) code));
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {
        handleBackspace();
    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {
        handleClose();
    }

    private void handleClose() {
        commitTyped(getCurrentInputConnection());
        requestHideSelf(0);
        mInputView.closing();
    }

    @Override
    public void swipeUp() {

    }

    public void pickSuggestionManually(int mSelectedIndex) {
    }

    private void keyDownUp(int keyEventCode) {
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    }

    private void handleBackspace() {
        final int length = mComposing.length();
        if (length > 1) {
            mComposing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(mComposing, 1);
//            updateCandidates();
        } else if (length > 0) {
            mComposing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
//            updateCandidates();
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
//        updateShiftKeyState(getCurrentInputEditorInfo());
    }

//    private void updateShiftKeyState(EditorInfo attr) {
//        if (attr != null
//                && mInputView != null && mNaKeyBoard == mInputView.getKeyboard()) {
//            int caps = 0;
//            EditorInfo ei = getCurrentInputEditorInfo();
//            if (ei != null && ei.inputType != EditorInfo.TYPE_NULL) {
//                caps = getCurrentInputConnection().getCursorCapsMode(attr.inputType);
//            }
//            mInputView.setShifted(mCapsLock || caps != 0);
//        }
//    }

//    private void updateCandidates() {
//        if (!mCompletionOn) {
//            if (mComposing.length() > 0) {
//                ArrayList<String> list = new ArrayList<String>();
//                list.add(mComposing.toString());
//                setSuggestions(list, true, true);
//            } else {
//                setSuggestions(null, false, false);
//            }
//        }
//    }

//    public void setSuggestions(List<String> suggestions, boolean completions, boolean typedWordValid) {
//        if (suggestions != null && suggestions.size() > 0) {
//            setCandidatesViewShown(true);
//        } else if (isExtractViewShown()) {
//            setCandidatesViewShown(true);
//        }
//        if (mCandidateView != null) {
//            mCandidateView.setSuggestions(suggestions, completions, typedWordValid);
//        }
//    }
}
