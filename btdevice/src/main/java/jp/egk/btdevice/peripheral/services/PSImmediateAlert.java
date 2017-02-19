package jp.egk.btdevice.peripheral.services;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;

import java.util.UUID;

import static jp.egk.btdevice.peripheral.PSServices.ALERT_LEVEL_UUID;
import static jp.egk.btdevice.peripheral.PSServices.ALERT_SERVICE_UUID;

/**
 * Created by kenta on 2017/02/19.
 */

public class PSImmediateAlert extends PSServiceBase {

    public PSImmediateAlert(BluetoothGattServer gattServer) {
        super(gattServer);
    }

    @Override
    public String getServiceUuid() {
        return ALERT_SERVICE_UUID;
    }

    @Override
    public String[] getCharacteristicUuid() {
        return new String[]{ALERT_LEVEL_UUID};
    }

    public int getServiceType() {
        return BluetoothGattService.SERVICE_TYPE_PRIMARY;
    }

    public void setupService(){

        BluetoothGattService fmpService = new BluetoothGattService(
                UUID.fromString(ALERT_SERVICE_UUID),
                BluetoothGattService.SERVICE_TYPE_PRIMARY);
        BluetoothGattCharacteristic fmpCharacteristic = new BluetoothGattCharacteristic(
                UUID.fromString(ALERT_LEVEL_UUID),
                BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE,
                BluetoothGattCharacteristic.PERMISSION_WRITE);
        fmpService.addCharacteristic(fmpCharacteristic);
        mGattServer.addService(fmpService);
    }

    @Override
    public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {

        if (characteristic.getUuid().toString().equals(ALERT_LEVEL_UUID)) {

        }
    }
}
