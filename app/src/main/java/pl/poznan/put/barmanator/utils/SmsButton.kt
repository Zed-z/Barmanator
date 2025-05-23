package pl.poznan.put.barmanator.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import pl.poznan.put.barmanator.R

@Composable
fun SmsButton(
    message: String,
) {
    val context = LocalContext.current

    FloatingActionButton(
        onClick = {
            val smsIntent = Intent(Intent.ACTION_VIEW).apply {
                data = "smsto:".toUri()
                putExtra("sms_body", message)
            }
            context.startActivity(smsIntent)
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        androidx.compose.material3.Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(id = R.drawable.sms),
            contentDescription = "Send SMS..."
        )
    }
}