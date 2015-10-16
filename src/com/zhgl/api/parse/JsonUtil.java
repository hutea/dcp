package com.zhgl.api.parse;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhgl.api.parse.json.UseData;
import com.zhgl.core.ebean.Point;
import com.zhgl.core.ebean.TowerCraneStatus;

public class JsonUtil {
	private static String path = "http://118.122.250.29:8010/dy/EquipSys/tools/towerMonitoring.aspx";

	public static void main(String[] args) {
		parseByfuid("54608");
	}

	public static boolean postPoint(String fuid, long sid, Point point,
			String basePath) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getUseTower");
		params.put("useid", fuid);
		params.put("monitowerid", sid + "");
		params.put("projectid", point.getId() + "");
		params.put("projectname", point.getName());
		params.put("projectaddress", point.getAddress());
		params.put("basepath", basePath + "/api/device/list/" + point.getId());
		String json = post(path, params, "UTF-8");
		System.out.println("提交的数据：" + params);
		System.out.println("提交俯视点：" + json);
		return true;
	}

	public static TowerCraneStatus parseByfuid(String fuid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getUseData");
		params.put("fid", fuid);
		try {
			String json = post(path, params, "UTF-8");
			ObjectMapper mapper = new ObjectMapper();
			TowerCraneStatus towerCraneStatus = mapper.readValue(json,
					TowerCraneStatus.class);
			return towerCraneStatus;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过使用备案编号获取相关数据列表
	 * 
	 * @param number
	 * @param code
	 * @return
	 */
	public static UseData parseUseData(String number, String code) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("action", "getUseDataList");
		params.put("userecordnumber", number);
		try {
			String json = post(path, params, "UTF-8");
			System.out.println(json);
			ObjectMapper mapper = new ObjectMapper();
			UseData useData = mapper.readValue(json, UseData.class);
			return useData;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 提交数据到服务器
	 * 
	 * @param path
	 *            请求路径
	 * @param params
	 *            请求参数 key为参数名,value为参数值
	 * @param encode
	 *            编码
	 */
	private static String post(String path, Map<String, String> params,
			String encode) {
		try {
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();// 用于存放请求参数
			for (Map.Entry<String, String> entry : params.entrySet()) {
				formparams.add(new BasicNameValuePair(entry.getKey(), entry
						.getValue()));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams,
					encode);
			HttpPost httppost = new HttpPost(path);
			httppost.setEntity(entity);
			HttpClient httpclient = new DefaultHttpClient(); // 看作是浏览器
			HttpResponse response = httpclient.execute(httppost);// 发送post请求
			InputStream in = response.getEntity().getContent();
			BufferedReader bf = new BufferedReader(new InputStreamReader(in,
					"UTF-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = bf.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

}
