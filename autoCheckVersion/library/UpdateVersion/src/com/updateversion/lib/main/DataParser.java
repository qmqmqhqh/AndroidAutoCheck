package com.updateversion.lib.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.xmlpull.v1.XmlPullParser;
import com.updateversion.lib.data.UpdataInfo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Environment;
import android.util.Xml;

/**
 * 数据解析和写文件
 * @author baoguo
 *
 */
public class DataParser {

	/**
	 * 用pull解析器解析服务器返回的xml文件
	 * @param is 输入输出流
	 * @param VersionName，版本信息标签名
	 * @param UrlName，最新APK地址标签名
	 * @param DescName，新版本描述信息标签名
	 * @return UpdataInfo
	 * @throws Exception
	 */
	public static UpdataInfo getUpdataInfo(InputStream is,String VersionName,String UrlName,String DescName) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if(VersionName.equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //获取版本号  
	            }else if (UrlName.equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件  
	            }else if (DescName.equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	} 
	
	
	
	/**
	 * 用pull解析器解析服务器返回的xml文件
	 * @param is 输入输出流
	 * @return UpdataInfo
	 * @throws Exception
	 */
	public static UpdataInfo getUpdataInfo(InputStream is) throws Exception{  
	    XmlPullParser  parser = Xml.newPullParser();    
	    parser.setInput(is, "utf-8");//设置解析的数据源   
	    int type = parser.getEventType();  
	    UpdataInfo info = new UpdataInfo();//实体  
	    while(type != XmlPullParser.END_DOCUMENT ){  
	        switch (type) {  
	        case XmlPullParser.START_TAG:  
	            if("version".equals(parser.getName())){  
	                info.setVersion(parser.nextText()); //获取版本号  
	            }else if ("url".equals(parser.getName())){  
	                info.setUrl(parser.nextText()); //获取要升级的APK文件  
	            }else if ("description".equals(parser.getName())){  
	                info.setDescription(parser.nextText()); //获取该文件的信息  
	            }  
	            break;  
	        }  
	        type = parser.next();  
	    }  
	    return info;  
	}  
	
	
	/**
	 * 从服务器下载文件，文件路径在sd卡根目录下
	 * @param path，文件路径，即所在url
	 * @param filename,下载文件保存的文件名
	 * @param pd，ProgressDialog，用于显示下载进程
	 * @return File，下载好的文件
	 * @throws Exception
	 */
	@SuppressLint("NewApi")
	public static File getFileFromServer(String path,String filename,ProgressDialog pd) throws Exception{  
	    //如果相等的话表示当前的sdcard挂载在手机上并且是可用的  
	    if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){  
	        URL url = new URL(path);  
	        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5000);  
	        //获取到文件的大小   
	        pd.setProgressNumberFormat("%1dkb/%2dkb");
	        pd.setMax(conn.getContentLength()/1024);
	        InputStream is = conn.getInputStream();  
	        File file = new File(Environment.getExternalStorageDirectory()
					.getPath(), filename);  
	        if (file.exists()) 
	        	file.delete();
	        file.createNewFile();
	        FileOutputStream fos = new FileOutputStream(file);  
	        BufferedInputStream bis = new BufferedInputStream(is);  
	        byte[] buffer = new byte[1024];  
	        int len ;  
	        int total=0;  
	        while((len =bis.read(buffer))!=-1){  
	            fos.write(buffer, 0, len);  
	            total+= len;  
	            //获取当前下载量  
	            pd.setProgress(total/1024);  
	        }  
	        fos.close();  
	        bis.close();  
	        is.close();  
	        return file;  
	    }  
	    else{  
	        return null;  
	    }  
	}  
	
}
