package com.example.gamecenter;

public class Score {

    private final int image2048 = R.drawable.game_versus;
    private String username;
    private int score;
    private int imageResource;

    public Score(String username, int score, int imageOption) {
        this.username = username;
        this.score = score;
        if (imageOption == 1) {
            this.imageResource = image2048;
        }

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
