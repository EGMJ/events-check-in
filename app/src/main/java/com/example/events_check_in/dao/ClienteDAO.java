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

    // construtor
    public ClienteDAO(Context context){
        // Abrir a conexão com o banco
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase(); // Para escrita no banco
    }

    // Método para inserir dados
    public long insert(Cliente aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        return banco.insert("aluno", null, values); // Inserindo os dados na tabela
    }

    // Método para verificar se o CPF já está cadastrado
    public boolean isCpfCadastrado(String cpf) {
        SQLiteDatabase db = conexao.getReadableDatabase(); // Para leitura no banco
        String query = "SELECT * FROM aluno WHERE cpf = ?";
        Cursor cursor = db.rawQuery(query, new String[]{cpf});
        boolean existe = cursor.getCount() > 0;
        cursor.close();
        return existe;
    }

    // Método para atualizar dados
    public void update(Cliente aluno){
        ContentValues values = new ContentValues();
        values.put("nome", aluno.getNome());
        values.put("cpf", aluno.getCpf());
        values.put("telefone", aluno.getTelefone());
        String[] args = {aluno.getId().toString()};
        banco.update("aluno", values, "id=?", args);
    }

    // Método para deletar dados
    public void delete(Cliente aluno){
        String[] args = {aluno.getId().toString()};
        banco.delete("aluno", "id=?", args);
    }

    // Consultar todos os alunos
    public List<Cliente> obterAlunos(){
        List<Cliente> alunos = new ArrayList<>();

        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                null, null, null, null, null);
        while(cursor.moveToNext()){
            Cliente a = new Cliente();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
            alunos.add(a);
        }
        cursor.close();
        return alunos;
    }

    // Consultar aluno específico por ID
    public Cliente read(Integer id) {
        String[] args = {String.valueOf(id)};
        Cliente a = null;  // Altere para 'null' em vez de criar um novo objeto 'Aluno'

        Cursor cursor = banco.query("aluno", new String[]{"id", "nome", "cpf", "telefone"},
                "id=?", args, null, null, null, null);

        // Verifique se o cursor tem algum dado
        if (cursor != null && cursor.moveToFirst()) {
            a = new Cliente();
            a.setId(cursor.getInt(0));
            a.setNome(cursor.getString(1));
            a.setCpf(cursor.getString(2));
            a.setTelefone(cursor.getString(3));
        }

        // Certifique-se de fechar o cursor
        if (cursor != null) {
            cursor.close();
        }

        return a;  // Retorna 'null' se não encontrar o aluno
    }

}
