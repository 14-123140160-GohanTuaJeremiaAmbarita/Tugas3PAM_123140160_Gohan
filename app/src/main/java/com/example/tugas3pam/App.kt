package com.example.tugas3pam

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Komponen reusable untuk Header Profil.
 * Menampilkan foto profil circular dengan overlay ikon edit, nama, dan bio.
 */
@Composable
fun ProfileHeader(name: String, bio: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Penggunaan BOX sebagai overlay (Requirement 2)
        Box(contentAlignment = Alignment.BottomEnd) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile Picture",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            // Overlay Ikon Edit (Requirement Box Overlay)
            Surface(
                modifier = Modifier.size(32.dp).offset(x = (-4).dp, y = (-4).dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primary,
                tonalElevation = 4.dp
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.padding(6.dp),
                    tint = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = name,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Text(
            text = bio,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp),
            lineHeight = 20.sp
        )
    }
}

/**
 * Komponen reusable untuk item informasi.
 * Menggunakan ROW (Requirement 2) untuk menyusun icon dan teks.
 */
@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.padding(8.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.outline)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
    }
}

/**
 * Komponen reusable sebagai wrapper CARD (Requirement 3) untuk section info.
 */
@Composable
fun ProfileCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) { content() }
    }
}

/**
 * Composable utama untuk aplikasi My Profile.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    // State untuk kontrol animasi detail (Requirement 5)
    var isDetailVisible by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Profile App", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfileHeader(
                name = "Gohan",
                bio = "Suka olahraga, rajin menabung, dan masih jomblo"
            )

            // Tombol Toggle Detail (Requirement 3 & 5)
            // Menghapus fitur follow sesuai permintaan user
            Button(
                onClick = { isDetailVisible = !isDetailVisible },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text(if (isDetailVisible) "Hide Profile Details" else "Show Profile Details")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Implementasi ANIMATEDVISIBILITY (Bonus Requirement 5)
            // Menggunakan kombinasi expand/shrink dan fade untuk animasi yang lebih halus
            AnimatedVisibility(
                visible = isDetailVisible,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                ProfileCard {
                    InfoItem(icon = Icons.Default.Email, label = "Email", value = "gohan.123140160@student.ac.id")
                    InfoItem(icon = Icons.Default.Phone, label = "Phone", value = "081260683740")
                    InfoItem(icon = Icons.Default.LocationOn, label = "Location", value = "Belwis")
                    InfoItem(icon = Icons.Default.Male, label = "Gender", value = "Laki-laki")
                    InfoItem(icon = Icons.Default.SportsEsports, label = "Hobby", value = "Game")
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// --- Previews ---

@Preview(showBackground = true)
@Composable
fun FullAppPreview() {
    MaterialTheme {
        App()
    }
}
