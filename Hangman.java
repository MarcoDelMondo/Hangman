import java.util.*;
import java.io.*;

public class Hangman {
	static ArrayList<String> Word = new ArrayList<String>();
	static ArrayList<Integer> HighScore = new ArrayList<Integer>();
	static ArrayList<String> Name = new ArrayList<String>();
	static int wordChoice = 0;
	static Integer score = 0;

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean run = true;
		int guesses = 7;
		int tempScore = 0;
		ArrayList<Character> Correct = new ArrayList<Character>();
		ArrayList<Character> Incorrect = new ArrayList<Character>();
		try {
			readDictionary("res/dictionary.txt");
			System.out.print("Hidden Word: ");
			for (int i = 0; i < Word.get(wordChoice).length(); i++) {
				System.out.print(" _ ");
			}
			while (run == true) {
				String guess = input.next().toUpperCase();
				Character charGuess = guess.charAt(0);
				if (guess.length() > 1) {
					System.out.println("Please enter only 1 letter");
					continue;
				} else if (Correct.contains(charGuess) || Incorrect.contains(charGuess)) {
					System.out.println("You've already guessed " + charGuess);
					System.out.println("Please try again");
				} else if (guess.length() == 1) {
					for (int i = 0; i < Word.get(wordChoice).length(); i++) {
						if (Word.get(wordChoice).charAt(i) == charGuess) {
							Correct.add(charGuess);
							Collections.sort(Correct);
							score += 10;
							tempScore += 10;
						}
					}
					if (!Word.get(wordChoice).contains(guess)) {
						Incorrect.add(charGuess);
						Collections.sort(Incorrect);
						guesses--;
					}
					System.out.print("Hidden Word: ");
					for (int i = 0; i < Word.get(wordChoice).length(); i++) {
						if (Word.get(wordChoice).charAt(i) == charGuess) {
							System.out.print(charGuess);
						} else if (Correct.contains(Word.get(wordChoice).charAt(i))) {
							System.out.print(Word.get(wordChoice).charAt(i));
						} else
							System.out.print(" _ ");
					}
					System.out.println();
					System.out.print("Incorrect Guesses: ");
					for (int i = 0; i < Incorrect.size(); i++) {
						if (i > 0) {
							System.out.print(", ");
						}
						System.out.print(Incorrect.get(i));
					}
					System.out.println();
					System.out.println("Guesses Left: " + guesses);
					System.out.println("Score: " + score);
					System.out.println("Enter Next Guess: ");
				}
				if (guesses == 0) {
					System.out.println("You are out of guesses!");
					System.out.println("Your word was: " + Word.get(wordChoice));
					System.out.println("Your Score: " + score);
					readHighScores();
					saveHighScore();
					displayHighScores();
					run = false;
				} else if (tempScore / 10 == Word.get(wordChoice).length()) {
					System.out.println("You guessed correctly! Your word was: " + Word.get(wordChoice));
					score += 100;
					score += 30 * guesses;
					readDictionary("res/dictionary.txt");
					Correct.clear();
					Incorrect.clear();
					System.out.print("New Hidden Word: ");
					tempScore = 0;
					guesses = 7;
					for (int i = 0; i < Word.get(wordChoice).length(); i++) {
						System.out.print(" _ ");
					}
					continue;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		input.close();
	}

	public static void readDictionary(String filename) throws FileNotFoundException {
		File file = new File(filename);
		Scanner input = new Scanner(file);
		while (input.hasNext()) {
			String word = input.next().toUpperCase();
			Word.add(word);
			wordChoice = (int) (Math.random() * Word.size());
		}
		input.close();
	}

	public static void readHighScores() throws FileNotFoundException {
		File file = new File("res/highscores.txt");
		Scanner read = new Scanner(file);
		while (read.hasNext()) {
			String name = read.next();
			Integer highScore = read.nextInt();
			Name.add(name);
			HighScore.add(highScore);
		}
		read.close();
	}

	public static void saveHighScore() {
		try {
			File file = new File("res/highscores.txt");
			Scanner input = new Scanner(System.in);
			readHighScores();
			PrintWriter output = new PrintWriter(file);
			boolean hasChanged = false;
			for (int i = 0; i < HighScore.size(); i++) {
				if (hasChanged == false) {
					if (score > HighScore.get(i)) {
						System.out.println("You have achieved a new high score!");
						System.out.println("Please enter your name");
						String userName = input.next();
						HighScore.set(i, score);
						Name.set(i, userName);
						output.println(Name.get(i) + " " + HighScore.get(i));
						hasChanged = true;
					}
				} else {
					output.println(Name.get(i) + " " + HighScore.get(i));
				}
			}
			output.close();
			input.close();
		} catch (InputMismatchException e) {
			System.out.println("Invalid input, please try again" + e);
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void displayHighScores() {
		try {
			File file = new File("res/highscores.txt");
			Scanner input = new Scanner(file);
			System.out.println("High Scores");
			while (input.hasNext()) {
				String name = input.next();
				int highScore = input.nextInt();
				System.out.println(name + " " + highScore);
			}
			input.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found " + e);
		}
	}
}