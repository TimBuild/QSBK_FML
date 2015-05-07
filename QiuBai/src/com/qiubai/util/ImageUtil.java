package com.qiubai.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.UUID;

import org.apache.http.HttpStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	
	/**
	 * store image
	 * @param bitmap
	 * @param file
	 */
	public static void storeImage(Bitmap bitmap, String path, String filename){
		try {
			File filepath = new File(path);
			if (!filepath.exists()) {
				filepath.mkdirs();
			}
			FileOutputStream fileOS = new FileOutputStream(path + "/" + filename);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOS);
			fileOS.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get image bitmap
	 * @param uri
	 * @return
	 */
	public static Bitmap getImageBitmap(String uri){
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			conn.setConnectTimeout(30000);
			conn.setRequestMethod("GET");
			conn.setReadTimeout(30000);
			if(conn.getResponseCode() == HttpStatus.SC_OK){
				return BitmapFactory.decodeStream(conn.getInputStream());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String uploadFile(File file, String uri){
		String result = null;
		String boundary = UUID.randomUUID().toString();
		String content_type = "multipart/form-data";
		String prefix = "--", line_end = "\r\n";
		
		try {
			URL url = new URL(uri);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(30000);
			conn.setConnectTimeout(30000);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", "utf-8"); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", content_type + ";boundary="+ boundary);
			if(file != null){
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(prefix);
				sb.append(boundary);
				sb.append(line_end);
				sb.append("Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getName() + "\"" + line_end);
	            sb.append("Content-Type: application/octet-stream; charset=utf-8" + line_end);
	            sb.append(line_end);
	            dos.write(sb.toString().getBytes());
	            FileInputStream fis = new FileInputStream(file);
	            byte[] bytes = new byte[1024];
	            int len = 0;
	            while ((len = fis.read(bytes)) != -1) {
	                dos.write(bytes, 0, len);
	            }
	            fis.close();
	            dos.write(line_end.getBytes());
	            byte[] end_data = (prefix + boundary + prefix + line_end).getBytes();
	            dos.write(end_data);
	            dos.flush();
	           
	            if (conn.getResponseCode() == HttpStatus.SC_OK) {
	                InputStream input = conn.getInputStream();
	                StringBuffer sb1 = new StringBuffer();
	                int ss;
	                while ((ss = input.read()) != -1) {
	                    sb1.append((char) ss);
	                }
	                result = sb1.toString();
	            } else {
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	       
}
