package com.example.nummo

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nummo.R
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Preview
@Composable
fun loginActivity(){
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ){
        Text(text="Nummo")
        Text(text="Welcome back user")
        Spacer(modifier = Modifier.height(20.dp))


        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value=email, onValueChange = {email=it},label={ Text(text = "Enter email") })
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(value=password, onValueChange = {password=it},label={ Text(text = "Enter password") }, visualTransformation = PasswordVisualTransformation())
        Spacer(modifier = Modifier.height(15.dp))
        Button(onClick = {
            Log.i("Email", email)
        }) { Text(text = "Log in") }
        Spacer(modifier = Modifier.height(15.dp))
        TextButton(onClick = {}) { Text(text="Forgot password?")}
    }
}