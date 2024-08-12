package org.exercise.remitconnectclaude.data.remote

import org.exercise.remitconnectclaude.data.SupportedMobileWallet
import org.exercise.remitconnectclaude.data.SupportedRecipientCountry
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MonecoApiService {
    @GET("wallets")
    suspend fun getMobileWallets(): List<SupportedMobileWallet>

    @GET("countries")
    suspend fun getSupportedRecipientCountries(): List<SupportedRecipientCountry>

    @GET("recipients")
    suspend fun getRecipientList(): List<RecipientListResponseItem>
}

interface ExtraCountryDetailApiService {
    @GET("name/{country}")
    suspend fun getFlagForCountries(@Path("country") countryName: String, @Query("fields") extraFields: String = "flags,idd"):
            List<CountryExtraResponseItem>

}