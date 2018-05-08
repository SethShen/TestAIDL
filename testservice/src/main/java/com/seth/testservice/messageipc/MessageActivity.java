package com.seth.testservice.messageipc;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.seth.testservice.R;

import org.jetbrains.annotations.Nullable;

/**
 * Created by hspcadmin on 2018/5/8.
 */

public class MessageActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();
    private static final int MSG_SAY_HEOOL  = 1;
    private Messenger messenger = null;
    private Boolean isBound = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
    }

    public void getMessage(View view){
        if(!isBound)
            return;
        Message msg = Message.obtain(null,MSG_SAY_HEOOL,0,0);
        try {
            messenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"开始绑定");
        Intent intent = new Intent();
        intent.setAction("com.seth.testservice.Service");
        intent.setPackage("com.seth.testservice");
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBound)
            unbindService(connection);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messenger = new Messenger(service);
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messenger = null;
            isBound = false;
        }
    };
}
