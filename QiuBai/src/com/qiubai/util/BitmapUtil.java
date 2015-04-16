package com.qiubai.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtil {

	/**
	 * 调整图片的大小 (先将图片缩小或者放大，然后对图片进行裁剪，让不同的图片都能占满不同的手机尺寸的屏幕)
	 * 
	 * @param boxWidth
	 * @param boxHeight
	 * @param bitmap
	 *            原图
	 * @return
	 */
	public static Bitmap resizeBitmap(int boxWidth, int boxHeight, Bitmap bitmap) {

		float scaleX = ((float) boxWidth) / ((float) bitmap.getWidth());
		float scaleY = ((float) boxHeight) / ((float) bitmap.getHeight());
		float scale = 1.0f;

		if ((scaleX >= scaleY && scaleY >= 1.0f)
				|| (scaleX > scaleY && scaleX < 1.0f)
				|| (scaleX >= 1.0f && scaleY < 1.0f)) {
			scale = scaleX;
		}
		if ((scaleY > scaleX && scaleX >= 1.0f)
				|| (scaleY > scaleX && scaleY < 1.0f)
				|| (scaleX < 1.0f && scaleY >= 1.0f)) {
			scale = scaleY;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		Bitmap alterBitmap = Bitmap.createBitmap(newBitmap, 0, 0, boxWidth,
				boxHeight);
		return alterBitmap;
	}

	/**
	 * 调整图片的大小适合box
	 * 
	 * @param boxWidth
	 * @param boxHeight
	 * @param bitmap 原图
	 * @return
	 */
	public static Bitmap resizeBitmapMatchBox(int boxWidth, int boxHeight,
			Bitmap bitmap) {
		float scaleX = ((float) boxWidth) / ((float) bitmap.getWidth());
		float scaleY = ((float) boxHeight) / ((float) bitmap.getHeight());
		System.out.println(scaleX + "------" + scaleY);
		float scale = 1.0f;

		if ((scaleX >= scaleY && scaleY >= 1.0f)
				|| (scaleX > scaleY && scaleX < 1.0f)
				|| (scaleX >= 1.0f && scaleY < 1.0f)) {
			scale = scaleY;
		}
		if ((scaleY > scaleX && scaleX >= 1.0f)
				|| (scaleY > scaleX && scaleY < 1.0f)
				|| (scaleX < 1.0f && scaleY >= 1.0f)) {
			scale = scaleX;
		}
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return newBitmap;
	}

	/**
	 * 调整正方形图片适应正方形盒子大小
	 * 
	 * @param width
	 * @param bitmap
	 * @return
	 */
	public static Bitmap resizeSquareBitmap(int width, Bitmap bitmap) {
		float scale = ((float) width) / ((float) bitmap.getWidth());
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return newBitmap;
	}

	/**
	 * 旋转图片(旋转中心为图片的正中心)
	 * 
	 * @param degree
	 * @param bitmap
	 * @return
	 */
	public static Bitmap rotateBitmap(float degree, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postRotate(degree, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return newBitmap;
	}
	
	/**
	 * 放大或者缩小图片
	 * @param scale
	 * @param bitmap
	 * @return
	 */
	public static Bitmap zoomBitmap(float scale, Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return newBitmap;
	}
}