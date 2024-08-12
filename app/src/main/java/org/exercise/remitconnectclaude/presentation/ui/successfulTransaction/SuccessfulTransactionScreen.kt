package org.exercise.remitconnectclaude.presentation.ui.successfulTransaction

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.exercise.remitconnectclaude.R

@Composable
fun SuccessfulTransactionScreen(onCloseBtnClick: () ->Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var firstPressed by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.green))
            .padding(horizontal = 24.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(painter = painterResource(id = R.drawable.ic_moneco_transaction_success), contentDescription = null )

        Text(
            text = stringResource(id = R.string.amount_transferring),
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(top = 32.dp)
                .wrapContentSize()
                .padding(top = 12.dp, start = 32.dp, end = 32.dp)
        )

        Button(
            onClick = {
                      onCloseBtnClick.invoke()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.label_text_green),
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(vertical = 16.dp),
            modifier = Modifier.padding(top = 60.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.got_it_label),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        }
    }

    BackHandler() {
        if (firstPressed) {
            val activity = (context as? ComponentActivity)
            activity?.finish()
        } else {
            firstPressed = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
            coroutineScope.launch {
                delay(2000L)  // set delay here as wished
                firstPressed = false
            }
        }
    }
}