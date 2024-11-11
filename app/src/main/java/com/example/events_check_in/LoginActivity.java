package com.example.events_check_in;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    //private static final String CPF_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // Regex para validação de e-mail
    private static final String ERROR_EMPTY_CPF = "Preencha o CPF!";
    private static final String ERROR_EMPTY_PASSWORD = "Preencha a Senha!";
    private static final String ERROR_INVALID_CPF = "CPF inválido!";
    private static final String ERROR_INVALID_PASSWORD = "A senha precisa ter pelo menos 6 caracteres!";

    private EditText editCpf;
    private EditText editSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;
    private TextView txtTelaCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Cor da barra
        getWindow().setStatusBarColor(Color.parseColor("#E0F3FD"));
        getWindow().setNavigationBarColor(Color.parseColor("#E0F3FD"));

        // ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Referências para os componentes
        editCpf = findViewById(R.id.editCpf);
        editSenha = findViewById(R.id.editSenha);
        btnEntrar = findViewById(R.id.btnEntrar);
        progressBar = findViewById(R.id.progressBar);
        txtTelaCadastro = findViewById(R.id.txtTelaCadastro);

        // On click botão "Entrar"
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v); // Esconde o teclado

                String cpf = editCpf.getText().toString();
                String senha = editSenha.getText().toString();

                if (cpf.isEmpty()) {
                    editCpf.setError(ERROR_EMPTY_CPF);
                    editCpf.requestFocus();
                    return;
                }

                if (senha.isEmpty()) {
                    editSenha.setError(ERROR_EMPTY_PASSWORD);
                    editSenha.requestFocus();
                    return;
                }

                if (cpf.length() < 11) {
                    Snackbar snackbar = Snackbar.make(v, ERROR_INVALID_CPF, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    editCpf.requestFocus();
                    return;
                }

                if (senha.length() < 6) {
                    Snackbar snackbar = Snackbar.make(v, ERROR_INVALID_PASSWORD, Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    editSenha.requestFocus();
                    return;
                }

                btnEntrar.setEnabled(false);

                // validar se o usuario existe no bd
                login();
            }
        });

        txtTelaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastro();
            }
        });

    }

    // Método para esconder o teclado
    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    // On click TextView "Cadastre-se"
    public void cadastro() {
        Intent it = new Intent(this, CadastroActivity.class);
        startActivity(it);
    }

    // Exibe a barra de progresso enquanto o login está sendo processado
    private void login() {
        progressBar.setVisibility(View.VISIBLE);

        // Cria um novo Handler para executar código no thread principal após um atraso
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Esconde a barra de progresso após o atraso
                progressBar.setVisibility(View.GONE);
                btnEntrar.setEnabled(true);
                redirectToLogin();
                Snackbar snackbar = Snackbar.make(progressBar, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        }, 3000);
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}