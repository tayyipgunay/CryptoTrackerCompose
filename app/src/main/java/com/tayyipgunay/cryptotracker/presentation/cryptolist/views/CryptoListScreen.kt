package com.tayyipgunay.cryptotracker.presentation.cryptolist.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.ui.Alignment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Horizontal
import androidx.compose.ui.Alignment.Vertical


import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.tayyipgunay.cryptotracker.R
import com.tayyipgunay.cryptotracker.domain.model.Crypto
import com.tayyipgunay.cryptotracker.presentation.cryptolist.CryptoEvent
import com.tayyipgunay.cryptotracker.presentation.cryptolist.CryptoViewModel

@Composable
fun CryptoListScreen(navController: NavController) {


    // Scaffold(
    //    topBar = { CryptoTopBar() }, // { CryptoTopBar()} ,
    //    ) { paddingValues ->
    /* Box(modifier = Modifier.fillMaxWidth()) {
        CryptoTopBar()
    }*/

   val  cryptoViewModel: CryptoViewModel= hiltViewModel()
    val state by cryptoViewModel.state.collectAsState()

  /*  if (state.isLoading) {
        CircularProgressIndicator()
    }

    // **3️⃣ Hata Mesajı**
    if (state.error.isNotEmpty()) {
        Text(
            text = state.error,
            color = Color.Red,
            fontSize = 18.sp
        )
    }*/






    CryptoTopBar()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 160.dp)
            .background(Color(0xFFEEF1FF)) // **Önceki mavi tonlarını kullandım**
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        if (state.error.isNotEmpty()) {
            Text(
                text = state.error,
                color = Color.Red,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
            )

        }
        //

        LazyColumn(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            items(state.crypto) { crypto ->
                CryptoItem(crypto, onItemClick = {
                    navController.navigate("crypto_details_screen/${crypto.id}")
                })
                }
            }
        }
    }





@Composable
fun CryptoItem(crypto: Crypto, onItemClick: (Crypto) -> Unit = {}){
               Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp).clickable {
                onItemClick(crypto)

            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Beyaz arka plan
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coin Logosu
            AsyncImage(
                model = crypto.image,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))

            // Kripto Adı ve Sembolü
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = crypto.symbol,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0052D4) // Koyu mavi (finansal güven verir)
                )
                Text(
                    text = crypto.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2C3E50) // Koyu gri tonu (okunabilirliği artırır)
                )
            }

            // Fiyat ve Değişim Oranı
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = crypto.currentPrice.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF37474F),
                    // Koyu gri tonu (şık ve modern)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically // Metin ve ikonu ortalamak için
                ) {
                    val changeIconColor =
                        if (crypto.priceChangePercentage24h < 0) Color(0xFFE74C3C) else Color(
                            0xFF27AE60
                        )
                    val changeIcon =
                        if (crypto.priceChangePercentage24h < 0) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp

                    // Önce OK ikonu
                    Icon(
                        imageVector = changeIcon,
                        contentDescription = null,
                        tint = changeIconColor,
                        modifier = Modifier.size(20.dp) // Ok ikonunu küçük tutuyoruz
                    )

                    Text(
                        text = crypto.priceChangePercentage24h.toString(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (crypto.priceChangePercentage24h < 0) Color(0xFFE74C3C)
                        else Color(0xFF27AE60) // Soft kırmızı & soft yeşil
                    )
                }
            }
        }
    }
}

@Composable
fun CryptoTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF6A5ACD)) // Mavi-Mor degrade
                )
            )
            .padding(top = 48.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Column {
            Text(
                text = "Crypto",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "Ranking List",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.9f)
            )

            FilterButtons() // Butonları ekleyelim

        }
    }
}
@Composable
fun CryptoTopBarWithoutBox() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF4A90E2), Color(0xFF6A5ACD)) // 🔹 Mavi-Mor degrade
                )
            )
            .padding(top = 48.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = "Crypto",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Ranking List",
            fontSize = 16.sp,
            color = Color.White.copy(alpha = 0.9f)
        )
        FilterButtons() // Butonları ekleyelim
    }
}

@Composable
fun FilterButtons(cryptoViewModel: CryptoViewModel = hiltViewModel()) {
    val filters = listOf(
        "Piyasa Değeri ↓",
        "Piyasa Değeri ↑",
        "Hacim ↓",
        "Hacim ↑",
        "İsim A→Z",
        "İsim Z→A"
    )
    val reversedFilters = mapOf(
        "Piyasa Değeri ↓" to "market_cap_desc",
        "Piyasa Değeri ↑" to "market_cap_asc",
        "Hacim ↓" to "volume_desc",
        "Hacim ↑" to "volume_asc",
        "İsim A→Z" to "id_asc",
        "İsim Z→A" to "id_desc"
    )

    var selectedFilter by remember { mutableStateOf("market_cap_desc") } // Varsayılan seçili filtre
    cryptoViewModel.onEvent(CryptoEvent.GetCrypto("market_cap_desc"))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            OutlinedButton(
                onClick = { selectedFilter =reversedFilters[filter]!!
                          cryptoViewModel.onEvent(CryptoEvent.GetCrypto(reversedFilters[filter] ?: filters[0]))
                          },
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (selectedFilter==reversedFilters[filter]) Color.White else Color.Transparent,
                    contentColor = if (selectedFilter==reversedFilters[filter]) Color.Black else Color.White
                ),
                modifier = Modifier.height(36.dp)
            ) {
                Text(
                    text = filter,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}









