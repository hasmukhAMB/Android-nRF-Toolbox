package no.nordicsemi.android.scanner.view

import android.bluetooth.BluetoothDevice

sealed class ScanDevicesViewEvent

data class OnDeviceSelected(val device: BluetoothDevice) : ScanDevicesViewEvent()
