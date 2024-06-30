package com.shadowblaxe123.musicapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.os.Build

class MainActivity : AppCompatActivity() {
    val externalStorageReadPermissionCode: Int = 100
    val audioMediaReadPermissionCode: Int = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Checking if version of android on client is Android 13 or more.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Newer !3+ Android
            checkPermissions(Manifest.permission.READ_MEDIA_AUDIO, audioMediaReadPermissionCode)
        } else {
            // For older Android versions. Like my own phone which runs Android 11
            checkPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, externalStorageReadPermissionCode)
        }
    }

    private fun checkPermissions(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this, "Permission is already given", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == externalStorageReadPermissionCode) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "READ_LOCAL_FILES Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "READ_LOCAL_FILES Permission Denied", Toast.LENGTH_LONG).show()
            }
        } else if (requestCode == audioMediaReadPermissionCode) {
            if (grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "READ_LOCAL_AUDIO Permission Granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "READ_LOCAL_AUDIO Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }
}