package jp.egk.btdevice.peripheral.services;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattService;
import android.os.Build;

import java.util.UUID;

import static jp.egk.btdevice.peripheral.PSServices.DEVICE_INFORMATION_SERVICE_UUID;
import static jp.egk.btdevice.peripheral.PSServices.FIRMWARE_REVISION_UUID;
import static jp.egk.btdevice.peripheral.PSServices.MANUFACTURE_NAME_UUID;
import static jp.egk.btdevice.peripheral.PSServices.MODEL_NUMBER_UUID;
import static jp.egk.btdevice.peripheral.PSServices.SERIAL_NUMBER_UUID;
import static jp.egk.btdevice.peripheral.PSServices.SOFTWARE_REVISION_UUID;
import static jp.egk.btdevice.peripheral.PSServices.SYSTEM_ID_UUID;

/**
 * Created by kenta on 2017/02/19.
 */

public class PSDeviceInformation extends PSServiceBase {

    private String mManufactureName = Build.MANUFACTURER;

    public PSDeviceInformation(BluetoothGattServer gattServer) {
        super(gattServer);
    }

    @Override
    public String getServiceUuid() {
        return DEVICE_INFORMATION_SERVICE_UUID;
    }

    @Override
    public String[] getCharacteristicUuid() {
        return new String[]{MANUFACTURE_NAME_UUID};
    }

    @Override
    public int getServiceType() {
        return BluetoothGattService.SERVICE_TYPE_PRIMARY;
    }

    @Override
    public void setupService() {

        BluetoothGattService service = new BluetoothGattService(
                UUID.fromString(DEVICE_INFORMATION_SERVICE_UUID),
                BluetoothGattService.SERVICE_TYPE_PRIMARY);

        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(MANUFACTURE_NAME_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(MODEL_NUMBER_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(SERIAL_NUMBER_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(FIRMWARE_REVISION_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(SOFTWARE_REVISION_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        characteristic = new BluetoothGattCharacteristic(
                UUID.fromString(SYSTEM_ID_UUID),
                BluetoothGattCharacteristic.PROPERTY_READ,
                BluetoothGattCharacteristic.PERMISSION_READ);
        service.addCharacteristic(characteristic);

        mGattServer.addService(service);

    }

    @Override
    public void onCharacteristicReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattCharacteristic characteristic) {
        byte[] value = new byte[0];

        if (characteristic.getUuid().toString().equals(MANUFACTURE_NAME_UUID)) {
            value = mManufactureName.getBytes();
        }
        else if (characteristic.getUuid().toString().equals(MODEL_NUMBER_UUID)) {
            value = Build.MODEL.getBytes();
        }
        else if (characteristic.getUuid().toString().equals(SERIAL_NUMBER_UUID)) {
            value = Build.SERIAL.getBytes();
        }
        else if (characteristic.getUuid().toString().equals(FIRMWARE_REVISION_UUID)) {
            value = Build.HARDWARE.getBytes();
        }
        else if (characteristic.getUuid().toString().equals(SOFTWARE_REVISION_UUID)) {
            value = Build.DISPLAY.getBytes();
        }
        else if (characteristic.getUuid().toString().equals(SYSTEM_ID_UUID)) {
            value = BluetoothAdapter.getDefaultAdapter().getAddress().getBytes();
        }

        characteristic.setValue(value);
        mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());


    }

//    @Override
//    public void onCharacteristicWriteRequest(BluetoothDevice device, int requestId, BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
//        if (characteristic.getUuid().toString().equals(MANUFACTURE_NAME_UUID)) {
//
//            mManufactureName = new String(value);
//            if (responseNeeded) {
//                mGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, characteristic.getValue());
//            }
//        }
//    }
}
