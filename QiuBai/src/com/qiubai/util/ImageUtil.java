package com.qiubai.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpStatus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	
	/**
	 * store image
	 * @param bitmap
	 * @param file
	 */
	public static void storeImage(Bitmap bitmap, String file){
		try {
			FileOutputStream fileOS = new FileOutputStream(new File(file));
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
}
