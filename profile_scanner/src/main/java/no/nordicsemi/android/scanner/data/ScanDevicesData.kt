package no.nordicsemi.android.scanner.data

import android.bluetooth.BluetoothDevice

data class ScanDevicesData(
    val devices: List<BluetoothDevice> = emptyList()
) {

    fun copyWithNewDevice(device: BluetoothDevice): ScanDevicesData {
        val newDevices = devices + device
        return copy(devices = newDevices)
    }

    fun copyWithNewDevices(bleDevices: List<BluetoothDevice>): ScanDevicesData {
        val newDevices = devices + bleDevices
        return copy(devices = newDevices)
    }
}
