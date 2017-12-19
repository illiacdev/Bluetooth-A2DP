package com.kcoppock.bluetoothconnector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by illiacDev on 2017-12-19.
 */

public class BL_Util {
    private static final String TAG = "BL";
    interface IOUT{
        void onDiscoveredDevice(BluetoothDevice device);
    }

    static public void listPairDevices(IOUT iout){


        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        ArrayList<String> mArrayAdapter = new ArrayList<>();
// If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                String e = device.getName() + "\n" + device.getAddress();
                mArrayAdapter.add(e);
                Log.i(TAG, "listPairDevices: " + e);
                iout.onDiscoveredDevice(device);
            }
        }
    }
}
