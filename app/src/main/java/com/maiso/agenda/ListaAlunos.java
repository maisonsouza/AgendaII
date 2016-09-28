package com.maiso.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ListaAlunos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        String [] alunos = {"Maison","Jose","Maison","Jose","Maison","Jose","Maison","Jose","Mateus","José"};
        ListView lista = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<String> listaadaptada = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);
        lista.setAdapter(listaadaptada);

        Button novo_aluno = (Button) findViewById(R.id.botao_adicionar);
        novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiproFormulario = new Intent(ListaAlunos.this,Formulario.class);
                startActivity(vaiproFormulario);
            }
        });
    }
}
