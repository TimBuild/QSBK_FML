package com.qiubai.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bt.qiubai.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CharacterFragment extends Fragment {
	private String tag = "==CharacterFragment==";
	
	private String[] fcd_support_text = new String[]{"12","23","34","123"};
	private String[] fcd_tread_text = new String[]{"3219","4329","5439","22"};
	private String[] fcd_follow_text = new String[]{"6","7","8","9"};
	
	private String[] fcd_context = new String[]{"通过上面的例子可以看到Map中图片项的value是资源id，这是针对项目中已存在的图片文件，为什么要用资源id而不是其他（比如Bitmap类型）呢，这是因为adapter的bindView()方法是负责解析图片并将其显示到ImageView中，但它只针对资源id类型做了判断。然而有一种情况，比如你的图片是从网络读取的Bitmap类型，你就需要对代码进行改写了。分",
			"通过前面的例子可以看到，ListView的所有item使用的都是相同的布局，如果想使用不同的布局呢？",
			"后像自定义ListView的步骤一样使用就行了，只是把SimpleAdapter替换为CustomImageAdapter，Map中图片项的value变为Bitmap类型了",
			"3.9日凌晨发生在上海新锦江大酒店的事情过去两天了，我寝食难安。通过两天的闭门思过，认识到，该事件的是非曲直对我本人来说已经不重要了，错了，就要有代价。我牵挂的是你们！我深深地感到痛心的是，无冤无仇，从未某过面的司机师傅，因与我的争执而受轻伤躺在医院。我深深地感到追悔莫及的是，重情重义的三位好兄弟，因此而遭受牵连，失去自由。在此，我郑重的对受伤司机师傅以及另外两位司机师傅道歉，请原谅因我而起的非我主观意愿的这个结果。我郑重的对受到牵"};
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(tag, "onCreateView()");
		View characterLayout = inflater.inflate(R.layout.fragment_character_layout,
				container, false);
		//取得ListView实例
		ListView listCharacterView = (ListView) characterLayout.findViewById(R.id.listView_fragment_character);
		
		//创建一个List集合，List集合的元素是Map
		List<Map<String, Object>> listItems = new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<fcd_support_text.length;i++){
			Map<String, Object> listItem = new HashMap<String, Object>();
			listItem.put("context_text", fcd_context[i]);
			listItem.put("support_text", fcd_support_text[i]);
			listItem.put("tread_text", fcd_tread_text[i]);
			listItem.put("follow_text", fcd_follow_text[i]);
			listItems.add(listItem);
			
		}
		
		//创建一个SimpleAdapter
		
		SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_character_detail, 
				new String[]{"context_text","support_text","tread_text","follow_text"}, 
				new int[]{R.id.fragment_character_detail_context,R.id.fragment_character_detail_support_text,R.id.fragment_character_detail_tread_text,R.id.fragment_character_detail_follow_text});
		
		listCharacterView.setAdapter(simpleAdapter);
		return characterLayout;

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
