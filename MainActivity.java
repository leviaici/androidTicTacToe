package com.firstapp.x0;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView ScoreOne, ScoreTwo, Time;
    private Button[] buttons = new Button[9];
    private Button Reset;

    private int so, st, counter;
    private boolean active;

    int w = 0;
    int board[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ScoreOne = (TextView)findViewById(R.id.ScoreOne);
        ScoreTwo = (TextView)findViewById(R.id.ScoreTwo);
        Time = (TextView)findViewById(R.id.Time);
        Time.setText(formatter.format(date));

        Reset = (Button) findViewById(R.id.Reset);

        for(int i = 0; i < buttons.length; i++) {
            String bID = "Button_" + i;
            int rID = getResources().getIdentifier(bID, "id", getPackageName());
            buttons[i] = (Button)findViewById(rID);
            buttons[i].setOnClickListener(this);
        }

        counter = 0;
        so = 0;
        st = 0;
        active = true;
    }

    @Override
    public void onClick (View view) {
        if(!((Button)view).getText().toString().equals(""))
            return;

        String bID = view.getResources().getResourceEntryName(view.getId());
        int gameState = Integer.parseInt(bID.substring(bID.length() - 1, bID.length()));

        if(active) {
            ((Button) view).setText("X");
            board[gameState] = 2;
        } else {
            board[gameState] = 1;
            ((Button)view).setText("O");
        }
        counter++;

        if(checkWinner()) {
            if(active) {
                so++;
                updateScore();
                Toast.makeText(this, "First player has won!", Toast.LENGTH_SHORT).show();
                reset();
            }else {
                st++;
                updateScore();
                Toast.makeText(this, "Second player has won!", Toast.LENGTH_SHORT).show();
                reset();
            }
        }else if(counter == 9) {
            reset();
            Toast.makeText(this, "You both suck!", Toast.LENGTH_SHORT).show();
        } else active = !active;

        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view) {
                        reset();
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                        Time.setText(formatter.format(date));
                        so = 0;
                        st = 0;
                        updateScore();
            }
        });
    }

    public boolean checkWinner() {
        for(int i = 0; i < 3; i++) {
            if(board[i * 3] == board[(i * 3) + 1] && board[i*3] == board[(i * 3) + 2] && board[i * 3] != 0) {
                w = board[i * 3];
                return true;
            } else if(board[0 + i] == board[3 + i] && board[0 + i] == board[6 + i] && board[0 + i] != 0) {
                w = board[0 + i];
                return true;
            }
        }
        if(board[0] == board[4] && board[0] == board[8] && board[0] != 0) {
            w = board[0];
            return true;
        } else if(board[2] == board[4] && board[2] == board[6] && board[2] != 0) {
            w = board[2];
            return true;
        }
        return false;
    }

    public void updateScore() {
        ScoreOne.setText(Integer.toString(so));
        ScoreTwo.setText(Integer.toString(st));
    }

    public void reset() {
        counter = 0;
        active = true;

        for(int i = 0; i < buttons.length; i++) {
            board[i] = 0;
            buttons[i].setText("");
        }
    }
}
