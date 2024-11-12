package com.example.events_check_in;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.events_check_in.dao.ClienteDAO;
import com.example.events_check_in.model.Cliente;
import com.example.events_check_in.util.CpfMask;
import com.example.events_check_in.util.TelefoneMask;

public class AjustesFragment extends Fragment {

    private EditText txtNome,txtCpf,txtTelefone;

    private Button btnAtualizar;
    private Button btnExcluir;

    private ClienteDAO clienteDAO;
    private Cliente cliente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        String cpf = getArguments() != null ? getArguments().getString("cpfLogin") : "";

        txtNome = view.findViewById(R.id.txtNome);
        txtCpf = view.findViewById(R.id.txtCpf);
        txtTelefone = view.findViewById(R.id.txtTelefone);

        CpfMask.applyCpfMask(txtCpf);
        TelefoneMask.applyTelefoneMask(txtTelefone);

        btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnExcluir = view.findViewById(R.id.btnExcluir);

        clienteDAO = new ClienteDAO(getActivity());
        cliente = clienteDAO.readByCpf(cpf);

        // Verificando se o cliente foi encontrado e exibindo os dados
        if (cliente != null) {
            // Atribuindo os valores nas TextViews
            txtNome.setText(cliente.getNome());
            txtCpf.setText(cliente.getCpf());
            txtTelefone.setText(cliente.getTelefone());
        } else {
            Toast.makeText(getActivity(), "Cliente não encontrado", Toast.LENGTH_SHORT).show();
        }

        // botão atualizar
        btnAtualizar.setOnClickListener(v -> {
            // Se o cliente for encontrado, atualiza os dados
            cliente.setNome(txtNome.getText().toString());
            cliente.setCpf(txtCpf.getText().toString());
            cliente.setTelefone(txtTelefone.getText().toString());
            clienteDAO.update(cliente);
            Toast.makeText(getActivity(), "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
        });

        // botão excluir
        btnExcluir.setOnClickListener(v -> {
            clienteDAO.delete(cliente);

            Toast.makeText(getActivity(), "Cliente excluido, saindo do app", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);

            getActivity().finish();
        });

        return view;
    }
}
