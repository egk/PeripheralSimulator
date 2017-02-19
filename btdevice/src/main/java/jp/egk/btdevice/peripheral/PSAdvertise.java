package jp.egk.btdevice.peripheral;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.ParcelUuid;

import java.util.UUID;

import android.content.Context;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;

import static jp.egk.btdevice.peripheral.PSServices.ALERT_LEVEL_UUID;
import static jp.egk.btdevice.peripheral.PSServices.ALERT_SERVICE_UUID;
import static jp.egk.btdevice.peripheral.PSServices.DEVICE_INFORMATION_SERVICE_UUID;
import static jp.egk.btdevice.peripheral.PSServices.MANUFACTURE_NAME_UUID;

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class PSAdvertise extends AdvertiseCallback {

    //アドバタイズの設定
    private static final boolean CONNECTABLE = true;
    private static final int TIMEOUT = 0;

    //BLE
    private BluetoothLeAdvertiser advertiser;
    private BluetoothGattServer gattServer;

    //アドバタイズを開始
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startAdvertise(Context context) {

        //BLE各種を取得
        BluetoothManager manager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter adapter = manager.getAdapter();
        advertiser = getAdvertiser(adapter);
        gattServer = getGattServer(context, manager);

//        //UUIDを設定
//        setUuid();

        //アドバタイズを開始
        advertiser.startAdvertising(makeAdvertiseSetting(),makeAdvertiseData(),this);
    }

    //アドバタイズを停止
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void stopAdvertise() {

        //サーバーを閉じる
        if (gattServer != null) {
            gattServer.clearServices();
            gattServer.close();
            gattServer = null;
        }

        //アドバタイズを停止
        if (advertiser != null) {
            advertiser.stopAdvertising(this);
            advertiser = null;
        }
    }

    //Advertiserを取得
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private BluetoothLeAdvertiser getAdvertiser(BluetoothAdapter adapter) {
        return adapter.getBluetoothLeAdvertiser();
    }

    //GattServerを取得
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private BluetoothGattServer getGattServer(Context context, BluetoothManager manager) {

        PSBLEServer bleServer = new PSBLEServer();

        BluetoothGattServer gattServer = manager.openGattServer(context, bleServer);

        bleServer.setBluetoothGattServer(gattServer);
        bleServer.setupServices();

        return gattServer;
    }

//    //UUIDを設定
//    private void setUuid() {
//
//        //serviceUUIDを設定
//        BluetoothGattService service = new BluetoothGattService(
//                UUID.fromString(DEVICE_INFORMATION_SERVICE_UUID),
//                BluetoothGattService.SERVICE_TYPE_PRIMARY);
//
//        //characteristicUUIDを設定
//        BluetoothGattCharacteristic characteristic = new BluetoothGattCharacteristic(
//                UUID.fromString(MANUFACTURE_NAME_UUID),
//                BluetoothGattCharacteristic.PROPERTY_READ |
//                        BluetoothGattCharacteristic.PROPERTY_WRITE,
//                BluetoothGattCharacteristic.PERMISSION_READ |
//                        BluetoothGattCharacteristic.PERMISSION_WRITE);
//
//        //characteristicUUIDをserviceUUIDにのせる
//        service.addCharacteristic(characteristic);
//        //serviceUUIDをサーバーにのせる
//        gattServer.addService(service);
//
//
//        BluetoothGattService fmpService = new BluetoothGattService(
//                UUID.fromString(ALERT_SERVICE_UUID),
//                BluetoothGattService.SERVICE_TYPE_PRIMARY);
//        BluetoothGattCharacteristic fmpCharacteristic = new BluetoothGattCharacteristic(
//                UUID.fromString(ALERT_LEVEL_UUID),
//                BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE,
//                BluetoothGattCharacteristic.PERMISSION_WRITE);
//        fmpService.addCharacteristic(fmpCharacteristic);
//        gattServer.addService(fmpService);
//    }

    //アドバタイズを設定
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private AdvertiseSettings makeAdvertiseSetting() {

        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();

        //アドバタイズモード
        builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
        //アドバタイズパワー
        builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_ULTRA_LOW);
        //ペリフェラルへの接続を許可する
        builder.setConnectable(CONNECTABLE);
        //調査中。。
        builder.setTimeout(TIMEOUT);

        return builder.build();
    }

    //アドバタイズデータを作成
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private AdvertiseData makeAdvertiseData() {

        AdvertiseData.Builder builder = new AdvertiseData.Builder();
        builder.addServiceUuid(new ParcelUuid(UUID.fromString(DEVICE_INFORMATION_SERVICE_UUID)));
        builder.addServiceUuid(new ParcelUuid(UUID.fromString(ALERT_SERVICE_UUID)));

        builder.setIncludeDeviceName(true);
        builder.setIncludeTxPowerLevel(true);


        return builder.build();
    }

    @Override
    public void onStartSuccess(AdvertiseSettings settingsInEffect) {
        super.onStartSuccess(settingsInEffect);
    }

    @Override
    public void onStartFailure(int errorCode) {
        super.onStartFailure(errorCode);
    }
}