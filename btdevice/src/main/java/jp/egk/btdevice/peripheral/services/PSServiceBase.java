package jp.egk.btdevice.peripheral.services;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.os.Build;

/**
 * Created by kenta on 2017/02/19.
 */

public abstract class PSServiceBase {

    protected BluetoothGattServer mGattServer;

    public PSServiceBase(BluetoothGattServer gattServer) {
        mGattServer = gattServer;
    }

    abstract public String getServiceUuid();

    abstract public String[] getCharacteristicUuid();

    abstract public int getServiceType();

    abstract public void setupService();


    public void onCharacteristicReadRequest(android.bluetooth.BluetoothDevice device, int requestId,
                                            int offset, BluetoothGattCharacteristic characteristic) {
    }


    public void onCharacteristicWriteRequest(android.bluetooth.BluetoothDevice device, int requestId,
                                             BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
                                             int offset, byte[] value) {
    }

//    public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {}
//
//
//    public void onServiceAdded(int status, BluetoothGattService service){}
//
//    public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {}
//
//    public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {}
//
//    public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {}
//
//    public void onNotificationSent(BluetoothDevice device, int status) {}
//
//    public void onMtuChanged(BluetoothDevice device, int mtu) {}




}
