package com.maiso.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.maiso.agenda.dao.AlunoDAO;
import com.maiso.agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);



        Button novo_aluno = (Button) findViewById(R.id.botao_adicionar);
        novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiproFormulario = new Intent(ListaAlunos.this,Formulario.class);
                startActivity(vaiproFormulario);
            }
        });
    }

    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        //String [] alunos = {"Maison","Jose","Maison","Jose","Maison","Jose","Maison","Jose","Mateus","José"};
        ListView listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> listaadaptada = new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(listaadaptada);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }
}
