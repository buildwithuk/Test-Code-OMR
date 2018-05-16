import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

// To classify the Errors
public class Test {

	public static void main(String[] args) throws IOException {
		System.out.println("Classify the errors...");
		int chemistry =0, english = 0, physics = 0, maths = 0;
		BufferedImage img = ImageIO.read(new File("D:\\My Imps\\Deans Data\\12sept2012\\B_0012_7.jpg"));
		Scanner scan = new Scanner(new File("D:\\My Imps\\Deans Data\\July31\\31-7-2012\\template2.txt"));
		ArrayList<int[]> list = new ArrayList<int[]>();
		final double  HEIGHT = 10.3;
		final double  WIDTH = 71.5;
		int pix_x =  (int) ((WIDTH/100)*img.getWidth());
		int pix_y =  (int) ((HEIGHT/100)*img.getHeight());
		int[][] data = new int[pix_y][img.getWidth() - pix_x +1];
		int count = 0;
		int black = 0;
		int required_edge = 0;
		for (int a = 0; a < pix_y; a++) {
			black = 0;
			for (int b = pix_x; b < img.getWidth(); b++) {
				if (Brightness(img.getRGB(b, a)) == 1) {
					data[a][count] = 1;
					black++;
				}
			}
			data[a][img.getWidth() - pix_x] = black;
			count++;
		}
		black = data[0][img.getWidth() - pix_x];
		required_edge = 0;
		for (int a = 0; a < data.length; a++)
			if (black < data[a][img.getWidth() - pix_x]) {
				required_edge = a;
				black = data[a][img.getWidth() - pix_x];
			}
		for (int a = pix_x; a < img.getWidth(); a++) {
			if (Brightness(img.getRGB(a, required_edge))==1)
				a = putToArrayList(a, required_edge, img, list);
		}
		Point p1 = new Point();
		p1.y = required_edge;
		int temp = 0;
		for (int a = 0; a < list.size(); a++) {
			int[] p = list.get(a);
			if (temp < p[2]) {
				temp = p[2];
				p1.x = p[0];
			}
		}
		int true_edge = required_edge;
		count = 0;
		int[][] collected_data = new int[3][2];
		Outer:
			for (int a = required_edge; a < (required_edge+3); a++) {
				for  (int b = pix_x; b < img.getWidth(); b++) {
					if (Brightness(img.getRGB(b, a))==1) {
						collected_data[count][0] = a;
						collected_data[count][1] = b;
						count++;
						break;
					}
					else continue;
				}
			}
		temp = collected_data[0][1];
		int edge = collected_data[0][0];
		for (int a = 0; a < collected_data.length; a++) {
			if (temp > collected_data[a][1]) {
				edge = collected_data[a][0];
				temp = collected_data[a][1];
			}
		}
		int edgex = temp; int edgey = edge;
		temp += 44;
		edge += 137;
	
		int[] roll = {-1, -1, -1, -1};
		// First digit
		count = 0;
		black = 0;
		int no = 0;
		int mark = -1;
		int white = 0;
		int tempx = temp;
		int tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count*56;
			for (int b = tempy; b < (tempy+ 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)	
					if (Brightness(img.getRGB(a, b))==1)
						black++;
					else if  (Brightness(img.getRGB(a, b))==0)
					white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no>1) {		
				System.out.println("Error in the roll number place 1");
				break;
			}
			count++;
		}
		roll[0] = mark;
		mark = -1;

		no = 0;
	
		// Second digit
	
