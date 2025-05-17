package com.example.nummo

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel : ViewModel() {
    private val _transactionId = MutableStateFlow("")
    val transactionId: StateFlow<String> = _transactionId

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _transactionIdError = MutableStateFlow(false)
    val transactionIdError: StateFlow<Boolean> = _transactionIdError

    private val _amountError = MutableStateFlow(false)
    val amountError: StateFlow<Boolean> = _amountError

    fun setTransactionId(id: String) {
        if (id.length <= 8 && id.all { it.isDigit() }) {
            _transactionId.value = id
            _transactionIdError.value = false
        }
    }

    fun setAmount(input: String) {
        if (input.isEmpty() || input.matches(Regex("""^(\d+)?(.\d*)?$"""))) {
            _amount.value = input
            _amountError.value = false
        }
    }

    fun validate(): Boolean {
        val validId = _transactionId.value.length == 8 && _transactionId.value.all { it.isDigit() }
        val validAmount = _amount.value.matches(Regex("""^(\d+)?(.\d+)?$"""))

        _transactionIdError.value = !validId
        _amountError.value = !validAmount

        return validId && validAmount
    }
}