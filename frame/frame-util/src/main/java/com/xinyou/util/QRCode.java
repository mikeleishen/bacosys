package com.xinyou.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

/**
 *  二维码图标产生 帮助类
 * */
public class QRCode {

	public QRCode() {
		this.imageType = BufferedImage.TYPE_INT_RGB;
		this.errorCorrect = 'M';
		this.encodeMode = 'B';
		this.version = 0;

		this.imageWidth = 0;
		this.lineWeight = 0;
		this.minPadding = 0;

		this.anchorLum = 20;
		this.anchorLumRange = 80;
	}

	/**
	 * 
	 * @param content
	 *            二维码内容
	 */
	public QRCode(String content) {
		this();
		this.content = content;
	}

	/**
	 * 
	 * @param content 二维码内容
	 * @param imageWidth 二维码图片的宽度(px)
	 */
	public QRCode(String content, int imageWidth) {
		this(content);
		this.imageWidth = imageWidth;
	}

	/**
	 * 
	 * @param content 二维码内容
	 * @param lineWeight 二维码线条的宽度(px)
	 * @param minPadding 二维码最小空白边距(px)
	 */
	public QRCode(String content, int lineWeight, int minPadding) {
		this(content);
		this.lineWeight = lineWeight;
		this.minPadding = minPadding;
	}

	/**
	 * 保存二维码图片
	 * 
	 * @param saveFolder 保存图片文件夹
	 * @param imageName 保存图片名称
	 * @param imageFormatName 保存图片格式
	 * @throws IOException
	 */
	public void saveImage(String saveFolder, String imageName, String imageFormatName) throws IOException {
		saveImage(saveFolder, imageName, imageFormatName, null);
	}

	/**
	 * 保存二维码图片，定位点随机颜色
	 * 
	 * @param saveFolder 保存图片文件夹
	 * @param imageName 保存图片名称
	 * @param imageFormatName 保存图片格式
	 * @throws IOException
	 */
	public void saveImageColourful(String saveFolder, String imageName, String imageFormatName) throws IOException {

		Color[] cs = new Color[] {
				new Color((int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum)),
				new Color((int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum)),
				new Color((int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum), (int) (Math.random() * anchorLumRange + anchorLum)) };

		saveImage(saveFolder, imageName, imageFormatName, cs);
	}

	/**
	 * 保存二维码图片，定位点指定颜色
	 * 
	 * @param saveFolder 保存图片文件夹
	 * @param imageName 保存图片名称
	 * @param imageFormatName 保存图片格式
	 * @param color 定位点颜色
	 * @throws IOException
	 */
	public void saveImageColourful(String saveFolder, String imageName, String imageFormatName, Color color) throws IOException {
		saveImage(saveFolder, imageName, imageFormatName, new Color[] { color, color, color });
	}

	/**
	 * 保存二维码图片，定位点指定颜色
	 * 
	 * @param saveFolder 保存图片文件夹
	 * @param imageName 保存图片名称
	 * @param imageFormatName 保存图片格式
	 * @param lefttop 左上角定位点颜色
	 * @param leftbottom 左下角定位点颜色
	 * @param righttop 右上角定位点颜色
	 * @throws IOException
	 */
	public void saveImageColourful(String saveFolder, String imageName, String imageFormatName, Color lefttop, Color leftbottom, Color righttop) throws IOException {
		saveImage(saveFolder, imageName, imageFormatName, new Color[] { lefttop, leftbottom, righttop });
	}

	private void saveImage(String saveFolder, String imageName, String imageFormatName, Color[] colors) throws IOException {
		Qrcode qrcode = new Qrcode();
		qrcode.setQrcodeErrorCorrect(this.errorCorrect);
		qrcode.setQrcodeEncodeMode(this.encodeMode);
		qrcode.setQrcodeVersion(this.version);

		byte[] contentBytes = content.getBytes("UTF-8");

		boolean[][] matrix = qrcode.calQrcode(contentBytes);

		int aPadding = minPadding;
		int aLineWeight = lineWeight == 0 ? 4 : lineWeight;
		int length = matrix.length;
		if (imageWidth > 0) {
			int aCodeWidth = imageWidth - minPadding * 2;
			aLineWeight = aCodeWidth / length;
			if (lineWeight > 0 && aLineWeight > lineWeight) {
				aLineWeight = lineWeight;
			}
			aCodeWidth = aLineWeight * length;
			aPadding = (imageWidth - aCodeWidth) / 2;
		} else {
			imageWidth = aLineWeight * length + aPadding * 2;
		}

		// 创建二维码
		BufferedImage bi = new BufferedImage(imageWidth, imageWidth + headerHeight + footerHeight, imageType);
		Graphics2D g = bi.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, bi.getWidth(), bi.getHeight());
		g.setColor(Color.BLACK);
		if (length > 0) {
			for (int y = 0; y < length; y++) {
				for (int x = 0; x < length; x++) {
					if (matrix[x][y]) {
						g.fillRect(x * aLineWeight + aPadding, y * aLineWeight + aPadding + headerHeight, aLineWeight, aLineWeight);
					}
				}
			}
		}

