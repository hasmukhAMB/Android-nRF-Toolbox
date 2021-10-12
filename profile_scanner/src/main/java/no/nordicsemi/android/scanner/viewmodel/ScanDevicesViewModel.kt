package no.nordicsemi.android.scanner.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.BluetoothUuid
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import no.nordicsemi.android.theme.viewmodel.CloseableViewModel
import javax.inject.Inject

@HiltViewModel
class ScanDevicesViewModel @Inject constructor() : CloseableViewModel() {

    private val scanner = BluetoothLeScannerCompat.getScanner()

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            super.onBatchScanResults(results)
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
        }
    }

    fun startScan(serviceId: String) {
        val scanner: BluetoothLeScannerCompat = BluetoothLeScannerCompat.getScanner()
        val settings: ScanSettings = ScanSettings.Builder()
            .setLegacy(false)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(5000)
            .setUseHardwareBatchingIfSupported(true)
            .build()

        val parcelUuid = BluetoothUuid.parseUuidFrom(serviceId.toByteArray())
        val filters: MutableList<ScanFilter> = ArrayList()
        filters.add(ScanFilter.Builder().setServiceUuid(parcelUuid).build())

        scanner.startScan(filters, settings, scanCallback)
    }

    fun stopScanner() {
        scanner.stopScan(scanCallback)
    }
}
