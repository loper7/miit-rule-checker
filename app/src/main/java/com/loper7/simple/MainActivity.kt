package com.loper7.simple

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.loper7.miit_rule_checker.MIITMethods
import com.loper7.miit_rule_checker.MIITRuleChecker
import com.loper7.simple.ui.theme.MiitrulecheckerTheme
import com.loper7.simple.widget.TopAppBar
import kotlinx.coroutines.launch
import java.net.NetworkInterface

class MainActivity : ComponentActivity() {

    @SuppressLint("HardwareIds")
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window , false)
        setContent {
            MiitrulecheckerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainPage()
                }
            }
        }

        //检查单个方法
//        MIITRuleChecker.check(MIITMethods.LocationManager.getLastKnownLocation)
        //检查内置的方法
        MIITRuleChecker.checkDefaults()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter" , "HardwareIds" , "QueryPermissionsNeeded")
@Composable
fun MainPage() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val wifiManager by lazy { context.getSystemService(Context.WIFI_SERVICE) as WifiManager }

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) {
        Surface(color = Color.White , modifier = Modifier.fillMaxSize()) {
            Column {
                TopAppBar {
                    Text(text = stringResource(id = R.string.app_name) ,
                        modifier = Modifier.padding(horizontal = 16.dp) ,
                        color = Color.White ,
                        fontSize = 20.sp ,
                        fontWeight = FontWeight.W600)
                }
                LazyColumn(Modifier) {
                    for (member in MIITMethods.getDefaultMethods()) {
                        if (member == null) return@LazyColumn
                        item {
                            Item(member.name) {

                                when (member.name) {
                                    "getMacAddress" -> {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = wifiManager.connectionInfo.macAddress ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getIpAddress" -> {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = wifiManager.connectionInfo.ipAddress.toString() ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getLastKnownLocation" -> {
                                        getLastKnownLocation(context)
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "getLastKnownLocation" ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "requestLocationUpdates" -> {
                                        requestLocationUpdates(context)
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "requestLocationUpdates" ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getHardwareAddress" -> {
                                        getHardwareAddress(context) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message = it ?: "null" ,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    }

                                    "getInstalledPackages" -> {
                                        val size = context.packageManager.getInstalledPackages(0).size
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "安装的app数量:$size" ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getInstallerPackageName" -> {
                                        val value = try {
                                            context.packageManager.getInstallerPackageName("com.tencent.mm")
                                        } catch (e : Exception) {
                                            "null"
                                        }
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "$value" ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getInstalledApplications" -> {
                                        val value = try {
                                            context.packageManager.getInstalledApplications(0)
                                        } catch (e : Exception) {
                                            "null"
                                        }
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = "$value" ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getPackageInfo" -> {
                                        val value = try {
                                            context.packageManager.getPackageInfo("com.tencent.mm" , 0).packageName
                                        } catch (e : Exception) {
                                            "null"
                                        }
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = value ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getString" -> {
                                        val value = Settings.Secure.getString(context.contentResolver ,
                                            Settings.Secure.ANDROID_ID)
                                        scope.launch {
                                            snackbarHostState.showSnackbar(message = value ,
                                                duration = SnackbarDuration.Short)
                                        }
                                    }

                                    "getDeviceId" -> {
                                        getDeviceId(context) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message = it ?: "null" ,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    }

                                    "getImei" -> {
                                        getIMEI(context) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message = it ?: "null" ,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    }

                                    "getSubscriberId" -> {
                                        getSubscriberId(context) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(message = it ?: "null" ,
                                                    duration = SnackbarDuration.Short)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier
                            .height(60.dp)
                            .fillMaxSize())
                    }
                }

            }
        }
    }
}

@Composable
fun Item(text : String , clickable : (() -> Unit)?) {
    Column(modifier = Modifier
        .background(color = Color.White)
        .clickable {
            clickable?.invoke()
        }) {
        Text(text = text , fontSize = 16.sp , fontWeight = FontWeight.W400 , modifier = Modifier.padding(all = 16.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .padding(start = 16.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.LightGray))
        }
    }
}

@Preview
@Composable
fun PreviewGreeting() {
    MiitrulecheckerTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            MainPage()
        }
    }
}

@SuppressLint("MissingPermission")
private fun getLastKnownLocation(context : Context) {
    val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    requestPermissions(context ,
        android.Manifest.permission.ACCESS_FINE_LOCATION ,
        android.Manifest.permission.ACCESS_COARSE_LOCATION) {
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Log.d("eachen" , "getLastKnownLocation->${location?.latitude}-${location?.longitude}")
    }
}


@SuppressLint("MissingPermission")
private fun requestLocationUpdates(context : Context) {
    val locationManager by lazy { context.getSystemService(Context.LOCATION_SERVICE) as LocationManager }
    requestPermissions(context ,
        android.Manifest.permission.ACCESS_FINE_LOCATION ,
        android.Manifest.permission.ACCESS_COARSE_LOCATION) {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER ,
            2000 ,
            1f * 1000 ,
            object : LocationListener {
                override fun onLocationChanged(location : Location) {
                    Log.d("eachen" , "requestLocationUpdates->onLocationChanged")
                }

                override fun onStatusChanged(provider : String? , status : Int , extras : Bundle?) {
                }

                override fun onProviderEnabled(provider : String) {
                }

                override fun onProviderDisabled(provider : String) {
                }
            })
    }
}

@SuppressLint("HardwareIds")
private fun getHardwareAddress(context : Context , callback : ((String?) -> Unit)) {
    requestPermissions(
        context ,
        Manifest.permission.READ_PHONE_STATE ,
    ) {
        val networkInterfaces = NetworkInterface.getNetworkInterfaces()
        for (nf in networkInterfaces) {
            val hardwareAddress = nf.hardwareAddress
            callback.invoke(hardwareAddress?.toString())
            return@requestPermissions
        }
    }
}

@SuppressLint("HardwareIds")
private fun getDeviceId(context : Context , callback : ((String?) -> Unit)) {
    val telephonyManager by lazy { context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    requestPermissions(
        context ,
        Manifest.permission.READ_PHONE_STATE ,
    ) {
        try {
            callback.invoke(telephonyManager.deviceId)
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("HardwareIds")
private fun getIMEI(context : Context , callback : ((String?) -> Unit)) {
    val telephonyManager by lazy { context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    requestPermissions(
        context ,
        Manifest.permission.READ_PHONE_STATE ,
    ) {
        try {
            callback.invoke(telephonyManager.imei)
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }
}

@SuppressLint("HardwareIds")
private fun getSubscriberId(context : Context , callback : ((String?) -> Unit)) {
    val telephonyManager by lazy { context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager }
    requestPermissions(
        context ,
        Manifest.permission.READ_PHONE_STATE ,
    ) {
        try {
            callback.invoke(telephonyManager.subscriberId)
        } catch (e : Throwable) {
            e.printStackTrace()
        }
    }
}


private fun requestPermissions(context : Context , vararg permissions : String , granted : (() -> Unit)?) {
    XXPermissions.with(context).permission(permissions).request { _ , allGranted ->
        if (allGranted) {
            granted?.invoke()
        }
    }
}