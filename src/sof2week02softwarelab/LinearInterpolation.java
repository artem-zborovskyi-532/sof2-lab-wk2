// Problem:
// The aim of this problem is to resample a set of data points store in an array. Write a class
// LinearInterpolation which will contain a set of static methods to resample data point
// using linear interpolation. The class should be in the package sof2week02softwarelab.
// To start with we will look at the simple case where
// we want to (almost) double the number of sample point using linear interpolation. For example,
// given an array of 4 known values, we want to resample the array in order to
// have 7 values. A simple approach is to use linear interpolation (see linear interpolation on
// Wikipedia). To compute the values in the grey boxes, you just have to take the two
// neighbouring boxes, add them together and then divide by 2.

package sof2week02softwarelab;

import java.util.Arrays;

public class LinearInterpolation {
    public static void main(String[] args) {
        // Ex 1
        int[] datapoints = { 1, 5, 13, 21 };
        int[] newDP = resample(datapoints);
        System.out.println(Arrays.toString(newDP));

        // Ex 2
        int[] newDP2 = resample(datapoints, 3);
        System.out.println(Arrays.toString(newDP2));

        // Ex 3
        int[][] image = {{1, 50, 20, 20}, {100, 255, 150, 30}, {10, 255, 130, 210}, {10, 255, 210, 210}};
        int[][] matrix = resample(image);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        // Ex 4
        int[][] matrixScale = resample(image, 3);
        for (int i = 0; i < matrixScale.length; i++) {
            for (int j = 0; j < matrixScale[i].length; j++) {
                System.out.print(matrixScale[i][j] + " ");
            }
            System.out.println();
        }
    }

    // 1. Implement a static method int[] resample(int[] datapoints) that does
    // just that.

    public static int[] resample(int[] datapoints) {
        int[] newDatapoints = new int[datapoints.length * 2 - 1];
        int insertIndex = 0;

        for (int i = 0; i < datapoints.length; i++) {
            newDatapoints[insertIndex] = datapoints[i];
            insertIndex += 2;
        }

        for (int i = 1; i < newDatapoints.length - 1; i += 2) {
            newDatapoints[i] = (newDatapoints[i - 1] + newDatapoints[i + 1]) / 2;
        }

        return newDatapoints;
    }

    // 2. Implement the method int[] resample(int[] datapoints, int scale)
    // that does just that. You will need to understand the formulae given in the section
    // “Linear interpolation between two known points” on the Wikipedia page. In our case
    // the x are the index of the element in the array, and the y are the value stored in the array.

    public static int[] resample(int[] datapoints, int scale) {
        int[] newDatapoints = new int[datapoints.length * scale - scale + 1];
        int insertIndex = 0;

        for (int i = 0; i < datapoints.length; i++) {
            newDatapoints[insertIndex] = datapoints[i];
            insertIndex += scale;
        }

        for (int i = 0; i < datapoints.length - 1; i++) {
            int startIndex = i * scale;
            int endIndex = (i + 1) * scale;

            for (int k = 1; k < scale; k++) {
                float fraction = (float) k / scale;
                newDatapoints[startIndex + k] =
                    Math.round(
                        newDatapoints[startIndex] +
                        fraction * (newDatapoints[endIndex] - newDatapoints[startIndex])
                    );
            }
        }

        return newDatapoints;
    }

    // Bilinear interpolation can be used in image processing to resample an image (scaling up the
    // resolution of an image). In this case, the image can be seen as a 2D array of pixel values.
    // Considering a black & white image (a.k.a. greyscale image), the “colours” range from 0 (black)
    // to 255 (white). For example, to double the size of the 4 × 4 image below, we first build a
    // 7 × 7 array with missing values. To compute the missing values, we first deal with the even
    // rows (0, 2, 4, 6). When considering the row r=0, we can use the linear interpolation seen in 1
    // to complete that row. Then we proceed to the row r=r+2 and so on. Once the rows 0, 2, 4 and
    // 6 have been interpolated, we tackle the columns 0, 1, …, 7. We interpolate one column at a
    // time using the method seen in 1.

    // 3. Implement int[][] resample(int[][] image) which double the size of a
    // greyscale image.

    public static int[][] resample(int[][] image) {
        int rows = image.length;
        int cols = image[0].length;
        int[][] newImage = new int[rows * 2 - 1][cols * 2 - 1];

        int row = 0;
        int el = 0;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                newImage[row][el] = image[i][j];
                el += 2;
            } 

            el = 0;
            row += 2;
        }

        for (int i = 0; i < newImage.length; i += 2) {
            for (int j = 1; j < newImage[i].length; j += 2) {
                newImage[i][j] = (newImage[i][j - 1] + newImage[i][j + 1]) / 2;
            }
        }

        for (int i = 1; i < newImage.length; i += 2) {
            for (int j = 0; j < newImage[i].length; j++) {
                newImage[i][j] = (newImage[i - 1][j] + newImage[i + 1][j]) / 2;
            }
        }

        return newImage;
    }

    // 4. Implement int[][] resample(int[][] image, int scale) which
    // resample a greyscale image given a scale factor.

    public static int[][] resample(int[][] image, int scale) {
        int originalSize = image.length;
        int newSize = originalSize * scale - scale + 1;
        int[][] newImage = new int[newSize][newSize];

        for (int i = 0; i < originalSize; i++) {
            for (int j = 0; j < originalSize; j++) {
                newImage[i * scale][j * scale] = image[i][j];
            }
        }

        for (int i = 0; i < newSize; i += scale) {
            for (int j = 0; j < newSize - scale; j += scale) {
                int start = newImage[i][j];
                int end = newImage[i][j + scale];

                for (int k = 1; k < scale; k++) {
                    float fraction = (float) k / scale;
                    newImage[i][j + k] = Math.round(start + fraction * (end - start));
                }
            }
        }

        for (int j = 0; j < newSize; j++) {
            for (int i = 0; i < newSize - scale; i += scale) {
                int start = newImage[i][j];
                int end = newImage[i + scale][j];

                for (int k = 1; k < scale; k++) {
                    float fraction = (float) k / scale;
                    newImage[i + k][j] = Math.round(start + fraction * (end - start));
                }
            }
        }

        return newImage;
    }
}