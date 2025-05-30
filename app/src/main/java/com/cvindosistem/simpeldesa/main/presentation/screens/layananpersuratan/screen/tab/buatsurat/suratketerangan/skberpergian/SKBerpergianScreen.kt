package com.cvindosistem.simpeldesa.main.presentation.screens.layananpersuratan.screen.tab.buatsurat.suratketerangan.skberpergian

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.cvindosistem.simpeldesa.core.components.AppBottomBar
import com.cvindosistem.simpeldesa.core.components.AppStepAnimatedContent
import com.cvindosistem.simpeldesa.core.components.AppTopBar

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SKBerpergianScreen(
    navController: NavController
) {
    var currentStep by remember { mutableIntStateOf(1) }
    val totalSteps = 3

    Scaffold(
        topBar = {
            Column {
                AppTopBar(
                    title = "SK Berpergian",
                    showBackButton = true,
                    onBackClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            AppBottomBar(
                onPreviewClick = {
                    // Handle preview - selalu ada
                },
                onBackClick = if (currentStep > 1) {
                    { currentStep -= 1 }
                } else null,
                onContinueClick = if (currentStep < totalSteps) {
                    { currentStep += 1 }
                } else null,
                onSubmitClick = if (currentStep == totalSteps) {
                    {
                        // Handle final submit - Ajukan Surat
                    }
                } else null,
            )
        }
    ) { paddingValues ->
        AppStepAnimatedContent(
            currentStep = currentStep,
            modifier = Modifier.padding(paddingValues)
        ) { step ->
            when (step) {
                1 -> SKBerpergian1Content()
                2 -> SKBerpergian2Content()
                3 -> SKBerpergian3Content()
            }
        }
    }
}