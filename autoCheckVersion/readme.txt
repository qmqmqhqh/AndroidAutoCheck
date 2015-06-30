需要注意以下几点：
1.Manifest中需要添加网络访问权限和文件读取权限
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
2.服务器中的更新信息必须存储在xml中
3.初始化的时候必须设置更新检测地址和更新文件名，文件名后缀必须以.apk结束
4.如果需要自定义对话框样式和进度条样式的话需要在check()之前设置对话框信息和进度条，即setProgress()和setLayoutInfo()。
5.服务器更新检测的xml中内容默认是以下格式
<?xml version="1.0" encoding="utf-8"?>  
<info>  
    <version>2.0</version>  
    <url>http://1.wuliutong.sinaapp.com/update/Test.apk</url>  
    <description>请更新</description>  
</info>  
如果不是这种格式，需要在check()之前指定版本号的标签，url的标签，描述信息的标签，即setVersionInfo();