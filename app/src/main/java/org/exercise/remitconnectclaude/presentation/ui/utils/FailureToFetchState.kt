package org.exercise.remitconnectclaude.presentation.ui.utils

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.exercise.remitconnectclaude.R
import java.io.IOException

@Composable
fun <T> BoxScope.FailureState(@StringRes customErrorMsg: Int? = null,
    throwable: Throwable?, data: List<T>, refreshData: () -> Unit){
    val contextForToast = LocalContext.current.applicationContext
    throwable?.let { error ->
        val errorMsg = if (error is IOException) {
            stringResource(id = customErrorMsg ?: R.string.no_internet_connection)
        } else {
            stringResource(id = R.string.something_went_wrong)
        }

        if (data.isEmpty()){
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        } else {
            Toast.makeText(contextForToast, errorMsg, Toast.LENGTH_SHORT).show()
        }

        InternetConnectivityManger {
            refreshData()
        }
    }
}