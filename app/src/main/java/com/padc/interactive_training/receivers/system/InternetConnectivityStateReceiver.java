package com.padc.interactive_training.receivers.system;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.padc.interactive_training.utils.NetworkUtils;

import java.util.Observable;

/**
 * Created by aung on 7/19/16.
 */
public class InternetConnectivityStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtils.isOnline(context)) {
            Toast.makeText(context, "Device is connected with Internet.", Toast.LENGTH_SHORT).show();
            getObservable().connectionChanged();
        } else {
            Toast.makeText(context, "Device is disconnected with Internet.", Toast.LENGTH_SHORT).show();
        }
    }

    public static class NetworkObservable extends Observable {
        private static NetworkObservable instance = null;

        private NetworkObservable() {
            // Exist to defeat instantiation.
        }

        public void connectionChanged(){
            setChanged();
            notifyObservers();
        }

        public static NetworkObservable getInstance(){
            if(instance == null){
                instance = new NetworkObservable();
            }
            return instance;
        }
    }

    public static NetworkObservable getObservable() {
        return NetworkObservable.getInstance();
    }
}
