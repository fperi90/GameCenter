package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Game2048 extends AppCompatActivity implements GestureDetector.OnGestureListener {
    //los siguientes atributos son los relacionados con el tablero.
    private static final int[] BUTTON_IDS = {
            R.id.button, R.id.button2, R.id.button3, R.id.button4,
            R.id.button5, R.id.button6, R.id.button7, R.id.button8,
            R.id.button9, R.id.button10, R.id.button11, R.id.button12,
            R.id.button13, R.id.button14, R.id.button15, R.id.button16
    };

    ArrayList<Button> primeraFila;
    ArrayList<Button> segundaFila;
    ArrayList<Button> terceraFila;
    ArrayList<Button> cuartaFila;
    ArrayList<Button> primeraColumna;
    ArrayList<Button> segundaColumna;
    ArrayList<Button> terceraColumna;
    ArrayList<Button> cuartaColumna;
    ArrayList<ArrayList<Button>> totalFilas;
    ArrayList<ArrayList<Button>> totalColumnas;
    List<Button> tablero;
    TextView puntuacion;
    int sumadorPuntuacion;
    ArrayList<Integer> nuevosValores;
    ArrayList<Integer> antiguosValores;

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<esto lo ha hecho jose
    Button[][] matrizTablero = new Button[4][4];
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    View panel;
    //el detector de gestos, para poder saber como se mueve el dedo del
    // usuario por la pantalla
    private GestureDetector detector;
    private static final String DEBUG_TAG = "MENSAJE GESTOS";
    private float x1, x2, y1, y2;
    private static final int MINIMUM_DISTANCE = 150;

    private Intent intent;
    private String userName = "toro";
    private TextView userNameTextView;
    private TextView recordTextView;
    private int highScore;

    private DBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2048);
        intent = getIntent();
        /*userName = intent.getExtras().getString("username");*/
        userNameTextView = (TextView) findViewById(R.id.gameUser);
        userNameTextView.setText(" User: \n" + userName);
        recordTextView = (TextView) findViewById(R.id.textViewRecord);
        helper = new DBHelper(this);
        highScore = helper.get2048HishScore(userName);
        recordTextView.setText(String.valueOf(highScore));
        tablero = new ArrayList<Button>();
        totalFilas = new ArrayList<>();
        totalColumnas = new ArrayList<>();
        primeraFila = new ArrayList<>();
        segundaFila = new ArrayList<>();
        terceraFila = new ArrayList<>();
        cuartaFila = new ArrayList<>();
        primeraColumna = new ArrayList<>();
        segundaColumna = new ArrayList<>();
        terceraColumna = new ArrayList<>();
        cuartaColumna = new ArrayList<>();
        puntuacion = (TextView) findViewById(R.id.puntuacion);
        sumadorPuntuacion = 0;

        //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<esto lo ha hecho jose
        asignarBotonesAlTablero();
        //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

        for (int id : BUTTON_IDS) {
            Button button = (Button) findViewById(id);
            tablero.add(button);
            button.setText("0");
        }
        agregarBotonesEnLinea();
        panel = (View) findViewById(R.id.gridLayout);
        detector = new GestureDetector(Game2048.this, this);
        replaceButtonColors();
    }

    private void agregarBotonesEnLinea() {
        for (int i = 0; i < 4; i++) {
            primeraFila.add(tablero.get(i));
            segundaFila.add(tablero.get(i + 4));
            terceraFila.add(tablero.get(i + 8));
            cuartaFila.add(tablero.get(i + 12));
        }
        //este metodo tengo que parametrizarlo
        primeraColumna.add(tablero.get(0));
        primeraColumna.add(tablero.get(4));
        primeraColumna.add(tablero.get(8));
        primeraColumna.add(tablero.get(12));

        segundaColumna.add(tablero.get(1));
        segundaColumna.add(tablero.get(5));
        segundaColumna.add(tablero.get(9));
        segundaColumna.add(tablero.get(13));

        terceraColumna.add(tablero.get(2));
        terceraColumna.add(tablero.get(6));
        terceraColumna.add(tablero.get(10));
        terceraColumna.add(tablero.get(14));
        cuartaColumna.add(tablero.get(3));
        cuartaColumna.add(tablero.get(7));
        cuartaColumna.add(tablero.get(11));
        cuartaColumna.add(tablero.get(15));
        totalFilas.add(primeraFila);
        totalFilas.add(segundaFila);
        totalFilas.add(terceraFila);
        totalFilas.add(cuartaFila);
        totalColumnas.add(primeraColumna);
        totalColumnas.add(segundaColumna);
        totalColumnas.add(terceraColumna);
        totalColumnas.add(cuartaColumna);
    }


    private void asignarBotonesAlTablero() {
        int auxiliar = 0;
        for (int x = 0; x <= 3; x++) {
            for (int y = 0; y <= 3; y++) {
                matrizTablero[x][y] =
                        findViewById(BUTTON_IDS[x + (y + auxiliar)]);
                System.out.print("[" + x + "][" + y + "] ");
            }
            auxiliar = auxiliar + 3;
        }
    }

    //MÉTODO QUE COLOCA ALEATORIAMENTE DOS NUMEROS NADA MAS COMENZAR LA PARTIDA.
    public void comenzar(View view) {

        //  BUCLE CORRECTO PARA PODER
        // RECORRER EL
        // TABLERO
        boolean colocado = false;
        int todosColocados = 0;
        while (!colocado) {
            if (todosColocados < 2) {
                int getRandomNumber = (int) (Math.random() * 16);
                if (tablero.get(getRandomNumber).getText().equals("0")) {
                    tablero.get(getRandomNumber).setText("2");
                    todosColocados++;
                }
            } else if (todosColocados == 2) {
                colocado = true;
            }
        }
        replaceButtonColors();
        view.setVisibility(View.GONE);
/*
        ESTE FUE EL PRIMER METODO FOR PARA VER COMO PODIA ACCEDER A LOS
        BOTONES CON VALORES ALEATORIOS---> NO SE USA*/

     /*   for (int i = 0; i < 12; i++) {
            tablero.get((int) (Math.random() * 16)).setText("2");
            // con esta instruccion hacemos desaparecer el boton, tampoco
            // estará presente fisicamente.
            view.setVisibility(View.GONE);
        }*/
    }

    private void añadirNumeroRandom() {
        boolean numeroColocado = false;
        boolean tableroLleno = true;
        tableroLleno = isTableroLleno(tableroLleno);
        while (!numeroColocado) {
            int probabilidad = (int) (Math.random() * 10);
            int randomButton = (int) (Math.random() * 16);
            if (tablero.get(randomButton).getText().equals("0")) {
                if (probabilidad < 5) {
                    tablero.get(randomButton).setText("2");
                    numeroColocado = true;
                } else {
                    tablero.get(randomButton).setText("4");
                    numeroColocado = true;
                }
            } else if (tableroLleno == true) {
                numeroColocado = true;
                System.out.println("no se pueden colocar más numeros");
            } else {
                System.out.println("imposible salir del bucle");
            }
        }replaceButtonColors();
    }

    private void comprobarVictoria() {
        for (int i = 0; i < tablero.size(); i++) {
            if (tablero.get(i).getText().equals("2048")) {
                Toast.makeText(this, "Has ganado!", Toast.LENGTH_SHORT).show();
      /*

                AQUI HAY QUE AÑADIR LOS INSERTS PARA LA BASE DE DATOS.

                */

            }
        }
    }

    private void comprobarPerdido(ArrayList<ArrayList<Button>> arrayDeFilas,
                                  ArrayList<ArrayList<Button>> arrayDeColumnas) {
        int sumadorHorizontalSinMov = 0;
        int sumadorVerticalSinMov = 0;
        boolean tableroLleno = true;
        tableroLleno = isTableroLleno(tableroLleno);
        if (tableroLleno) {
            for (ArrayList<Button> fila : arrayDeFilas) {
                if (!(fila.get(0).getText().equals(fila.get(1).getText()))
                        && !(fila.get(1).getText().equals(fila.get(2).getText()))
                        && !(fila.get(2).getText().equals(fila.get(3).getText()))
                ) {
                    sumadorHorizontalSinMov++;
                }
            }
            for (ArrayList<Button> columna : arrayDeColumnas) {
                if (!(columna.get(0).getText().equals(columna.get(1).getText()))
                        && !(columna.get(1).getText().equals(columna.get(2).getText()))
                        && !(columna.get(2).getText().equals(columna.get(3).getText()))
                ) {
                    sumadorVerticalSinMov++;
                }
            }
            if ((sumadorHorizontalSinMov == 4) && (sumadorVerticalSinMov == 4)) {
                Toast.makeText(this, "Has perdido, no mas movimientos posibles", Toast.LENGTH_SHORT).show();
                System.out.println("!!!!!!!!!!!!!!!! " + userName + " con " +
                        "puntuacion " + sumadorPuntuacion);
                helper.add2048Score(userName, sumadorPuntuacion);
                toGameOverActivity();
            }
        }
    }

    private void toGameOverActivity() {
        Intent intent = new Intent(this, EndGameActivity.class);
        intent.putExtra("username", userName);
        startActivity(intent);
    }

    private boolean isTableroLleno(boolean tableroLleno) {
        for (int i = 0; i < tablero.size(); i++) {
            if (tablero.get(i).getText().equals("0")) {
                tableroLleno = false;
            }
        }
        return tableroLleno;
    }

    private boolean ejecutarTablero(int a, int b, int c, int d,
                                    ArrayList<ArrayList<Button>> arrayDeFilas) {
        int sumatorio = 0;
        nuevosValores = new ArrayList<>();
        //este método hace que cualquier cero que se localize en una posicion
        // inferior, se desplace automaticamente hacia el margen señalado por
        // el movimiento
        for (ArrayList<Button> Fila : arrayDeFilas) {
            for (int i = 0; i <= 3; i++) {
                if (Fila.get(a).getText().equals("0")) {
                    Fila.get(a).setText(Fila.get(b).getText());
                    Fila.get(b).setText(Fila.get(c).getText());
                    Fila.get(c).setText(Fila.get(d).getText());
                    Fila.get(d).setText("0");
                } else if (Fila.get(b).getText().equals("0")) {
                    Fila.get(b).setText(Fila.get(c).getText());
                    Fila.get(c).setText(Fila.get(d).getText());
                    Fila.get(d).setText("0");
                } else if (Fila.get(c).getText().equals("0")) {
                    Fila.get(c).setText(Fila.get(d).getText());
                    Fila.get(d).setText("0");
                }
            }
            //a copntinuación se describen en la parte superior de los
            // apartados la condición que se esta evaluando
            //  [A][A][?][?]
            if (Fila.get(a).getText().equals(Fila.get(b).getText())) {
                sumatorio =
                        Integer.parseInt(Fila.get(b).getText().toString());
                Fila.get(a).setText(String.valueOf(sumatorio * 2));
                sumarPuntos(sumatorio);
                //  [A][A][B][B]    ==>  [A][B][0][0]
                if (Fila.get(c).getText().equals(Fila.get(d).getText())) {
                    sumatorio =
                            Integer.parseInt(Fila.get(c).getText().toString());
                    Fila.get(b).setText(String.valueOf(sumatorio * 2));
                    sumarPuntos(sumatorio);
                    Fila.get(c).setText("0");
                    Fila.get(d).setText("0");
                }
                // [A][A][B][C]  ==  >  [A][B][C][0]
                else {
                    Fila.get(b).setText(Fila.get(c).getText().toString());
                    Fila.get(c).setText(Fila.get(d).getText().toString());
                    Fila.get(d).setText("0");
                }
                //[A][B][?][?]
            } else {
                //[A][B][B][C]   ==>  [A][B][C][0]
                if (Fila.get(b).getText().equals(Fila.get(c).getText())) {
                    sumatorio =
                            Integer.parseInt(Fila.get(b).getText().toString());
                    Fila.get(b).setText(String.valueOf(sumatorio * 2));
                    sumarPuntos(sumatorio);
                    Fila.get(c).setText(Fila.get(d).getText().toString());
                    Fila.get(d).setText("0");
                }
                //[A][B][C][C]
                else {
                    if (Fila.get(c).getText().equals(Fila.get(d).getText())) {
                        sumatorio =
                                Integer.parseInt(Fila.get(c).getText().toString());
                        Fila.get(c).setText(String.valueOf(sumatorio * 2));
                        sumarPuntos(sumatorio);
                        Fila.get(d).setText("0");
                    } else {
                        //caso en el que todos los valores son
                        // diferentes  ====>  [A][B][C][D]
                    }
                }
            }
        }
        for (Button button : tablero) {
            nuevosValores.add(Integer.parseInt(button.getText().toString()));
        }

        replaceButtonColors();


        return true;
    }

    private void replaceButtonColors() {
        for (Button button : tablero) {
            if (button.getText().toString().equals("0")) {
                button.setBackgroundResource(R.drawable.btn_2048_0_style);
            }
            if (button.getText().toString().equals("2")) {
                button.setBackgroundResource(R.drawable.btn_2048_2_style);
            }
            if (button.getText().toString().equals("4")) {
                button.setBackgroundResource(R.drawable.btn_2048_4_style);
            }
            if (button.getText().toString().equals("8")) {
                button.setBackgroundResource(R.drawable.btn_2048_8_style);
            }
            if (button.getText().toString().equals("16")) {
                button.setBackgroundResource(R.drawable.btn_2048_16_style);
            }
            if (button.getText().toString().equals("32")) {
                button.setBackgroundResource(R.drawable.btn_2048_32_style);
            }
            if (button.getText().toString().equals("64")) {
                button.setBackgroundResource(R.drawable.btn_2048_64_style);
            }
            if (button.getText().toString().equals("128")) {
                button.setBackgroundResource(R.drawable.btn_2048_128_style);
            }
            if (button.getText().toString().equals("256")) {
                button.setBackgroundResource(R.drawable.btn_2048_256_style);
            }
            if (button.getText().toString().equals("512")) {
                button.setBackgroundResource(R.drawable.btn_2048_512_style);
            }
            if (button.getText().toString().equals("1024")) {
                button.setBackgroundResource(R.drawable.btn_2048_1024_style);
            }
            if (button.getText().toString().equals("2048")) {
                button.setBackgroundResource(R.drawable.btn_2048_2048_style);
            }


        }
    }

    private void sumarPuntos(int sumatorio) {
        sumadorPuntuacion += sumatorio * 2;
        puntuacion.setText("SCORE \n" + String.valueOf(sumadorPuntuacion));
    }

    //tenemos que sobreescribir este metodo para señalar acciones cuando se
    // toque
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean movimientoRealizado = false;
        antiguosValores = new ArrayList<>();
        for (Button button : tablero) {
            antiguosValores.add(Integer.parseInt(button.getText().toString()));
        }
        detector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float valueOnX = x2 - x1;
                float valueOnY = y2 - y1;

                if (Math.abs(valueOnX) > MINIMUM_DISTANCE) {
                    if (x2 > x1) {
                        movimientoRealizado = ejecutarTablero(3, 2, 1
                                , 0,
                                totalFilas);
   /*                     Toast.makeText(this, "Swipe to the right",
                                Toast.LENGTH_SHORT).show();*/
                        Log.d(DEBUG_TAG, "SWIPE TO RIGHT ACCOMPLISHED.");
                        if (movimientoRealizado) {
                            comprobarEstadoJuego();
                        } else {
                            System.out.println("naranjas de la china");
                        }
                    } else {
                        movimientoRealizado = ejecutarTablero(0, 1, 2, 3, totalFilas);
                        if (movimientoRealizado) {
                            comprobarEstadoJuego();
                        } else {
                            System.out.println("naranjas de la china");
                        }

                    /*    Toast.makeText(this, "Swipe to the left",
                                Toast.LENGTH_SHORT).show();*/
                        Log.d(DEBUG_TAG, "SWIPE TO LEFT ACCOMPLISHED.");

                    }
                } else if (Math.abs(valueOnY) > MINIMUM_DISTANCE) {
                    if (y2 > y1) {
                        movimientoRealizado = ejecutarTablero(3, 2, 1, 0, totalColumnas);
                        if (movimientoRealizado) {
                            comprobarEstadoJuego();
                        } else {
                            System.out.println("naranjas de la china");
                        }
                    /*    Toast.makeText(this, "Swipe to the bottom",
                                Toast.LENGTH_SHORT).show();*/
                        Log.d(DEBUG_TAG, "SWIPE TO BOTTOM ACCOMPLISHED.");
                    } else {
                        movimientoRealizado = ejecutarTablero(0, 1, 2, 3, totalColumnas);
                        if (movimientoRealizado) {
                            comprobarEstadoJuego();
                        } else {
                            System.out.println("naranjas de la china");
                        }
                     /*   Toast.makeText(this, "Swipe to the top",
                                Toast.LENGTH_SHORT).show();*/
                        Log.d(DEBUG_TAG, "SWIPE TO TOP ACCOMPLISHED.");
                    }
                }
        }
        return super.onTouchEvent(event);
    }

    private void comprobarEstadoJuego() {
        comprobarPerdido(totalFilas, totalColumnas);
        if (hayCambio()) {
            añadirNumeroRandom();
            comprobarVictoria();
        } else {
            System.out.println("tablero sin cambios, no se ejecuta");
        }
    }

    //este método sirve para comparar dos arrayslist con los valores que
    // almacenaban antes y despues los botones de la tabla... si no hay
    // cambios devuelve false al metodo.
    private boolean hayCambio() {
        for (int i = 0; i < nuevosValores.size(); i++) {
            if (antiguosValores.get(i) != nuevosValores.get(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}