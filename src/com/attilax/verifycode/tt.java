/**
 * @author attilax 老哇的爪子
	@since  2014-6-13 上午10:18:39$
 */
package com.attilax.verifycode;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import com.attilax.net.requestImp;
import com.thoughtworks.xstream.persistence.FileStreamStrategy;

/**
 * @author attilax 老哇的爪子
 *@since 2014-6-13 上午10:18:39$
 */
public class tt {
	public static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * @author attilax 老哇的爪子
	 * @since 2014-6-13 上午10:18:39$
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		t1();
		
		// attilax 老哇的爪子 上午10:18:39 2014-6-13
		JspWriter out = new JspWriterImp(5, true);
		PageContext pageContext = new PageContextImp();
		HttpServletResponse response = new HttpServletResponseImp();
		HttpServletRequest request = new requestImp();
		HttpSession session = new HttpSessionImp();

		try {
			out.clear();// ????resin???????tomacat??????
			out = pageContext.pushBody();
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);

			int width = 60, height = 20;
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);

			Graphics g = image.getGraphics();
			Random random = new Random();

			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);

			g.setFont(new Font("Times New Roman", Font.PLAIN, 18));

			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			String sRand = "";
			for (int i = 0; i < 4; i++) {
				String rand = String.valueOf(random.nextInt(10));
				sRand += rand;

				g.setColor(new Color(20 + random.nextInt(110), 20 + random
						.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(rand, 13 * i + 6, 16);
			}
			// ??????SESSION
			session.setAttribute("rand", sRand);

			g.dispose();

			// ImageIO.write(image, "JPEG", response.getOutputStream());
			ImageIO.write(image, "JPEG", new FileOutputStream("c:\\j.jpg"));
			System.out.println("--ok");

		} catch (Exception e) {
			System.out.println("--err");
			e.printStackTrace();
		}

	}
/**
 * 
@author attilax 老哇的爪子
	@since  2014-6-13 下午12:32:16$
 */
	private static void t1() {
		// attilax 老哇的爪子  上午10:54:26   2014-6-13 
		
	}
}

// attilax 老哇的爪子