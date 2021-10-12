package no.nordicsemi.android.theme.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import no.nordicsemi.android.theme.NordicColors

@Composable
fun StringListDialog(items: List<String>, onItemSelected: (Int) -> Unit) {
    Dialog(onDismissRequest = {}) {
        StringListView(items, onItemSelected)
    }
}

@Composable
private fun StringListView(items: List<String>, onItemSelected: (Int) -> Unit) {
    Card(
        modifier = Modifier.height(300.dp),
        backgroundColor = NordicColors.NordicGray4.value(),
        shape = RoundedCornerShape(10.dp),
        elevation = 0.dp
    ) {
        Column {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wheel size",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {

                items.forEachIndexed { i, entry ->
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = entry,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemSelected(i) }
                    )

                    if (i != items.size - 1) {
                        Spacer(modifier = Modifier.height(4.dp))
                        TabRowDefaults.Divider(color = NordicColors.NordicLightGray.value(), thickness = 1.dp/2)
                    }
                }
            }
        }
    }
}
