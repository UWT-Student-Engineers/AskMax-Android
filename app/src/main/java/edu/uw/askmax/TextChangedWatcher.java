package edu.uw.askmax;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * TextWatcher that only implements a simplified version of onTextChanged.
 * TODO: Implement a configurable delay between when the text is changed and when onTextChanged()
 * is called.
 */
public abstract class TextChangedWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Don't need this
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextChanged(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        // Don't need this
    }

    public abstract void onTextChanged(String s);
}
