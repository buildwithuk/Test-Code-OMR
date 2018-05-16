package aIIntelligent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Start {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

	//	String source_string = "E:\\Workouts\\OMR\\Doing (copied for sir)\\MCIT_2013_14\\B_0166_24.jpg";
		
		String source_string = "E:\\Workouts\\OMR\\Doing (copied for sir)\\MCIT_2013_14\\B_0167_3.jpg";
		String correct_ans = "E:\\Workouts\\OMR\\Doing (copied for sir)\\ans.txt";

		Start x = new Start();
		int no_of_questions = 60;
		x.startProcessing(source_string, correct_ans, no_of_questions);

	}

	public void startProcessing(String image, String correctans,
			int no_of_questions) throws IOException {

		Result result = new Result();

		// Image to be obtained from the file
		Scanner scan = new Scanner(new File(correctans));
		BufferedImage img_source = ImageIO.read(new File(image));

		// BufferedImage that is grayscaled
		BufferedImage img = new BufferedImage(img_source.getWidth(),
				img_source.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		Graphics g = img.getGraphics();
		g.drawImage(img_source, 0, 0, img.getWidth(), img.getHeight(), null);
		double radians = doIt(img);
		
		if (radians > 2) {

			System.out.println("Image is to much skewed. Cant process");
			System.exit(1);
		}
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.rotate(radians);
		g.dispose();
		g2d.dispose();
		// Process image for the deskew

		// Code to be placed here

		// Finding for the coordinates for the start of the square
		Struct right = returnRightPosition(img);
		Struct left = returnLeftPosition(img);

		processRoll(img, result, right);

		int tempx = left.X + 310;
		int tempy = left.Y + 1210;

		System.out.println("File: " + image + "\n");
		System.out.println("Correct ans: " + correctans + "\n");
		System.out.println("LeftX: " + left.X + " LeftY: " + left.Y + "\n");
		System.out.println("RightX: " + right.X + " RightY: " + right.Y + "\n");
		System.out.println("Tempx: " + tempx + " " + tempy);
		ArrayList<Integer> black_list = new ArrayList<Integer>();
		for (int y = tempy; y < (tempy + 31); y++) {
			for (int x = tempx; x < (tempx + 311); x++) {

				if (isDark(img.getRGB(x, y))) {
					black_list.add(x);
				}
			}
		}
		int ChoiceA = tempx + 67 * 1;
		int ChoiceB = tempx + 67 * 2;
		int ChoiceC = tempx + 67 * 3;
		int ChoiceD = tempx + 67 * 4;

		// fOR QUESTION ONE

		int nA = 0, nB = 0, nC = 0, nD = 0;
		int question_no = 0;
		int answer_from_template = scan.nextInt();
		int answer_marked = -1;
		for (int a = 0; a < black_list.size(); a++) {

			int temp = black_list.get(a);
			if (temp <= ChoiceA)
				nA++;
			else if (temp > ChoiceA && temp <= ChoiceB)
				nB++;
			else if (temp > ChoiceB && temp <= ChoiceC)
				nC++;
			else if (temp > ChoiceC && temp <= ChoiceD)
				nD++;
		}
		System.out.println("============== Question: " + (question_no + 1)
				+ " ==============\n");

		System.out.println(black_list.size() + " " + nA + " " + nB + " " + nC
				+ " " + nD);
		if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
			System.out.println("Choice marked: A");
			answer_marked = 0;
		} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: B");
			answer_marked = 1;
		} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: C");
			answer_marked = 2;
		} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
			System.out.println("Choice marked: D");
			answer_marked = 3;
		} else {
			System.out.println("Choice could not be retrieved");
		}
		if (answer_from_template == answer_marked) {
			result.english++;
			System.out.println("Correct: Added to english");
			System.out.println("Total english: " + result.english);
		} else if (answer_marked == -1) {
			System.out.println("Not or all answers are marked");
		} else {
			System.out.println("Marked wrong");
		}

		System.out.println("Answer marked from the template: "
				+ answer_from_template);
		System.out.println("Choice marked: " + answer_marked + "\n");
		if (no_of_questions == question_no) {
			System.out
					.println("-----------------------------------------------");
			System.out.println("Processing finished");
			scan.close();
			System.exit(1);
		}

		// For the next row
		answer_marked = -1;
		question_no++;
		int temp_new_Y = tempy + 71;
		int temp_new_X = tempx;
		black_list = new ArrayList<Integer>();
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;

		while (question_no < 25) {

			answer_from_template = scan.nextInt();
			for (int y = temp_new_Y; y < (temp_new_Y + 31); y++) {
				for (int x = temp_new_X; x < (temp_new_X + 311); x++) {

					if (isDark(img.getRGB(x, y))) {
						black_list.add(x);
					}
				}
			}
			for (int a = 0; a < black_list.size(); a++) {

				int temp = black_list.get(a);
				if (temp <= ChoiceA)
					nA++;
				else if (temp > ChoiceA && temp <= ChoiceB)
					nB++;
				else if (temp > ChoiceB && temp <= ChoiceC)
					nC++;
				else if (temp > ChoiceC && temp <= ChoiceD)
					nD++;
			}
			System.out.println("============== Question: " + (question_no + 1)
					+ " ==============\n");

			if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
				System.out.println("Choice marked: A");
				answer_marked = 0;
			} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: B");
				answer_marked = 1;
			} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: C");
				answer_marked = 2;
			} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
				System.out.println("Choice marked: D");
				answer_marked = 3;
			} else {
				System.out.println("Choice could not be retrieved");
			}
			if (answer_from_template == answer_marked) {
				result.english++;
				System.out.println("Correct: Added to english");
				System.out.println("Total english: " + result.english);
			} else if (answer_marked == -1) {
				System.out.println("Not or all answers are marked");
			} else {
				System.out.println("Marked wrong");
			}
			System.out.println("Answer marked from the template: "
					+ answer_from_template);
			System.out.println("Choice marked: " + answer_marked);
			System.out.println(black_list.size() + " " + nA + " " + nB + " "
					+ nC + " " + nD + "\n");

			// Initializations for the next loop
			answer_marked = -1;
			black_list = new ArrayList<Integer>();
			nA = 0;
			nB = 0;
			nC = 0;
			nD = 0;
			question_no++;
			temp_new_Y += 80;
			// checking for the number of questions
			if (no_of_questions == question_no) {
				System.out
						.println("-----------------------------------------------");
				System.out.println("Processing finished");
				scan.close();
				System.out.println(result);
				System.exit(1);
			}

		}

		// FOR QUESTION 26

		tempx = left.X + 310 + 182 + 310;
		tempy = left.Y + 1210;
		System.out.println(tempx + " " + tempy);
		black_list = new ArrayList<Integer>();
		for (int y = tempy; y < (tempy + 31); y++) {
			for (int x = tempx; x < (tempx + 311); x++) {

				if (isDark(img.getRGB(x, y))) {
					black_list.add(x);
				}
			}
		}
		ChoiceA = tempx + 67 * 1;
		ChoiceB = tempx + 67 * 2;
		ChoiceC = tempx + 67 * 3;
		ChoiceD = tempx + 67 * 4;
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;
		answer_from_template = scan.nextInt();
		answer_marked = -1;
		for (int a = 0; a < black_list.size(); a++) {

			int temp = black_list.get(a);
			if (temp <= ChoiceA)
				nA++;
			else if (temp > ChoiceA && temp <= ChoiceB)
				nB++;
			else if (temp > ChoiceB && temp <= ChoiceC)
				nC++;
			else if (temp > ChoiceC && temp <= ChoiceD)
				nD++;
		}
		System.out.println("============== Question: " + (question_no + 1)
				+ " ==============\n");

		System.out.println(black_list.size() + " " + nA + " " + nB + " " + nC
				+ " " + nD);
		if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
			System.out.println("Choice marked: A");
			answer_marked = 0;
		} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: B");
			answer_marked = 1;
		} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: C");
			answer_marked = 2;
		} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
			System.out.println("Choice marked: D");
			answer_marked = 3;
		} else {
			System.out.println("Choice could not be retrieved");
		}
		if (answer_from_template == answer_marked) {
			result.maths++;
			System.out.println("Correct: Added to Maths");
			System.out.println("Total english: " + result.maths);
		} else if (answer_marked == -1) {
			System.out.println("Not or all answers are marked");
		} else {
			System.out.println("Marked wrong");
		}

		System.out.println("Answer marked from the template: "
				+ answer_from_template);
		System.out.println("Choice marked: " + answer_marked + "\n");
		if (no_of_questions == question_no) {
			System.out
					.println("-----------------------------------------------");
			System.out.println("Processing finished");
			scan.close();
			System.exit(1);
		}

		// For 26 to 50

		answer_marked = -1;
		question_no++;
		temp_new_Y = tempy + 71;
		temp_new_X = tempx;
		black_list = new ArrayList<Integer>();
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;

		while (question_no < 50) {

			answer_from_template = scan.nextInt();
			for (int y = temp_new_Y; y < (temp_new_Y + 31); y++) {
				for (int x = temp_new_X; x < (temp_new_X + 311); x++) {

					if (isDark(img.getRGB(x, y))) {
						black_list.add(x);
					}
				}
			}
			for (int a = 0; a < black_list.size(); a++) {

				int temp = black_list.get(a);
				if (temp <= ChoiceA)
					nA++;
				else if (temp > ChoiceA && temp <= ChoiceB)
					nB++;
				else if (temp > ChoiceB && temp <= ChoiceC)
					nC++;
				else if (temp > ChoiceC && temp <= ChoiceD)
					nD++;
			}
			System.out.println("============== Question: " + (question_no + 1)
					+ " ==============\n");

			if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
				System.out.println("Choice marked: A");
				answer_marked = 0;
			} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: B");
				answer_marked = 1;
			} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: C");
				answer_marked = 2;
			} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
				System.out.println("Choice marked: D");
				answer_marked = 3;
			} else {
				System.out.println("Choice could not be retrieved");
			}
			if (answer_from_template == answer_marked) {
				result.maths++;
				System.out.println("Correct: Added to Maths");
				System.out.println("Total maths: " + result.maths);
			} else if (answer_marked == -1) {
				System.out.println("Not or all answers are marked");
			} else {
				System.out.println("Marked wrong");
			}
			System.out.println("Answer marked from the template: "
					+ answer_from_template);
			System.out.println("Choice marked: " + answer_marked);
			System.out.println(black_list.size() + " " + nA + " " + nB + " "
					+ nC + " " + nD + "\n");

			// Initializations for the next loop
			answer_marked = -1;
			black_list = new ArrayList<Integer>();
			nA = 0;
			nB = 0;
			nC = 0;
			nD = 0;
			question_no++;
			temp_new_Y += 80;
			// checking for the number of questions
			if (no_of_questions == question_no) {
				System.out
						.println("-----------------------------------------------");
				System.out.println("Processing finished");
				scan.close();
				System.out.println(result);
				System.exit(1);
			}

		}

		
		// Question No: 51
		

		tempx = left.X + 310 + 182 + 310 + 182 + 310;
		tempy = left.Y + 1210;
		System.out.println(tempx + " " + tempy);
		black_list = new ArrayList<Integer>();
		for (int y = tempy; y < (tempy + 31); y++) {
			for (int x = tempx; x < (tempx + 311); x++) {

				if (isDark(img.getRGB(x, y))) {
					black_list.add(x);
				}
			}
		}
		ChoiceA = tempx + 67 * 1;
		ChoiceB = tempx + 67 * 2;
		ChoiceC = tempx + 67 * 3;
		ChoiceD = tempx + 67 * 4;
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;
		answer_from_template = scan.nextInt();
		answer_marked = -1;
		for (int a = 0; a < black_list.size(); a++) {

			int temp = black_list.get(a);
			if (temp <= ChoiceA)
				nA++;
			else if (temp > ChoiceA && temp <= ChoiceB)
				nB++;
			else if (temp > ChoiceB && temp <= ChoiceC)
				nC++;
			else if (temp > ChoiceC && temp <= ChoiceD)
				nD++;
		}
		System.out.println("============== Question: " + (question_no + 1)
				+ " ==============\n");

		System.out.println(black_list.size() + " " + nA + " " + nB + " " + nC
				+ " " + nD);
		
		if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
			System.out.println("Choice marked: A");
			answer_marked = 0;
		} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: B");
			answer_marked = 1;
		} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: C");
			answer_marked = 2;
		} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
			System.out.println("Choice marked: D");
			answer_marked = 3;
		} else {
			System.out.println("Choice could not be retrieved");
		}
		if (answer_from_template == answer_marked) {
			result.maths++;
			System.out.println("Correct: Added to Maths");
			System.out.println("Total english: " + result.maths);
		} else if (answer_marked == -1) {
			System.out.println("Not or all answers are marked");
		} else {
			System.out.println("Marked wrong");
		}

		System.out.println("Answer marked from the template: "
				+ answer_from_template);
		System.out.println("Choice marked: " + answer_marked + "\n");
		if (no_of_questions == question_no) {
			System.out
					.println("-----------------------------------------------");
			System.out.println("Processing finished");
			scan.close();
			System.exit(1);
		}
		
		// For physics 51 - 75
		

		answer_marked = -1;
		question_no++;
		temp_new_Y = tempy + 71;
		temp_new_X = tempx;
		black_list = new ArrayList<Integer>();
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;

		while (question_no < 75) {

			answer_from_template = scan.nextInt();
			for (int y = temp_new_Y; y < (temp_new_Y + 31); y++) {
				for (int x = temp_new_X; x < (temp_new_X + 311); x++) {

					if (isDark(img.getRGB(x, y))) {
						black_list.add(x);
					}
				}
			}
			for (int a = 0; a < black_list.size(); a++) {

				int temp = black_list.get(a);
				if (temp <= ChoiceA)
					nA++;
				else if (temp > ChoiceA && temp <= ChoiceB)
					nB++;
				else if (temp > ChoiceB && temp <= ChoiceC)
					nC++;
				else if (temp > ChoiceC && temp <= ChoiceD)
					nD++;
			}
			System.out.println("============== Question: " + (question_no + 1)
					+ " ==============\n");

			if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
				System.out.println("Choice marked: A");
				answer_marked = 0;
			} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: B");
				answer_marked = 1;
			} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: C");
				answer_marked = 2;
			} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
				System.out.println("Choice marked: D");
				answer_marked = 3;
			} else {
				System.out.println("Choice could not be retrieved");
			}
			if (answer_from_template == answer_marked) {
				result.physics++;
				System.out.println("Correct: Added to Physics");
				System.out.println("Total physics: " + result.physics);
			} else if (answer_marked == -1) {
				System.out.println("Not or all answers are marked");
			} else {
				System.out.println("Marked wrong");
			}
			System.out.println("Answer marked from the template: "
					+ answer_from_template);
			System.out.println("Choice marked: " + answer_marked);
			System.out.println(black_list.size() + " " + nA + " " + nB + " "
					+ nC + " " + nD + "\n");

			// Initializations for the next loop
			answer_marked = -1;
			black_list = new ArrayList<Integer>();
			nA = 0;
			nB = 0;
			nC = 0;
			nD = 0;
			question_no++;
			temp_new_Y += 80;
			// checking for the number of questions
			if (no_of_questions == question_no) {
				System.out
						.println("-----------------------------------------------");
				System.out.println("Processing finished");
				scan.close();
				System.out.println(result);
				System.exit(1);
			}

		}
		
		
		
		// For Question 76
		

		tempx = left.X + 310 + 182 + 310 + 182 + 310 + 182 + 310;
		tempy = left.Y + 1210;
		System.out.println(tempx + " " + tempy);
		black_list = new ArrayList<Integer>();
		for (int y = tempy; y < (tempy + 31); y++) {
			for (int x = tempx; x < (tempx + 311); x++) {

				if (isDark(img.getRGB(x, y))) {
					black_list.add(x);
				}
			}
		}
		ChoiceA = tempx + 67 * 1;
		ChoiceB = tempx + 67 * 2;
		ChoiceC = tempx + 67 * 3;
		ChoiceD = tempx + 67 * 4;
		nA = 0;
		nB = 0;
		nC = 0;
		nD = 0;
		answer_from_template = scan.nextInt();
		answer_marked = -1;
		for (int a = 0; a < black_list.size(); a++) {

			int temp = black_list.get(a);
			if (temp <= ChoiceA)
				nA++;
			else if (temp > ChoiceA && temp <= ChoiceB)
				nB++;
			else if (temp > ChoiceB && temp <= ChoiceC)
				nC++;
			else if (temp > ChoiceC && temp <= ChoiceD)
				nD++;
		}
		System.out.println("============== Question: " + (question_no + 1)
				+ " ==============\n");

		System.out.println(black_list.size() + " " + nA + " " + nB + " " + nC
				+ " " + nD);
		
		if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
			System.out.println("Choice marked: A");
			answer_marked = 0;
		} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: B");
			answer_marked = 1;
		} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
			System.out.println("Choice marked: C");
			answer_marked = 2;
		} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
			System.out.println("Choice marked: D");
			answer_marked = 3;
		} else {
			System.out.println("Choice could not be retrieved");
		}
		if (answer_from_template == answer_marked) {
			result.chemistry++;
			System.out.println("Correct: Added to Chemistry");
			System.out.println("Total chemistry: " + result.chemistry);
		} else if (answer_marked == -1) {
			System.out.println("Not or all answers are marked");
		} else {
			System.out.println("Marked wrong");
		}

		System.out.println("Answer marked from the template: "
				+ answer_from_template);
		System.out.println("Choice marked: " + answer_marked + "\n");
		if (no_of_questions == question_no) {
			System.out
					.println("-----------------------------------------------");
			System.out.println("Processing finished");
			scan.close();
			System.exit(1);
		}
		
		
		while (question_no < 100) {

			answer_from_template = scan.nextInt();
			for (int y = temp_new_Y; y < (temp_new_Y + 31); y++) {
				for (int x = temp_new_X; x < (temp_new_X + 311); x++) {

					if (isDark(img.getRGB(x, y))) {
						black_list.add(x);
					}
				}
			}
			for (int a = 0; a < black_list.size(); a++) {

				int temp = black_list.get(a);
				if (temp <= ChoiceA)
					nA++;
				else if (temp > ChoiceA && temp <= ChoiceB)
					nB++;
				else if (temp > ChoiceB && temp <= ChoiceC)
					nC++;
				else if (temp > ChoiceC && temp <= ChoiceD)
					nD++;
			}
			System.out.println("============== Question: " + (question_no + 1)
					+ " ==============\n");

			if (nA > 450 && nC < 450 && nB < 450 && nD < 450) {
				System.out.println("Choice marked: A");
				answer_marked = 0;
			} else if (nB > 450 && nC < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: B");
				answer_marked = 1;
			} else if (nC > 450 && nB < 450 && nA < 450 && nD < 450) {
				System.out.println("Choice marked: C");
				answer_marked = 2;
			} else if (nD > 450 && nA < 450 && nB < 450 && nC < 450) {
				System.out.println("Choice marked: D");
				answer_marked = 3;
			} else {
				System.out.println("Choice could not be retrieved");
			}
			if (answer_from_template == answer_marked) {
				result.chemistry++;
				System.out.println("Correct: Added to Chemistry");
				System.out.println("Total chemistry: " + result.chemistry);
			} else if (answer_marked == -1) {
				System.out.println("Not or all answers are marked");
			} else {
				System.out.println("Marked wrong");
			}
			System.out.println("Answer marked from the template: "
					+ answer_from_template);
			System.out.println("Choice marked: " + answer_marked);
			System.out.println(black_list.size() + " " + nA + " " + nB + " "
					+ nC + " " + nD + "\n");

			// Initializations for the next loop
			answer_marked = -1;
			black_list = new ArrayList<Integer>();
			nA = 0;
			nB = 0;
			nC = 0;
			nD = 0;
			question_no++;
			temp_new_Y += 80;
			// checking for the number of questions
			if (no_of_questions == question_no) {
				System.out
						.println("-----------------------------------------------");
				System.out.println("Processing finished");
				scan.close();
				System.out.println(result);
				System.exit(1);
			}

		}
		
		System.out
				.println("---------------------------------------------------");
		System.out.println(result);

	}

	public Struct returnLeftPosition(BufferedImage img) {
		ArrayList<ArrayList<Points>> left_square = new ArrayList<ArrayList<Points>>();
		int HEIGHT = 140;
		int WIDTH = 200;
		ArrayList<Points> points = new ArrayList<Points>();
		for (int a = 0; a < WIDTH; a++) {
			for (int b = 0; b < HEIGHT; b++) {
				if (isDark(img.getRGB(a, b)))
					points.add(new Points(a, b));

			}
			if (points.size() > 30) {
				left_square.add(points);
			}
			points = new ArrayList<Points>();

		}
		ArrayList<Points> p = left_square.get(left_square.size() - 1);
		Points t = p.get(p.size() - 1);
		Struct s = new Struct(t.X, t.Y);

		// To use some formulation for the detection of the wrong or not
		// detected square

		return s;
	}

	public Struct returnRightPosition(BufferedImage img) {

		ArrayList<ArrayList<Points>> right_square = new ArrayList<ArrayList<Points>>();
		int HEIGHT = 140;
		int WIDTH = img.getWidth() - 200;

		ArrayList<Points> points = new ArrayList<Points>();
		for (int a = WIDTH; a < img.getWidth(); a++) {
			for (int b = 0; b < HEIGHT; b++) {
				if (isDark(img.getRGB(a, b)))
					points.add(new Points(a, b));

			}
			if (points.size() > 30) {
				right_square.add(points);
			}
			points = new ArrayList<Points>();

		}
		ArrayList<Points> p = right_square.get(right_square.size() - 1);
		Points t = p.get(p.size() - 1);
		Struct struct = new Struct(t.X, t.Y);

		// To use some formulation for the detection of the wrong or not
		// detected square
		// Probably to use something such as the height and the width of the
		// square
		// Also i need to put some machine learning
		return struct;

	}

	public class Struct {
		public int X;
		public int Y;

		public Struct(int X, int Y) {
			this.X = X;
			this.Y = Y;

		}

		public String toString() {
			return String.format("(%d, %d)", X, Y);
		}
	}

	public static Boolean isDark(int rgb) {
		int r, g, b;
		r = (rgb >> 16) & 0xff; // red component
		g = (rgb >> 8) & 0xff; // green component
		b = rgb & 0xff; // blue component

		double brightness = Math.sqrt(r * r * .241 + g * g * .691 + b * b
				* .068);
		if (brightness == 255)
			return false;
		else
			return true;

	}

	class Points {
		public Points(int width, int height) {
			// TODO Auto-generated constructor stub
			X = width;
			Y = height;
		}

		public int X;
		public int Y;

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "Point: (" + X + ", " + Y + ")";
		}

		public int returnX() {
			return X;
		}

		public int returnY() {
			return Y;
		}

	}

	class Result {
		int[] roll;
		int english, maths, physics, chemistry;

		public Result() {
			roll = new int[] { -1, -1, -1 };

		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			String result = String
					.format("Roll number: %d%d%d\n\nEnglish: %d\nMaths: %d\nPhysics: %d\nChemistry: %d\nTotal: %d",
							roll[0], roll[1], roll[2], english, maths, physics,
							chemistry, (english + maths + physics + chemistry));
			return result;
		}

	}

	// Code for processing the image to find the deskew angle and then rotate it

	public double doIt(BufferedImage image) {
		final double skewRadians;
		BufferedImage black = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		final Graphics2D g = black.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();

		skewRadians = findSkew(black);
		System.out.println("Deskew angle: "
				+ (-57.295779513082320876798154814105 * skewRadians));
		return skewRadians;
	}

	public static int getByteWidth(final int width) {
		return (width + 7) / 8;
	}

	public static int next_pow2(final int n) {
		int retval = 1;
		while (retval < n) {
			retval <<= 1;
		}
		return retval;
	}

	static class BitUtils {
		static int[] bitcount_ = new int[256];
		static int[] invbits_ = new int[256];

		static {
			for (int i = 0; i < 256; i++) {
				int j = i, cnt = 0;
				do {
					cnt += j & 1;
				} while ((j >>= 1) != 0);
				int x = (i << 4) | (i >> 4);
				x = ((x & 0xCC) >> 2) | ((x & 0x33) << 2);
				x = ((x & 0xAA) >> 1) | ((x & 0x55) << 1);
				bitcount_[i] = cnt;
				invbits_[i] = x;
			}
		}
	}

	public double findSkew(final BufferedImage img) {
		final DataBuffer buffer = img.getRaster().getDataBuffer();
		final int byteWidth = getByteWidth(img.getWidth());
		final int padmask = 0xFF << ((img.getWidth() + 7) % 8);
		int elementIndex = 0;
		for (int row = 0; row < img.getHeight(); row++) {
			for (int col = 0; col < byteWidth; col++) {
				int elem = buffer.getElem(elementIndex);
				elem ^= 0xff;// invert colors
				elem = BitUtils.invbits_[elem]; // Change the bit order
				buffer.setElem(elementIndex, elem);
				elementIndex++;
			}
			final int lastElement = buffer.getElem(elementIndex - 1) & padmask;
			buffer.setElem(elementIndex - 1, lastElement); // Zero trailing bits
		}
		final int w2 = next_pow2(byteWidth);
		final int ssize = 2 * w2 - 1; // Size of sharpness table
		final int sharpness[] = new int[ssize];
		radon(img.getWidth(), img.getHeight(), buffer, 1, sharpness);
		radon(img.getWidth(), img.getHeight(), buffer, -1, sharpness);
		int i, imax = 0;
		int vmax = 0;
		double sum = 0.;
		for (i = 0; i < ssize; i++) {
			final int s = sharpness[i];
			if (s > vmax) {
				imax = i;
				vmax = s;
			}
			sum += s;
		}
		final int h = img.getHeight();
		if (vmax <= 3 * sum / h) { // Heuristics !!!
			return 0;
		}
		final double iskew = imax - w2 + 1;
		
		return  Math.atan(iskew / (8 * w2));
	}

	static void radon(final int width, final int height,
			final DataBuffer buffer, final int sign, final int sharpness[]) {

		int[] p1_, p2_; // Stored columnwise

		final int w2 = next_pow2(getByteWidth(width));
		final int w = getByteWidth(width);
		final int h = height;

		final int s = h * w2;
		p1_ = new int[s];
		p2_ = new int[s];
		// Fill in the first table
		int row, column;
		int scanlinePosition = 0;
		for (row = 0; row < h; row++) {
			scanlinePosition = row * w;
			for (column = 0; column < w; column++) {
				if (sign > 0) {
					final int b = buffer.getElem(0, scanlinePosition + w - 1
							- column);
					p1_[h * column + row] = BitUtils.bitcount_[b];
				} else {
					final int b = buffer.getElem(0, scanlinePosition + column);
					p1_[h * column + row] = BitUtils.bitcount_[b];
				}
			}
		}

		int[] x1 = p1_;
		int[] x2 = p2_;
		// Iterate
		int step = 1;
		for (;;) {
			int i;
			for (i = 0; i < w2; i += 2 * step) {
				int j;
				for (j = 0; j < step; j++) {
					// Columns-sources:
					final int s1 = h * (i + j);// x1 pointer
					final int s2 = h * (i + j + step); // x1 pointer

					// Columns-targets:
					final int t1 = h * (i + 2 * j); // x2 pointer
					final int t2 = h * (i + 2 * j + 1); // x2 pointer
					int m;
					for (m = 0; m < h; m++) {
						x2[t1 + m] = x1[s1 + m];
						x2[t2 + m] = x1[s1 + m];
						if (m + j < h) {
							x2[t1 + m] += x1[s2 + m + j];
						}
						if (m + j + 1 < h) {
							x2[t2 + m] += x1[s2 + m + j + 1];
						}
					}
				}
			}

			// Swap the tables:
			final int[] aux = x1;
			x1 = x2;
			x2 = aux;
			// Increase the step:
			step *= 2;
			if (step >= w2) {
				break;
			}
		}
		// Now, compute the sum of squared finite differences:
		for (column = 0; column < w2; column++) {
			int acc = 0;
			final int col = h * column;
			for (row = 0; row + 1 < h; row++) {
				final int diff = x1[col + row] - x1[col + row + 1];
				acc += diff * diff;
			}
			sharpness[w2 - 1 + sign * column] = acc;
		}
	}

	public void processRoll(BufferedImage img, Result result, Struct right) {
		int rey = right.Y;
		int rex = right.X;

		rey = rey + 419;
		rex = rex - 487;
		int temp = rex;
		int edge = rey;
		// First digit
		int count = 0;
		int black = 0;
		int no = 0;
		int mark = -1;
		int white = 0;
		int tempx = temp;
		int tempy = edge;

		// Code to find out the Roll Number

		/*
		 * while (count < 10) { black = 0; white = 0; tempy = edge + count * 56;
		 * for (int b = tempy; b < (tempy + 38); b++) { for (int a = tempx; a <
		 * (tempx + 21); a++) if (Brightness(img.getRGB(a, b)) == 1) black++;
		 * else if (Brightness(img.getRGB(a, b)) == 0) white++; } if (black >
		 * white) { no++; mark = count; } if (no > 1) {
		 * 
		 * System.out.println("Error at the roll number place 1"); } count++; }
		 * result.roll[0] = mark; mark = -1;
		 * 
		 * no = 0;
		 */
		// First Digit

		count = 0;
		black = 0;
		tempx = temp + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count * 56;
			for (int b = tempy; b < (tempy + 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)
					if (isDark(img.getRGB(a, b)))
						black++;
					else
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no > 1) {
				System.out.println("Error at the roll number place 1");
			}
			count++;
		}
		result.roll[0] = mark;
		mark = -1;
		no = 0;

		// Second digit
		count = 0;
		black = 0;
		tempx = temp + 127 + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count * 56;
			for (int b = tempy; b < (tempy + 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)
					if (isDark(img.getRGB(a, b)))
						black++;
					else
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no > 1) {
				System.out.println("Error at the roll number place 2");
			}
			count++;
		}
		result.roll[1] = mark;
		mark = -1;
		no = 0;

		// Fourth Digit
		count = 0;
		black = 0;
		tempx = temp + 127 + 127 + 127;
		tempy = edge;
		while (count < 10) {
			black = 0;
			white = 0;
			tempy = edge + count * 56;
			for (int b = tempy; b < (tempy + 38); b++) {
				for (int a = tempx; a < (tempx + 21); a++)
					if (isDark(img.getRGB(a, b)))
						black++;
					else
						white++;
			}
			if (black > white) {
				no++;
				mark = count;
			}
			if (no > 1) {
				System.out.println("Error at the roll number place 3");
			}
			count++;
		}
		result.roll[2] = mark;
		mark = 0;
		no = 0;
		System.out
				.println("------------------------- Printing out the roll number -------------------------");
		System.out.println("Roll number: " + result.roll[0] + result.roll[1]
				+ result.roll[2]);

	}
}