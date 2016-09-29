package com.maiso.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.maiso.agenda.dao.AlunoDAO;
import com.maiso.agenda.modelo.Aluno;

import java.io.Serializable;
import java.util.List;

public class ListaAlunos extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
         listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);
                Intent vaiproFormulario = new Intent(ListaAlunos.this,Formulario.class);
                vaiproFormulario.putExtra("aluno", aluno);
                startActivity(vaiproFormulario);
                Toast.makeText(ListaAlunos.this," Aluno "+aluno.getNome()+" ",Toast.LENGTH_SHORT).show();


            }
        });



        Button novo_aluno = (Button) findViewById(R.id.botao_adicionar);
        novo_aluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vaiproFormulario = new Intent(ListaAlunos.this,Formulario.class);
                startActivity(vaiproFormulario);
            }
        });
        registerForContextMenu(listaAlunos);
    }




    private void carregaLista() {
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        //String [] alunos = {"Maison","Jose","Maison","Jose","Maison","Jose","Maison","Jose","Mateus","José"};

        ArrayAdapter<Aluno> listaadaptada = new ArrayAdapter<Aluno>(this,android.R.layout.simple_list_item_1,alunos);
        listaAlunos.setAdapter(listaadaptada);
    }



    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
                AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                Toast.makeText(ListaAlunos.this," Deletar o aluno "+aluno.getNome()+" ",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }


}
