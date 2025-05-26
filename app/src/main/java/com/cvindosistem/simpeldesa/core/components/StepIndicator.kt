package com.cvindosistem.simpeldesa.core.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun StepIndicator(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(horizontal = 16.dp)) {
        val colorPrimary = MaterialTheme.colorScheme.primary
        val colorGray = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        // Garis horizontal di latar belakang
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(25.dp) // Tinggi untuk menyesuaikan ruang kotak
            .align(Alignment.TopCenter)
        ) {
            val stepCount = steps.size
            val spacing = size.width / (stepCount - 1)
            val centerY = size.height / 2

            for (i in 0 until stepCount - 1) {
                val startX = i * spacing
                val endX = (i + 1) * spacing

                drawLine(
                    color = if (i < currentStep) colorPrimary
                    else colorGray,
                    start = Offset(startX, centerY),
                    end = Offset(endX, centerY),
                    strokeWidth = 4f
                )
            }
        }

        // Isi kotak dan teks
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            steps.forEachIndexed { index, title ->
                val isCompleted = index < currentStep
                val isCurrent = index == currentStep

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                shape = RectangleShape
                            )
                            .border(
                                width = 0.2.dp,
                                color = if (isCompleted) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                shape = RectangleShape
                            )
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            isCurrent -> MaterialTheme.colorScheme.onBackground
                            isCompleted -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        },
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(max = 80.dp) // biar rata tengah walaupun panjang
                    )
                }
            }
        }
    }
}

@Composable
fun StepIndicatorFlexible(
    steps: List<String>,
    currentStep: Int,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.padding(horizontal = 16.dp)) {
        val colorPrimary = MaterialTheme.colorScheme.primary
        val colorGray = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        // Garis horizontal di latar belakang
        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(25.dp) // Tinggi untuk menyesuaikan ruang kotak
            .align(Alignment.TopCenter)
        ) {
            val stepCount = steps.size
            val spacing = size.width / (stepCount - 1)
            val centerY = size.height / 2

            for (i in 0 until stepCount - 1) {
                val startX = i * spacing
                val endX = (i + 1) * spacing

                drawLine(
                    color = if (i < currentStep) colorPrimary
                    else colorGray,
                    start = Offset(startX, centerY),
                    end = Offset(endX, centerY),
                    strokeWidth = 4f
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(top = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            steps.forEachIndexed { index, title ->
                val isCompleted = index < currentStep
                val isCurrent = index == currentStep

                Column(
                    modifier = Modifier
                        .padding(end = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(18.dp)
                            .background(
                                color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
                                shape = RectangleShape
                            )
                            .border(
                                width = 0.2.dp,
                                color = if (isCompleted) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                shape = RectangleShape
                            )
                    ) {
                        if (isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        color = when {
                            isCurrent -> MaterialTheme.colorScheme.onBackground
                            isCompleted -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                            else -> MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        },
                        fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.widthIn(max = 80.dp)
                    )
                }
            }
        }
    }
}