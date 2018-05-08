package com.seth.testservice.messageipc;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

/**
 * Created by hspcadmin on 2018/5/8.
 */

public class MessageService extends Service {
    private static final int MSG_SAY_HELLO = 1;

    class ServiceHandle extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "Say Hello~~", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    final Messenger messenger = new Messenger(new ServiceHandle());
    
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(this.getClass().getSimpleName()+":bing    ","绑定结束");
        Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }
}
