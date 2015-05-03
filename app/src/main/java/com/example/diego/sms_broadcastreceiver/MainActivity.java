package com.example.diego.sms_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
TextView texto;
    BroadcastReceiver Broad=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto= (TextView) findViewById(R.id.textView);
        Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_SHORT).show();


        Broad=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                Toast.makeText(context,"BroadcasrReceiver",Toast.LENGTH_SHORT).show();
                final Bundle bundle = intent.getExtras();

                try {
                    if(bundle != null){

                        final Object[] pdusObj = (Object[]) bundle.get("pdus");

                        for(int i = 0; i < pdusObj.length; i++){
                            SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                            String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                            String senderNum = phoneNumber;
                            String message = currentMessage.getDisplayMessageBody();
                            Toast.makeText(context,"senderNum: " + senderNum + " message: " + message,Toast.LENGTH_SHORT).show();
                            texto.setText(message);
                        }
                    }
                } catch (Exception e) {
                    Log.e("SmsReceiver", "Exception smsReceiver" +e);
                }

            }
        };

        registerReceiver(Broad, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));//POWER_CONNECTED

        registerReceiver(Broad, new IntentFilter("android.intent.action.POWER_CONNECTED"));//POWER_CONNECTED
        registerReceiver(Broad,new IntentFilter("android.intent.action.ACTION_POWER_CONNECTED"));

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(Broad, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));//POWER_CONNECTED
        Toast.makeText(getApplicationContext(),"onResume",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onPause() {
        super.onPause();
      //  registerReceiver(Broad, new IntentFilter("android.intent.action.SMS_RECEIVED"));

        Toast.makeText(getApplicationContext(),"onPause",Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(Broad);
        Toast.makeText(getApplicationContext(),"onDestroy",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
