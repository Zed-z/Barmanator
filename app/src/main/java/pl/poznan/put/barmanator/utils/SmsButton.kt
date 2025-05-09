package pl.poznan.put.barmanator.utils

import android.content.Intent
import android.net.Uri
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun SmsButton(
    message: String,
    buttonText: String = "Send SMS"
) {
    val context = LocalContext.current

    Button(onClick = {
        val smsIntent = Intent(Intent.ACTION_VIEW).apply {
            data = "smsto:".toUri()
            putExtra("sms_body", message)
        }
        context.startActivity(smsIntent)
    }) {
        Text(buttonText)
    }
}