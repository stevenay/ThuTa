package com.padc.interactive_training.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.padc.interactive_training.utils.MMFontUtils;

/**
 * Created by NayLinAung on 10/30/2016.
 */

public class MMRadioButton extends RadioButton {

    public MMRadioButton(Context context) {
        super(context);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }

    public MMRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            MMFontUtils.setMMFont(this);
    }
}
