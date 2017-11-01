package com.weiyong.jstockchart.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.MapMaker;
import com.weiyong.jstockchart.model.Minute;
import com.weiyong.jstockchart.model.Quote;


public class ForexService {

	
	private static ForexService instance = null;
	private ConcurrentMap<String,Object> cacheMap = new MapMaker().expiration(1, TimeUnit.DAYS).makeMap();	
	
	
	public static ForexService getInstance() {
		if (instance == null) {
			synchronized (ForexService.class) {
				if (instance == null)
					instance = new ForexService();
			}
		}
		return instance;
	}
	
	
	
	private static String getDataJson(String fileName) throws IOException, URISyntaxException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		File file = new File(classLoader.getResource("data" + File.separator + fileName).toURI().getPath());
		StringBuffer strJson = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		String data = br.readLine();
		while (data != null) {
			strJson.append(data);
			data = br.readLine();
		}
		br.close();
		return strJson.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Quote> getForexQuoteData() throws Exception {
		List<Quote> quotes = (List<Quote>) cacheMap.get("forex_quote_pic_data");
		if (quotes != null && !quotes.isEmpty()) return quotes;
		JSONObject jsonObject = JSONObject.parseObject(getDataJson("FOREX_QUOTE.json").replaceAll("\\(|\\)|;", ""));
		if (jsonObject == null) return null;
		JSONArray jsonArray = jsonObject.getJSONArray("Data");
		if (jsonArray == null || jsonArray.size() == 0) return null;
		JSONArray array = jsonArray.getJSONArray(0);
		Quote quote = null;
		List<Quote> list = new ArrayList<Quote>();
		for (int i = 0; i < array.size(); i++) {
			JSONArray data = array.getJSONArray(i);
			quote = new Quote();
			quote.setCode(data.getString(0));
			quote.setName(data.getString(1));
			quote.setOpen(data.getBigDecimal(2));
			quote.setClose(data.getBigDecimal(3));
			quote.setHigh(data.getBigDecimal(4));
			quote.setLow(data.getBigDecimal(5));
			quote.setPrice(data.getBigDecimal(6));
			quote.setPreClose(data.getBigDecimal(7));
			quote.setLastclose(data.getBigDecimal(8));
			quote.setUpDown(data.getBigDecimal(9));
			quote.setType(data.getIntValue(10));
			quote.setPriceweight(data.getBigDecimal(11));
			quote.setDatetime(data.getString(12));
			list.add(quote);			
		}
		cacheMap.put("forex_quote_pic_data", list);
		return list;
	}
	
	
	public  List<Minute> getMinute(String code, int num, String startDatetime) throws Exception {
		String json = getDataJson("MINUTE_"+code+".json");
		if (StringUtils.isEmpty(json)) return null;
		json = json.replaceAll("\\(|\\)|;", "");
		JSONObject jsonObject = JSONObject.parseObject(json);
		if (jsonObject == null) return null;
		JSONArray jsonArray = jsonObject.getJSONArray("Data");
		if (jsonArray == null || jsonArray.size() == 0)	return null;
		JSONArray array = jsonArray.getJSONArray(0);
		List<Minute> list = new ArrayList<Minute>();
		for (int i = 0; i < array.size(); i++) {
			JSONArray data = array.getJSONArray(i);
			Minute minute = new Minute();
			minute.setDatetime(data.getString(0));
			minute.setPrice(data.getBigDecimal(1));
			minute.setAmount(data.getIntValue(2));
			minute.setVolume(data.getIntValue(3));
			minute.setAvePrice(data.getBigDecimal(4));
			minute.setLastClose(jsonArray.getBigDecimal(1));
			minute.setHigh(jsonArray.getBigDecimal(2));
			minute.setLow(jsonArray.getBigDecimal(3));
			minute.setPriceWeight(jsonArray.getBigDecimal(4));
			list.add(minute);
		}
		return list;
	}
	
	
}
