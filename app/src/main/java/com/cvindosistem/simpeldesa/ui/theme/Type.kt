package com.cvindosistem.simpeldesa.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cvindosistem.simpeldesa.R

val WorkSans = FontFamily(
    Font(R.font.worksans_regular, FontWeight.Normal),
    Font(R.font.worksans_medium, FontWeight.Medium),
    Font(R.font.worksans_semibold, FontWeight.SemiBold),
    Font(R.font.worksans_bold, FontWeight.Bold)
)

val Typography = Typography(
    // Use Work Sans for all text styles
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    // Add other text styles as needed
)