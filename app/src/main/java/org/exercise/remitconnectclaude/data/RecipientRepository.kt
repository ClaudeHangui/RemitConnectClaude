package org.exercise.remitconnectclaude.data

import android.util.Log
import org.exercise.remitconnectclaude.data.local.RecipientDao
import org.exercise.remitconnectclaude.data.remote.MonecoApiService
import org.exercise.remitconnectclaude.util.SharedPrefHelper
import org.exercise.remitconnectclaude.util.SharedPrefHelper.Companion.PREF_RECIPIENT_ID
import javax.inject.Inject

class RecipientRepository @Inject constructor(
    private val apiService: MonecoApiService,
    private val recipientDao: RecipientDao,
    private val sharedPrefHelper: SharedPrefHelper
    ) {

    suspend fun getAllPreviousRecipients() = try {
        val apiResponse = apiService.getRecipientList()
        if (apiResponse.isNotEmpty()){
            val mappedData = apiResponse.map {
                Recipient(
                    id = it.id,
                    firstName = it.name.substringBefore(" "),
                    lastName = it.name.substringAfterLast(" "),
                    mobileWallet = it.mobile_wallet,
                    phoneNumber = "",
                    dialingCode = "",
                    country = if (it.country == "Maroc") "Morocco" else it.country
                )
            }
            val idOnLastItem = mappedData.last().id
            val prevId = sharedPrefHelper.getData(PREF_RECIPIENT_ID, "")
            Log.e("RecipientRepository", "previous Id: $prevId")
            if (prevId.isEmpty()){
                sharedPrefHelper.saveData(PREF_RECIPIENT_ID, idOnLastItem)
            }

            recipientDao.insertPreviousRecipients(mappedData)
        }
        Result.Success(recipientDao.getPreviousRecipients())
    } catch (e: Exception){
        e.printStackTrace()
        Result.Error(e, recipientDao.getPreviousRecipients())
    }


    suspend fun insertRecipient(recipient: Recipient) = recipientDao.createRecipient(recipient)
}