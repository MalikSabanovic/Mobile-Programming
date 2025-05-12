package com.example.nummo

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.example.nummo.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val userBalance = "2,090,960 BAM"

    val transactions = listOf(
        Triple("Sent to John", "-1,500 BAM", "Today"),
        Triple("Received from Sarah", "+2,000 BAM", "Yesterday"),
        Triple("Sent to Anna", "-500 BAM", "2 days ago")
    )

    val paymentRequests = listOf(
        Triple("Alice", "2,000 BAM", "Request for dinner"),
        Triple("Bob", "1,200 BAM", "Group trip share")
    )

    Surface(color = Color(0xFFF0F0F0)) { // Light gray background
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.cityscape3),
                    contentDescription = "Cityscape Header",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }

            item {
                Text("Balance", style = MaterialTheme.typography.titleMedium)
                Text(userBalance, style = MaterialTheme.typography.headlineMedium)

                Spacer(modifier = Modifier.height(24.dp))
                Text("Transaction History", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(transactions) { (desc, amount, date) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(desc)
                            Text(date, style = MaterialTheme.typography.bodySmall)
                        }
                        Text(amount, color = if (amount.startsWith("+")) Color(0xFF4CAF50) else Color.Red)
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text("Payment Requests", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(paymentRequests) { (name, amount, reason) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("$name requested $amount")
                            Text(reason, style = MaterialTheme.typography.bodySmall)
                        }
                        Button(
                            onClick = { /* TODO */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                        ) {
                            Text("Pay")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NummoBottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Send, contentDescription = "Send") },
            label = { Text("Send") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AttachMoney, contentDescription = "Request") }, // Replace icon if needed
            label = { Text("Request") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
