package com.cvindosistem.simpeldesa.app.di

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.cvindosistem.simpeldesa.R
import com.zynksoftware.documentscanner.ScanActivity
import com.zynksoftware.documentscanner.model.DocumentScannerErrorModel
import com.zynksoftware.documentscanner.model.ScannerResults

class AppScanActivity : ScanActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_scan_activity_layout)
        addFragmentContentLayout()
    }

    override fun onError(error: DocumentScannerErrorModel) {
        Toast.makeText(this, "Scan error: ${error.errorMessage}", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSuccess(scannerResults: ScannerResults) {
        val resultIntent = Intent()
        resultIntent.putExtra("originalImagePath", scannerResults.originalImageFile?.absolutePath)
        resultIntent.putExtra("croppedImagePath", scannerResults.croppedImageFile?.absolutePath)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    override fun onClose() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}