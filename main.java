package recommend;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class main {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		RecoSystem system = new RecoSystem();
		System.out.println("Welcome to the recommender system!");

		boolean play = true;
		while(play){
			System.out.println("Which dataset would you like to choose? Big one press b, "
					+ "small one press s, quit press q.");
			Scanner scanner = new Scanner(System.in);
			String input = scanner.nextLine();
			//if user want big dataset
			if (input.equals("b")) {
				system.data.processData("ratings.dat");
				boolean start = true;
				while(start) {
					System.out.println("If you want to predict, press a, want recommendation, press b， "
							+ "if you want to quit the system, press c");
					Scanner scanner1 = new Scanner(System.in);
					String input1 = scanner1.nextLine();
					//if user want to predict preference
					if (input1.equals("a")) {
						boolean start2 = true;
						while(start2){
							System.out.println("choose KNN with Pearson, press a, choose KNN with Cosine, press b, "
									+ "choose baseline, press c, quit, press d");
							Scanner scanner2 = new Scanner(System.in);
							String input2 = scanner2.nextLine();
							//do KNN with Pearson
							if (input2.equals("a")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.calPreference(name, title, false);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//KNN with Cosine
							else if (input2.equals("b")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.calPreference(name, title, true);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//do baseline
							else if (input2.equals("c")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.baselinePredict(name, title);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//quit
							else if (input2.equals("d")) {
								start2 = false;
							}
							else {
								System.out.println("invalid input");
							}
						}
					}////if user want recommendation list
					else if (input1.equals("b")) {
						boolean start2 = true;
						while(start2){
							System.out.println("choose KNN with Pearson, press a, choose KNN with Cosine, press b, "
									+ " choose baseline, press c, quit, press d");
							Scanner scanner2 = new Scanner(System.in);
							String input2 = scanner2.nextLine();
							//if choose KNN with Pearson
							if (input2.equals("a")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 1);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//choose KNN with Cosine
							else if (input2.equals("b")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 2);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}
							else if (input2.equals("c")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 3);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}
							else if (input2.equals("d")) {
								start2 = false;
							}
							else {
								System.out.println("invalid input");
							}
						}

					}else if (input1.equals("c")) {
						start = false;
					}
					else {
						System.out.println("invalid input");
					}
				}

			}
			//if user chooses small dataset
			else if (input.equals("s")) {
				system.data.processDataCSV("ratings.csv");
				boolean start = true;
				while(start) {
					System.out.println("If you want to predict, press a, want recommendation, press b， "
							+ "if you want to quit the system, press c");
					Scanner scanner1 = new Scanner(System.in);
					String input1 = scanner1.nextLine();
					//if user want to predict preference
					if (input1.equals("a")) {
						boolean start2 = true;
						while(start2){
							System.out.println("choose KNN with Pearson, press a, choose KNN with Cosine, press b, "
									+ "choose baseline, press c, quit, press d");
							Scanner scanner2 = new Scanner(System.in);
							String input2 = scanner2.nextLine();
							//do KNN with Pearson
							if (input2.equals("a")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.calPreference(name, title, false);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//KNN with Cosine
							else if (input2.equals("b")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.calPreference(name, title, true);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//do baseline
							else if (input2.equals("c")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and itemID. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int title = Integer.valueOf(s[1]);
										double prediction = system.baselinePredict(name, title);
										System.out.println("prediction is " + prediction);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//quit
							else if (input2.equals("d")) {
								start2 = false;
							}
							else {
								System.out.println("invalid input");
							}
						}
					}////if user want recommendation list
					else if (input1.equals("b")) {
						boolean start2 = true;
						while(start2){
							System.out.println("choose KNN with Pearson, press a, choose KNN with Cosine, press b, "
									+ " choose baseline, press c, quit, press d");
							Scanner scanner2 = new Scanner(System.in);
							String input2 = scanner2.nextLine();
							//if choose KNN with Pearson
							if (input2.equals("a")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 1);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}//choose KNN with Cosine
							else if (input2.equals("b")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 2);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}
							else if (input2.equals("c")) {
								boolean start3 = true;
								while(start3) {
									System.out.println("Enter userID and number of recommendation. use a comma to seperate"
											+ " e.g 1,18");
									Scanner scanner3 = new Scanner(System.in);
									String input3 = scanner3.nextLine();
									try {
										String[] s = input3.split(",");
										int name = Integer.valueOf(s[0]);
										int num = Integer.valueOf(s[1]);
										ArrayList<Integer> recoList = system.findRecommendation(name, num, 3);
										System.out.println("recommendations are " + recoList);
										start3 = false;

									}catch (NumberFormatException e) {
										System.out.println("enter again");
									}catch (ArrayIndexOutOfBoundsException e){
										System.out.println("enter again");
									}
								}	
							}
							else if (input2.equals("d")) {
								start2 = false;
							}
							else {
								System.out.println("invalid input");
							}
						}

					}else if (input1.equals("c")) {
						start = false;
					}
					else {
						System.out.println("invalid input");
					}
				}

			}
			else if (input.equals("q")) {
				play = false;
			}
			else {
				System.out.println("enter again");
			}
		}



	}

}
