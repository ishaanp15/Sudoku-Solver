package com.example.table;

import java.lang.*;

public class Sudoku {
    int[] mat[];
    int N; // number of columns/rows.
    int SRN; // square root of N
    int K; // No. Of missing digits

    // Constructor
    Sudoku(int N, int K) {
        this.N = N;
        this.K = K;

        // Compute square root of N
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();

        mat = new int[N][N];
        for(int i=0; i<N; i++)
            for(int j=0; j<N; j++)
                mat[i][j]=0;
    }

    // Sudoku Generator
    public void fillValues() {
        // Fill the diagonal of SRN x SRN matrices
        fillDiagonal();

        // Fill remaining blocks
        fillRemaining(0, SRN);
    }

    // Fill the diagonal SRN number of SRN x SRN matrices
    void fillDiagonal() {

        for (int i = 0; i<N; i=i+SRN)

            // for diagonal box, start coordinates->i==j
            fillBox(i, i);
    }

    // Returns false if given 3 x 3 block contains num.
    boolean unUsedInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;

        return true;
    }

    // Fill a 3 x 3 matrix.
    void fillBox(int row,int col) {
        int num;
        for (int i=0; i<SRN; i++) {
            for (int j=0; j<SRN; j++) {
                do {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));

                mat[row+i][col+j] = num;
            }
        }
    }

    // Random generator
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }

    // Check if safe to put in cell
    boolean CheckIfSafe(int i,int j,int num) {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }

    // check in the row for existence
    boolean unUsedInRow(int i,int num) {
        for (int j = 0; j<N; j++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // check in the column for existence
    boolean unUsedInCol(int j,int num) {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }

    // A recursive function to fill remaining
    // matrix
    boolean fillRemaining(int i, int j) {
        //  System.out.println(i+" "+j);
        if (j>=N && i<N-1) {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;

        if (i < SRN) {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN) {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else {
            if (j == N-SRN) {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }

        for (int num = 1; num<=N; num++){
            if (CheckIfSafe(i, j, num)) {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;

                mat[i][j] = 0;
            }
        }
        return false;
    }

    // Remove the K no. of digits to
    public void removeKDigits(){
        int count = K;
        while (count != 0){
            int cellId = randomGenerator(N*N);

            int i = (cellId/N);
            int j = cellId%9;
            if (i == 9)
                i--;
            if (j == 9)
                j--;

            if (mat[i][j]!=0){
                count--;
                mat[i][j] = 0;
            }
        }
    }

    // get sudoku
    public int getSudoku(int i, int j)
    {
        return mat[i][j];
    }

    //add two matrix
    public void addTwoMatrix(Sudoku m1 , Sudoku m2)
    {
        for (int i = 0; i<N; i=i+1)
            for (int j = 0; j<N; j=j+1)
                mat[i][j] = m1.getSudoku(i,j) + m2.getSudoku(i,j);
    }

    //add to specific position of matrix
    public void addToPos(int r , int c , int num)
    {
        mat[r][c] = num;
    }

    //helper for solveSudoku()
    public boolean isSafe(int row, int col, int num)
    {
        // Row has the unique (row-clash)
        // Check if the number we are trying to
        // place is already present in
        // that row, return false;
        for (int d = 0; d < mat.length; d++)
            if (mat[row][d] == num)
                return false;

        // Column has the unique numbers (column-clash)
        // Check if the number
        // we are trying to
        // place is already present in
        // that column, return false;
        for (int[] ints : mat)
            if (ints[col] == num) {
                return false;
            }

        // Corresponding square has
        // unique number (box-clash)
        int sqrt = (int)Math.sqrt(mat.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;

        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++)
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++)
                if (mat[r][d] == num)
                    return false;

        // if there is no clash, it's safe
        return true;
    }

    // solves sudoku and return
    public boolean solveSudoku()
    {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
                if (mat[i][j] == 0) {
                    row = i;
                    col = j;

                    // We still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            if (!isEmpty) {
                break;
            }
        }

        // No empty space left
        if (isEmpty)
            return true;

        // Else for each-row backtrack
        for (int num = 1; num <= N; num++)
            if (isSafe(row, col, num)) {
                mat[row][col] = num;
                // print(board, n);
                // replace it
                if (solveSudoku())
                    return true;
                else mat[row][col] = 0;
            }
        return false;
    }
}
