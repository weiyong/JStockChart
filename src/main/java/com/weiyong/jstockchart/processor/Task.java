package com.weiyong.jstockchart.processor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task {

	public static void main(String[] args) {
		final DrawChart drawChart = new DrawChart();
		ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
		service.scheduleWithFixedDelay(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					drawChart.chart();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, 0, 5, TimeUnit.MINUTES);
	}

}
