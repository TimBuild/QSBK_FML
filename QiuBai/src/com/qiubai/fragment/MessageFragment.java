package com.qiubai.fragment;

import com.bt.qiubai.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MessageFragment extends Fragment {
	private String tag = "==MessageFragment==";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(tag, "onCreateView()");
		View messageLayout = inflater.inflate(R.layout.message_layout,
				container, false);
		return messageLayout;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		Log.d(tag, "onAttach()");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(tag, "onCreate()");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.d(tag, "onActivityCreated()");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(tag, "onStart()");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(tag, "onResume()");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(tag, "onPause()");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(tag, "onStop()");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(tag, "onDestroyView()");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(tag, "onDestroy()");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(tag, "onDetach()");
	}
	
	
}
