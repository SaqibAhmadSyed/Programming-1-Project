package unluckyrobot;

import java.util.Random;
import java.util.Scanner;

/**
 * A robot walks into a 5*5 grid to collect some points from some of the cells.
 * The objective of the game is to reach one of the end cells or to collect
 * above 2,000 points. Of course, like every other game, you will lose if your
 * points fall under a certain range.
 *
 * @author Saqib Syed
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //initializing the values
        int x = 0;
        int y = 0;
        int itrCount = 0;
        int totalScore = 300;
        int reward = 0;
        char direction;

        // outputs
        do {
            displayInfo(x, y, itrCount, totalScore);
            switch (inputDirection()) {
                case 'w':
                    direction = 'w';
                    y++;
                    itrCount++;
                    totalScore -= 10;

                    if (doesExceed(x, y, direction) == false) {
                        System.out.println("Exceed boundary, -2000 damage "
                                + "applied");
                        totalScore -= 2000;
                    } else {
                        reward = reward();
                        reward = punishOrMercy(direction, reward);
                        System.out.println("");
                        totalScore += reward;
                    }
                    break;
                case 'a':
                    direction = 'a';
                    x--;
                    itrCount++;
                    totalScore -= 50;

                    if (doesExceed(x, y, direction) == true) {
                        System.out.println("Exceed boundary, -2000 damage "
                                + "applied");
                        totalScore -= 2000;
                    } else {
                        reward = reward();
                        System.out.println("");
                        totalScore += reward;
                    }
                    break;
                case 's':
                    direction = 's';
                    y--;
                    itrCount++;
                    totalScore -= 50;

                    if (doesExceed(x, y, direction) == true) {
                        System.out.println("Exceed boundary, -2000 damage "
                                + "applied");
                        totalScore -= 2000;
                    } else {
                        reward = reward();
                        System.out.println("");
                        totalScore += reward;
                    }
                    break;
                case 'd':
                    direction = 'd';
                    x++;
                    itrCount++;
                    totalScore -= 50;

                    if (doesExceed(x, y, direction) == true) {
                        System.out.println("Exceed boundary, -2000 damage "
                                + "applied");
                        totalScore -= 2000;
                    } else {
                        reward = reward();
                        System.out.println("");
                        totalScore += reward;
                    }
                    break;
            }

        } while (isGameOver(x, y, totalScore, itrCount) == false);
        evaluation(totalScore);
    }

    /**
     *
     * @param x x coordinate (horizontal axis)
     * @param y y coordinate (vertical axis)
     * @param itrCount numbers of time the steps executes
     * @param totalScore the total score that the robot gained
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.printf("%s (X=%d, Y=%d) %s: %d %s: %d\n", "For point", x, y,
                "at iteration", itrCount, "the total score is", totalScore);
    }

    /**
     *
     * @param x x coordinate (horizontal axis)
     * @param y y coordinate (vertical axis)
     * @param direction inputed direction key
     * @return returns true if inputed direction key goes beyond the grid
     */
    public static boolean doesExceed(int x, int y, char direction) {
        if (x > 5 && direction == 'd' || y < 5 && direction == 'w'
                || x < 0 && direction == 'a' || y < 0 && direction == 's') {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return returns the value of the reward depending on the number the die
     * rolled in
     */
    public static int reward() {
        int dice = (int) (Math.random() * 6) + 1;

        switch (dice) {
            case (1):
                System.out.println("Dice: 1, reward: -100");
                return -100;
            case (2):
                System.out.println("Dice: 2, reward: -200");
                return -200;
            case (3):
                System.out.println("Dice: 3, reward: -300");
                return -300;
            case (4):
                System.out.println("Dice: 4, reward: 300");
                return 300;
            case (5):
                System.out.println("Dice: 5, reward: 400");
                return 400;
            case (6):
                System.out.println("Dice: 6, reward: 600");
                return 600;
        }
        return reward();
    }

    /**
     *
     * @param direction inputed direction key
     * @param reward the score given by the dice or by the coin
     * @return returns the reward depending on the side of the coin
     */
    public static int punishOrMercy(char direction, int reward) {
        Random flip = new Random();
        int coin = flip.nextInt(2); //0 = tail  1 = head

        if (reward < 0 && direction == 'w' && coin == 0) {
            System.out.println("Coin: tail | Mercy, the negative reward is "
                    + "removed");
            reward = 0;
            return reward;
        } else if (reward < 0 && direction == 'w' && coin == 1) {
            System.out.println("Coin: head | No mercy, the negative rewarded is"
                    + " applied");
            return reward;
        }
        return reward;
    }

    /**
     *
     * @param str String used to format the name into title case
     * @return returns the title cased name
     */
    public static String toTitleCase(String str) {
        str = str.toLowerCase();
        String firstName = Character.toTitleCase(str.charAt(0))
                + str.substring(1, str.indexOf(' '));
        String secondName = Character.toTitleCase(str.charAt(str.indexOf(' ')
                + 1)) + str.substring((str.indexOf(' ') + 2));

        return firstName + " " + secondName;
    }

    /**
     *
     * @param totalScore the total score that the robot gained
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);

        System.out.println("Please enter your name (only two words): ");
        String str = console.nextLine();

        if (totalScore >= 2000) {
            System.out.println("Victory! " + toTitleCase(str) + ", your score"
                    + " is " + totalScore);
        } else {
            System.out.println("Mission failed! " + toTitleCase(str)
                    + ", your score is " + totalScore);
        }
    }
    /**
     * 
     * @return returns the inputed key that represents the direction
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        char direction;

        //w = up | a = left | s = down | d = right//
        do {
            System.out.print("Please input a valid direction (w, a, s, d): ");
            direction = console.next().charAt(0);
        } while (direction != 'w' && direction != 'a' && direction != 's'
                && direction != 'd');

        return direction;
    }
    /**
     * 
     * @param x x coordinate (horizontal axis)
     * @param y y coordinate (vertical axis)
     * @param totalScore the total score that the robot gained
     * @param itrCount 
     * @return 
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount)
    {
        if (itrCount > 20 || totalScore < -1000 || totalScore > 2000
                || x == 5 && y == 5 || x == 5 && y == 0) {
            return true;
        } else {
            return false;
        }
    }
}
