package com.maiso.agenda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.maiso.agenda.adapter.AlunosAdapter;
import com.maiso.agenda.converter.AlunoConverter;
import com.maiso.agenda.dao.AlunoDAO;
import com.maiso.agenda.modelo.Aluno;

import java.io.Serializable;
import java.util.List;
import java.util.jar.Manifest;

import static android.content.Intent.ACTION_CALL;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.parseUri;

public class ListaAlunos extends AppCompatActivity {

    public static final int CODIGO_SMS = 2;
    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        if (ActivityCompat.checkSelfPermission(ListaAlunos.this, android.Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ListaAlunos.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, CODIGO_SMS);
        }

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
        //ArrayAdapter<Aluno> listaadaptada = new ArrayAdapter<Aluno>(this,R.layout.item_lista,alunos);
        AlunosAdapter adapter = new AlunosAdapter(this,alunos);
        listaAlunos.setAdapter(adapter);
    }



    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_lista_alunos_enviar_notas:
                new EnviaAlunosTask(this).execute();

            return true;
        }
        return false;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);
        final MenuItem item_ligar = menu.add("Ligar");
        item_ligar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (ActivityCompat.checkSelfPermission(ListaAlunos.this, android.Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(ListaAlunos.this, new String[]{android.Manifest.permission.CALL_PHONE},123);
                }else{
                    Intent intentvaipraLigacao = new Intent(ACTION_CALL);
                    intentvaipraLigacao.setData(Uri.parse("tel:"+aluno.getTelefone()));
                    startActivity(intentvaipraLigacao);
                }
                return false;
            }
        });






        MenuItem item_visitar = menu.add("Visitar Site");
        MenuItem deletar = menu.add("Deletar");
        MenuItem item_enviarSms = menu.add("Enviar SMS");
        MenuItem item_visualizarmapa = menu.add("Visualizar no mapa");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO dao = new AlunoDAO(ListaAlunos.this);
                dao.deleta(aluno);
                dao.close();
                carregaLista();
                Toast.makeText(ListaAlunos.this," Aluno "+aluno.getNome()+" Excluído com sucesso ",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Intent intentvaiproSite = new Intent(ACTION_VIEW);
        intentvaiproSite.setData(Uri.parse("http://"+aluno.getSite()));
        item_visitar.setIntent(intentvaiproSite);

        Intent intentvaiproSms = new Intent(ACTION_VIEW);
        intentvaiproSms.setData(Uri.parse("sms:(92)"+aluno.getTelefone()));
        item_enviarSms.setIntent(intentvaiproSms);

        Intent intentvaiproMapa = new Intent(ACTION_VIEW);
        intentvaiproMapa.setData(Uri.parse("geo:0,0?q="+aluno.getEndereco()));
        item_visualizarmapa.setIntent(intentvaiproMapa);




    }

}
