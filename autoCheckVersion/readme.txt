��Ҫע�����¼��㣺
1.Manifest����Ҫ����������Ȩ�޺��ļ���ȡȨ��
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
2.�������еĸ�����Ϣ����洢��xml��
3.��ʼ����ʱ��������ø��¼���ַ�͸����ļ������ļ�����׺������.apk����
4.�����Ҫ�Զ���Ի�����ʽ�ͽ�������ʽ�Ļ���Ҫ��check()֮ǰ���öԻ�����Ϣ�ͽ���������setProgress()��setLayoutInfo()��
5.���������¼���xml������Ĭ�������¸�ʽ
<?xml version="1.0" encoding="utf-8"?>  
<info>  
    <version>2.0</version>  
    <url>http://1.wuliutong.sinaapp.com/update/Test.apk</url>  
    <description>�����</description>  
</info>  
����������ָ�ʽ����Ҫ��check()֮ǰָ���汾�ŵı�ǩ��url�ı�ǩ��������Ϣ�ı�ǩ����setVersionInfo();