package com.xinyou.util;

import java.awt.Color;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QRCode_Demo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
//		QRCode q= new QRCode("WIFI:S:haigesi;T:WEP;P:051255110777*;;", 120, 0);
		QRCode q= new QRCode("http://www.bacobook.com", 120, 0);
		q.setErrorCorrect('H');
//		q.setLineWeight(0);
//		q.setHeaderHeight(16);
//		q.setHeaderContent("123123123123");
//		q.setHeaderFontSize(12);
//		q.setFooterHeight(16);
//		q.setFooterContent("123123123123");
//		q.setFoojterFontSize(12);
		q.saveImage("C:/Users/mikeshen/Desktop/QR", "qrcode-"+sdf.format(new Date()), "png");
		q.saveImageColourful("C:/Users/mikeshen/Desktop/QR", "qrcode-"+sdf.format(new Date()), "png");
		q.saveImageColourful("C:/Users/mikeshen/Desktop/QR", "qrcode-"+sdf.format(new Date()), "png", new Color(30,50,80));
		q.saveImageColourful("C:/Users/mikeshen/Desktop/QR", "qrcode-"+sdf.format(new Date()), "png", Color.RED, Color.GREEN, Color.BLUE);
	}

}
