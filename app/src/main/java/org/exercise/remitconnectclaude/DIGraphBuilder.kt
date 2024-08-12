package org.exercise.remitconnectclaude

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.exercise.remitconnectclaude.data.remote.ExtraCountryDetailApiService
import org.exercise.remitconnectclaude.data.local.MobileWalletDao
import org.exercise.remitconnectclaude.data.MobileWalletRepository
import org.exercise.remitconnectclaude.data.remote.MonecoApiService
import org.exercise.remitconnectclaude.data.local.TransactionsDao
import org.exercise.remitconnectclaude.data.local.MonecoDb
import org.exercise.remitconnectclaude.data.local.RecipientDao
import org.exercise.remitconnectclaude.data.RecipientRepository
import org.exercise.remitconnectclaude.data.local.SupportedCountriesDao
import org.exercise.remitconnectclaude.data.SupportedCountriesRepository
import org.exercise.remitconnectclaude.data.TransactionsRepository
import org.exercise.remitconnectclaude.presentation.ui.utils.ResourceHelper
import org.exercise.remitconnectclaude.util.SharedPrefHelper
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
class DIGraphBuilder {
    companion object {
        const val TIMEOUT = 60L
        const val BASE_URL = "https://my-json-server.typicode.com/MonecoHQ/fake-api/"
        const val COUNTRY_FLAG_BASE_URL = "https://restcountries.com/v3.1/"
        val DB_NAME = "moneco.db"
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideGsonConverter() = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        okHttpClient: OkHttpClient,
        gson: Gson
    ) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)


    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .retryOnConnectionFailure(true)
        .build()

    @Provides
    @Singleton
    fun provideRestService(retrofitBuilder: Retrofit.Builder) = retrofitBuilder.baseUrl(BASE_URL)
        .build()
        .create(MonecoApiService::class.java)

    @Provides
    @Singleton
    fun provideExtraCountryDetailsRestService(retrofitBuilder: Retrofit.Builder) =
        retrofitBuilder.baseUrl(
            COUNTRY_FLAG_BASE_URL
        )
            .build()
            .create(ExtraCountryDetailApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            MonecoDb::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()

    @Provides
    @Singleton
    fun provideTransactionDao(database: MonecoDb) = database.transactionsDao()

    @Provides
    @Singleton
    fun provideCountriesDao(database: MonecoDb) = database.countriesDao()

    @Provides
    @Singleton
    fun provideMobileWalletsDao(database: MonecoDb) = database.mobileWalletDao()

    @Provides
    @Singleton
    fun provideRecipientsDao(database: MonecoDb) = database.recipientDao()


    @Provides
    @Singleton
    fun provideTransactionRepository(dao: TransactionsDao) = TransactionsRepository(dao)

    @Provides
    @Singleton
    fun provideRecipientCountryRepository(
        apiService: MonecoApiService,
        extraCountryDetailApiService: ExtraCountryDetailApiService,
        dao: SupportedCountriesDao
    ) = SupportedCountriesRepository(
        apiService,
        extraCountryDetailApiService, dao
    )

    @Provides
    @Singleton
    fun provideSupportedMobileWalletRepository(apiService: MonecoApiService, dao: MobileWalletDao) =
        MobileWalletRepository(apiService, dao)

    @Provides
    @Singleton
    fun provideRecipientRepository(apiService: MonecoApiService, dao: RecipientDao, helper: SharedPrefHelper) =
        RecipientRepository(apiService, dao, helper)

    @Provides
    @Singleton
    fun provideResourceManager(@ApplicationContext context: Context) = ResourceHelper(context)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideSharedPrefHelper(sharedPreferences: SharedPreferences) =
        SharedPrefHelper(sharedPreferences)
}