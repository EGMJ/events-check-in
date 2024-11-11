package com.example.events_check_in;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.events_check_in.dao.ClienteDAO;
import com.example.events_check_in.model.Cliente;

public class AjustesFragment extends Fragment {
    private EditText edtId;
    private EditText edtNome;
    private EditText edtCpf;
    private EditText edtTelefone;
    private Button btnAtualizar;
    private Button btnExcluir;
    private Button btnVoltar;
    private ClienteDAO dao;
    private Cliente cliente;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ajustes, container, false);

        edtId = view.findViewById(R.id.edtId);
        edtNome = view.findViewById(R.id.edtNome);
        edtCpf = view.findViewById(R.id.edtCpf);
        edtTelefone = view.findViewById(R.id.edtTelefone);
        btnAtualizar = view.findViewById(R.id.btnAtualizar);
        btnExcluir = view.findViewById(R.id.btnExcluir);
        btnVoltar = view.findViewById(R.id.btnVoltar);

        // botão atualizar
        btnAtualizar.setOnClickListener(v -> {
            String idText = edtId.getText().toString().trim();
            if (idText.isEmpty()) {
                Toast.makeText(getActivity(), "Por favor, insira um ID para atualizar os dados.", Toast.LENGTH_SHORT).show();
            } else {
                dao = new ClienteDAO(getActivity());

                // Verifica se o ID existe no banco
                cliente = dao.read(Integer.parseInt(idText));

                // Se o cliente não for encontrado, exibe uma mensagem
                if (cliente.getId() == null) {
                    Toast.makeText(getActivity(), "ID não encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Se o cliente for encontrado, atualiza os dados
                    cliente.setNome(edtNome.getText().toString());
                    cliente.setCpf(edtCpf.getText().toString());
                    cliente.setTelefone(edtTelefone.getText().toString());
                    dao.update(cliente);
                    Toast.makeText(getActivity(), "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // botão excluir
        btnExcluir.setOnClickListener(v -> {
            String idText = edtId.getText().toString().trim();
            if (idText.isEmpty()) {
                Toast.makeText(getActivity(), "Por favor, insira um ID para excluir os dados.", Toast.LENGTH_SHORT).show();
            } else {
                dao = new ClienteDAO(getActivity());

                // Verifica se o ID existe no banco
                cliente = dao.read(Integer.parseInt(idText));

                // Se o cliente não for encontrado, exibe uma mensagem
                if (cliente.getId() == null) {
                    Toast.makeText(getActivity(), "ID não encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    // Se o cliente for encontrado, exclui os dados
                    dao.delete(cliente);
                    edtId.setText(null);
                    edtNome.setText(null);
                    edtCpf.setText(null);
                    edtTelefone.setText(null);
                    Toast.makeText(getActivity(), "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // botão voltar
        btnVoltar.setOnClickListener(v -> {
            requireActivity().finish();
        });

        return view;
    }
}
