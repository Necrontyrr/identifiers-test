package com.oysta.privilegedtest

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var btn: Button
    private lateinit var tv: TextView
    private lateinit var tv2: TextView
    private lateinit var tv3: TextView

    private val permissionLocation = Manifest.permission.ACCESS_FINE_LOCATION
    private val permissionBluetooth = Manifest.permission.BLUETOOTH_CONNECT
    private val permissions = arrayOf(permissionLocation, permissionBluetooth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.button)
        tv = findViewById(R.id.textView)
        tv2 = findViewById(R.id.textView2)
        tv3 = findViewById(R.id.textView3)

        btn.setOnClickListener {
            //method 1
            var macAddress: String? =
                Settings.Secure.getString(contentResolver, "bluetooth_address")
            tv.text = "M1 MAC address: $macAddress"

            //method 2
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            val bluetoothAdapter = bluetoothManager.adapter
            macAddress = bluetoothAdapter.address
            tv2.text = "M2 MAC address: $macAddress"

            //imei
            try {
                val telephonyManager =
                    getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                val imei = telephonyManager.imei
                tv3.text = "IMEI: $imei"
            } catch(e: SecurityException) {
                tv3.text = "IMEI: ${e.message}"
            }
        }

        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            enable()
        }
        requestPermissionLauncher.launch(permissions)

    }

    private fun enable() {
        btn.isEnabled = true
    }

}