package game;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The main class for the Jurassic World game. This is the main game driver that runs the game.
 *
 */
public class GameDriver {
    /**
     * This keeps track of desired turn goal for challenge mode.
     */
    private static int challengeTurn;
    /**
     * This keeps track of desired eco points goal for challenge mode.
     */
    private static int challengeEcoPoints;
    /**
     * This keeps track if challenge mode has been initiated.
     */
    private static boolean challengeMode = false;

    /**
     * The main executing method for the game to run.
     * @param args default args
     */
    public static void main(String[] args) {
        int choice;
        Scanner scanner = new Scanner(System.in);
        try {
            do {
                challengeMode = false;
                System.out.printf("Welcome to Jurassic Park Simulator! %n" +
                        "Please select your desired game mode: %n" +
                        "1. Challenge %n" +
                        "2. Sandbox %n" +
                        "3. Quit %n");
                choice = scanner.nextInt();
                if (choice <= 0 || choice > 3) {
                    System.out.println("Input not defined, please try again.");
                    continue;
                } else if (choice == 1) {
                    challengeMode = true;
                    challengeSettings();
                } else if (choice == 2) {
                    System.out.println("You have selected sandbox! Please enjoy the game!");
                } else {
                    break;
                }
                WorldBuilder worldBuilder = new WorldBuilder();
                worldBuilder.generateMaps();
                System.out.println();
            } while (choice != 3);
        } catch (InputMismatchException e){
            System.out.println("Incorrect input format, please key in integer values.");
        }
    }

    /**
     * The method deals with the challenge settings for challenge mode. The user has control over
     * how many eco points and turns they want to beat the game by.
     */
    public static void challengeSettings(){
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.printf(
                    "You have selected challenge! In this game mode you can set the winning conditions for the game.%n" +
                            "If the determined conditions are not met, you will lose the game!%n" +
                            "Please tweak the settings for EcoPoints and Number of Moves.%n" +
                            "Starting from EcoPoints,%n" +
                            "Please Enter the amount of EcoPoints required to win:  %n");
            challengeEcoPoints = scanner.nextInt();
            System.out.printf(
                    "Great! Your input for EcoPoints win condition is %d%n" +
                            "Now, %n" + "Please Enter the maximum number of moves required before the game ends:  %n", challengeEcoPoints);
            challengeTurn = scanner.nextInt();
            System.out.println("Your input for maximum number of moves is " + challengeTurn);
        } catch (InputMismatchException e){
            System.out.println("Incorrect input format, please key in integer values.");
        }
    }

    /**
     * The getter method for challenge mode.
     * @return challengeMode true or false
     */
    public static boolean isChallengeMode(){
        return challengeMode;
    }

    /**
     * The getter method for the desired turn goal for challenge mode.
     * @return the turn inputted
     */
    public static int getChallengeTurn(){
        return challengeTurn;
    }

    /**
     * The setter method for challenge mode turns.
     * @param turn the amount of turns wanted.
     */
    public static void setChallengeTurn(int turn){
        challengeTurn = turn;
    }

    /**
     * The getter method for the desired eco points goal for challenge mode.
     * @return the eco points inputted
     */
    public static int getChallengeEcoPoints(){
        return challengeEcoPoints;
    }

    /**
     * The setter method for challenge mode eco points.
     * @param points the amount of eco points wanted.
     */
    public static void setChallengeEcoPoints(int points){
        challengeEcoPoints = points;
    }

}
