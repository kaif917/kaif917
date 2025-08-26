import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        int totalScore = 0; // track score
        boolean playAgain = true;

        System.out.println(" Welcome to the Number Guessing Game!");

        while (playAgain) {
            int lower = 1;
            int upper = 100;
            int numberToGuess = rand.nextInt(upper - lower + 1) + lower;
            int attemptsAllowed = 7;  // limit attempts
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI'm thinking of a number between " + lower + " and " + upper + ".");
            System.out.println("You have " + attemptsAllowed + " attempts to guess it.");

            while (attempts < attemptsAllowed) {
                System.out.print("Enter your guess: ");
                int guess = sc.nextInt();
                attempts++;

                if (guess == numberToGuess) {
                    System.out.println("Correct! You guessed the number in " + attempts + " attempts.");
                    totalScore += (attemptsAllowed - attempts + 1); // higher score if fewer attempts
                    guessedCorrectly = true;
                    break;
                } else if (guess < numberToGuess) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The number was: " + numberToGuess);
            }

            System.out.println(" Your current score: " + totalScore);

            System.out.print("\nDo you want to play another round? (yes/no): ");
            String response = sc.next().toLowerCase();
            playAgain = response.equals("yes");
        }

        System.out.println("\n Game over! Your final score: " + totalScore);
        sc.close();
    }
}
