package com.maiso.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.maiso.agenda.converter.AlunoConverter;
import com.maiso.agenda.dao.AlunoDAO;
import com.maiso.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by maiso on 16/10/2016.
 */

public class EnviaAlunosTask extends AsyncTask<Object,Object,String> {


    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

         dialog = ProgressDialog.show(context,"Aguarde","Enviando alunos...",true,true);
    }

    @Override
    protected String doInBackground(Object[] params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);
        return resposta;
    }

    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context,resposta,Toast.LENGTH_LONG).show();
    }
}
