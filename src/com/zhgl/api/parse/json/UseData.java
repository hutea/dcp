package com.zhgl.api.parse.json;

import java.util.List;

public class UseData {

	private int size;

	private List<UseRecord> list;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<UseRecord> getList() {
		return list;
	}

	public void setList(List<UseRecord> list) {
		this.list = list;
	}
}

// {size:4,list:[{"FID":"","FUseRecordNUmber":"","FRecordNUmber":"","FProjectName":""}]}

