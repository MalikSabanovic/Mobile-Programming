package com.example.nummo

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RequestViewModel : ViewModel() {

    private val _transactionId = MutableStateFlow("")
    val transactionId: StateFlow<String> = _transactionId

    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount

    private val _notes = MutableStateFlow("")
    val notes: StateFlow<String> = _notes

    private val _proofUri = MutableStateFlow<Uri?>(null)
    val proofUri: StateFlow<Uri?> = _proofUri

    private val _transactionIdError = MutableStateFlow(false)
    val transactionIdError: StateFlow<Boolean> = _transactionIdError

    private val _amountError = MutableStateFlow(false)
    val amountError: StateFlow<Boolean> = _amountError

    fun setTransactionId(value: String) {
        _transactionId.value = value
        _transactionIdError.value = false
    }

    fun setAmount(value: String) {
        _amount.value = value
        _amountError.value = false
    }

    fun setNotes(value: String) {
        _notes.value = value
    }

    fun setProofUri(uri: Uri?) {
        _proofUri.value = uri
    }

    fun validate(): Boolean {
        val validId = _transactionId.value.length == 8 && _transactionId.value.all { it.isDigit() }
        val validAmount = _amount.value.matches(Regex("""^(\d+)?(.\d+)?$"""))

        _transactionIdError.value = !validId
        _amountError.value = !validAmount

        return validId && validAmount
    }
}