		count = 0;
		black = 0;
		tempx = temp + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count*56;
			for (int b = tempy; b < (tempy+ 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)
					if (Brightness(img.getRGB(a, b))==1)
						black++;
					else if  (Brightness(img.getRGB(a, b))==0)
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no>1) {		
				System.out.println("Error in the roll number place 2");
				break;
			}
			count++;
		}
		roll[1] = mark;
		mark = -1;
		no = 0;
	
		// third digit
		count = 0;
		black = 0;
		tempx = temp + 127 + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count*56;
			for (int b = tempy; b < (tempy+ 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)				
					if (Brightness(img.getRGB(a, b))==1)
						black++;
					else if  (Brightness(img.getRGB(a, b))==0)
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no>1) {		
				System.out.println("Error in the roll number place 3");
				break;
			}
			count++;
		}
		roll[2] = mark;
		mark = -1;
		no = 0;
			
		// fourth
		count = 0;
		black = 0;
		tempx = temp + 127 + 127 + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count*56;
			for (int b = tempy; b < (tempy+ 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)		
					if (Brightness(img.getRGB(a, b))==1)
						black++;
					else if  (Brightness(img.getRGB(a, b))==0)
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no>1) {		
				System.out.println("Error in the roll number place 4");
				break;
			}
			count++;
		}
		roll[3] = mark;
		mark = 0;
		no = 0;
		if (roll[0]==-1 || roll[1]==-1 || roll[2]==-1 || roll[3]==-1 ) {
			
			System.out.println("Error in full roll no");
			System.out.println("======================================================");
		}
		else {
			System.out.println(String.format("%d%d%d%d", roll[0], roll[1], roll[2], roll[3]));
		}

		int choice = 0;
		int answerx = edgex - 1397;
		int answery = edgey + 907 ;
	
		count = 0;

		tempx = answerx;
		tempy = answery;
		int ans = 0;
		ans = scan.nextInt();
		double[] avg = new double[4];
		int[] whitespace = new int[4];
		double tempans = 0;
		int no_of_white = 0;
		for (int a = 0; a < 4; a++) {
			tempx = answerx + a*65;
				for (int b = tempy; b < (tempy + 35); b++) {
					for (int c = tempx; c < (tempx + 20); c++) {
						
						avg[a] += ansBrightness(img.getRGB(c, b));
						if (Brightness(img.getRGB(c,b))==0)
							whitespace[a]++;
					}
				}	
				avg[a] = avg[a]/avg.length;	
		}
		System.out.println("Question no " + (count+1));
		System.out.println("Answer " + ans);
		for (int a = 0; a < whitespace.length; a++) {
			System.out.println(whitespace[a] + "\t" + avg[a]);
			if (whitespace[a] >= 550)
				no_of_white++;
		}
		if (no_of_white != 3) {
			System.out.println("Marked all or none");// Can be the source for errors
			System.out.println("======================================================");
		}
	//	else if (no_of_white == 4) {
	//		System.out.println("Marked none");
	//		System.out.println("======================================================");
	//	}
		else {
			choice = 0;
			tempans = avg[0];
			for (int a = 0; a < avg.length; a++) {
				System.out.println(avg[a]);
				if (tempans > avg[a]) {
					choice = a;
					tempans = avg[a];
				}
			}
			if (choice == ans) {
				System.out.println("Choice: " + choice);
				System.out.println("Added to english");
				english++;
				System.out.println("======================================================");
			}
				
			else  {
				System.out.println("Choice: " + choice);
				System.out.println("Marked wrong");
				System.out.println("======================================================");
			}
		}
		count++;
		avg = new double[4];
		whitespace = new int[4]; 
		no_of_white = 0;
		tempx = answerx;
		tempy = answery + 66;
		while (count < 25) {
			ans = scan.nextInt();
			for (int a = 0; a < 4; a++) {
				tempx = answerx + a*65;
				tempy = answery + count*80;
			
				for (int b = tempy; b < (tempy + 35); b++) {
					for (int c = tempx; c < (tempx + 20); c++) {
						avg[a] += ansBrightness(img.getRGB(c, b));
						if (Brightness(img.getRGB(c,b))==0)
							whitespace[a]++;
						
					}
				}	
				avg[a] = avg[a]/avg.length;	
			}
			System.out.println("Question no " + (count+1));
			System.out.println("Answer " + ans);
			for (int a = 0; a < whitespace.length; a++) {
				System.out.println(whitespace[a] + "\t" + avg[a]);
				if (whitespace[a] >= 550)
					no_of_white++;
			}
			if (no_of_white != 3) {
				System.out.println("Marked all or none");// Can be the source for errors
				System.out.println("======================================================");
			}
		//	else if (no_of_white == 4) {
		//		System.out.println("Marked none");
		//		System.out.println("======================================================");
		//	}
			else {
				choice = 0;
				tempans = avg[0];
				for (int a = 0; a < avg.length; a++) {
					System.out.println(avg[a]);
					if (tempans > avg[a]) {
						choice = a;
						tempans = avg[a];
					}
				}
				if (choice == ans) {
					System.out.println("Choice: " + choice);
					System.out.println("Added to english");
					english++;
					System.out.println("======================================================");
				}
					
				else  {
					System.out.println("Choice: " + choice);
					System.out.println("Marked wrong");
					System.out.println("======================================================");
				}
			}
			count++;
			avg = new double[4];
			whitespace = new int[4];
			no_of_white = 0;
		}
		avg = new double[4];
		whitespace = new int[4];
		no_of_white = 0;
		tempy = answery;
		ans = scan.nextInt();
		for (int a = 0; a < 4; a++) {
			tempx = answerx + a*65 + 480;
			for (int b = tempy; b < (tempy + 35); b++) {
				for (int c = tempx; c < (tempx + 20); c++) {
					avg[a] += ansBrightness(img.getRGB(c, b));
					if (Brightness(img.getRGB(c,b))==0)
						whitespace[a]++;
				}
			}	
			avg[a] = avg[a]/avg.length;	
		}
		System.out.println("Question no " + (count+1));
		System.out.println("Answer " + ans);
		for (int a = 0; a < whitespace.length; a++) {
			System.out.println(whitespace[a]);
			if (whitespace[a] >= 550)
				no_of_white++;
		}
		if (no_of_white != 3) {
			System.out.println("Marked all or none");// Can be the source for errors
			System.out.println("======================================================");
		}
	//	else if (no_of_white == 4) {
	//		System.out.println("Marked none");
	//		System.out.println("======================================================");
	//	}
		else {
			choice = 0;
			tempans = avg[0];
			for (int a = 0; a < avg.length; a++) {
				System.out.println(whitespace[a] + "\t" + avg[a]);
				if (tempans > avg[a]) {
					choice = a;
					tempans = avg[a];
				}
			}
			if (choice == ans) {
				System.out.println("Choice: " + choice);
				System.out.println("Added to maths");
				maths++;
				System.out.println("======================================================");
			}
				
			else  {
				System.out.println("Choice: " + choice);
				System.out.println("Marked wrong");
				System.out.println("======================================================");
			}
		}
	
		count++;
		avg = new double[4];
		whitespace = new int[4];
		no_of_white = 0;
		int count_x = 1;
		while (count < 50) {
			ans = scan.nextInt();
			for (int a = 0; a < 4; a++) {
				tempx = answerx + a*65+ 480;
				tempy = answery + count_x*80;
				for (int b = tempy; b < (tempy + 35); b++) {
					for (int c = tempx; c < (tempx + 20); c++) {
						avg[a] += ansBrightness(img.getRGB(c, b));
						if (Brightness(img.getRGB(c,b))==0)
							whitespace[a]++;
					}
				}	
				avg[a] = avg[a]/avg.length;	
			}
			System.out.println("Question no " + (count+1));
			System.out.println("Answer " + ans);
			for (int a = 0; a < whitespace.length; a++) {
				System.out.println(whitespace[a]);
				if (whitespace[a] >= 550)
					no_of_white++;
			}
			if (no_of_white != 3) {
				System.out.println("Marked all or none");// Can be the source for errors
				System.out.println("======================================================");
			}
		//	else if (no_of_white == 4) {
		//		System.out.println("Marked none");
		//		System.out.println("======================================================");
		//	}
			else {
				choice = 0;
				tempans = avg[0];
				for (int a = 0; a < avg.length; a++) {
					System.out.println(whitespace[a] + "\t" + avg[a]);
					if (tempans > avg[a]) {
						choice = a;
						tempans = avg[a];
					}
				}
				if (choice == ans) {
					System.out.println("Choice: " + choice);
					System.out.println("Added to maths");
					maths++;
					System.out.println("======================================================");
				}
					
				else  {
					System.out.println("Choice: " + choice);
					System.out.println("Marked wrong");
					System.out.println("======================================================");
				}
			}
			avg = new double[4];
			whitespace = new int[4];
			no_of_white = 0;
			count++;
			count_x++;
		}
		avg = new double[4];
		whitespace = new int[4];
		ans = scan.nextInt();
		tempy = answery;
		for (int a = 0; a < 4; a++) {
		
			tempx = answerx + a*65 + 480 + 480;
		
			for (int b = tempy; b < (tempy + 35); b++) {
				for (int c = tempx; c < (tempx + 20); c++) {
					avg[a] += ansBrightness(img.getRGB(c, b));
					if (Brightness(img.getRGB(c,b))==0)
						whitespace[a]++;
				}
			}	
			avg[a] = avg[a]/avg.length;	
		}
		System.out.println("Question no " + (count+1));
		System.out.println("Answer " + ans);
		for (int a = 0; a < whitespace.length; a++) {
			System.out.println(whitespace[a]);
			if (whitespace[a] >= 550)
				no_of_white++;
		}
		if (no_of_white != 3) {
			System.out.println("Marked all or none");// Can be the source for errors
			System.out.println("======================================================");
		}
	//	else if (no_of_white == 4) {
	//		System.out.println("Marked none");
	//		System.out.println("======================================================");
	//	}
		else {
			choice = 0;
			tempans = avg[0];
			for (int a = 0; a < avg.length; a++) {
				System.out.println(whitespace[a] + "\t" + avg[a]);
				if (tempans > avg[a]) {
					choice = a;
					tempans = avg[a];
				}
			}
			if (choice == ans) {
				System.out.println("Choice: " + choice);
				System.out.println("Added to physics");
				physics++;
				System.out.println("======================================================");
			}
				
			else  {
				System.out.println("Choice: " + choice);
				System.out.println("Marked wrong");
				System.out.println("======================================================");
			}
		}
		
		count++;
		avg = new double[4];
		whitespace = new int[4];
		no_of_white = 0;
		count_x = 1;
		while (count < 75) {
			ans = scan.nextInt();
			for (int a = 0; a < 4; a++) {
				tempx = answerx + a*65+ 480 + 480;
				tempy = answery + count_x*80;
				for (int b = tempy; b < (tempy + 35); b++) {
					for (int c = tempx; c < (tempx + 20); c++) {
						avg[a] += ansBrightness(img.getRGB(c, b));
						if (Brightness(img.getRGB(c,b))==0)
							whitespace[a]++;
					}
				}	
				avg[a] = avg[a]/avg.length;	
			}
			System.out.println("Question no " + (count+1));
			System.out.println("Answer " + ans);
			for (int a = 0; a < whitespace.length; a++) {
				System.out.println(whitespace[a]);
				if (whitespace[a] >= 550)
					no_of_white++;
			}
			if (no_of_white != 3) {
				System.out.println("Marked all or none");// Can be the source for errors
				System.out.println("======================================================");
			}
		//	else if (no_of_white == 4) {
		//		System.out.println("Marked none");
		//		System.out.println("======================================================");
		//	}
			else {
				choice = 0;
				tempans = avg[0];
				for (int a = 0; a < avg.length; a++) {
					System.out.println(whitespace[a] + "\t" + avg[a]);
					if (tempans > avg[a]) {
						choice = a;
						tempans = avg[a];
					}
				}
				if (choice == ans) {
					System.out.println("Choice: " + choice);
					System.out.println("Added to physics");
					physics++;
					System.out.println("======================================================");
				}
					
				else  {
					System.out.println("Choice: " + choice);
					System.out.println("Marked wrong");
					System.out.println("======================================================");
				}
			}
			avg = new double[4];
			whitespace = new int[4];
			no_of_white = 0;
			count++;
			count_x++;
		}
		avg = new double[4];
		whitespace = new int[4];
		choice = 0;
		ans = scan.nextInt();
		tempy = answery;
		for (int a = 0; a < 4; a++) {
			tempx = answerx + a*65 + 480 + 480 + 480;
			black = 0;
			white = 0;
			for (int b = tempy; b < (tempy + 35); b++) {
				for (int c = tempx; c < (tempx + 20); c++) {
					avg[a] += ansBrightness(img.getRGB(c, b));
					if (Brightness(img.getRGB(c,b))==0)
						whitespace[a]++;
				}
			}	
			avg[a] = avg[a]/avg.length;	
		}
		System.out.println("Question no " + (count+1));
		System.out.println("Answer " + ans);
		for (int a = 0; a < whitespace.length; a++) {
			System.out.println(whitespace[a] + "\t" + avg[a]);
			if (whitespace[a] >= 550)
				no_of_white++;
		}
		if (no_of_white != 3) {
			System.out.println("Marked all or none");// Can be the source for errors
			System.out.println("======================================================");
		}
	//	else if (no_of_white == 4) {
	//		System.out.println("Marked none");
	//		System.out.println("======================================================");
	//	}
		else {
			choice = 0;
			tempans = avg[0];
			for (int a = 0; a < avg.length; a++) {
				System.out.println(avg[a]);
				if (tempans > avg[a]) {
					choice = a;
					tempans = avg[a];
				}
			}
			if (choice == ans) {
				System.out.println("Choice: " + choice);
				System.out.println("Added to chemistry");
				chemistry++;
				System.out.println("======================================================");
			}
				
			else  {
				System.out.println("Choice: " + choice);
				System.out.println("Marked wrong");
				System.out.println("======================================================");
			}
		}
		avg = new double[4];
		whitespace = new int[4];
		no_of_white = 0;
		count++;
		count_x = 1;

		while (count < 100) {
			ans = scan.nextInt();
			for (int a = 0; a < 4; a++) {
				tempx = answerx + a*65+ 480 + 480 + 480;
				tempy = answery + count_x*80;
			
				for (int b = tempy; b < (tempy + 35); b++) {
					for (int c = tempx; c < (tempx + 20); c++) {
						avg[a] += ansBrightness(img.getRGB(c, b));
						if (Brightness(img.getRGB(c,b))==0)
							whitespace[a]++;
					}
				}	
				avg[a] = avg[a]/avg.length;	
			}
			System.out.println("Question no " + (count+1));
			System.out.println("Answer " + ans);
			for (int a = 0; a < whitespace.length; a++) {
				System.out.println(whitespace[a] + "\t" + avg[a]);
				if (whitespace[a] >= 550)
					no_of_white++;
			}
			if (no_of_white != 3) {
				System.out.println("Marked all or none");// Can be the source for errors
				System.out.println("======================================================");
			}
		//	else if (no_of_white == 4) {
		//		System.out.println("Marked none");
		//		System.out.println("======================================================");
		//	}
			else {
				choice = 0;
				tempans = avg[0];
				for (int a = 0; a < avg.length; a++) {
					System.out.println(avg[a]);
					if (tempans > avg[a]) {
						choice = a;
						tempans = avg[a];
					}
				}
				if (choice == ans) {
					System.out.println("Choice: " + choice);
					System.out.println("Added to chemistry");
					chemistry++;
					System.out.println("======================================================");
				}
					
				else  {
					System.out.println("Choice: " + choice);
					System.out.println("Marked wrong");
					System.out.println("======================================================");
				}
			}
			avg = new double[4];
			whitespace = new int[4];
			no_of_white = 0;
			count++;
			count_x++;
		}
		System.out.println("answers are: " + english +"," + maths + "," + physics+ "," + chemistry);
		scan.close();
	}
	
	public static int putToArrayList(int a, int edge_number, BufferedImage img, ArrayList list) {
		
		int[] count = new int[3];
		count[0] = a;
		for (int b = a; b < img.getWidth(); b++) {
			if (Brightness(img.getRGB(b, edge_number))==0) {
				count[1] = b;
				count[2] = b-a;
				list.add(count);
				return b;
			}	
			if (b== img.getWidth()-1 && (Brightness(img.getRGB(b-1, edge_number))==1)) {
				count[1] = b;
				count[2] = b-a;
				list.add(count);		
				return b;
			}
			else continue;	
		}
		return a;
	}
	
	
	public static int Brightness(int rgb) {
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

	
	public static double ansBrightness(int rgb) {
		double brightness;
		int r, g, b;
		r = (rgb >> 16) & 0xff; // red component
		g = (rgb >> 8) & 0xff; // green component
		b = rgb & 0xff; // blue component

		brightness =  Math.sqrt(r * r * .241 + g * g * .691 + b * b * .068);
	
		return brightness;
	}
}