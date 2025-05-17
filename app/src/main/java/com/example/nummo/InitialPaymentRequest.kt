package com.example.nummo

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nummo.R
import com.example.nummo.showPaymentRequestNotification
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nummo.showPaymentRequestNotification
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.collectAsState
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import androidx.compose.runtime.collectAsState

@Composable
fun InitialRequestScreen(
    sharedProofUri: Uri? = null,
    viewModel: RequestViewModel = viewModel()
) {
    val context = LocalContext.current

    val transactionId by viewModel.transactionId.collectAsState()
    val amount by viewModel.amount.collectAsState()
    val notes by viewModel.notes.collectAsState()
    val proofUri by viewModel.proofUri.collectAsState()

    val transactionIdError by viewModel.transactionIdError.collectAsState()
    val amountError by viewModel.amountError.collectAsState()

    val greenColor = Color(0xFF4CAF50)
    val darkRed = Color(0xFFD32F2F)

    LaunchedEffect(sharedProofUri) {
        if (sharedProofUri != null && proofUri == null) {
            viewModel.setProofUri(sharedProofUri)
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> if (uri != null) viewModel.setProofUri(uri) }

    val requestHistory = remember {
        listOf(
            Triple("Request to John", "150 BAM", "Pending"),
            Triple("Request to Sarah", "200 BAM", "Accepted"),
            Triple("Request to Anna", "100 BAM", "Declined"),
            Triple("Request to Josh", "155 BAM", "Pending")
        )
    }

    var receivedRequests by remember {
        mutableStateOf(
            mutableListOf(
                mutableStateOf(Triple("From Ben", "90 BAM", "Pending")),
                mutableStateOf(Triple("From Mia", "110 BAM", "Accepted")),
                mutableStateOf(Triple("From Kevin", "70 BAM", "Pending"))
            )
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        item {
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

            Image(
                painter = painterResource(id = R.drawable.payment_request1),
                contentDescription = "Nummo Banner",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(365.dp)
            )

            Column(modifier = Modifier.padding(24.dp)) {
                Text("Make Request", fontSize = 24.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = transactionId,
                    onValueChange = {
                        if (it.length <= 8 && it.all { c -> c.isDigit() }) {
                            viewModel.setTransactionId(it)
                        }
                    },
                    isError = transactionIdError,
                    label = { Text("Recipient's Transaction ID") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                if (transactionIdError) {
                    Text("Transaction ID must be exactly 8 digits.", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = amount,
                    onValueChange = { viewModel.setAmount(it) },
                    isError = amountError,
                    label = { Text("Amount") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                if (amountError) {
                    Text("Enter a valid amount (e.g. 10.99).", color = Color.Red, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = notes,
                    onValueChange = { viewModel.setNotes(it) },
                    label = { Text("Notes (Reason for request)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(8.dp)),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = greenColor,
                        unfocusedBorderColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { imagePickerLauncher.launch("image/*") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Select Proof from Gallery", color = Color.Black)
                }

                Spacer(modifier = Modifier.height(16.dp))

                proofUri?.let { uri ->
                    Text("Attached Proof", fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    if (uri.toString().endsWith(".pdf")) {
                        Text("PDF attached: ${uri.lastPathSegment}", color = Color.Gray)
                    } else {
                        Image(
                            painter = rememberAsyncImagePainter(uri),
                            contentDescription = "Proof Image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Button(
                    onClick = {
                        if (viewModel.validate()) {
                            Toast.makeText(context, "Request sent", Toast.LENGTH_SHORT).show()
                            showPaymentRequestNotification(
                                context = context,
                                amount = amount,
                                transactionId = transactionId
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = greenColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text("Request", fontSize = 18.sp, color = Color.White)
                }

                Spacer(modifier = Modifier.height(32.dp))
                Text("Your Request History", fontSize = 20.sp, color = Color.Black)
            }
        }


        item {
            LazyColumn(
                modifier = Modifier
                    .height(250.dp)
                    .padding(horizontal = 24.dp)
            ) {
                items(requestHistory) { (desc, amt, status) ->
                    val bgColor = when (status) {
                        "Accepted" -> Color(0xFFDFF0D8)
                        "Declined" -> Color(0xFFF8D7DA)
                        "Pending" -> Color(0xFFFFF3CD)
                        else -> Color.White
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(desc, fontSize = 16.sp)
                                Text("Status: $status", fontSize = 12.sp, color = Color.Gray)
                            }
                            Text(amt, fontSize = 16.sp)
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                "Requests You Received",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            LazyColumn(
                modifier = Modifier
                    .height(300.dp)
                    .padding(horizontal = 24.dp)
            ) {
                items(receivedRequests) { requestState ->
                    val (from, amt, status) = requestState.value
                    val bgColor = when (status) {
                        "Accepted" -> Color(0xFFDFF0D8)
                        "Declined" -> Color(0xFFF8D7DA)
                        else -> Color.White
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = bgColor)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(from, fontSize = 16.sp)
                            Text("Requested: $amt", fontSize = 14.sp, color = Color.Gray)
                            Text("Status: $status", fontSize = 12.sp, color = Color.Gray)

                            if (status == "Pending") {
                                Spacer(modifier = Modifier.height(8.dp))
                                Row {
                                    Button(
                                        onClick = {
                                            requestState.value = requestState.value.copy(third = "Accepted")
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = greenColor)
                                    ) {
                                        Text("Accept")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            requestState.value = requestState.value.copy(third = "Declined")
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = darkRed)
                                    ) {
                                        Text("Decline")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
