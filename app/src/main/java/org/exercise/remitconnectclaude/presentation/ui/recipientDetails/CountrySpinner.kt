package org.exercise.remitconnectclaude.presentation.ui.recipientDetails

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import org.exercise.remitconnectclaude.R
import org.exercise.remitconnectclaude.presentation.ui.CountriesViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.FailureState
import org.exercise.remitconnectclaude.presentation.ui.utils.ShowLoader

@Composable
fun CountrySpinner(
    modifier: Modifier = Modifier,
    countryName: String = "",
    onCountryChange: (String, String) -> Unit,
    onNoCountryAvailable: (Boolean) -> Unit,
    countriesViewModel: CountriesViewModel = hiltViewModel()
) {

    var expanded by remember { mutableStateOf(false) }
    val state = countriesViewModel.countriesFlow.collectAsStateWithLifecycle()

    state.value.apply {
        if (loading){
           ShowLoader()
        }

        AnimatedVisibility(visible = throwable != null && supportedCountries.isEmpty()) {
            onNoCountryAvailable.invoke(false)
            Box {
                FailureState(
                    customErrorMsg = R.string.turn_online_to_get_countries,
                    throwable = throwable, data = supportedCountries){
                    countriesViewModel.getSupportedCountries()
                }
            }
        }

        AnimatedVisibility(visible = supportedCountries.isNotEmpty()) {
            onNoCountryAvailable.invoke(true)
            val previousSelectedCountry = if (countryName.isEmpty()) null else
                supportedCountries.find { it.name == countryName }

            val initialCountry = supportedCountries.firstOrNull()
            var selectedCountry by rememberSaveable(state.value) {
                mutableStateOf(previousSelectedCountry ?: initialCountry)
            }

            Column {
                Text(
                    text = stringResource(id = R.string.country_label),
                    color = colorResource(id = R.color.dark_blue),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp, bottom = 12.dp)
                )

                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(8.dp),
                            color = colorResource(id = R.color.border_gray)
                        )
                        .background(
                            Color.White,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .wrapContentSize()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clickable {
                                expanded = !expanded
                            }
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        selectedCountry?.let { country ->
                            onCountryChange.invoke(country.international_dialing_code, country.name)
                            Row {
                                Image(
                                    painter = rememberAsyncImagePainter(
                                        model = ImageRequest.Builder(context = LocalContext.current)
                                            .data(country.flag_url)
                                            .decoderFactory(SvgDecoder.Factory())
                                            .error(R.drawable.ic_moneco_image_placeholder)
                                            .placeholder(R.drawable.ic_moneco_image_placeholder)
                                            .build()
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = country.name ?: "Unavailable",
                                    color = colorResource(id = R.color.dark_blue),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                            Row {
                                Text(
                                    text = country.international_dialing_code ?: "Unavailable",
                                    color = colorResource(id = R.color.small_label_light_gray),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else
                                        Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    tint = colorResource(id = R.color.dark_blue)
                                )
                            }
                        }
                    }

                    MaterialTheme(
                        colorScheme = MaterialTheme.colorScheme.copy(
                            surface = Color.White,
                            surfaceTint = Color.White,
                            background = Color.White,
                            onBackground = Color.White,
                            scrim = Color.White
                        ),
                    ) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .padding(horizontal = 24.dp)
                                .fillMaxWidth()
                        ) {
                            supportedCountries.forEach { country ->

                                androidx.compose.material.DropdownMenuItem(onClick = {
                                    selectedCountry = country
                                    expanded = false
                                }) {
                                    Row {
                                        Image(
                                            painter = rememberAsyncImagePainter(
                                                model = ImageRequest.Builder(context = LocalContext.current)
                                                    .data(country.flag_url)
                                                    .decoderFactory(SvgDecoder.Factory())
                                                    .error(R.drawable.ic_moneco_image_placeholder)
                                                    .placeholder(R.drawable.ic_moneco_image_placeholder)
                                                    .build()
                                            ),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))

                                        Text(
                                            text = country.name,
                                            color = colorResource(id = R.color.dark_blue),
                                            style = MaterialTheme.typography.titleMedium
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        Text(
                                            text = country.international_dialing_code,
                                            color = colorResource(id = R.color.small_label_light_gray),
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}