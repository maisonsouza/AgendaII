package com.maiso.agenda.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.maiso.agenda.ListaAlunos;
import com.maiso.agenda.R;
import com.maiso.agenda.dao.AlunoDAO;

/**
 * Created by maiso on 06/10/2016.
 */

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlunoDAO dao = new AlunoDAO(context);
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String format = (String) intent.getSerializableExtra("format");

        SmsMessage sms = SmsMessage.createFromPdu(pdu,format);
        String telefone = sms.getDisplayOriginatingAddress();

        if(dao.ehAluno(telefone)){
            Toast.makeText(context,"Chegou SMS de um aluno",Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }

    }
}
