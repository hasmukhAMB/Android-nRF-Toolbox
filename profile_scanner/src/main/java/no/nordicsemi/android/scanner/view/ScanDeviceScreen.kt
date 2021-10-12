package no.nordicsemi.android.scanner.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import no.nordicsemi.android.scanner.viewmodel.ScanDevicesViewModel
import no.nordicsemi.android.theme.view.StringListDialog

@Composable
fun ScanDeviceScreen(serviceId: String, finishAction: (ScanDeviceScreenResult) -> Unit) {
    val viewModel: ScanDevicesViewModel = hiltViewModel()
    val data = viewModel.data.collectAsState().value

    val isScreenActive = viewModel.isActive.collectAsState().value

    LaunchedEffect(isScreenActive) {
        if (!isScreenActive) {
            viewModel.stopScanner()
            finishAction(ScanDeviceScreenResult.OK)
        } else {
            viewModel.startScan(serviceId)
        }
    }

    StringListDialog(items = data.devices.map { it.displayName() }) {
        val selectedDevice = data.devices[it]
        viewModel.onEvent(OnDeviceSelected(selectedDevice))
    }
}
