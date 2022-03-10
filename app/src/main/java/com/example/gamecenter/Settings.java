package com.example.gamecenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerViewPeg;
    private RecyclerView mRecyclerView2048ByName;
    private RecyclerView mRecyclerViewPegByName;

    private ArrayList<Score> mScoresData;
    private ArrayList<Score> mScoresDataPeg;
    private ArrayList<Score> mScoresData2048ByName;
    private ArrayList<Score> mScoresDataPegByName;

    private ScoresAdapter mAdapter;
    private ScoresAdapter mAdapterPeg;
    private ScoresAdapter mAdapter2048ByName;
    private ScoresAdapter mAdapterPegByName;


    private DBHelper helper;

    private final static int A2048 = 1, PEG = 2, A2048_BY_NAME = 3,
            PEG_BY_NAME = 4;
    private final static int TABLE2048 = 1, TABLEPEG = 2;

    private Button registryButton;
    private Button score2048byScore;
    private Button score2048byName;

    private Button scorePegByScore;
    private Button scorePegByName;

    private LinearLayout linear1;
    private LinearLayout linear2;
    private LinearLayout linear3;
    private LinearLayout linear4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        registryButton = (Button) findViewById(R.id.settingsRegistryButton);

        score2048byScore = (Button) findViewById(R.id.textViewScore2048);
        score2048byName = (Button) findViewById(R.id.textViewScore2048byName);
        scorePegByScore = (Button) findViewById(R.id.textViewScorePeg);
        scorePegByName = (Button) findViewById(R.id.textViewScorePegByName);

        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        linear4 = (LinearLayout) findViewById(R.id.linear4);


        helper = new DBHelper(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerViewPeg = findViewById(R.id.recyclerViewPeg);
        mRecyclerView2048ByName = findViewById(R.id.recyclerView2048ByName);
        mRecyclerViewPegByName = findViewById(R.id.recyclerView2048ByName);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewPeg.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView2048ByName.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewPegByName.setLayoutManager(new LinearLayoutManager(this));

        mScoresData = new ArrayList<>();
        mScoresDataPeg = new ArrayList<>();
        mScoresData2048ByName = new ArrayList<>();
        mScoresDataPegByName = new ArrayList<>();


        mAdapter = new ScoresAdapter(this, mScoresData);
        mAdapterPeg = new ScoresAdapter(this, mScoresDataPeg);
        mAdapter2048ByName = new ScoresAdapter(this, mScoresData2048ByName);
        mAdapterPegByName = new ScoresAdapter(this, mScoresDataPegByName);


        mRecyclerView.setAdapter(mAdapter);
        mRecyclerViewPeg.setAdapter(mAdapterPeg);
        mRecyclerView2048ByName.setAdapter(mAdapter2048ByName);
        mRecyclerViewPegByName.setAdapter(mAdapterPegByName);




        initializeData();
        ItemTouchHelper itemTouch2048Helper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        System.out.println(mScoresData.get(viewHolder.getAdapterPosition()).getUsername());
                        System.out.println(mScoresData.get(viewHolder.getAdapterPosition()).getScore());
                        String name =
                                mScoresData.get(viewHolder.getAdapterPosition()).getUsername();
                        int score =
                                mScoresData.get(viewHolder.getAdapterPosition()).getScore();
                        helper.deleteScore(name, score, TABLE2048);
                        mScoresData.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        itemTouch2048Helper.attachToRecyclerView(mRecyclerView);


        ItemTouchHelper itemTouchPegHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        System.out.println(mScoresDataPeg.get(viewHolder.getAdapterPosition()).getUsername());
                        System.out.println(mScoresDataPeg.get(viewHolder.getAdapterPosition()).getScore());
                        String name =
                                mScoresDataPeg.get(viewHolder.getAdapterPosition()).getUsername();
                        int score =
                                mScoresDataPeg.get(viewHolder.getAdapterPosition()).getScore();
                        helper.deleteScore(name, score, TABLEPEG);
                        mScoresDataPeg.remove(viewHolder.getAdapterPosition());
                        mAdapterPeg.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchPegHelper.attachToRecyclerView(mRecyclerViewPeg);

        ItemTouchHelper itemTouch2048byNameHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        System.out.println(mScoresData2048ByName.get(viewHolder.getAdapterPosition()).getUsername());
                        System.out.println(mScoresData2048ByName.get(viewHolder.getAdapterPosition()).getScore());
                        String name =
                                mScoresData2048ByName.get(viewHolder.getAdapterPosition()).getUsername();
                        int score =
                                mScoresData2048ByName.get(viewHolder.getAdapterPosition()).getScore();
                        helper.deleteScore(name, score, TABLE2048);
                        mScoresData2048ByName.remove(viewHolder.getAdapterPosition());
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        itemTouch2048byNameHelper.attachToRecyclerView(mRecyclerView2048ByName);

        ItemTouchHelper itemTouchPegbyNameHelper =
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        System.out.println(mScoresDataPegByName.get(viewHolder.getAdapterPosition()).getUsername());
                        System.out.println(mScoresDataPegByName.get(viewHolder.getAdapterPosition()).getScore());
                        String name =
                                mScoresDataPegByName.get(viewHolder.getAdapterPosition()).getUsername();
                        int score =
                                mScoresDataPegByName.get(viewHolder.getAdapterPosition()).getScore();
                        helper.deleteScore(name, score, TABLEPEG);
                        mScoresDataPegByName.remove(viewHolder.getAdapterPosition());
                        mAdapterPegByName.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        itemTouchPegbyNameHelper.attachToRecyclerView(mRecyclerViewPegByName);


        registryButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, Register.class);
            startActivity(intent);
        });

        score2048byScore.setOnClickListener(view -> {

            linear2.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);
            linear4.setVisibility(View.GONE);
            linear1.setVisibility(View.VISIBLE);
        });
        score2048byName.setOnClickListener(view -> {
            linear1.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);
            linear4.setVisibility(View.GONE);
            linear2.setVisibility(View.VISIBLE);
        });

        scorePegByScore.setOnClickListener(view -> {
            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.GONE);
            linear4.setVisibility(View.GONE);
            linear3.setVisibility(View.VISIBLE);
        });
        scorePegByName.setOnClickListener(view -> {

            linear1.setVisibility(View.GONE);
            linear2.setVisibility(View.GONE);
            linear3.setVisibility(View.GONE);
            linear4.setVisibility(View.VISIBLE);
        });


    }

    private void initializeData() {
        ArrayList<Score> auxiliar2048;
        ArrayList<Score> auxiliarPeg;
        ArrayList<Score> auxiliar2048byName;
        ArrayList<Score> auxiliarPegbyName;

        mScoresData.clear();
        mScoresDataPeg.clear();
        mScoresData2048ByName.clear();
        mScoresDataPegByName.clear();

        auxiliar2048 = helper.getScores(A2048);
        if (auxiliar2048 != null) {
            if (auxiliar2048.size() > 0) {
                for (int i = 0; i < auxiliar2048.size(); i++) {
                    mScoresData.add(new Score(auxiliar2048.get(i).getUsername(),
                            auxiliar2048.get(i).getScore(), 1));
                }
                mAdapter.notifyDataSetChanged();
            }
        }
        auxiliarPeg = helper.getScores(PEG);
        if (auxiliarPeg != null) {
            if (auxiliarPeg.size() > 0) {
                for (int i = 0; i < auxiliarPeg.size(); i++) {
                    mScoresDataPeg.add(new Score(auxiliarPeg.get(i).getUsername(),
                            auxiliarPeg.get(i).getScore(), 1));
                }
                mAdapterPeg.notifyDataSetChanged();
            }
        }

        auxiliar2048byName = helper.getScores(A2048_BY_NAME);
        if (auxiliar2048byName != null) {
            if (auxiliar2048byName.size() > 0) {
                for (int i = 0; i < auxiliar2048byName.size(); i++) {
                    mScoresData2048ByName.add(new Score(auxiliar2048byName.get(i).getUsername(),
                            auxiliar2048byName.get(i).getScore(), 1));
                }
                mAdapter2048ByName.notifyDataSetChanged();
            }
        }
        auxiliarPegbyName = helper.getScores(PEG_BY_NAME);
        if (auxiliarPegbyName != null) {
            if (auxiliarPegbyName.size() > 0) {
                for (int i = 0; i < auxiliarPegbyName.size(); i++) {
                    mScoresDataPegByName.add(new Score(auxiliarPegbyName.get(i).getUsername(),
                            auxiliarPegbyName.get(i).getScore(), 1));
                }
                mAdapterPegByName.notifyDataSetChanged();
            }
        }
    }
}