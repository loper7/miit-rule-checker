package com.loper7.simple

import android.app.Application
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import com.loper7.miit_rule_checker.MIITMethodCountChecker
import com.loper7.miit_rule_checker.MIITMethods
import com.loper7.miit_rule_checker.MIITRuleChecker
import kotlinx.coroutines.launch

/**
 *@Author loper7
 *@Date 2023/12/4 10:02
 *@Description
 **/
class App: Application() {
    override fun onCreate() {
        super.onCreate()

//        MIITRuleChecker.check(MIITMethods.getPackageManagerAll())
        MIITMethodCountChecker.startCount(MIITMethods.getPackageManagerAll() , 20 * 1000)


        val size = packageManager.getInstalledPackages(0).size
        Log.e("app","-------------------------${size}")
    }
}