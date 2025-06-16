package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


@Composable
fun BodyMediumText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            color = color,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp
        ),
        fontWeight = fontWeight
    )
}

@Composable
fun LargeText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun TitleLargeText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun TitleMediumText(
    text: String,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = fontWeight,
            color = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun BodySmallText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            color = color,
            textAlign = textAlign,
            lineHeight = 20.sp
        )
    )
}

@Composable
fun TitleSmallText(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = color,
            textAlign = TextAlign.Start,
            lineHeight = 20.sp
        )
    )
}

@Composable
fun PlaceholderTitleSmallText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall.copy(
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
            textAlign = TextAlign.Start,
            lineHeight = 20.sp
        )
    )
}

@Composable
fun CardTitleText(
    text: String
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BodyLargeText(
    text: String,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = fontWeight
    )
}

@Composable
fun ClickableText(
    onClick: () -> Unit,
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Medium
        ),
        modifier = Modifier
            .clickable {
                onClick
            }
    )
}

@Composable
fun LabelSmallText(
    text: String,
    style: TextStyle = MaterialTheme.typography.labelSmall
) {
    Text(
        text,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
    )
}