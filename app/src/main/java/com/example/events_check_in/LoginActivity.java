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

import com.example.events_check_in.dao.ClienteDAO;
import com.example.events_check_in.model.Cliente;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private static final String ERROR_EMPTY_CPF = "Preencha o CPF!";
    private static final String ERROR_EMPTY_PASSWORD = "Preencha a Senha!";
    private static final String ERROR_INVALID_CPF = "CPF inválido!";
    private static final String ERROR_INVALID_PASSWORD = "A senha precisa ter pelo menos 6 caracteres!";

    private EditText editCpf;
    private EditText editSenha;
    private Button btnEntrar;
    private ProgressBar progressBar;
    private TextView txtTelaCadastro;

    private ClienteDAO clienteDAO;

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
                btnEntrar.setEnabled(false);
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

                String cpf = editCpf.getText().toString();
                String senha = editSenha.getText().toString();

//                 Inicializa ClienteDAO com o contexto correto
                clienteDAO = new ClienteDAO(LoginActivity.this);
                boolean isLoginValido = clienteDAO.isLoginValido(cpf, senha);

//                boolean isLoginValido = true;

                // Se o usuário existir no banco de dados, redireciona e exibe a mensagem de sucesso
                if (isLoginValido) {
                    redirectToLogin();
                    Snackbar snackbar = Snackbar.make(progressBar, "Login efetuado com sucesso!", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    // Caso contrário, exibe a mensagem de erro e limpa os campos de texto
                    editCpf.setText(null);
                    editSenha.setText(null);

                    Snackbar snackbar = Snackbar.make(progressBar, "Usuário ou senha incorretos", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }
        }, 3000);
    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
