package truonglt.demo.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import truonglt.demo.se1414_messaging.MainActivity;

public class SMSReceiver extends BroadcastReceiver {
    private String SMS_RECEIVED = "SMS_RECEIVED_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] sms = null;
        String str = "SMS from ";
        if(bundle != null){
            Object[] pdus = (Object[])  bundle.get("pdus");
            sms = new SmsMessage[pdus.length];
            for (int i = 0; i< sms.length;i++ ){
                sms[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                if(i == 0){
                    str += sms[i].getOriginatingAddress();
                    str += ": ";
                }
                str += sms[i].getMessageBody().toString();
            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            Log.d("Receiver", str);
            if(MainActivity.status == 2){
                Intent mainIntent = new Intent(context, MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mainIntent);
            }
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(SMS_RECEIVED);
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
        }
    }
}
