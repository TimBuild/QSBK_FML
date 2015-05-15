package com.qiubai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.qiubai.entity.Picture;
import com.qiubai.util.HttpUtil;
import com.qiubai.util.ReadPropertiesUtil;

public class PictureService {

	private String protocol;
	private String ip;
	private String port;
	
	public PictureService() {
		super();
	}
	
	/**
	 * @param map
	 * @return 从服务器获得图片版块的service
	 */
	public String getPictures(Map<String, String> map){
		protocol = ReadPropertiesUtil.read("config", "protocol");
		ip = ReadPropertiesUtil.read("config", "ip");
		port = ReadPropertiesUtil.read("config", "port");
		return HttpUtil.doPost(map, protocol + ip + ":" + port
				+ ReadPropertiesUtil.read("link", "Picture"));
	}
	
	
	/**
	 * @param map
	 * @return 从服务器获得单个图片的service
	 */
	public String getPictureById(Map<String, String> map){
		protocol = ReadPropertiesUtil.read("config", "protocol");
		ip = ReadPropertiesUtil.read("config", "ip");
		port = ReadPropertiesUtil.read("config", "port");
		return HttpUtil.doPost(map, protocol + ip + ":" + port
				+ ReadPropertiesUtil.read("link", "PictureById"));
	}
	
	
	/**
	 * @param map
	 * @return 从服务器获得图片详情的service
	 */
	public String getPictureDetail(Map<String, String> map){
		protocol = ReadPropertiesUtil.read("config", "protocol");
		ip = ReadPropertiesUtil.read("config", "ip");
		port = ReadPropertiesUtil.read("config", "port");
		return HttpUtil.doPost(map, protocol + ip + ":" + port
				+ ReadPropertiesUtil.read("link", "PictureDetails"));
	}
	
	
	public List<Picture> getPictureByJson(String pictureJson,String detailJson){
		
		List<Picture> pictures = new ArrayList<Picture>();
		Picture picture = null;
		if(pictureJson!=null || !pictureJson.equals("error")){
			try {
				JSONObject jsonpicObjects = new JSONObject(pictureJson);
				int id = jsonpicObjects.getInt("id");
				String pic_time = jsonpicObjects.getString("pic_time");
				String pic_title = jsonpicObjects.getString("pic_title");
				String userid = jsonpicObjects.getString("userid");
				//Log.d("PictureFragment", "id:"+id+"pic_time:"+pic_time+"pic_title:"+pic_title+"userid:"+user_id);
				
				if(detailJson!=null||!detailJson.equals("error")){
					JSONObject jsonDetObjects = new JSONObject(detailJson);
					JSONArray jsonObjs = jsonDetObjects.getJSONArray("pictureDetail");
					for(int i=0;i<jsonObjs.length()-1;i++){
						JSONObject jsonObject = jsonObjs.getJSONObject(i);
						picture = new Picture();
						picture.setId(id);
						picture.setUserid(userid);
						picture.setPic_time(pic_time);
						picture.setPic_title(pic_title);
						picture.setPic_address(jsonObject.getString("pic_address"));
						picture.setPic_describe(jsonObject.getString("pic_describe"));
						pictures.add(picture);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return pictures;
		
	}
	
	
}
