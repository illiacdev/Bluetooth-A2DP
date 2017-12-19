package com.kcoppock.bluetoothconnector;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kcoppock.bluetoothconnector.databinding.ItemLayoutBinding;

import java.util.ArrayList;

/**
 * Created by illiacDev on 2017-12-19.
 */

public class DeviceSelectorFrgment extends DialogFragment {
    public DeviceSelectorFrgment() {}

    interface InterComm{
        void onSelect(BluetoothDevice device);
    }

    public void setInterComm(InterComm interComm) {
        this.interComm = interComm;
    }

    InterComm interComm;
   /* @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        return super.onCreateDialog(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("테스트")
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }*/

    ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
    private MyAdapter adapter;

//    public DeviceSelectorFrgment(ArrayList<BluetoothDevice> device) {this.device = device;}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        DeviceListView view = new DeviceListView(getContext());
        view.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MyAdapter();
        view.setAdapter(adapter);
//        TextView view = new TextView(getContext());
//        view.setText("테스트!");
        new Thread(() -> {
            BL_Util.listPairDevices(device -> deviceList.add(device));

            adapter.notifyDataSetChanged();

        }).run();

        return view;

    }

    class DeviceListView extends RecyclerView {

        public DeviceListView(Context context) {
            super(context);


        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ItemLayoutBinding bind;

        public MyViewHolder(ItemLayoutBinding bind) {
            super(bind.getRoot());

            this.bind = bind;
            bind.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("BL", "onClick: " + getAdapterPosition());
            interComm.onSelect(deviceList.get(getAdapterPosition()));
            DeviceSelectorFrgment.this.dismiss();
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater from = LayoutInflater.from(parent.getContext());
//            .inflate(R.layout.item_layout,
//                                                                      parent, false);
            ItemLayoutBinding bind = ItemLayoutBinding.inflate(from, parent, false);
            return new MyViewHolder(bind);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Log.i("BL", "onBindViewHolder: " + deviceList.get(position).getName());
//            holder.view.setText(deviceList.get(position).getName());
            holder.bind.name.setText(deviceList.get(position).getName());

        }

        @Override
        public int getItemCount() {
            return deviceList.size();
        }
    }

}
