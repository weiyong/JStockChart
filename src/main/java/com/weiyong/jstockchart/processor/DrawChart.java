package com.weiyong.jstockchart.processor;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.Range;
import org.jstockchart.JStockChartFactory;
import org.jstockchart.area.PriceArea;
import org.jstockchart.area.TimeseriesArea;
import org.jstockchart.area.VolumeArea;
import org.jstockchart.axis.TickAlignment;
import org.jstockchart.axis.logic.CentralValueAxis;
import org.jstockchart.axis.logic.LogicDateAxis;
import org.jstockchart.axis.logic.LogicNumberAxis;
import org.jstockchart.dataset.TimeseriesDataset;
import org.jstockchart.model.TimeseriesItem;
import org.jstockchart.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weiyong.jstockchart.model.Minute;
import com.weiyong.jstockchart.model.Quote;
import com.weiyong.jstockchart.service.ForexService;
import com.weiyong.jstockchart.utils.ImageGeo;
import com.weiyong.jstockchart.utils.StringUtils;

public class DrawChart {

	
	//最新价
	private double latestPrice = 0.0D;
	private static Color TITLE_COLOR = new Color(0, 0, 255);
	private static Color LATEST_PRICE_COLOR = new Color(0, 154, 0);
	private ForexService forexService = ForexService.getInstance();
	private  final Logger log = LoggerFactory.getLogger(DrawChart.class);
	

	public static void main(String[] args) throws Exception {
		final DrawChart drawChart = new DrawChart();
		drawChart.chart();
	}

	public void chart() throws Exception {
		String startDate = StringUtils.beginOfHour();
		String endDate = StringUtils.getNowDate();
		int num = StringUtils.subMinutes(startDate, endDate);
		log.info(" --  " + StringUtils.formatDate(endDate, StringUtils.PATTERN_ISO_DEFAULT_DATETIME) + "  ---------->  start...");
		List<Quote> quotes = forexService.getForexQuoteData();
		for (Quote quote : quotes) {
			createChart(quote.getCode().toUpperCase().trim(), quote.getName(), num, startDate, endDate);
		}
	}

