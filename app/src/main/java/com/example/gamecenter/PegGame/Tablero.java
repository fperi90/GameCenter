package com.example.gamecenter.PegGame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.gamecenter.DBHelper;
import com.example.gamecenter.EndGameActivity;
import com.example.gamecenter.R;

public class Tablero {

    int disponible = R.drawable.ic_disponible;
    int no_Disponible = R.drawable.ic_no_disponible;
    int seleccionado = R.drawable.ic_selected;
    int bloqueado = R.drawable.ic_bloqueado;


    private final int[][] IMAGE_BUTTON_IDS = {
            {R.id.imageButton0, R.id.imageButton1, R.id.imageButton2, R.id.imageButton3,
                    R.id.imageButton4, R.id.imageButton5, R.id.imageButton6},
            {R.id.imageButton7, R.id.imageButton8, R.id.imageButton9, R.id.imageButton10,
                    R.id.imageButton11, R.id.imageButton12, R.id.imageButton13},
            {R.id.imageButton14, R.id.imageButton15, R.id.imageButton16, R.id.imageButton17,
                    R.id.imageButton18, R.id.imageButton19, R.id.imageButton20},
            {R.id.imageButton21, R.id.imageButton22, R.id.imageButton23, R.id.imageButton24,
                    R.id.imageButton25, R.id.imageButton26, R.id.imageButton27},
            {R.id.imageButton28, R.id.imageButton29, R.id.imageButton30, R.id.imageButton31,
                    R.id.imageButton32, R.id.imageButton33, R.id.imageButton34},
            {R.id.imageButton35, R.id.imageButton36, R.id.imageButton37, R.id.imageButton38,
                    R.id.imageButton39, R.id.imageButton40, R.id.imageButton41},
            {R.id.imageButton42, R.id.imageButton43, R.id.imageButton44, R.id.imageButton45,
                    R.id.imageButton46, R.id.imageButton47, R.id.imageButton48}
    };

    private final int[] IMAGE_BUTTON_NULL = {
            R.id.imageButton0, R.id.imageButton1, R.id.imageButton5, R.id.imageButton6,
            R.id.imageButton7, R.id.imageButton8, R.id.imageButton12, R.id.imageButton13,
            R.id.imageButton35, R.id.imageButton36, R.id.imageButton40, R.id.imageButton41,
            R.id.imageButton42, R.id.imageButton43, R.id.imageButton47, R.id.imageButton48
    };

    private final int[][] COPIA_IMAGE_BUTTON_IDS = IMAGE_BUTTON_IDS;

    private Activity activityContext;
    private Context contexto;
    private PegBoard pegBoard;
    private Chronometer chronometer;
    private long timeBeforeStopped;
    public static TextView pegScoreTextView;

    private int score;

    private final int START = 1, STOP = 2, RESTART = 3;

    private DBHelper helper;

    private String userName;
    private TextView recordTextView;
    private int highScore;

    public Tablero(Activity activity, String userName) {

        this.activityContext = activity;
        this.userName = userName;
        contexto = activity.getBaseContext();
        pegBoard = new PegBoard(2);
        pegScoreTextView = (TextView) activity.findViewById(R.id.pegScore);
        pegScoreTextView.setText("SCORE \n" + String.valueOf(pegBoard.getPuntuacion()));
        helper = new DBHelper(contexto);
        recordTextView = (TextView) activity.findViewById(R.id.pegHighScore);
        highScore = helper.getPEGHishScore(userName);
        recordTextView.setText(String.valueOf(highScore));

        addListeners();
        updateView();
  /*      fillNulls();
        fillTable();*/

    }

