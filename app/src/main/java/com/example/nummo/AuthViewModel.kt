package com.example.nummo

import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class AuthViewModel : ViewModel() {
    // Login fields
    var loginEmail = mutableStateOf("")
    var loginPassword = mutableStateOf("")

    // Register fields
    var name = mutableStateOf("")
    var surname = mutableStateOf("")
    var username = mutableStateOf("")
    var dob = mutableStateOf("")
    var email = mutableStateOf("")
    var password = mutableStateOf("")

    // Error messages
    var nameError = mutableStateOf("")
    var surnameError = mutableStateOf("")
    var usernameError = mutableStateOf("")
    var dobError = mutableStateOf("")
    var emailError = mutableStateOf("")
    var passwordError = mutableStateOf("")

    fun validateRegisterFields(): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        nameError.value = if (name.value.isBlank()) "First name is required." else if (!name.value.all(Char::isLetter)) "Only letters allowed." else ""
        surnameError.value = if (surname.value.isBlank()) "Last name is required." else if (!surname.value.all(Char::isLetter)) "Only letters allowed." else ""
        usernameError.value = if (username.value.length < 4) "Username must be at least 4 characters." else ""
        dobError.value = try {
            val birthDate = LocalDate.parse(dob.value, formatter)
            if (Period.between(birthDate, LocalDate.now()).years < 18) "Must be at least 18 years old." else ""
        } catch (e: DateTimeParseException) {
            "Format must be yyyy-MM-dd"
        }
        emailError.value = if (!Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) "Invalid email format." else ""
        passwordError.value = if (password.value.length < 7) "Password must be at least 7 characters." else ""

        return listOf(nameError, surnameError, usernameError, dobError, emailError, passwordError).all { it.value.isEmpty() }
    }

    fun getPasswordStrength(): String = when {
        password.value.length < 7 && password.value.isNotEmpty() -> "Weak"
        password.value.any { it.isDigit() } && password.value.any { it.isUpperCase() } && password.value.length >= 10 -> "Strong"
        password.value.length >= 7 -> "Medium"
        else -> ""
    }
}