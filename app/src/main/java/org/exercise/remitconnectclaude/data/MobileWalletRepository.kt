package org.exercise.remitconnectclaude.data

import org.exercise.remitconnectclaude.data.local.MobileWalletDao
import org.exercise.remitconnectclaude.data.remote.MonecoApiService
import javax.inject.Inject

class MobileWalletRepository @Inject constructor(
    private val monecoApiService: MonecoApiService,
    private val mobileWalletDao: MobileWalletDao
) {
    suspend fun getSupportedMobileWallets() =
        try {
            val response = monecoApiService.getMobileWallets()
            if (response.isNotEmpty()) {
                mobileWalletDao.insertAllRecipientSupportedWallets(response)
            }
            Result.Success(mobileWalletDao.getAllWallets())
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e, mobileWalletDao.getAllWallets())
        }

}