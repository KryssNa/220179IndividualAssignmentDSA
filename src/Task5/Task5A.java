package Task5;

/*
Implement hill climbing algorithm
*/

// Necessary imports
import java.util.Random;

public class Task5A {

    // utility method to generate a random solution of a given length
    public static char[] generateRandomSolution(int length) {

        // a new char array of the given length
        char[] solution = new char[length];

        // a new random object
        Random random = new Random();

        // for loop to generate a random solution
        for (int i = 0; i < length; i++) {
            solution[i] = (char) (random.nextInt(94) + 32);
        }

        // return the solution
        return solution;
    }


    // utility method to evaluate a given solution
    public static int evaluate(char[] solution) {
        String target = "kryss, Na!"; // the target string
        int diff = 0; // the difference between the solution and the target

        // for loop to calculate the difference between the solution and the target
        for (int i = 0; i < target.length(); i++) {
            char s = solution[i]; // the current character of the solution
            char t = target.charAt(i); // the current character of the target
            diff += Math.abs((int) s - (int) t); // add the difference between the two characters to the total difference
        }

        return diff; // return the total difference

    }

    // utility method to mutate a given solution
    public static void mutateSolution(char[] solution) {
        Random random = new Random();

        // generate a random index and mutate the character at that index
        int index = random.nextInt(solution.length);
        solution[index] = (char) (random.nextInt(94) + 32); // mutate the character at the random index
    }


    // main method
    public static void main(String[] args) {

        // generate a random solution and evaluate it
        char[] best = generateRandomSolution(13);
        int bestScore = evaluate(best);

        // while loop to mutate the solution and evaluate it
        while (true) {
            System.out.println("Best score so far: " + bestScore + " Solution: " + new String(best));

            // base case to break out of the loop if the best score is 0
            if (bestScore == 0) {
                break;
            }

            // generate a new solution and evaluate it
            char[] newSolution = best.clone();

            // mutate the new solution
            mutateSolution(newSolution);

            // evaluate the new solution
            int score = evaluate(newSolution);

            // if the new solution is better than the best solution, replace the best solution with the new solution
            if (score < bestScore) {
                best = newSolution;
                bestScore = score;
            }
        }
    }
}
