package com.example.events_check_in.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CpfMask {
    public static void applyCpfMask(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isUpdating;
            private String oldText = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isUpdating) {
                    isUpdating = false;
                    return;
                }

                String newText = s.toString().replaceAll("[^\\d]", "");
                StringBuilder formattedText = new StringBuilder();

                int index = 0;
                while (index < newText.length() && formattedText.length() < 14) {
                    if (formattedText.length() == 3 || formattedText.length() == 7) {
                        formattedText.append(".");
                    } else if (formattedText.length() == 11) {
                        formattedText.append("-");
                    }
                    formattedText.append(newText.charAt(index));
                    index++;
                }

                isUpdating = true;
                editText.setText(formattedText.toString());
                editText.setSelection(formattedText.length());
            }
        });
    }
}
