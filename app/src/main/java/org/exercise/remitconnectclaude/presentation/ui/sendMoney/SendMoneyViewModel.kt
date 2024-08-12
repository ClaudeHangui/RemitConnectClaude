package org.exercise.remitconnectclaude.presentation.ui.sendMoney

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.exercise.remitconnectclaude.presentation.ui.utils.ResourceHelper
import javax.inject.Inject

@HiltViewModel
class SendMoneyViewModel @Inject constructor(private val resourceHelper: ResourceHelper) :
    ViewModel() {

    fun checkIfModeIsAvailable(id: Int): Boolean {
        return moneyTransferMethods.firstOrNull { it.typeId == 3 }?.typeId == id
    }


    val moneyTransferMethods = resourceHelper.buildSendMoneyTypes()

    val methodsToSentToAfrica = resourceHelper.methodsToSendToAfrica()

}