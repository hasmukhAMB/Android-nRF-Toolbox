package no.nordicsemi.android.nrftoolbox

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import no.nordicsemi.android.permission.tools.NordicBleScanner
import no.nordicsemi.android.permission.tools.PermissionHelper
import no.nordicsemi.android.permission.tools.ScannerStatus
import no.nordicsemi.android.permission.viewmodel.BluetoothPermissionState
import no.nordicsemi.android.service.SelectedBluetoothDeviceHolder
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val bleScanner: NordicBleScanner,
    private val permissionHelper: PermissionHelper,
    private val selectedDevice: SelectedBluetoothDeviceHolder
): ViewModel() {

    val state= MutableStateFlow(NavigationTarget(NavDestination.HOME))
    private var targetDestination = NavDestination.HOME

    fun navigate(destination: NavDestination) {
        targetDestination = destination
        navigateToNextScreen()
    }

    fun navigateUp() {
        targetDestination = NavDestination.HOME
        state.value = NavigationTarget(NavDestination.HOME)
    }

    fun finish() {
        if (state.value.destination != targetDestination) {
            navigateToNextScreen()
        }
    }

    private fun getBluetoothState(): BluetoothPermissionState {
        return if (!permissionHelper.isRequiredPermissionGranted()) {
            BluetoothPermissionState.PERMISSION_REQUIRED
        } else when (bleScanner.getBluetoothStatus()) {
            ScannerStatus.NOT_AVAILABLE -> BluetoothPermissionState.BLUETOOTH_NOT_AVAILABLE
            ScannerStatus.DISABLED -> BluetoothPermissionState.BLUETOOTH_NOT_ENABLED
            ScannerStatus.ENABLED -> selectedDevice.device?.let { BluetoothPermissionState.READY } ?: BluetoothPermissionState.DEVICE_NOT_CONNECTED
        }
    }

    private fun navigateToNextScreen() {
        val destination = when (getBluetoothState()) {
            BluetoothPermissionState.PERMISSION_REQUIRED -> NavDestination.REQUEST_PERMISSION
            BluetoothPermissionState.BLUETOOTH_NOT_AVAILABLE -> NavDestination.BLUETOOTH_NOT_AVAILABLE
            BluetoothPermissionState.BLUETOOTH_NOT_ENABLED -> NavDestination.BLUETOOTH_NOT_ENABLED
            BluetoothPermissionState.DEVICE_NOT_CONNECTED -> NavDestination.DEVICE_NOT_CONNECTED
            BluetoothPermissionState.READY -> targetDestination
        }

        val args = if (destination == NavDestination.DEVICE_NOT_CONNECTED) {
            val key = NavDestination.DEVICE_NOT_CONNECTED.argsKey
            val value = createServiceId(targetDestination)
            "$key=$value"
        } else {
            null
        }
        state.tryEmit(NavigationTarget(destination, args))
    }

    private fun createServiceId(destination: NavDestination): String {
        return when (destination) {
            NavDestination.HOME -> ""
            NavDestination.CSC -> ""
            NavDestination.HRS -> ""
            NavDestination.HTS -> ""
            NavDestination.GLS -> ""
            NavDestination.REQUEST_PERMISSION,
            NavDestination.BLUETOOTH_NOT_AVAILABLE,
            NavDestination.BLUETOOTH_NOT_ENABLED,
            NavDestination.DEVICE_NOT_CONNECTED -> throw IllegalArgumentException("There is no serivce related to the destination: $destination")
        }
    }
}
