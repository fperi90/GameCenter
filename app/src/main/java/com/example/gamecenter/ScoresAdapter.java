package com.example.gamecenter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {


    private ArrayList<Score> mScoresData;
    private Context mContext;


    public ScoresAdapter(Context mContext, ArrayList<Score> mScoresData) {
        this.mScoresData = mScoresData;
        this.mContext = mContext;
    }

    @Override
    public ScoresAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ScoresAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Score currentScore = mScoresData.get(position);
        // Populate the textviews with data.
        holder.bindTo(currentScore);
        Glide.with(mContext).load(currentScore.getImageResource()).into(holder.mGameImage);


    }

    @Override
    public int getItemCount() {
        return mScoresData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mGameImage;
        // Member Variables for the TextViews
        private TextView mGameText;
        private TextView mInfoText;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);
            // Initialize the views.
            mGameText = itemView.findViewById(R.id.playerNameTextView);
            mInfoText = itemView.findViewById(R.id.scoreValue);
            mGameImage = (ImageView) itemView.findViewById(R.id.scoreGameImage);

        }

        void bindTo(Score currentScore) {
            // Populate the textviews with data.
            mGameImage.setImageDrawable(ActivityCompat.getDrawable(mContext,
                    R.drawable.game_versus));
            mGameText.setText(currentScore.getUsername());
            mInfoText.setText(String.valueOf(currentScore.getScore()));
        }
    }


}
