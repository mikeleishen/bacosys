package com.xinyou.util;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class QRCodeUtil {

	/**
	 * 生成QR码图片
	 * 
	 * @param content
	 *            QR码内容
	 * @param filePath
	 *            QR码保存文件夹
	 * @param fileName
	 *            QR码图片保存文件名
	 * @param suffix
	 *            QR图片文件格式(jpg, png等)
	 * @throws IOException 
	 */
	public static void Encode(String content, String filePath, String fileName, String suffix) throws IOException {
		int lineweight = 4;

		Qrcode qrcode = new Qrcode();
		// 容错率 L(%7) M(15%) Q(25%) H(30%)
		qrcode.setQrcodeErrorCorrect('M');
		// 字元模式,N A 或 其它, A是英文,N是數字,其它是8 byte
		qrcode.setQrcodeEncodeMode('B');
		// 可使用的字串长短跟所设定的QrcodeVersion有关,越大可设定的字越多
		// 0-40,0是自動
		qrcode.setQrcodeVersion(0);

		byte[] d = content.getBytes();

		boolean[][] b = qrcode.calQrcode(d);

		// createGraphics
		BufferedImage bi = new BufferedImage(b.length * lineweight + 4,
				b.length * lineweight + 4, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, bi.getWidth(), bi.getHeight());
		g.setColor(Color.BLACK);
		if (d.length > 0 && d.length < 1818) {
			for (int i = 0; i < b.length; i++) {
				for (int j = 0; j < b.length; j++) {
					if (b[j][i]) {
						g.fillRect(j * lineweight + 2, i * lineweight + 2,
								lineweight, lineweight);
					}
				}
			}
		}
		g.dispose();
		bi.flush();

		if (!(new File(filePath).isDirectory()))
			new File(filePath).mkdirs();
		
		File f = new File(filePath, fileName+"."+suffix);
		ImageIO.write(bi, suffix, f);
	}

	/**
	 * 生成QR码图片（默认png格式）
	 * 
	 * @param content
	 *            QR码内容
	 * @param filePath
	 *            QR码保存文件夹
	 * @param fileName
	 *            QR码图片保存文件名
	 * @throws IOException 
	 */
	public static void Encode(String content, String filePath, String fileName) throws IOException {
		Encode(content, filePath, fileName, "png");
	}
	 
}
