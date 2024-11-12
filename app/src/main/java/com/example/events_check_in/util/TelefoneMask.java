package com.example.events_check_in.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class TelefoneMask {
    public static void applyTelefoneMask(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            private boolean isFormatting;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Não faz nada
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                if (isFormatting) return;

                isFormatting = true;
                String str = charSequence.toString();
                StringBuilder formatted = new StringBuilder();

                // Remover tudo o que não for número
                str = str.replaceAll("[^\\d]", "");

                // Formatar telefone no formato (XX) XXXXX-XXXX
                if (str.length() > 0) {
                    formatted.append("(");
                    formatted.append(str.substring(0, Math.min(2, str.length())));
                    formatted.append(") ");
                }
                if (str.length() > 2) {
                    formatted.append(str.substring(2, Math.min(7, str.length())));
                    if (str.length() > 6) {
                        formatted.append("-");
                        formatted.append(str.substring(7, Math.min(11, str.length())));
                    }
                }

                // Aplica a máscara no EditText
                editText.setText(formatted.toString());
                editText.setSelection(formatted.length());
                isFormatting = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Não faz nada
            }
        });
    }
}
