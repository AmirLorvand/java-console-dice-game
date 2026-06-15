// Author: Amir Lorvand

package dice.game;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DiceGame {

    private static final String[] PLAYERS = {"Player 1", "Player 2"};
    private static final String[] CATEGORIES = {"Ones", "Twos", "Threes", "Fours", "Fives", "Sixes", "Sequence"};
    private static final int[] CATEGORY_VALUES = {1, 2, 3, 4, 5, 6, 20};
    private static int[][] scoreTable;
    private static int selectedCategoryIndex;
    private static int selectedValue;

    //use for rolling dice
    private static int[] rollDice(int numDice) {
        int[] results = new int[numDice];
        final Random random = new Random();

        for (int i = 0; i < numDice; i++) {
            results[i] = random.nextInt(6) + 1;
        }

        return results;
    }

    //check that if sequence is valid or not
    private static boolean checkSequence(int[] selectedArray) {
        int[] sortedArray = Arrays.copyOf(selectedArray, selectedArray.length);

        Arrays.sort(sortedArray);

        return Arrays.equals(sortedArray, new int[]{1, 2, 3, 4, 5}) || Arrays.equals(sortedArray, new int[]{2, 3, 4, 5, 6});
    }

    //show the remaining dice and prompt for selection
    private static int[] handleDiceRoll(int remainingDice) {
        int[] dice = rollDice(remainingDice);

        System.out.print("Throw: ");
        for (int value : dice) {
            System.out.print("[" + value + "] ");
        }

        System.out.println();

        return dice;
    }

    //select option for sequence
    private static int[] selectIndices(int[] dice) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("0: None");
        for (int i = 0; i < dice.length; i++) {
            System.out.println("Index " + (i + 1) + ": " + dice[i]);
        }

        System.out.print("Enter a sequence of indices (space separated): ");
        String userIndicesInput = scanner.nextLine();

        if (userIndicesInput.equals("0")) {
            return new int[0];
        }

        String[] userIndicesArray = userIndicesInput.split(" ");
        int[] userIndices = new int[userIndicesArray.length];

        for (int i = 0; i < userIndicesArray.length; i++) {
            userIndices[i] = Integer.parseInt(userIndicesArray[i]) - 1;
        }

        return userIndices;
    }

    //player turn logic
    private static void playTurn(String player) {
        int score = 0;
        int remainingDice = 5;
        boolean categoryChosen = false;
        int throwCount = 1;
        displayScoreTable();

        while (throwCount <= 3) {
            if (categoryChosen) {

                System.out.printf("That throw had %d dice with value %d. Setting aside %d dice: %s%n",
                        score / CATEGORY_VALUES[selectedCategoryIndex],
                        selectedValue,
                        score / CATEGORY_VALUES[selectedCategoryIndex],
                        repeat("[" + CATEGORY_VALUES[selectedCategoryIndex] + "] ", score / CATEGORY_VALUES[selectedCategoryIndex]));
            }

            System.out.printf("%d throw of this turn %s to throw %d dice:%n", (4 - throwCount), player, remainingDice);

            Scanner scanner = new Scanner(System.in);

            System.out.print("Throw " + remainingDice + " dice, enter 't' to throw or 'f' to forfeit > ");
            String continueInput = scanner.nextLine();

            if (continueInput.equals("t")) {
                System.out.printf("%d throws remaining for this turn.%n", (3 - throwCount));
                int[] dice = handleDiceRoll(remainingDice);

                if (!categoryChosen) {
                    System.out.print("Enter 's' to select category (number on die/dice) or 'd' to defer > ");
                    String select = scanner.nextLine();

                    if (select.equals("d")) {
                        continue;
                    } else {

                        System.out.print("Please enter the name of Category -> Ones, Twos, Threes, Fours, Fives, Sixes or Sequence: ");
                        String categoryInput = scanner.nextLine();
                        selectedCategoryIndex = getCategoryIndex(categoryInput);

                        while (selectedCategoryIndex == -1 || scoreTable[getPlayerIndex(player)][selectedCategoryIndex] != 0) {
                            if (selectedCategoryIndex == -1) {
                                System.out.println("Invalid category. Please choose a valid category.");
                            } else {
                                System.out.println("Category already chosen. Please choose a different category.");
                            }
                            categoryInput = scanner.nextLine();
                            selectedCategoryIndex = getCategoryIndex(categoryInput);
                        }

                        System.out.println(CATEGORIES[selectedCategoryIndex] + " selected.");
                    }

                    categoryChosen = true;

                    if (selectedCategoryIndex == 6) {
                        int[] selectedSequence = new int[0];
                        boolean isNotFirst = false;

                        while (throwCount <= 3) {
                            if (isNotFirst) {
                                System.out.printf("Throw %d dice, enter 't' to throw or 'f' to forfeit > ", remainingDice);
                                continueInput = scanner.nextLine();

                                if (continueInput.equals("t")) {
                                    System.out.printf("%d throws remaining for this turn.%n", (3 - throwCount));

                                    dice = handleDiceRoll(remainingDice);
                                    int[] userIndices = selectIndices(dice);

                                    if (userIndices.length > 0) {
                                        int[] newSelectedSequence = new int[selectedSequence.length + userIndices.length];

                                        System.arraycopy(selectedSequence, 0, newSelectedSequence, 0, selectedSequence.length);

                                        for (int i = 0; i < userIndices.length; i++) {
                                            newSelectedSequence[selectedSequence.length + i] = dice[userIndices[i]];
                                        }

                                        selectedSequence = newSelectedSequence;

                                        System.out.print("Selected sequence: ");
                                        for (int j : selectedSequence) {
                                            System.out.printf("[%d]", j);
                                        }

                                        System.out.print("\n ");

                                        remainingDice -= userIndices.length;
                                    }

                                    throwCount++;

                                } else {
                                    break;
                                }
                            } else {

                                isNotFirst = true;
                                int[] userIndices = selectIndices(dice);

                                if (userIndices[0] != -1) {

                                    int[] newSelectedSequence = new int[userIndices.length];

                                    for (int i = 0; i < userIndices.length; i++) {
                                        newSelectedSequence[i] = dice[userIndices[i]];
                                    }

                                    selectedSequence = newSelectedSequence;
                                    remainingDice -= userIndices.length;

                                    System.out.print("Selected sequence: ");

                                    for (int j : selectedSequence) {
                                        System.out.printf("[%d]", j);

                                    }

                                    System.out.print("\n ");

                                }
                                throwCount++;
                            }
                        }

                        boolean isSequence = checkSequence(selectedSequence);

                        if (isSequence) {

                            System.out.println("It's a sequence!");
                            selectedValue = CATEGORY_VALUES[selectedCategoryIndex];
                            score += selectedValue;

                        } else {

                            System.out.println("It's not a sequence");
                            score += -1;

                        }
                    } else {

                        selectedValue = CATEGORY_VALUES[selectedCategoryIndex];

                        score += countDice(dice, selectedValue) * selectedValue;

                        remainingDice -= countDice(dice, selectedValue);

                        throwCount++;
                    }
                } else {

                    selectedValue = CATEGORY_VALUES[selectedCategoryIndex];

                    score += countDice(dice, selectedValue) * selectedValue;

                    remainingDice -= countDice(dice, selectedValue);

                    throwCount++;
                }
            } else {
                break;
            }
        }

        if (score == 0) {
            score = -1;
        }

        System.out.printf("Turn ends. Scored %d points for %s.%n", score, CATEGORIES[selectedCategoryIndex]);

        scoreTable[getPlayerIndex(player)][selectedCategoryIndex] = score;

    }

    //show the score table
    private static void displayScoreTable() {

        System.out.println("\n+------------------------------+");
        System.out.println("|CATEGORY PLAYER 1   PLAYER 2  ");
        System.out.println("+------------------------------+");

        for (String category : CATEGORIES) {
            System.out.printf("|%-8s|", category);

            for (String player : PLAYERS) {

                int score = scoreTable[getPlayerIndex(player)][getCategoryIndex(category)];

                if (score != -1) {
                    System.out.printf("\t%s|\t", (score != 0) ? score : " ");
                } else {
                    System.out.printf("\t%s|\t", "0");
                }
            }

            System.out.println();
        }

        System.out.println("+******************************+");
        System.out.print("|TOTAL\t|");

        for (String player : PLAYERS) {

            int[] playerScores = scoreTable[getPlayerIndex(player)];
            int totalScore = 0;

            for (int score : playerScores) {

                if (score == -1) {
                    score = 0;
                }

                totalScore += score;
            }
            System.out.printf("\t%d|\t", totalScore);
        }

        System.out.println("\n+******************************+");
    }

    //play the game
    private static void playGame() {
        int numRounds = 7;

        for (int roundNum = 0; roundNum < numRounds; roundNum++) {

            System.out.println("--------------");
            System.out.printf("Round: %d%n", roundNum + 1);
            System.out.println("--------------");

            for (String player : PLAYERS) {
                playTurn(player);
            }

            displayScoreTable();

        }

        String winner = determineWinner();
        System.out.printf("%n%s wins the game!%n", winner);
    }

    //check that who is winner
    private static String determineWinner() {

        int maxTotalScore = Integer.MIN_VALUE;
        String winner = null;

        for (String player : PLAYERS) {

            int[] playerScores = scoreTable[getPlayerIndex(player)];
            int totalScore = 0;

            for (int score : playerScores) {
                if (score == -1) {
                    score = 0;
                }
                totalScore += score;
            }

            if (totalScore > maxTotalScore) {
                maxTotalScore = totalScore;
                winner = player;
            }
        }

        return winner;
    }

    private static int getPlayerIndex(String player) {
        for (int i = 0; i < PLAYERS.length; i++) {
            if (PLAYERS[i].equals(player)) {
                return i;
            }
        }
        return -1;
    }

    private static int getCategoryIndex(String category) {
        for (int i = 0; i < CATEGORIES.length; i++) {
            if (CATEGORIES[i].equalsIgnoreCase(category)) {
                return i;
            }
        }
        return -1;
    }

    //for storing data rapid time
    private static String repeat(String str, int count) {

        StringBuilder repeatedString = new StringBuilder();

        for (int i = 0; i < count; i++) {
            repeatedString.append(str);
        }

        return repeatedString.toString();
    }

    //how many of that specific dice we have
    private static int countDice(int[] dice, int value) {

        int count = 0;

        for (int die : dice) {
            if (die == value) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        scoreTable = new int[PLAYERS.length][CATEGORIES.length];
        playGame();
    }
}
