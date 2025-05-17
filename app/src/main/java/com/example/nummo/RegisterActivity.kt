package com.example.nummo

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.withStyle
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

@Composable
fun registerActivity(
    onBackToLogin: () -> Unit,
    viewModel: AuthViewModel = viewModel()
) {
    val context = LocalContext.current

    val name = viewModel.name
    val surname = viewModel.surname
    val username = viewModel.username
    val dob = viewModel.dob
    val email = viewModel.email // ✅ FIXED: Correct property name
    val password = viewModel.password // ✅ FIXED: Correct property name

    val nameError = viewModel.nameError
    val surnameError = viewModel.surnameError
    val usernameError = viewModel.usernameError
    val dobError = viewModel.dobError
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.nummo_logo),
            contentDescription = "Nummo Logo",
            modifier = Modifier.height(100.dp).padding(bottom = 16.dp)
        )

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = Color(0xFF006400), fontWeight = FontWeight.Bold)) { append("Num") }
                withStyle(SpanStyle(color = Color(0xFF66BB6A), fontWeight = FontWeight.Bold)) { append("mo") }
            },
            fontSize = 32.sp, modifier = Modifier.padding(bottom = 24.dp)
        )

        val fieldModifier = Modifier.fillMaxWidth()

        @Composable
        fun ErrorText(error: String) {
            if (error.isNotEmpty()) Text(error, color = Color.Red, fontSize = 12.sp)
        }

        OutlinedTextField(
            value = name.value,
            onValueChange = {
                name.value = it
                nameError.value = if (it.isBlank()) "First name is required."
                else if (!it.all(Char::isLetter)) "Only letters allowed." else ""
            },
            label = { Text("First Name") },
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(nameError.value)

        OutlinedTextField(
            value = surname.value,
            onValueChange = {
                surname.value = it
                surnameError.value = if (it.isBlank()) "Last name is required."
                else if (!it.all(Char::isLetter)) "Only letters allowed." else ""
            },
            label = { Text("Last Name") },
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(surnameError.value)

        OutlinedTextField(
            value = username.value,
            onValueChange = {
                username.value = it
                usernameError.value = if (it.length < 4) "Username must be at least 4 characters." else ""
            },
            label = { Text("Username") },
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(usernameError.value)

        OutlinedTextField(
            value = dob.value,
            onValueChange = {
                dob.value = it
                dobError.value = try {
                    val birthDate = LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    if (Period.between(birthDate, LocalDate.now()).years < 18) "Must be at least 18 years old." else ""
                } catch (e: Exception) {
                    "Format must be yyyy-MM-dd"
                }
            },
            label = { Text("Date of Birth (yyyy-MM-dd)") },
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(dobError.value)

        OutlinedTextField(
            value = email.value,
            onValueChange = {
                email.value = it
                emailError.value = if (!Patterns.EMAIL_ADDRESS.matcher(it).matches()) "Invalid email format." else ""
            },
            label = { Text("Email") },
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(emailError.value)

        OutlinedTextField(
            value = password.value,
            onValueChange = {
                password.value = it
                passwordError.value = if (it.length < 7) "Password must be at least 7 characters." else ""
            },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = fieldModifier,
            colors = greenFieldColors()
        )
        ErrorText(passwordError.value)

        PasswordStrengthMeter(password.value)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (viewModel.validateRegisterFields()) { // ✅ FIXED: Correct method call
                    Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                    onBackToLogin()
                } else {
                    Toast.makeText(context, "Please fix the errors", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text("Register!", style = MaterialTheme.typography.labelLarge.copy(color = Color.White))
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onBackToLogin) {
            Text("Already have an account? Log In", color = Color(0xFF2E7D32))
        }
    }
}

@Composable
fun greenFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = Color(0xFF006400),
    unfocusedBorderColor = Color(0xFF66BB6A),
    focusedLabelColor = Color(0xFF006400)
)

@Composable
fun PasswordStrengthMeter(password: String) {
    val strength = when {
        password.length < 7 && password.isNotEmpty() -> "Weak"
        password.any { it.isDigit() } && password.any { it.isUpperCase() } && password.length >= 10 -> "Strong"
        password.length >= 7 -> "Medium"
        else -> ""
    }

    val color = when (strength) {
        "Weak" -> Color.Red
        "Medium" -> Color(0xFFFFA000)
        "Strong" -> Color(0xFF2E7D32)
        else -> Color.Transparent
    }

    if (strength.isNotEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Strength: $strength", color = color, fontSize = 12.sp)
        }
    }
}

