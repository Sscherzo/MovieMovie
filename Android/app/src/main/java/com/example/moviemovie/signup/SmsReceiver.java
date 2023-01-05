package com.example.moviemovie.signup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.text.SimpleDateFormat;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        // parseSmsMessage() 메서드의 코드들은 SMS문자의 내용을 뽑아내는 정형화된 코드이다.
        // 복잡해 보일 수 있으나 그냥 그대로 가져다 쓰면 된다.
        SmsMessage[] messages = parseSmsMessage(bundle);

        if(messages.length>0){
            // 문자메세지 내용 추출
            String contents = messages[0].getMessageBody().toString();
            Log.d(TAG, "contents: "+contents);
            // 해당 내용을 모두 합쳐서 액티비티로 보낸다.
            sendToActivity(context,contents);
        }
    }

    private void sendToActivity(Context context, String str){
        Intent intent = new Intent(context, SignUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("string", str);
        context.startActivity(intent);
    }

    // 정형화된 코드. 그냥 가져다 쓰면 된다.
    private SmsMessage[] parseSmsMessage(Bundle bundle){
        Object[] objs = (Object[])bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];

        for(int i=0;i<objs.length;i++){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i], format);
            }
            else{
                messages[i] = SmsMessage.createFromPdu((byte[])objs[i]);
            }

        }
        return messages;
    }
}