	private void createChart(String code, String name, int num, String startDate, String endDate) throws Exception {
		String imageDir = "./min";
		File images = new File(StringUtils.getRealFilePath(imageDir));
		if (!images.exists()) {
			images.mkdir();
		}
		
		List<Minute> minutes = forexService.getMinute(code, num, startDate);
		if (minutes == null || minutes.isEmpty()) return;
		log.info(" --  " + name + "[" + code + "] ------>  start...");
	
		// 创建TimeseriesItem实例
		List<TimeseriesItem> data = getTimeseriesItem(minutes);

		// 创建SegmentedTimeline实例，表示时间区间"00:00-11:30，13:00-24:00"(即24小时中空缺了"11:31-12:59"这段时间)
		SegmentedTimeline timeline = new SegmentedTimeline(SegmentedTimeline.MINUTE_SEGMENT_SIZE, 1351, 89);
		timeline.setStartTime(SegmentedTimeline.firstMondayAfter1900() + 780 * SegmentedTimeline.MINUTE_SEGMENT_SIZE);

		// 创建TimeseriesDataset实例，时间间隔为1分钟
		TimeseriesDataset dataset = new TimeseriesDataset(org.jfree.data.time.Minute.class, 1, timeline, true);

		// 向dataset中加入TimeseriesItem的List
		dataset.addDataItems(data);

		// 创建逻辑价格坐标轴。指定中间价为 latestPrice(最新价)，显示9个坐标值，坐标值的格式为"0.0000"
		CentralValueAxis logicPriceAxis = new CentralValueAxis(new Double(latestPrice),
				new Range(dataset.getMinPrice().doubleValue(), dataset.getMaxPrice().doubleValue()), 9,
				new DecimalFormat("0.0000"));

		// 创建价格区域
		PriceArea priceArea = new PriceArea(logicPriceAxis);

		// 创建逻辑量坐标轴。显示5个坐标值，坐标值的格式为"0"
		LogicNumberAxis logicVolumeAxis = new LogicNumberAxis(
				new Range(dataset.getMinVolume().doubleValue(), dataset.getMaxVolume().doubleValue()), 5,
				new DecimalFormat("0"));

		// 创建量区域
		VolumeArea volumeArea = new VolumeArea(logicVolumeAxis);

		// 创建时序图区域
		TimeseriesArea timeseriesArea = new TimeseriesArea(priceArea, volumeArea,
				createlogicDateAxis(DateUtils.createDate(2017, 11, 01)));
/*		TimeseriesArea timeseriesArea = new TimeseriesArea(priceArea, volumeArea,
				createlogicDateAxis(DateUtils.createDate(Integer.parseInt(StringUtils.getYear(startDate)),
						Integer.parseInt(StringUtils.getMonth(startDate)),
						Integer.parseInt(StringUtils.getDay(startDate)))));*/
		// 不显示量图
		timeseriesArea.setVolumeWeight(0);

		JFreeChart jfreechart = JStockChartFactory.createTimeseriesChart(" ", dataset, timeline, timeseriesArea, false);
		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		BufferedImage image = jfreechart.createBufferedImage(770, 460, BufferedImage.TYPE_INT_RGB, info);
		
		ImageGeo.alphaWords2Image(image, TITLE_COLOR, "  " + StringUtils.getDate(endDate)+"/"+StringUtils.getTime(endDate) +"  "+ name + "[" + code + "]", 55, 20);
		ImageGeo.alphaWords2Image(image, LATEST_PRICE_COLOR, "最新价 " + latestPrice, 640, 20);
		
		FileOutputStream fos = new FileOutputStream(StringUtils.getRealFilePath(imageDir + File.separator + code + ".gif"));
		javax.imageio.ImageIO.write(image, "gif", fos);
		
		log.info(" --  " + name + "[" + code + "] ------>  finish!");
	}
	

	private List<TimeseriesItem> getTimeseriesItem(List<Minute> minutes) throws ParseException {
		latestPrice = minutes.get(minutes.size()-1).getPrice().divide(minutes.get(0).getPriceWeight()).doubleValue();
		List<TimeseriesItem> timeseriesItems = new ArrayList<TimeseriesItem>();
		TimeseriesItem timeseriesItem = null;
		for (Minute minute : minutes) {
			timeseriesItem = new TimeseriesItem(
					FastDateFormat.getInstance(StringUtils.PATTERN_ISO_ON_DATETIME).parse(minute.getDatetime()),
					//现价
					minute.getPrice().divide(minute.getPriceWeight()).doubleValue(),
					//振幅
					(minute.getHigh().divide(minute.getPriceWeight()).subtract(minute.getLow().divide(minute.getPriceWeight())))
									.divide(minute.getLastClose().divide(minute.getPriceWeight()), 4,RoundingMode.HALF_UP).doubleValue());
			timeseriesItems.add(timeseriesItem);
		}
		return timeseriesItems;
	}

	// 指定时期坐标轴中的逻辑坐标
	private static LogicDateAxis createlogicDateAxis(Date baseDate) {
		LogicDateAxis logicDateAxis = new LogicDateAxis(baseDate, new SimpleDateFormat("HH:mm"));
		logicDateAxis.addDateTick("08:00", TickAlignment.START);
		logicDateAxis.addDateTick("10:00");
		logicDateAxis.addDateTick("11:30", TickAlignment.END);
		logicDateAxis.addDateTick("13:00", TickAlignment.START);
		logicDateAxis.addDateTick("15:00");
		logicDateAxis.addDateTick("17:00");
		logicDateAxis.addDateTick("20:00");
		logicDateAxis.addDateTick("24:00");
		logicDateAxis.addDateTick("02:00");
		logicDateAxis.addDateTick("06:00", TickAlignment.END);
		return logicDateAxis;
	}

}
