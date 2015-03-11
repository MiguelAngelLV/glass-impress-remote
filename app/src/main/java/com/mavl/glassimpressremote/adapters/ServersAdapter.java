package com.mavl.glassimpressremote.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.widget.CardScrollAdapter;

import org.libreoffice.impressremote.communication.Server;
import org.libreoffice.impressremote.util.Intents;

import java.util.ArrayList;

public class ServersAdapter extends CardScrollAdapter {

    private ArrayList<Server> mServers;


    public ServersAdapter() {
        mServers = new ArrayList<>();
    }

    public void addServer(Server server) {
        mServers.add(server);
        notifyDataSetChanged();
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
        return null;
    }

    @Override
    public int getPosition(Object o) {

        for (int i = 0; i < mServers.size(); i++)
            if (mServers.get(i) == o)
                return i;

        return -1;

    }


    private BroadcastReceiver mEvents = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intents.Actions.SERVERS_LIST_CHANGED.equals(intent.getAction()))

        }
    };


}
