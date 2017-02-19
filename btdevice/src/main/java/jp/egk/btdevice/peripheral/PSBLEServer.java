package jp.egk.btdevice.peripheral;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;

import java.util.HashMap;
import java.util.UUID;

import jp.egk.btdevice.peripheral.services.PSDeviceInformation;
import jp.egk.btdevice.peripheral.services.PSImmediateAlert;
import jp.egk.btdevice.peripheral.services.PSServiceBase;

import static jp.egk.btdevice.peripheral.PSServices.ALERT_SERVICE_UUID;
import static jp.egk.btdevice.peripheral.PSServices.DEVICE_INFORMATION_SERVICE_UUID;
import static jp.egk.btdevice.peripheral.PSServices.MANUFACTURE_NAME_UUID;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class PSBLEServer extends BluetoothGattServerCallback {

    //BLE
    private BluetoothGattServer bluetoothGattServer;

    private HashMap<String, PSServiceBase> mServiceMap = new HashMap<>();



    public PSBLEServer() {
    }

    public void setBluetoothGattServer(BluetoothGattServer gattServer) {
        this.bluetoothGattServer = gattServer;
    }

    public void createServices() {
        mServiceMap.put(DEVICE_INFORMATION_SERVICE_UUID, new PSDeviceInformation(bluetoothGattServer));
        mServiceMap.put(ALERT_SERVICE_UUID, new PSImmediateAlert(bluetoothGattServer));
    }


    public void setupServices() {
        createServices();

        for (PSServiceBase service : mServiceMap.values()) {
            service.setupService();
        }
    }

    protected PSServiceBase getService(UUID serviceUuid) {

        if (mServiceMap.containsKey(serviceUuid.toString())) {
            return mServiceMap.get(serviceUuid.toString());
        }

        return null;

    }




    //セントラル（クライアント）からReadRequestが来ると呼ばれる
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onCharacteristicReadRequest(android.bluetooth.BluetoothDevice device, int requestId,
                                            int offset, BluetoothGattCharacteristic characteristic) {

        PSServiceBase service = getService(characteristic.getService().getUuid());
        if (service != null) {
            service.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        }
        else {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
        }
//
//        //セントラルに任意の文字を返信する
//        characteristic.setValue("something you want to send");
//        bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset,
//                characteristic.getValue());
//
    }

    //セントラル（クライアント）からWriteRequestが来ると呼ばれる
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void onCharacteristicWriteRequest(android.bluetooth.BluetoothDevice device, int requestId,
                                             BluetoothGattCharacteristic characteristic, boolean preparedWrite, boolean responseNeeded,
                                             int offset, byte[] value) {

        PSServiceBase service = getService(characteristic.getService().getUuid());
        if (service != null) {
            service.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }
        else {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
        }
//
//        //セントラルにnullを返信する
//        bluetoothGattServer.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, offset, null);
    }

    @Override
    public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
        super.onConnectionStateChange(device, status, newState);
    }

    @Override
    public void onServiceAdded(int status, BluetoothGattService service) {
        super.onServiceAdded(status, service);
    }

    @Override
    public void onDescriptorReadRequest(BluetoothDevice device, int requestId, int offset, BluetoothGattDescriptor descriptor) {
        super.onDescriptorReadRequest(device, requestId, offset, descriptor);
    }

    @Override
    public void onDescriptorWriteRequest(BluetoothDevice device, int requestId, BluetoothGattDescriptor descriptor, boolean preparedWrite, boolean responseNeeded, int offset, byte[] value) {
        super.onDescriptorWriteRequest(device, requestId, descriptor, preparedWrite, responseNeeded, offset, value);
    }

    @Override
    public void onExecuteWrite(BluetoothDevice device, int requestId, boolean execute) {
        super.onExecuteWrite(device, requestId, execute);
    }

    @Override
    public void onNotificationSent(BluetoothDevice device, int status) {
        super.onNotificationSent(device, status);
    }

    @Override
    public void onMtuChanged(BluetoothDevice device, int mtu) {
        super.onMtuChanged(device, mtu);
    }
}