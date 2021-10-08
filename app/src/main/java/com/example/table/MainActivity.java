package com.example.table;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {

    TextView textView = null;
    TextView diff_lvl;
    TextView numbutt = null;
    TableRow tb;
    Sudoku sudoku = new Sudoku(9, 60);
    Sudoku userInp = new Sudoku(9,60);
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("dif_lvl", MODE_PRIVATE);
        k = sp.getInt("setting.level", 55);
        diff_lvl = findViewById(R.id.textView);
        diff_lvl.setText(sp.getString("diff_name", "Medium"));
        diff_lvl.setTextColor(sp.getInt("colour", 0xFFFFE400));

        refresh(null);

    }

    //selecting the element of sudoku on clicking
    public void select(View v){
        if(textView!=null)
            textView.setBackgroundColor(0x00000000);
        textView = findViewById(v.getId());
        textView.setBackgroundColor(0xFF00abfa);
    }

    //entry of an no at an element place
    public void entry(View v){
        if(textView==null){
            Toast.makeText(this, "no element selected", Toast.LENGTH_SHORT).show();
            return;
        }
        numbutt = findViewById(v.getId());
        String s = numbutt.getText().toString();
        textView.setText(s);

        //Updating user sudoku
        String id=getResources().getResourceEntryName(textView.getId());
        char r = id.charAt(2);
        char c = id.charAt(3);
        int num = Integer.parseInt(s);
        int r1 = Character.getNumericValue(r);
        int c1 = Character.getNumericValue(c);
        userInp.addToPos(r1-1,c1-1,num);
    }

    //Putting the sudoku in the Sudoku view
    public void updtsdk(Sudoku s){
        Resources r = getResources();
        String name = getPackageName();
        for(int i=1; i<10; i++){
            tb = findViewById(r.getIdentifier("tb" + i, "id", name));
            for(int j=0; j<9; j++){
                View v2 = tb.getVirtualChildAt(j);
                textView = findViewById(v2.getId());
                if(s.mat[i-1][j]!=0) {
                    textView.setText(String.valueOf(s.getSudoku(i - 1, j)));
                    textView.setBackgroundColor(0xFFB8CFED);
                }
                else {
                    textView.setText("");
                    textView.setBackgroundColor(0x00000000);
                }
            }
        }
    }

    //clear the sudoku board
    public void clearAll(View v){
        sudoku = new Sudoku(9,0);
        updtsdk(sudoku);
        start(sudoku);
    }

    public void refresh(View v){
        end();
        int K = k + (int) Math.floor((Math.random()*6));
        sudoku = new Sudoku(9,K);
        userInp = new Sudoku(9, K);
        sudoku.fillValues();
        sudoku.removeKDigits();
        updtsdk(sudoku);
        start(sudoku);
    }

    //Solving with user input
    public void solveUserInput(View v) {
        userInp.addTwoMatrix(userInp,sudoku);
        if (userInp.solveSudoku()) {
            Toast.makeText(this, "Solved using user input", Toast.LENGTH_SHORT).show();
            updtsdk(userInp);
        }

        else{
            Toast.makeText(this, "User input was wrong , solved without them", Toast.LENGTH_SHORT).show();
            sudoku.solveSudoku();
            updtsdk(sudoku);
        }

        end();
    }

    public void start(Sudoku s){
        Resources r = getResources();
        String name = getPackageName();
        for(int i=1; i<10; i++){
            tb = findViewById(r.getIdentifier("tb" + i, "id", name));
            for(int j=0; j<9; j++){
                View v2 = tb.getVirtualChildAt(j);
                textView = findViewById(v2.getId());
                if(s.mat[i-1][j]==0)
                    textView.setClickable(true);
            }
        }

        textView = null;

        for(int i=1; i<10; i++){
            numbutt = findViewById(r.getIdentifier("num" + i, "id", name));
            numbutt.setClickable(false);
            numbutt.setClickable(true);
        }

        numbutt = null;
    }

    public void end(){
        Resources r = getResources();
        String name = getPackageName();
        for(int i=1; i<10; i++){
            tb = findViewById(r.getIdentifier("tb" + i, "id", name));
            for(int j=0; j<9; j++){
                View v2 = tb.getVirtualChildAt(j);
                textView = findViewById(v2.getId());
                textView.setClickable(false);
            }
        }

        for(int i=1; i<10; i++){
            numbutt = findViewById(r.getIdentifier("num" + i, "id", name));
            numbutt.setClickable(false);
        }
    }
}