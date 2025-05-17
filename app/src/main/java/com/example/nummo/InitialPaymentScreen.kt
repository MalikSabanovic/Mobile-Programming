package com.example.nummo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun InitialPaymentScreen(
    viewModel: PaymentViewModel = viewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    val transactionId by viewModel.transactionId.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val transactionIdError by viewModel.transactionIdError.collectAsState()
    val amountError by viewModel.amountError.collectAsState()

    val greenColor = Color(0xFF4CAF50)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = greenColor,
        unfocusedBorderColor = Color.Gray
    )

    val transactions = listOf(
        Triple("Sent to John", "-1,500 BAM", "2025-05-12 09:30"),
        Triple("Received from Sarah", "+2,000 BAM", "2025-05-11 18:45"),
        Triple("Sent to Mark", "-1,000 BAM", "2025-05-08 20:15"),
        Triple("Received from Linda", "+2,500 BAM", "2025-05-08 11:10")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(greenColor),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.logo1),
                    contentDescription = "Nummo Logo",
                    modifier = Modifier.size(58.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("ummo", fontSize = 48.sp, color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Make Payment", fontSize = 24.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Fill in recipient's credentials", fontSize = 18.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = transactionId,
                onValueChange = { viewModel.setTransactionId(it) },
                isError = transactionIdError,
                label = { Text("Recipient's Transaction ID") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                colors = textFieldColors
            )

            if (transactionIdError) {
                Text(
                    "Transaction ID must be exactly 8 digits.",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { viewModel.setAmount(it) },
                isError = amountError,
                label = { Text("Amount") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                colors = textFieldColors
            )

            if (amountError) {
                Text(
                    "Enter a valid amount (e.g. 10.99).",
                    color = Color.Red,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (viewModel.validate()) {
                        navController.navigate("confirmPayment")
                    } else {
                        Toast.makeText(context, "Please fix the errors", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text("Continue", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Transaction History", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    items(transactions) { (desc, amt, datetime) ->
                        Column(modifier = Modifier.padding(vertical = 8.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = desc, fontSize = 16.sp)
                                    Text(text = datetime, fontSize = 12.sp, color = Color.Gray)
                                }
                                Text(
                                    text = amt,
                                    color = if (amt.startsWith("+")) Color(0xFF4CAF50) else Color.Red,
                                    fontSize = 16.sp
                                )
                            }
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}
