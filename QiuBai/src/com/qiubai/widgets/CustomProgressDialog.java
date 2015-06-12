package com.qiubai.widgets;

import com.bt.qiubai.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {

	private Context context = null;
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context) {
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context) {
		customProgressDialog = new CustomProgressDialog(context,
				R.style.CommonProgressDialog);
		customProgressDialog.setContentView(R.layout.progress_dialog_custom);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;

		return customProgressDialog;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		if (customProgressDialog == null) {
			return;
		}

		ImageView imageView = (ImageView) customProgressDialog
				.findViewById(R.id.dialog_loadingImageView);
		AnimationDrawable animationDrawable = (AnimationDrawable) imageView
				.getBackground();
		animationDrawable.start();
	}

	/**
	 * 标题
	 * 
	 * @param title
	 * @return
	 */
	public CustomProgressDialog setTitle(String title) {
		return customProgressDialog;
	}

	/**
	 * 文本
	 * 
	 * @param message
	 * @return
	 */
	public CustomProgressDialog setMessage(String message) {
		TextView txtMsg = (TextView) customProgressDialog
				.findViewById(R.id.dialog_tv_loadingmsg);
		if (txtMsg != null) {
			txtMsg.setText(message);
		}

		return customProgressDialog;
	}

}
