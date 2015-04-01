package com.qiubai.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.bt.qiubai.R;
import com.qiubai.adapter.CharacterBaseAdapter;
import com.qiubai.entity.Character;
import com.qiubai.service.CharacterService;
import com.qiubai.ui.CharacterListView;
import com.qiubai.ui.CharacterListView.OnRefreshListener;
import com.qiubai.ui.CharacterListView.onLoadListener;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class CharacterFragment extends Fragment implements OnRefreshListener,onLoadListener{
	private static String TAG = "CharacterFragment";

	private String[] fcd_support_text = new String[] { "12", "23", "34", "123" };
	private String[] fcd_tread_text = new String[] { "3219", "4329", "5439",
			"22" };
	private String[] fcd_follow_text = new String[] { "6", "7", "8", "9" };

	private String[] fcd_context = new String[] {
			"通过上面的例子可以看到Map中图片项的value是资源id，这是针对项目中已存在的图片文件，为什么要用资源id而不是其他（比如Bitmap类型）呢，这是因为adapter的bindView()方法是负责解析图片并将其显示到ImageView中，但它只针对资源id类型做了判断。然而有一种情况，比如你的图片是从网络读取的Bitmap类型，你就需要对代码进行改写了。分",
			"通过前面的例子可以看到，ListView的所有item使用的都是相同的布局，如果想使用不同的布局呢？",
			"后像自定义ListView的步骤一样使用就行了，只是把SimpleAdapter替换为CustomImageAdapter，Map中图片项的value变为Bitmap类型了",
			"3.9日凌晨发生在上海新锦江大酒店的事情过去两天了，我寝食难安。通过两天的闭门思过，认识到，该事件的是非曲直对我本人来说已经不重要了，错了，就要有代价。我牵挂的是你们！我深深地感到痛心的是，无冤无仇，从未某过面的司机师傅，因与我的争执而受轻伤躺在医院。我深深地感到追悔莫及的是，重情重义的三位好兄弟，因此而遭受牵连，失去自由。在此，我郑重的对受伤司机师傅以及另外两位司机师傅道歉，请原谅因我而起的非我主观意愿的这个结果。我郑重的对受到牵" };

	private TextView share_text;
	private CharacterService characterService;
	private final static int GET_CHARACTER = 1;
	//private ListView listCharacterView;
	//变化
	private CharacterListView listCharacterView;
	private String characterURL = "http://192.168.1.69:8081/QiuBaiServer/rest/CharacterService/getCharacters";
	
	View head_view;
	
	CharacterBaseAdapter characterAdapter;
	List<Character> listChars =new ArrayList<Character>();
	
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			
//			List<Character> result = (List<Character>) msg.obj;
			listChars = (List<Character>) msg.obj;
			listCharacterView.setResultSize(listChars.size());
//			System.out.println("result:"+result.size());
			switch (msg.what) {
			case CharacterListView.REFRESH:
				listCharacterView.onRefreshComplete();
				
//				listChars.clear();
				listChars.addAll(listChars);
				characterAdapter.changeValue(listChars);
				break;
			case CharacterListView.LOAD:
				listCharacterView.onLoadComplete();
				listChars.addAll(listChars);
				characterAdapter.changeValue(listChars);
				break;

			}
			
			characterAdapter.notifyDataSetChanged();
		};
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "==onCreate==");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView()");
		View characterLayout = inflater.inflate(
				R.layout.fragment_character_layout, container, false);
		// 取得ListView实例
		/*listCharacterView = (ListView) characterLayout
				.findViewById(R.id.listView_fragment_character);*/
		listCharacterView = (CharacterListView) characterLayout.findViewById(R.id.listView_fragment_character);
		////////
		/*characterService = new CharacterService();
		String resultUrl = characterService.getCharacter(characterURL);
		listChars = characterService.getCharacterByJson(resultUrl);
		characterAdapter = new CharacterBaseAdapter(getActivity(), listChars,
				listCharacterView);
		listCharacterView.setAdapter(characterAdapter);*/
		//////
		characterAdapter = new CharacterBaseAdapter(getActivity(), listChars, listCharacterView);
		listCharacterView.setonRefreshListener(this);
		listCharacterView.setOnLoadListener(this);
		
		loadData(CharacterListView.REFRESH);
		listCharacterView.setAdapter(characterAdapter);
		
		
		// 创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
		
		

		/*
		 * for (int i = 0; i < fcd_support_text.length; i++) { Map<String,
		 * Object> listItem = new HashMap<String, Object>();
		 * listItem.put("context_text", fcd_context[i]);
		 * listItem.put("support_text", fcd_support_text[i]);
		 * listItem.put("tread_text", fcd_tread_text[i]);
		 * listItem.put("follow_text", fcd_follow_text[i]);
		 * listItems.add(listItem);
		 * 
		 * }
		 */
		// 使用异步线程来处理
		//new ReadHttpGet().execute(characterURL);
		//下拉刷新实现
		/*listCharacterView.setonRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//handleList();
						new ReadHttpGet().execute(characterURL);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						characterAdapter.notifyDataSetChanged();
						listCharacterView.onRefreshComplete();
					}

				}.execute();				
			}
		});*/
		
		return characterLayout;

	}
	
	private void loadData(final int what){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = handler.obtainMessage();
				msg.what = what;
				characterService = new CharacterService();
				String resultUrl = characterService.getCharacter(characterURL);
				listChars = characterService.getCharacterByJson(resultUrl);
//				System.out.println("listChars长度："+listChars.size());
				
				msg.obj = listChars;
				handler.sendMessage(msg);
			}
		}).start();
	}
	
	private class ReadHttpGet extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... params) {

			characterService = new CharacterService();
			return characterService.getCharacter(params[0].toString());
		}

		@Override
		protected void onPostExecute(Object result) {
			super.onPostExecute(result);
			listChars = characterService
					.getCharacterByJson(result.toString());

			characterAdapter = new CharacterBaseAdapter(
					getActivity(), listChars,listCharacterView);
			listCharacterView.setAdapter(characterAdapter);
			
		}

	}
	
	
	@Override
	public void onLoad() {
		loadData(CharacterListView.LOAD);
	}

	@Override
	public void onRefresh() {
		loadData(CharacterListView.REFRESH);
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

	



}
