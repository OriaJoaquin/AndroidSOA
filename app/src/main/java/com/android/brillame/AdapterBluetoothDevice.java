package com.android.brillame;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterBluetoothDevice extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<BluetoothDevice> items = new ArrayList<BluetoothDevice>();

    public AdapterBluetoothDevice (Activity activity, ArrayList<BluetoothDevice> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public BluetoothDevice getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.list_item_bluetooth_device, null);
        }

        BluetoothDevice dir = items.get(position);

        TextView title = v.findViewById(R.id.valor);
        title.setText(dir.getAddress());

        TextView description = v.findViewById(R.id.texto);
        description.setText(dir.getName());

        return v;
    }

}
