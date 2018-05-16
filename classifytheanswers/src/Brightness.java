import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Brightness {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedImage img = ImageIO.read(new File("D:\\My Imps\\Deans Data\\errors\\brightnes\\3.bmp"));
		for (int a = 0; a < img.getWidth(); a++) {
			for (int b = 0; b < img.getHeight(); b++) {
				System.out.println(a + ", " + b + "\t" + brightnessRange(img.getRGB(a, b)) + "\t" + brightnessBoolean(img.getRGB(a, b)));
			}
		}
		
	}

	private static int brightnessBoolean(int rgb) {
		int brightness;
		int r, g, b;
		r = (rgb >> 16) & 0xff; // red component
		g = (rgb >> 8) & 0xff; // green component
		b = rgb & 0xff; // blue component

		brightness = (int) Math.sqrt(r * r * .241 + g * g * .691 + b * b * .068);
	
		if (brightness >= 0 && brightness <= 140)
			return 1; // for the dark
		else return 0;

	}

	private static double brightnessRange(int rgb) {
		double brightness;
		int r, g, b;
		r = (rgb >> 16) & 0xff; // red component
		g = (rgb >> 8) & 0xff; // green component
		b = rgb & 0xff; // blue component

		brightness =  Math.sqrt(r * r * .241 + g * g * .691 + b * b * .068);
	
		return brightness;
	}

}
