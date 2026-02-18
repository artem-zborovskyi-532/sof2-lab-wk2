package sof2week02softwarelab;

import java.util.Arrays;

public class TextUtils {
    public static void main(String[] args) {
        // Ex 1
        int res = toBase10("10001011");
        System.out.println(res);

        // Ex 2
        String inpt = "   Hello world jjj     hhh    ";
        String[] res2 = split(inpt);
        System.out.println(Arrays.toString(res2));

        // Ex 3
        String inpt2 = "   H,ello w/orld jjj     hhh    ";
        String[] res3 = split(inpt2, " /,");
        System.out.println(Arrays.toString(res3));

        // Ex 4
        int[] data = {1, 2, 3, 4, 5, 6};
        int[][] matrix = rasterise(data, 3);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Exercise 1:
    // Write a static method int toBase10(String binary) that take a String representation
    // of a binary number (base 2), convert it into a decimal number (base 10) and return the base 10
    // value.

    public static int toBase10(String binary) {
        int base10 = 0;

        for (int i = 0; i < binary.length(); i++) {
            int binaryBit = binary.charAt(i) - '0';
            base10 += Math.pow(2, binary.length() - 1 - i) * binaryBit;
        }

        return base10;
    }

    // Exercise 2: reinventing the wheel! (again)
    // For this question we are emulating the method split() from the type str In Python. In the
    // class TextUtils implement the static method String[] split(String text) where
    // text is a string. The method returns an array of String which contains the words from the text
    // (split by a blank space).
    // You must NOT use the any existing classes such as StringTokenizer to solve the
    // problem.

    public static String[] split(String text) {
        final char separator = ' ';

        StringBuilder word = new StringBuilder();
        String[] words = new String[0];

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (currentChar == separator) {
                if (!word.isEmpty()) {
                    String[] newWords = Arrays.copyOf(words, words.length + 1);
                    newWords[newWords.length - 1] = word.toString();
                    words = newWords;
                    word.setLength(0);
                }
            } else {
                word.append(currentChar);
            }
        }

        if (!word.isEmpty()) {
            String[] newWords = Arrays.copyOf(words, words.length + 1);
            newWords[newWords.length - 1] = word.toString();
            words = newWords;
        }

        return words;
    }

    // Exercise 3: a more flexible split.
    // In TextUtils, overload the method split(String text, String separators)
    // where text is a string to be split, separators is a string containing all the characters used
    // to split the text (for example “,.!? “). The method returns an array of String containing the
    // list of tokens separated by one of the separators.

    public static String[] split(String text, String separators) {
        StringBuilder word = new StringBuilder();
        String[] words = new String[0];

        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);

            if (separators.indexOf(currentChar) != -1) {
                if (!word.isEmpty()) {
                    String[] newWords = Arrays.copyOf(words, words.length + 1);
                    newWords[newWords.length - 1] = word.toString();
                    words = newWords;
                    word.setLength(0);
                }
            } else {
                word.append(currentChar);
            }
        }

        if (!word.isEmpty()) {
            String[] newWords = Arrays.copyOf(words, words.length + 1);
            newWords[newWords.length - 1] = word.toString();
            words = newWords;
        }

        return words;
    }

    // Exercise 4:
    // Write a static method rasterise(int[] data, int width) that transforms a 1D
    // array passed as parameter into a 2D array, where each sub-array have width elements. If the
    // length of the 1D array is not a multiple of width, the method should return null.
        // For example:
        // rasterise({1,2,3,4,5,6,7,8},4)→ {{1,2,3,4},{5,6,7,8}}
        // rasterise({1,2,3,4,5,6,7,8},3)→ null

    public static int[][] rasterise(int[] data, int width) {
        if (data.length % width != 0) {
            return null;
        }

        int numOfSubArrays = data.length / width;
        int[][] matrix = new int[numOfSubArrays][width];
        int startIndex = 0;
        int subArrayIndex = 0;
        
        for (int i = 0; i < numOfSubArrays; i++) {
            for (int j = startIndex; j < startIndex + width; j++) {
                matrix[i][subArrayIndex] = data[j];
                subArrayIndex++;
            }

            startIndex += width;
            subArrayIndex = 0;
        }

        return matrix;
    }
}