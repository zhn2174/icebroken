package com.icebroken.utils;

import com.parse.codec.binary.Hex;
import com.parse.codec.digest.DigestUtils;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import anet.channel.util.StringUtils;

public class SignUtil {
	public static String createSign(JSONObject map) {
		StringBuilder buffer = new StringBuilder();
		List<String> arrayList = new ArrayList<>();
		Iterator ite = map.keys();
		while (ite.hasNext()) {
			String key = ite.next().toString();
			String value = map.opt(key).toString();
			if (!StringUtils.isBlank(value)){
				arrayList.add(key + "=" +value+ "&");
			}
		}
//		Collections.sort(arrayList);
		for (String string : arrayList) {
			buffer.append(string);
		}
		String content;
		content = buffer + "6E1C357C155476D5";
		String result = "";
		try {
			byte[] data = content.getBytes("UTF-8");
			result = Hex.encodeHexString(DigestUtils.md5(data));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}



}
