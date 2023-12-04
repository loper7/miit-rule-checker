![](https://github.com/loper7/miit-rule-checker/blob/master/snapshot/logo.png)
<br/>
![Maven Central](https://img.shields.io/maven-central/v/io.github.loper7/miit-rule-checker)&ensp;[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)&ensp;[![](https://img.shields.io/badge/platform-android-green)](https://github.com/loperSeven)&ensp;[![](https://img.shields.io/badge/license-Apache2.0-blue)](http://www.apache.org/licenses/LICENSE-2.0.txt)
<br/>
<br/>
<br/>
⭐🎉虽迟但到，这是一个通过拦截Java方法调用用以检测应用是否合规的工具，如果你的APP正饱受监管部门或应用市场时不时下发整改通知的折磨，那么用它来检查你的代码以及引用的三方库是再好不过的选择了！
<br/>
## 如何引入
Step 1. 添加 `mavenCentral`
```
allprojects {
	repositories {
		...
	 mavenCentral()
	}
}
```
Step 2. 添加 `Gradle` 依赖
```
dependencies {
    ...
    implementation 'io.github.loper7:miit-rule-checker:0.1.2'
}
```
## 如何使用
- `需求一` 检查APP内是否存在不合规的方法调用
  > 检查MIITRuleChecker内置的不合规的方法，具体可见下方方法列表
  ```kotlin
  MIITRuleChecker.checkDefaults()
  ```
  > 如果内置的方法不满足当前需求，可自定义方法添加到list中进行检查；<br/>
  > 比如新增一个 MainActivity 的 onCreate 方法的调用检查；<br/>
  ```kotlin
  val list = MIITMethods.getDefaultMethods()
  list.add(MainActivity::class.java.getDeclaredMethod("onCreate" , Bundle::class.java))
  MIITRuleChecker.check(list)
  ```
  当然，如果你想检查多个内置方法外的方法，只需要创建一个新的集合，往集合里放你想检查的方法`member`,然后传入 `MIITRuleChecker.check()`内即可。
  <br/>
  <br/>
  `log`打印如下所示：
   <br/>
   <br/>
  ![](https://github.com/loper7/miit-rule-checker/blob/master/snapshot/method_androidid.png)
  
- `需求二` 检查指定方法调用并查看调用栈堆
    ```kotlin
    //查看 WifiInfo class 内 getMacAddress 的调用栈堆
   MIITRuleChecker.check(MIITMethods.WifiInfo.getMacAddress)
  ```
  `log`打印如下所示：
  <br/>
  <br/>
  ![](https://github.com/loper7/miit-rule-checker/blob/master/snapshot/method_macaddress.png)
- `需求三` 检查一定时间内指定方法调用次数统计
  ```kotlin
   //多个方法统计 （deadline 为从方法调用开始到多少毫秒后截至统计）
   val list = mutableListOf<Member?>().apply {
            add(MIITMethods.LocationManager.getLastKnownLocation)
            add(MIITMethods.LocationManager.requestLocationUpdates)
            add(MIITMethods.Secure.getString)
        }
  MIITMethodCountChecker.startCount(list , 20 * 1000)
  
  //单个方法统计（deadline 为从方法调用开始到多少毫秒后截至统计）
  MIITMethodCountChecker.startCount(MIITMethods.LocationManager.getLastKnownLocation , deadline = 20 * 1000)
  ```
  `log`打印如下所示：
  <br/>
  <br/>
  ![](https://github.com/loper7/miit-rule-checker/blob/master/snapshot/log_count.png)
## 切记
 检查完成并完成整改后务必移除方法 miit-rule-checker 库内的所有方法调用，将库一起移除最好<br/>
 检查完成并完成整改后务必移除方法 miit-rule-checker 库内的所有方法调用，将库一起移除最好<br/>
 检查完成并完成整改后务必移除方法 miit-rule-checker 库内的所有方法调用，将库一起移除最好
## 内置方法表
 内置常量 | 对应的系统方法 | 备注
 ------------ | ------------- | -------------
 `MIITMethods.WifiInfo.getMacAddress` | `android.net.wifi.WifiInfo.getMacAddress()` | 获取MAC地址
 `MIITMethods.WifiInfo.getIpAddress` | `android.net.wifi.WifiInfo.getIpAddress()` | 获取IP地址
 `MIITMethods.LocationManager.getLastKnownLocation` | `android.location.LocationManager.getLastKnownLocation(String)` | 获取上次定位的地址
 `MIITMethods.LocationManager.requestLocationUpdates` | `android.location.LocationManager.requestLocationUpdates(String,Long,Float,LocationListener)` | 
 `MIITMethods.NetworkInterface.getHardwareAddress` | `java.net.NetworkInterface.getHardwareAddress()` | 获取主机地址
 `MIITMethods.ApplicationPackageManager.getInstalledPackages` | `android.app.ApplicationPackageManager.getInstalledPackages(Int)` | 获取已安装的应用
 `MIITMethods.ApplicationPackageManager.getInstalledApplications` | `android.app.ApplicationPackageManager.getInstalledApplications(Int)` | 获取已安装的应用
 `MIITMethods.ApplicationPackageManager.getInstallerPackageName` | `android.app.ApplicationPackageManager.getInstallerPackageName(String)` | 获取应用安装来源
 `MIITMethods.ApplicationPackageManager.getPackageInfo` | `android.app.ApplicationPackageManager.getPackageInfo(String，Int)` | 获取应用信息
 `MIITMethods.PackageManager.getInstalledPackages` | `android.content.pm.PackageManager.getInstalledPackages(Int)` | 获取已安装的应用
 `MIITMethods.PackageManager.getInstalledApplications` | `android.content.pm.PackageManager.getInstalledApplications(Int)` | 获取已安装的应用
 `MIITMethods.PackageManager.getInstallerPackageName` | `android.content.pm.PackageManager.getInstallerPackageName(String)` | 获取应用安装来源
 `MIITMethods.PackageManager.getPackageInfo` | `android.content.pm.PackageManager.getPackageInfo(String，Int)` | 获取应用信息
 `MIITMethods.PackageManager.getPackageInfo1` | `android.content.pm.PackageManager.getPackageInfo(String，PackageInfoFlags)` | 获取应用信息（版本号大于33）
 `MIITMethods.PackageManager.getPackageInfo2` | `android.content.pm.PackageManager.getPackageInfo(VersionedPackage，Int)` | 获取应用信息（版本号大于26）
 `MIITMethods.PackageManager.getPackageInfo3` | `android.content.pm.PackageManager.getPackageInfo(VersionedPackage，PackageInfoFlags)` | 获取应用信息（版本号大于33）
 `MIITMethods.Secure.getString` | `android.provider.Settings.Secure.getString(ContentResolver，String)` | 获取androidId
 `MIITMethods.TelephonyManager.getDeviceId` | `android.telephony.TelephonyManager.getDeviceId()` | 获取 DeviceId
 `MIITMethods.TelephonyManager.getDeviceIdWithInt` | `android.telephony.TelephonyManager.getDeviceId(Int)` | 获取 DeviceId
 `MIITMethods.TelephonyManager.getImei` | `android.telephony.TelephonyManager.getImei()` | 获取 Imei
 `MIITMethods.TelephonyManager.getImeiWithInt` | `android.telephony.TelephonyManager.getImei(Int)` | 获取 Imei
 `MIITMethods.TelephonyManager.getSubscriberId` | `android.telephony.TelephonyManager.getSubscriberId()` | 获取 SubscriberId

# 相关文章
- [工业和信息化部关于开展纵深推进APP侵害用户权益专项整治行动的通知](https://www.gov.cn/zhengce/zhengceku/2020-08/02/content_5531975.htm)
- [工业和信息化部关于进一步提升移动互联网应用服务能力的通知](https://www.gov.cn/zhengce/zhengceku/2023-03/02/content_5744106.htm)
- [APP合规政策解读爽文](https://juejin.cn/post/7250507911201226812)

## 鸣谢
- [Pine](https://github.com/canyie/pine/blob/master/README_cn.md)

## 联系我
- loper7@163.com
- [issue](https://github.com/loper7/miit-rule-checker/issues)
