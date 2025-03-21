package com.tayyipgunay.cryptotracker.presentation.cryptodetails.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.tayyipgunay.cryptotracker.R
import com.tayyipgunay.cryptotracker.domain.model.CryptoDetail
import com.tayyipgunay.cryptotracker.presentation.cryptodetails.CryptoDetailViewModel
import com.tayyipgunay.cryptotracker.presentation.cryptodetails.CryptoDetailsEvent

@Composable
fun CryptoDetailScreen(id: String) {


    val cryptoDetailViewModel: CryptoDetailViewModel = hiltViewModel()
    LaunchedEffect(id) {
        //cryptoDetailViewModel.getCryptoDetails(id)
        cryptoDetailViewModel.onEvent(CryptoDetailsEvent.GetCryptoDetail(id))
    }

    val state by cryptoDetailViewModel.state.collectAsState()




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A1F44)) // Premium koyu mavi arka plan
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        when {
            state.error.isNotEmpty() -> {
                Text(
                    text = state.error,
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            state.isLoading -> {
                println("yükleniyorrr")
                CircularProgressIndicator(modifier=Modifier.padding(horizontal =64.dp , vertical = 16.dp))
            }


            state.cryptoDetail.isNotEmpty() -> { // Eğer liste boş değilse
                val cryptoDetail = state.cryptoDetail.firstOrNull()
                cryptoDetail?.let { CryptoDetail ->


                    IconButton(
                        onClick = { /* Back action */ },
                        modifier = Modifier.align(Alignment.Start)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White,
                            modifier = Modifier.size(56.dp) // Beyaz ikon rengi
                                .padding(top = 7.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    AsyncImage(
                        model = CryptoDetail.image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)

                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = CryptoDetail.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(text = CryptoDetail.symbol, fontSize = 16.sp, color = Color(0xFFADB5BD))
                    Spacer(modifier = Modifier.height(16.dp))

                    // Scrollable Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        // LazyRow - Zaman Aralığı Seçici

                        val timeFrames = listOf("1H", "24H", "7D", "30D", "1Y", "All")
                        var selectedTimeFrame by remember { mutableStateOf(timeFrames[0]) }


                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(timeFrames) { timeFrame ->
                                Button(
                                    onClick = { selectedTimeFrame = timeFrame },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selectedTimeFrame == timeFrame) Color(
                                            0xFF1E3A8A
                                        ) else Color.Transparent,
                                        contentColor = if (selectedTimeFrame == timeFrame) Color.White else Color(
                                            0xFFADB5BD
                                        )
                                    ),
                                    shape = RoundedCornerShape(20.dp),
                                    modifier = Modifier.padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = timeFrame,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }

                        // Bilgiler Kartı
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF12264C)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(
                                        "Current Price",
                                        fontSize = 14.sp,
                                        color = Color(0xFFADB5BD)
                                    )
                                    Text(
                                        CryptoDetail.currentPrice.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Lowest 24H", fontSize = 14.sp, color = Color(0xFFADB5BD))
                                    Text(
                                        CryptoDetail.low24h.toString(),
                                        fontSize = 16.sp,
                                        color = Color.Red
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Market Cap", fontSize = 14.sp, color = Color(0xFFADB5BD))
                                    Text(
                                        CryptoDetail.marketCap.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                                Column {
                                    Text("Change", fontSize = 14.sp, color = Color(0xFFADB5BD))
                                    Text(
                                        CryptoDetail.priceChangePercentage24h.toString(),
                                        fontSize = 16.sp,
                                        color=if (CryptoDetail.priceChangePercentage24h > 0) Color.Green else Color.Red
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text("Highest 24H", fontSize = 14.sp, color = Color(0xFFADB5BD))
                                    Text(
                                        CryptoDetail.high24h.toString(),
                                        fontSize = 16.sp,
                                        color = Color.Green
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "24 Hours Volume",
                                        fontSize = 14.sp,
                                        color = Color(0xFFADB5BD)
                                    )
                                    Text(
                                        CryptoDetail.totalVolume24h.toString(),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        // Grafik Yer Tutucu
                     /*   Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(Color(0xFF1E3A8A), shape = RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Loading Graph...", color = Color.White)
                        }*/
                    }
                }
            }

        }
    }
}




