package com.example.events_check_in.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.events_check_in.model.Cliente;
import com.example.events_check_in.util.Conexao;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    private Conexao conexao;
    private SQLiteDatabase banco;

    // Construtor
    public ClienteDAO(Context context){
        // Abrir a conexão com o banco
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase(); // Para escrita no banco
    }

    // Método para inserir dados
    public long insert(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf", cliente.getCpf());
        values.put("telefone", cliente.getTelefone());
        values.put("senha", cliente.getSenha());
        return banco.insert("cliente", null, values); // Inserindo os dados na tabela
    }

    // Método para verificar login (CPF e senha juntos)
    public boolean isLoginValido(String cpf, String senha) {
        String query = "SELECT * FROM cliente WHERE cpf = ? AND senha = ?";
        Cursor cursor = banco.rawQuery(query, new String[]{cpf, senha});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    public boolean isCpfCadastrado(String cpf) {
        String query = "SELECT * FROM cliente WHERE cpf = ?";
        Cursor cursor = banco.rawQuery(query, new String[]{cpf});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Método para atualizar dados
    public void update(Cliente cliente){
        ContentValues values = new ContentValues();
        values.put("nome", cliente.getNome());
        values.put("cpf", cliente.getCpf());
        values.put("telefone", cliente.getTelefone());
        values.put("senha", cliente.getSenha());
        String[] args = {cliente.getId().toString()};
        banco.update("cliente", values, "id=?", args);
    }

    // Método para deletar dados
    public void delete(Cliente cliente){
        String[] args = {cliente.getId().toString()};
        banco.delete("cliente", "id=?", args);
    }

    // Consultar todos os clientes
    public List<Cliente> obterClientes(){
        List<Cliente> clientes = new ArrayList<>();

        Cursor cursor = banco.query("cliente", new String[]{"id", "nome", "cpf", "telefone", "senha"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Cliente cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setNome(cursor.getString(1));
            cliente.setCpf(cursor.getString(2));
            cliente.setTelefone(cursor.getString(3));
            cliente.setSenha(cursor.getString(4));
            clientes.add(cliente);
        }
        cursor.close();
        return clientes;
    }

    // Consultar cliente específico por ID
    public Cliente read(Integer id) {
        String[] args = {String.valueOf(id)};
        Cliente cliente = null;

        Cursor cursor = banco.query("cliente", new String[]{"id", "nome", "cpf", "telefone", "senha"},
                "id=?", args, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            cliente = new Cliente();
            cliente.setId(cursor.getInt(0));
            cliente.setNome(cursor.getString(1));
            cliente.setCpf(cursor.getString(2));
            cliente.setTelefone(cursor.getString(3));
            cliente.setSenha(cursor.getString(4)); // Adicionando a senha
        }

        if (cursor != null) {
            cursor.close();
        }

        return cliente; // Retorna 'null' se não encontrar o cliente
    }
}
