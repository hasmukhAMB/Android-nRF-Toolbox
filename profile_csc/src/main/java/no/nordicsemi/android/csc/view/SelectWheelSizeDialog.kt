package no.nordicsemi.android.csc.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import no.nordicsemi.android.csc.R
import no.nordicsemi.android.theme.TestTheme
import no.nordicsemi.android.theme.view.StringListDialog

@Composable
internal fun SelectWheelSizeDialog(onEvent: (OnWheelSizeSelected) -> Unit) {
    val wheelEntries = stringArrayResource(R.array.wheel_entries)
    val wheelValues = stringArrayResource(R.array.wheel_values)

    StringListDialog(wheelEntries.toList()) {
        onEvent(OnWheelSizeSelected(wheelValues[it].toInt(), wheelEntries[it]))
    }
}

@Preview
@Composable
internal fun DefaultPreview() {
    TestTheme {
        val wheelEntries = stringArrayResource(R.array.wheel_entries)
        StringListDialog(wheelEntries.toList()) { }
    }
}
