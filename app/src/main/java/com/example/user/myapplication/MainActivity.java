package com.example.user.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView)findViewById(R.id.textView);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Intent intent = new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this,0,intent,0);
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(nfcAdapter != null){
            nfcAdapter.enableForegroundDispatch(this,pendingIntent,null,null);
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        if(nfcAdapter != null){
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        Parcelable[] rasMsgs =intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        if(rasMsgs !=null){
            NdefMessage msgs=(NdefMessage)rasMsgs[0];
            NdefRecord[] rec=msgs.getRecords();
            byte[] bt=rec[0].getPayload();
            String text =new String(bt);
            textView.setText(text);
        }
    }
}