    private void addListeners() {
        Button button = (Button) activityContext.findViewById((R.id.undoButton));
        button.setOnClickListener(view -> {
            pegBoard.undo();
            updateView();
        });
        chronometer =
                (Chronometer) activityContext.findViewById(R.id.textViewChromoneter);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setFormat("Time Running - %s"); // set the format for a
 /*       chronometer.start(); // start a chronometer
        chronometer.setFormat("Time Running - %s"); // set the format for a
        // chronometer*/

        for (int x = 0; x < IMAGE_BUTTON_IDS.length; x++) {
            for (int y = 0; y < IMAGE_BUTTON_IDS[0].length; y++) {
                ImageButton imageButton =
                        (ImageButton) activityContext.findViewById(IMAGE_BUTTON_IDS[x][y]);
                int finalY = y;
                int finalX = x;
                imageButton.setOnClickListener(view -> {
                    pegBoard.select(finalX, finalY);
                    updateView();
                });
            }
        }
        Button standard =
                (Button) activityContext.findViewById(R.id.pegStandard);
        standard.setOnClickListener(view -> {

            pegBoard = new PegBoard(1);
            updateView();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        });
        Button french =
                (Button) activityContext.findViewById(R.id.pegFrench);
        french.setOnClickListener(view -> {

            pegBoard = new PegBoard(2);
            updateView();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        });
        Button square =
                (Button) activityContext.findViewById(R.id.pegSquare);
        square.setOnClickListener(view -> {
            pegBoard = new PegBoard(3);
            updateView();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        });
        Button test =
                (Button) activityContext.findViewById(R.id.pegTest);
        test.setOnClickListener(view -> {
            pegBoard = new PegBoard(4);
            updateView();
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
        });


    }

    private void updateImageButton() {
        for (int i = 0; i < IMAGE_BUTTON_IDS.length; i++) {
            for (int j = 0; j < IMAGE_BUTTON_IDS[0].length; j++) {
                IMAGE_BUTTON_IDS[i][j] = disponible;
            }
        }
    }

    private void updateView() {
        addScore();
        if (pegBoard.getGameStatus() == START) chronometer.start();
        if (pegBoard.getGameStatus() == STOP) {
            timeBeforeStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
            chronometer.stop();
            executeEndGame();
        }
        int[][] panelCopy = pegBoard.getCells();
        for (int x = 0; x < panelCopy.length; x++) {
            for (int y = 0; y < panelCopy[0].length; y++) {
                int valor = panelCopy[x][y];
                ImageButton imageButton;
                switch (valor) {
                    case -1:
                        imageButton =
                                activityContext.findViewById(IMAGE_BUTTON_IDS[x][y]);
                        imageButton.setImageDrawable(ContextCompat.getDrawable(contexto, bloqueado));
                        break;
                    case 1:
                        imageButton =
                                activityContext.findViewById(IMAGE_BUTTON_IDS[x][y]);
                        imageButton.setImageDrawable(ContextCompat.getDrawable(contexto, disponible));
                        break;
                    case 0:
                        imageButton =
                                activityContext.findViewById(IMAGE_BUTTON_IDS[x][y]);
                        imageButton.setImageDrawable(ContextCompat.getDrawable(contexto, no_Disponible));
                        break;
                }
            }
        }
        int[] coordenada = pegBoard.getSelected();
        if (coordenada != null) {
            ImageButton imageButton =
                    activityContext.findViewById(IMAGE_BUTTON_IDS[coordenada[0]][coordenada[1]]);
            imageButton.setImageDrawable(ContextCompat.getDrawable(contexto,
                    seleccionado));
        }
    }

    private void executeEndGame() {
        score = pegBoard.getPuntuacion();
        TextView userTextView =
                (TextView) activityContext.findViewById(R.id.gameUserPeg);
        userName = userTextView.getText().toString();
        System.out.println(userTextView.getText());
        System.out.println(score);
        helper.addPegScore(userName, score);
        toEndGameActivity();
    }

    private void toEndGameActivity() {
        Intent intent = new Intent(activityContext, EndGameActivity.class);
        intent.putExtra("username", userName);
        activityContext.startActivity(intent);
    }

    private void addScore() {
        pegScoreTextView.setText(String.valueOf(pegBoard.getPuntuacion()));
    }
}
