package com.qiubai.entity;

import java.util.List;

public class PictureList {
	private List<Picture> pictures;

	public PictureList(List<Picture> pictures) {
		super();
		this.pictures = pictures;
	}

	public PictureList() {
		super();
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	@Override
	public String toString() {
		return "PictureList [pictures=" + pictures + "]";
	}

}