		// 修改定位点颜色
		if (colors != null && colors.length == 3) {
			g.setColor(colors[0]);
			g.fillRect(2 * aLineWeight + aPadding, 2 * aLineWeight + aPadding + headerHeight, aLineWeight * 3, aLineWeight * 3);
			g.setColor(colors[1]);
			g.fillRect(2 * aLineWeight + aPadding, (length - 5) * aLineWeight + aPadding + headerHeight, aLineWeight * 3, aLineWeight * 3);
			g.setColor(colors[2]);
			g.fillRect((length - 5) * aLineWeight + aPadding, 2 * aLineWeight + aPadding + headerHeight, aLineWeight * 3, aLineWeight * 3);
		}
		
		File logoFile = new File("/config/qrcode_logo.png");
		if(logoFile.exists()){
			 BufferedImage logoSrc = ImageIO.read(logoFile);
			 Image logo = logoSrc.getScaledInstance(aLineWeight*5, aLineWeight*5, Image.SCALE_DEFAULT);
			 g.drawImage(logo, aLineWeight + aPadding, aLineWeight + aPadding, null);
		}

		g.dispose();
		
		//hearder and footer

		if (headerHeight > 0) {
			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, headerFontSize);
			g = bi.createGraphics();
			g.setPaint(Color.BLACK);
			g.setFont(font);
			g.drawString(headerContent, minPadding + headerPosX, minPadding + headerPosY + headerFontSize);
			g.dispose();
		}

		if (footerHeight > 0) {
			Font font = new Font(Font.SANS_SERIF, Font.PLAIN, footerFontSize);
			g = bi.createGraphics();
			g.setPaint(Color.BLACK);
			g.setFont(font);
			g.drawString(footerContent, minPadding + footerPosX, minPadding + bi.getHeight() - footerHeight + footerPosY + footerFontSize);
			g.dispose();
		}

		bi.flush();

		if (!(new File(saveFolder).isDirectory()))
			new File(saveFolder).mkdirs();

		File f = new File(saveFolder, imageName + "." + imageFormatName);
		ImageIO.write(bi, imageFormatName, f);

		_savedFilePath = f.getAbsolutePath();
	}

	private String _savedFilePath;

	/**
	 * 获取二维码图片的绝对路径，在保存图片之前为空
	 * 
	 * @return 图片绝对路径
	 */
	public String getSavedFilePath() {
		return _savedFilePath;
	}

	/**
	 * 保存图片类型
	 * 
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_RGB TYPE_INT_RGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_ARGB TYPE_INT_ARGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_ARGB_PRE TYPE_INT_ARGB_PRE}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_BGR TYPE_INT_BGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_3BYTE_BGR TYPE_3BYTE_BGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_4BYTE_ABGR TYPE_4BYTE_ABGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_4BYTE_ABGR_PRE TYPE_4BYTE_ABGR_PRE}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_GRAY TYPE_BYTE_GRAY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_GRAY TYPE_USHORT_GRAY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_BINARY TYPE_BYTE_BINARY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_INDEXED TYPE_BYTE_INDEXED}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_555_RGB TYPE_USHORT_555_RGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_565_RGB TYPE_USHORT_565_RGB}
	 */
	private int imageType;
	/** 容错率：L(%7)、M(15%)、Q(25%)、H(30%) */
	private char errorCorrect;
	/** 字元模式：N、A 或 其它。A是英文，N是數字，其它是8 byte */
	private char encodeMode;
	/** 可使用的字串长短跟所设定的QrcodeVersion有关，越大可设定的字越多。0-40，0是自動 */
	private int version;

	/** 线宽，如果图片容纳不下该线宽的二维码，则将忽略此项 */
	private int lineWeight;
	/** 最小空白边距 */
	private int minPadding;
	/** 图片宽度。如果小于等于0，则自适应图片宽度 */
	private int imageWidth;

	/** 二维码内容 */
	private String content;

	// header
	private String headerContent;
	private int headerFontSize;
	private int headerHeight;
	private int headerPosX;
	private int headerPosY;
	// footer
	private String footerContent;
	private int footerFontSize;
	private int footerHeight;
	private int footerPosX;
	private int footerPosY;

	private int anchorLum;
	private int anchorLumRange;

	public int getImageType() {
		return imageType;
	}

	/**
	 * 设置保存图片类型
	 * 
	 * @param imageType
	 *            type of Image
	 * 
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_RGB TYPE_INT_RGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_ARGB TYPE_INT_ARGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_ARGB_PRE TYPE_INT_ARGB_PRE}
	 * @see {@link java.awt.image.BufferedImage#TYPE_INT_BGR TYPE_INT_BGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_3BYTE_BGR TYPE_3BYTE_BGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_4BYTE_ABGR TYPE_4BYTE_ABGR}
	 * @see {@link java.awt.image.BufferedImage#TYPE_4BYTE_ABGR_PRE TYPE_4BYTE_ABGR_PRE}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_GRAY TYPE_BYTE_GRAY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_GRAY TYPE_USHORT_GRAY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_BINARY TYPE_BYTE_BINARY}
	 * @see {@link java.awt.image.BufferedImage#TYPE_BYTE_INDEXED TYPE_BYTE_INDEXED}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_555_RGB TYPE_USHORT_555_RGB}
	 * @see {@link java.awt.image.BufferedImage#TYPE_USHORT_565_RGB TYPE_USHORT_565_RGB}
	 */
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	public char getErrorCorrect() {
		return errorCorrect;
	}

	/**
	 * 设置容错率：L(%7)、M(15%)、Q(25%)、H(30%)
	 * 
	 * @param errorCorrect
	 */
	public void setErrorCorrect(char errorCorrect) {
		this.errorCorrect = errorCorrect;
	}

	public char getEncodeMode() {
		return encodeMode;
	}

	/**
	 * 设置字元模式：N、A 或 其它。A是英文，N是數字，其它是8 byte
	 * 
	 * @param encodeMode
	 */
	public void setEncodeMode(char encodeMode) {
		this.encodeMode = encodeMode;
	}

	public int getVersion() {
		return version;
	}

	/**
	 * 设置可使用的字串长短跟所设定的QrcodeVersion有关，越大可设定的字越多。0-40，0是自動
	 * 
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	public int getLineWeight() {
		return lineWeight;
	}

	/**
	 * 设置线宽，如果图片容纳不下该线宽的二维码，则将忽略此项
	 * 
	 * @param lineWeight
	 */
	public void setLineWeight(int lineWeight) {
		this.lineWeight = lineWeight;
	}

	public int getMinPadding() {
		return minPadding;
	}

	/**
	 * 设置最小空白边距
	 * 
	 * @param minPadding
	 */
	public void setMinPadding(int minPadding) {
		this.minPadding = minPadding;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	/**
	 * 设置图片宽度。如果小于等于0，则自适应图片宽度
	 * 
	 * @param imageWidth
	 */
	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getContent() {
		return content;
	}

	/**
	 * 设置二维码内容
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public String getHeaderContent() {
		return headerContent;
	}

	public void setHeaderContent(String headerContent) {
		this.headerContent = headerContent;
	}

	public int getHeaderFontSize() {
		return headerFontSize;
	}

	public void setHeaderFontSize(int headerFontSize) {
		this.headerFontSize = headerFontSize;
	}

	public int getHeaderHeight() {
		return headerHeight;
	}

	public void setHeaderHeight(int headerHeight) {
		this.headerHeight = headerHeight;
	}

	public int getHeaderPosX() {
		return headerPosX;
	}

	public void setHeaderPosX(int headerPosX) {
		this.headerPosX = headerPosX;
	}

	public int getHeaderPosY() {
		return headerPosY;
	}

	public void setHeaderPosY(int headerPosY) {
		this.headerPosY = headerPosY;
	}

	public String getFooterContent() {
		return footerContent;
	}

	public void setFooterContent(String footerContent) {
		this.footerContent = footerContent;
	}

	public int getFooterFontSize() {
		return footerFontSize;
	}

	public void setFooterFontSize(int footerFontSize) {
		this.footerFontSize = footerFontSize;
	}

	public int getFooterHeight() {
		return footerHeight;
	}

	public void setFooterHeight(int footerHeight) {
		this.footerHeight = footerHeight;
	}

	public int getFooterPosX() {
		return footerPosX;
	}

	public void setFooterPosX(int footerPosX) {
		this.footerPosX = footerPosX;
	}

	public int getFooterPosY() {
		return footerPosY;
	}

	public void setFooterPosY(int footerPosY) {
		this.footerPosY = footerPosY;
	}
}