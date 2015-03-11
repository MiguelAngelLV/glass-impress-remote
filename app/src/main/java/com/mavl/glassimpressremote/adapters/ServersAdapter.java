package com.mavl.glassimpressremote.adapters;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;

import org.libreoffice.impressremote.communication.CommunicationService;
import org.libreoffice.impressremote.communication.Server;
import org.libreoffice.impressremote.util.Intents;

import java.util.ArrayList;
import java.util.List;

public class ServersAdapter extends CardScrollAdapter implements ServiceConnection{

    private Context mContext;
    private List<Server> mServers;
    private CommunicationService mService;

    public ServersAdapter(Context context) {
        mContext = context;
        mServers = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return mServers.size();
    }

    @Override
    public Object getItem(int i) {
        return mServers.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CardBuilder builder = new CardBuilder(mContext, CardBuilder.Layout.TEXT_FIXED);
        builder.setText(mServers.get(i).getName() + "\n" + mServers.get(i).getAddress());
        return builder.getView(view, viewGroup);
    }

    @Override
    public int getPosition(Object o) {
        return mServers.indexOf(o);
    }


    private void configureService(Context context) {
        //Conectamos con el servicio
        Intent aServiceIntent = Intents.buildCommunicationServiceIntent(context);
        context.startService(aServiceIntent);
        context.bindService(aServiceIntent, this, Context.BIND_AUTO_CREATE);

        //Añadimos los eventos que queremos recibir
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intents.Actions.SERVERS_LIST_CHANGED);
        context.registerReceiver(mEvents, filter);

    }


    private BroadcastReceiver mEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mService == null)
                return;

            if (!Intents.Actions.SERVERS_LIST_CHANGED.equals(intent.getAction()))
                return;

            mServers = mService.getServers();
            notifyDataSetChanged();
        }
    };


    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        CommunicationService.ServiceBinder aServiceBinder = (CommunicationService.ServiceBinder) service;
        mService = aServiceBinder.getService();
        mService.startServersSearch();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mService = null;
    }


 }
