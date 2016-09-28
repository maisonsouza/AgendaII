package com.maiso.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaAlunos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        String [] alunos = {"Maison","Jose"};
        ListView lista = (ListView) findViewById(R.id.lista_alunos);
        ArrayAdapter<String> listaadaptada = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,alunos);
        lista.setAdapter(listaadaptada);
    }
}
