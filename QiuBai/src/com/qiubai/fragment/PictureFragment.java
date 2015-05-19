package com.qiubai.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bt.qiubai.PictureTextActivity;
import com.bt.qiubai.R;
import com.qiubai.adapter.PictureBaseAdapter;
import com.qiubai.entity.Picture;
import com.qiubai.entity.PictureList;
import com.qiubai.service.PictureService;
import com.qiubai.util.HttpUtil;
import com.qiubai.view.CharacterListView;
import com.qiubai.view.CharacterListView.OnRefreshListener;
import com.qiubai.view.CharacterListView.onLoadListener;
import com.squareup.picasso.Picasso;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class PictureFragment extends Fragment implements OnRefreshListener,onLoadListener{
	private static String TAG = "PictureFragment";
	
	private PictureService pictureService;
	
	private static String OFFSET = "0";
	private static String ROWS = "3";
	
	private int picture_start = 0;
	private int picture_count = CharacterListView.pageSize;
	
	private List<PictureList> pictureLists;
	private List<PictureList> pictureListsResult = new ArrayList<PictureList>();
	private PictureList pictureList;
	
	private Context context;
	private CharacterListView listPictureView;
	private PictureBaseAdapter pictureAdapter;
	
	private Intent intent;
	
	


	public PictureFragment() {
		super();
	}
	
	public PictureFragment(Context context) {
		super();
		this.context = context;
	}



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "==onCreate==");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView()");
		View pictureLayout = inflater.inflate(R.layout.fragment_picture_layout,
				container, false);
		// 取得listview实例
		listPictureView = (CharacterListView) pictureLayout
				.findViewById(R.id.listview_fragment_picture);

		pictureAdapter = new PictureBaseAdapter(
				getActivity(),pictureListsResult);
		listPictureView.setonRefreshListener(this);
		listPictureView.setOnLoadListener(this);
		listPictureView.setAdapter(pictureAdapter);
		
		listPictureView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				intent = new Intent(getActivity(), PictureTextActivity.class);
				
				intent.putExtra("pic_id", pictureListsResult.get(position-1).getPictures().get(0).getId());
				intent.putExtra("pic_title", pictureListsResult.get(position-1).getPictures().get(0).getPic_title());
//				Log.d(TAG, "position:"+position+"-->"+pictureListsResult.get(position-1).getPictures().get(0).getId());
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.stay_in_place);
				
			}
		});
		
		new PictureLoad().execute(CharacterListView.REFRESH);

		return pictureLayout;

	}
	
	private int[] getIdByResult(String result){
		int[] id = null;
		if(result!=null || !result.equals("error")){
			try {
				JSONObject jsonObjects = new JSONObject(result);
				JSONArray jsonObjs = jsonObjects.getJSONArray("picture");
				if(jsonObjs!=null){
					id=new int[jsonObjs.length()-1];
					for(int i=0;i<jsonObjs.length()-1;i++){
						JSONObject jsonObject = jsonObjs.getJSONObject(i);
						id[i] = jsonObject.getInt("id");
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return id;
	}
	
	private class PictureLoad extends AsyncTask<Object, Void, Object>{

		@Override
		protected Object doInBackground(Object... params) {
			pictureService = new PictureService();
			pictureLists = new ArrayList<PictureList>();
			String result = null;
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> mapId = new HashMap<String, String>();
			Map<String, String> mapDetail = new HashMap<String, String>();
			if(params[0].equals(CharacterListView.REFRESH)){
				picture_count = picture_count+picture_start;
				picture_start = 0;
				mapDetail.put("offset", OFFSET);
				mapDetail.put("rows", ROWS);
				map.put("offset", String.valueOf(picture_start));
				map.put("rows", String.valueOf(picture_count));
				String picture_result = pictureService.getPictures(map);
				Log.d(TAG, "picture_result:"+picture_result);
				int[] ids = getIdByResult(picture_result);
				List<Picture> pictures;
				if(ids!=null){
					for(int id:ids){
						mapId.put("id", String.valueOf(id));
						mapDetail.put("id", String.valueOf(id));
						String picture_id_result = pictureService.getPictureById(mapId);
						String detail_result = pictureService.getPictureDetail(mapDetail);
						pictures = pictureService.getPictureByJson(picture_id_result, detail_result);
						pictureList = new PictureList();
						pictureList.setPictures(pictures);
						pictureLists.add(pictureList);
						
					}
				}
				Log.d(TAG, "onRefresh:pictureLists-->"+pictureLists.toString());
				if(pictureLists!=null){
					result = "REFRESH";
				}else{
					result ="REFRESH_error";
				}
				
			}else if(params[0].equals(CharacterListView.LOAD)){
				picture_count = CharacterListView.pageSize;
				picture_start = picture_start+picture_count;
				mapDetail.put("offset", OFFSET);
				mapDetail.put("rows", ROWS);
				map.put("offset", String.valueOf(picture_start));
				map.put("rows", String.valueOf(picture_count));
				String picture_result = pictureService.getPictures(map);
				Log.d(TAG, "picture_result:"+picture_result);
				int[] ids = getIdByResult(picture_result);
				List<Picture> pictures;
				if(ids!=null){
					for(int id:ids){
						mapId.put("id", String.valueOf(id));
						mapDetail.put("id", String.valueOf(id));
						String picture_id_result = pictureService.getPictureById(mapId);
						String detail_result = pictureService.getPictureDetail(mapDetail);
						pictures = pictureService.getPictureByJson(picture_id_result, detail_result);
						pictureList = new PictureList();
						pictureList.setPictures(pictures);
						pictureLists.add(pictureList);
					}
				}
				Log.d(TAG, "load:pictureLists-->"+pictureLists.size());
				if(pictureLists!=null){
					result = "LOAD";
				}else{
					result ="LOAD_error";
				}
			}
			
			Log.d(TAG, "pictureLists-->"+pictureLists.size());
//			pictureService.getPictures(map);
			return result;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			Log.d(TAG, "result--->:"+result.toString());
			if(result.equals("REFRESH")){
				listPictureView.setResultSize(pictureLists.size());
				listPictureView.onRefreshComplete();
				pictureListsResult.clear();
				pictureListsResult.addAll(0,pictureLists);
			}else if(result.equals("LOAD")){
				listPictureView.setResultSize(pictureLists.size());
				listPictureView.onLoadComplete();
				pictureListsResult.addAll(pictureLists);
			}
			
			pictureAdapter.setData(pictureListsResult);
			pictureAdapter.notifyDataSetChanged();
			
			
		}
	}
	
	

	@Override
	public void onStart() {
		super.onStart();
		Log.d(TAG, "==onStart==");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "==onResume==");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "==onPause==");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "==onStop==");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.d(TAG, "==onDestroyView==");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "==onDestroy==");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.d(TAG, "==onDetach==");
	}

	@Override
	public void onLoad() {
		new PictureLoad().execute(CharacterListView.LOAD);
	}

	@Override
	public void onRefresh() {
		new PictureLoad().execute(CharacterListView.REFRESH);
	}

}
