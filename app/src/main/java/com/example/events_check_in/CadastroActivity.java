package com.example.events_check_in;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.events_check_in.dao.ClienteDAO;
import com.example.events_check_in.model.Cliente;
import com.example.events_check_in.util.CpfMask;
import com.example.events_check_in.util.TelefoneMask;

public class CadastroActivity extends AppCompatActivity {

    private Button btnCadastrar,btnSair;
    private EditText edtNome,edtCpf,edtTelefone,edtSenha,edtConfirmaSenha;

    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.cadastroActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnCadastrar = findViewById(R.id.btnCadastrar);
        edtNome = findViewById(R.id.edtNome);
        edtCpf = findViewById(R.id.edtCpf);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmaSenha = findViewById(R.id.edtConfirmaSenha);

        CpfMask.applyCpfMask(edtCpf);
        TelefoneMask.applyTelefoneMask(edtTelefone);

        btnCadastrar.setOnClickListener(view -> {
            // Obter os dados dos campos
            String nome = edtNome.getText().toString();
            String cpf = edtCpf.getText().toString();
            String telefone = edtTelefone.getText().toString();
            String senha = edtSenha.getText().toString();
            String confSenha = edtConfirmaSenha.getText().toString();

            // Verificar se algum campo está vazio
            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
                return; // Interrompe a execução se algum campo estiver vazio
            }

            if (!senha.equals(confSenha)) {
                Toast.makeText(getApplicationContext(), "As senhas são diferentes", Toast.LENGTH_SHORT).show();
                return; // Interrompe a execução se algum campo estiver vazio
            }

            // Verificar se o CPF já está cadastrado
            clienteDAO = new ClienteDAO(CadastroActivity.this);
            boolean isCpfExistente = clienteDAO.isCpfCadastrado(cpf); // Método que você criará no DAO para verificar CPF

            if (isCpfExistente) {
                Toast.makeText(getApplicationContext(), "Este CPF já está cadastrado!", Toast.LENGTH_SHORT).show();
                return; // Interrompe a execução se o CPF já existir
            }

            // Cria um novo cliente e define os atributos
            Cliente cliente = new Cliente();
            cliente.setNome(nome);
            cliente.setCpf(cpf);
            cliente.setTelefone(telefone);
            cliente.setSenha(senha);
            // Inserir no banco
            long id = clienteDAO.insert(cliente);

            if (id > 0) {
                Toast.makeText(getApplicationContext(), "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this,LoginActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Erro ao cadastrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}