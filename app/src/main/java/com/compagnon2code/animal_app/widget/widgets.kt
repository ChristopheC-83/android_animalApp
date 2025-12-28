package com.compagnon2code.animal_app.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = { Text(text = title) },
        text = { Text(text = text) },
        // clic hors de la box AlertDialog
        onDismissRequest = { onDismiss() },
        //clic sur refus
        dismissButton = {
            TextButton(
                onClick = { onDismiss() }
            ) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() }
            ) {
                Text(text = "Confirm")
            }
        },
    )
}

