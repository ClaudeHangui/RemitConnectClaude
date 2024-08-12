package org.exercise.remitconnectclaude.data

import org.exercise.remitconnectclaude.data.local.SupportedCountriesDao
import org.exercise.remitconnectclaude.data.remote.ExtraCountryDetailApiService
import org.exercise.remitconnectclaude.data.remote.MonecoApiService
import javax.inject.Inject

class SupportedCountriesRepository @Inject constructor(
    private val apiService: MonecoApiService,
    private val countryFlagsApiService: ExtraCountryDetailApiService,
    private val supportedCountriesDao: SupportedCountriesDao
) {
    suspend fun getSupportedCountriesForRecipients() = try {
        val supportedCountriesResponse = apiService.getSupportedRecipientCountries()
        val country = supportedCountriesResponse.map { countryItem ->
            val countryExtraResponse =
                countryFlagsApiService.getFlagForCountries(countryItem.name)
            countryItem.flag_url = countryExtraResponse.firstOrNull()?.flags?.svg ?: ""
            countryItem.international_dialing_code =
                countryExtraResponse.firstOrNull()?.idd?.let {
                    (it.root + it.suffixes.firstOrNull())
                } ?: ""
            countryItem
        }
        if (country.isNotEmpty()) {
            supportedCountriesDao.insertAllRecipientCountries(country)
        }

        Result.Success(supportedCountriesDao.getAllSupportedCountries())
    } catch (e: Exception) {
        e.printStackTrace()
        Result.Error(e, supportedCountriesDao.getAllSupportedCountries())
    }
}