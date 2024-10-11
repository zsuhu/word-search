import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.File;

public class Main {
    // creates the directions in the word search
    public static final int[] rowDirection = {-1, -1, -1, 0, 0, 1, 1, 1};
    public static final int[] colDirection = {-1, 0, 1, -1, 1, -1, 0, 1};

    // generates a random letter
    public static void getRandomLetter(char[] arrayName, int listSize, char[][] gridName, int rowIndexName, int colIndexName) {
        char randomLetter = arrayName[(int) (Math.random() * listSize)];
        gridName[rowIndexName][colIndexName] = randomLetter;
        System.out.print(gridName[rowIndexName][colIndexName] + " ");
    }

    public static void main (String[] args) {
        // get user input
        Scanner input = new Scanner(System.in);
        System.out.println("How many rows? ");
        int numberOfRows = input.nextInt();
        System.out.println("How many columns? ");
        int numberOfColumns = input.nextInt();
        System.out.println("What is the minimum word length? ");
        int minimumWordLength = input.nextInt();
        input.close();

        // create 2d array and letter lists
        char [][] grid = new char [numberOfRows][numberOfColumns];
        char[] vowelList = {'A', 'E', 'I', 'O', 'U'};
        char[] consonantList = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};

        // loops through the grid to insert a random vowel/consonant
        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < grid[rowIndex].length; columnIndex++) {
                if ((Math.random() * 10) <= 2) {
                    getRandomLetter(vowelList, 5, grid, rowIndex, columnIndex);
                } else {
                    getRandomLetter(consonantList, 21, grid, rowIndex, columnIndex);
                }
            }
            System.out.println(" ");
        }

        // adds the content of words.txt into an arraylist
        ArrayList<String> wordList = new ArrayList<String>();
        try {
            File words = new File("words.txt") ;
            Scanner fileReader = new Scanner(words);

            while (fileReader.hasNextLine()) {
                wordList.add(fileReader.nextLine());
            }
            fileReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }


        // searches for a word in the grid in all 8 directions
        ArrayList<String> foundWords = new ArrayList<String>();
        for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < grid[rowIndex].length; columnIndex++) {
                for (String word : wordList) { // going to each word in word.txt

                    for (int dir = 0; dir < 8; dir++) {
                        // resets the variables when the direction changes
                        int tempRow = rowIndex;
                        int tempCol = columnIndex;

                        for (int stringIndex = 0; stringIndex < word.length(); stringIndex++) { // going to access each character in the word
                            if (tempCol >= grid[0].length || tempCol < 0) {
                                break; // breaks if column out of bounds
                            }
                            if (tempRow >= grid.length || tempRow < 0) {
                                break; // breaks if row out of bounds
                            }
                            if (word.charAt(stringIndex) != (grid[tempRow][tempCol])) {
                                break; // breaks if the characters don't match
                            }

                            // when the word has been found, add to the arraylist
                            if (stringIndex == word.length() - 1) {
                                if (word.length() >= minimumWordLength) {
                                    foundWords.add(word);
                                    break;
                                }
                            }

                            // search index moves in the direction
                            tempRow += rowDirection[dir];
                            tempCol += colDirection[dir];

                        }
                    }
                }
            }
        }


        // lambda expression to sort by length then alphabetically
        Collections.sort(foundWords, (elem1, elem2) -> { // swaps or keeps the elements based on their return value
            if (elem1.length() != elem2.length()) {
                return elem1.length() - elem2.length();
            }
            return elem1.compareTo(elem2);
        });


        // print each word
        for (String word : foundWords) {
            System.out.println(word);
        }
    }
}