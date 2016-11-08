package com.nile.nainputmethod;

import android.content.Context;
import android.inputmethodservice.Keyboard;

/**
 * @actor:taotao
 * @DATE: 16/9/13
 */
public class NaKeyBoard extends Keyboard{
    public final static int KEYCODE_UP = -11;
    public final static int KEYCODE_DOWN = -12;
    public final static int KEYCODE_LEFT = -13;
    public final static int KEYCODE_RIGHT = -14;
    public NaKeyBoard(Context context, int xmlLayoutResId) {
        super(context, xmlLayoutResId);
    }

    public NaKeyBoard(Context context, int xmlLayoutResId, int modeId, int width, int height) {
        super(context, xmlLayoutResId, modeId, width, height);
    }

    public NaKeyBoard(Context context, int xmlLayoutResId, int modeId) {
        super(context, xmlLayoutResId, modeId);
    }

    public NaKeyBoard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
        super(context, layoutTemplateResId, characters, columns, horizontalPadding);
    }
}
