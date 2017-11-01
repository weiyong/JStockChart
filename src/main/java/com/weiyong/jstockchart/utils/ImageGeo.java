package com.weiyong.jstockchart.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class ImageGeo {
	
	
	private static Font font = new Font("大河马字体", Font.PLAIN, 12);

	/**
	 * 在源图片上设置水印文字
	 * 
	 * @param color
	 *            字体颜色(例如：黑色--Color.BLACK)
	 * @param inputWords
	 *            输入显示在图片上的文字
	 * @param x
	 *            文字显示起始的x坐标
	 * @param y
	 *            文字显示起始的y坐标
	 * @throws IOException
	 */
	public static void alphaWords2Image(BufferedImage image, Color color, String inputWords, int x, int y) throws IOException {
		try {
			// 创建java2D对象
			Graphics2D g2d = image.createGraphics();
			// 用源图像填充背景
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null, null);
			// 设置透明度
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F);
			g2d.setComposite(ac);
			// 设置文字字体名称、样式、大小
			g2d.setFont(font);
			g2d.setColor(color);// 设置字体颜色
			g2d.drawString(inputWords, x, y); // 输入水印文字及其起始x、y坐标
			g2d.dispose();//生成图片
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
