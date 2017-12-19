package com.kcoppock.bluetoothconnector;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by illiacDev on 2017-12-19.
 */
@RunWith(AndroidJUnit4.class)
public class BL_UtilTest {

    @Test
    public void listPairDevices() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        BL_Util.listPairDevices();
    }

}