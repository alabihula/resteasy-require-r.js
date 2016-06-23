package com.server.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.codec.binary.Base64;

/**
 * 验证码生成器类，可生成数字、大写、小写字母及三者混合类型的验证码。 支持自定义验证码字符数量； 支持自定义验证码图片的大小； 支持自定义需排除的特殊字符；
 * 支持自定义干扰线的数量； 支持自定义验证码图文颜色
 * 
 * @author minxing
 * @version 1.0
 */
public class SecurityImage {
	private static Random random = new Random();

	/*
	 * 获得字体
	 */
	public Font getFont(int fSize) {
		return new Font("Fixedsys", Font.CENTER_BASELINE, fSize);
	}

	 /** 获取随机颜色 */  
    private Color getRandomColor() {  
        int r = getRandomNumber(255);  
        int g = getRandomNumber(255);  
        int b = getRandomNumber(255);  
        return new Color(r, g, b);  
    }  
  
    /** 获取随机数 */  
    private int getRandomNumber(int number) {  
        return random.nextInt(number);  
    } 
	
	/*
	 * 绘制干扰线
	 */
	public void drowLine(Graphics g, int width, int height) {
		int xs = getRandomNumber(width);  
        int ys = getRandomNumber(height);  
        int xe = xs + getRandomNumber(width / 8);  
        int ye = ys + getRandomNumber(height / 8);  
        g.setColor(getRandomColor());  
        g.drawLine(xs, ys, xe, ye);  
	}

	/*
	 * 获得颜色
	 */
	public Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/**
	 * 生成验证码图片
	 * 
	 * @param securityCode
	 *            验证码字符
	 * @return BufferedImage 图片
	 */
	public BufferedImage createImage(String securityCode) {
		// 验证码长度
		int codeLength = securityCode.length();
		// 字体大小
		int fSize = 35;
		int fWidth = fSize;
		// 图片宽度
		int width = (codeLength-4) * fWidth;
		// 图片高度
		int height = fSize * 2;
		// 图片
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		// 设置背景色
		g.setColor(Color.WHITE);
		// 填充背景
		g.fillRect(0, 0, width, height);
		// 设置边框颜色
		g.setColor(Color.LIGHT_GRAY);
		// 边框字体样式
		g.setFont(new Font("Arial", Font.BOLD, height - 2));
		// g.setFont(getFont());
		// 绘制边框
		g.drawRect(0, 0, width - 1, height - 1);

		// 绘制干扰线
		for (int i = 0; i <= 100; i++) {
			drowLine(g, width, height);
		}

		// 绘制验证码
		int codeY = height - 20;
		// 设置字体颜色和样式
		g.setFont(getFont(fSize));
		for (int i = 0; i < codeLength; i++) {
			g.setColor(getRandomColor());
			g.drawString(String.valueOf(securityCode.charAt(i)), i * 16 + 5, codeY);
		}
		// 关闭资源
		g.dispose();
		return image;
	}

	/**
	 * 返回验证码图片的流格式
	 * 
	 * @param securityCode
	 *            验证码
	 * @return ByteArrayInputStream 图片流
	 */
	public String getImageAsInputStream(String securityCode) {
		BufferedImage image = createImage(securityCode);
		// 创建储存图片二进制流的输出流
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 创建ImageOutputStream流
		ImageOutputStream imageOutputStream = null;
		InputStream in = null;
		try {
			imageOutputStream = ImageIO.createImageOutputStream(baos);
			// 将二进制数据写进ByteArrayOutputStream
			ImageIO.write(image, "jpg", imageOutputStream);
			in = new ByteArrayInputStream(baos.toByteArray());
		} catch (IOException e) {

		} finally {
		}
		return getImageBinary(in);
	}

	public String getImageBinary(InputStream in) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(in);
			int width = bi.getWidth(); // 得到源图宽
			int height = bi.getHeight(); // 得到源图长
			System.out.println(width + "---" + height);
			width = (width) * 7 / 10;
			height = (height) * 7 / 10;

			Image image = bi.getScaledInstance(width, height, Image.SCALE_DEFAULT);
			BufferedImage biNew = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics g = biNew.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(biNew, "JPEG", baos);
			byte[] bytes = baos.toByteArray();

			return Base64.encodeBase64String(bytes).trim();
		} catch (Exception e) {
		}
		return null;
	}

	public static void main(String[] args) {
		String securityCode = SecurityCode.getSecurityCode().replaceAll("", " ");
		String base64Str = new SecurityImage().getImageAsInputStream(securityCode);
		System.out.println("data:image/gif;base64," + base64Str);
	}
}