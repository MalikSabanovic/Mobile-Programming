package com.example.nummo

import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProfileViewModel : ViewModel() {
    private val _firstName = MutableStateFlow("Michael")
    val firstName: StateFlow<String> = _firstName

    private val _lastName = MutableStateFlow("Weir")
    val lastName: StateFlow<String> = _lastName

    private val _username = MutableStateFlow("Michaelnaniew")
    val username: StateFlow<String> = _username

    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    fun setFirstName(name: String) { _firstName.value = name }
    fun setLastName(name: String) { _lastName.value = name }
    fun setUsername(name: String) { _username.value = name }
    fun setImageUri(uri: Uri?) { _imageUri.value = uri }
}