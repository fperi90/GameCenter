package com.example.gamecenter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gameCenter.db";
    private static final String TABLE_USERS = "t_users";
    private static final String TABLE_2048_SCORES = "t_2048_scores";
    private static final String TABLE_PEG_SCORES = "t_peg_scores";

    private ArrayList<String> names_2048 = new ArrayList<>();
    private ArrayList<Integer> scores_2048 = new ArrayList<>();
    private ArrayList<Score> scoreList = new ArrayList<>();

    private static final int IMAGE_2048 = 1, PEGIMAGE = 2;

    private String message;

    public String getMessage() {
        message = "MESSAGE FROM DATABASE: " + message;
        return message;
    }

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "password TEXT NOT NULL, " +
                "game2048score INTEGER DEFAULT 0," +
                "gamePegScore INTEGER DEFAULT 0)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_2048_SCORES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "game2048score INTEGER DEFAULT 0)");

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PEG_SCORES + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "gamePegScore INTEGER DEFAULT 0)");
        addTestValues(sqLiteDatabase);
    }

    private void addTestValues(SQLiteDatabase sqLiteDatabase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "toro");
        contentValues.put("game2048score", 456);
        sqLiteDatabase.insert("t_2048_scores", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("name", "toro");
        contentValues.put("game2048score", 1785);
        sqLiteDatabase.insert("t_2048_scores", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("name", "toro");
        contentValues.put("game2048score", 968);
        sqLiteDatabase.insert("t_2048_scores", null, contentValues);

        contentValues = new ContentValues();
        contentValues.put("name", "fran");
        contentValues.put("gamePegScore", 156);
        sqLiteDatabase.insert("t_peg_scores", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("name", "cris");
        contentValues.put("gamePegScore", 4589);
        sqLiteDatabase.insert("t_peg_scores", null, contentValues);
        contentValues = new ContentValues();
        contentValues.put("name", "toro");
        contentValues.put("gamePegScore", 3668);
        sqLiteDatabase.insert("t_peg_scores", null, contentValues);


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", user.getName());
        contentValues.put("password", user.getPassword());
        database.insert("t_users", null, contentValues);
        database.close();
        System.out.println("!!!!!!!!!!! añadido usuario con nombre: " +
                "" + user.getName() + " y password:" + user.getPassword());
    }

    public boolean checkUser(String username, String userPassword) {
        message = new String();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from t_users where name =? and password = ?";
        String[] arguments = new String[]{username, userPassword};
        /*database.rawQuery(query, arguments);*/
        Cursor cursor = database.rawQuery(query, arguments);
        if (cursor.getCount() > 0) {
            cursor.close();
            message = "login correcto";
        } else {
            cursor.close();
            message = "No hay usuario registrado o passwrod no coinciden";
            return false;
        }
        return true;
    }

    public void add2048Score(String userName, Integer valor) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues newRegister = new ContentValues();
        newRegister.put("name", userName);
        newRegister.put("game2048score", valor);
        database.insert("t_2048_scores", null, newRegister);

    }

    @SuppressLint("Range")
    public int get2048HishScore(String userName) {
        int highScore = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from t_2048_scores where name like ? " +
                "order by game2048score DESC limit 1 ";
        String[] arguments = new String[]{userName};
        Cursor cursor = database.rawQuery(query, arguments);
        if (cursor != null && cursor.moveToFirst()) {
            highScore = cursor.getInt(cursor.getColumnIndex("game2048score"));
            return highScore;
        } else {

            return highScore;
        }
    }

    @SuppressLint("Range")
    public int getPEGHishScore(String userName) {
        int highScore = 0;
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select * from t_peg_scores where name like ? " +
                "order by gamePegScore DESC limit 1 ";
        String[] arguments = new String[]{userName};
        Cursor cursor = database.rawQuery(query, arguments);
        if (cursor != null && cursor.moveToFirst()) {
            highScore = cursor.getInt(cursor.getColumnIndex("gamePegScore"));
            cursor.close();

            return highScore;
        } else {
            cursor.close();

            return highScore;
        }
    }

    public void addPegScore(String userName, Integer valor) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues newRegister = new ContentValues();
        newRegister.put("name", userName);
        newRegister.put("gamePegScore", valor);
        database.insert("t_peg_scores", null, newRegister);

    }

    @SuppressLint("Range")
    public ArrayList<Score> getScores(int opcion) {
        scoreList = new ArrayList<>();
        String name;
        int currentScore;
        Score score;
        String query = new String();
        String columnIndex = new String();
        SQLiteDatabase database = this.getReadableDatabase();
        switch (opcion) {
            case 1:
                query = "select * from t_2048_scores order by game2048score " +
                        "DESC";
                columnIndex = "game2048score";
                break;
            case 2:
                query = "select * from t_peg_scores order by gamePegScore " +
                        "DESC";
                columnIndex = "gamePegScore";
                break;
            case 3:
                query = "select * from t_2048_scores order by name ";
                columnIndex = "game2048score";
                break;
            case 4:
                query = "select * from t_peg_scores order by name";
                columnIndex = "gamePegScore";
                break;
        }
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex("name"));
                currentScore = cursor.getInt(cursor.getColumnIndex(columnIndex));
                score = new Score(name, currentScore, IMAGE_2048);
                scoreList.add(score);
            } while (cursor.moveToNext());
            cursor.close();
            return scoreList;
        }
        return null;
    }

    public void deleteScore(String name, int score, int table) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = new String();
        if (table == 1) {
            query = "delete from t_2048_scores where name like '" + name +
                    "' and game2048score = " + score;
        }
        if (table == 2) {
            query = "delete from t_peg_scores where name like '" + name +
                    "' and gamePegScore = " + score;
        }
        database.execSQL(query);
    }

    public void updateUser(String username, String password, String newPassword) {
        String query = new String();
        SQLiteDatabase database = this.getWritableDatabase();
        query = "update t_users set password = '" + newPassword + "' where name " +
                "like '" + username + "' and password like '" + password + "'";
        database.execSQL(query);
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query =
                " select * from t_users where name like '" + username + "' and " +
                        "password like '" + password + "'";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }
}
