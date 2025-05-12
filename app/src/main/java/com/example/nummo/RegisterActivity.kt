package com.example.nummo

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@Composable
fun registerActivity(onBackToLogin: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Nummo", fontSize = 25.sp)
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("First Name") })
        OutlinedTextField(value = surname, onValueChange = { surname = it }, label = { Text("Last Name") })
        OutlinedTextField(value = username, onValueChange = { username = it }, label = { Text("Username") })
        OutlinedTextField(value = dob, onValueChange = { dob = it }, label = { Text("Date of Birth (yyyy-MM-dd)") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val birthDate = LocalDate.parse(dob, formatter)
                val age = Period.between(birthDate, LocalDate.now()).years

                if (age < 18) {
                    Toast.makeText(context, "You must be at least 18 years old to register", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                if (password.length >= 4) {
                    Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                    onBackToLogin()
                } else {
                    Toast.makeText(context, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show()
                }

            } catch (e: DateTimeParseException) {
                Toast.makeText(context, "Invalid date format. Use yyyy-MM-dd", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Register!")
        }

        TextButton(onClick = onBackToLogin) {
            Text("Already have an account? Log In")
        }
    }
}
