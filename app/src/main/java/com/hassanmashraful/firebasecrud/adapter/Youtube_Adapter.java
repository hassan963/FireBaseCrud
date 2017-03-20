package com.hassanmashraful.firebasecrud.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.hassanmashraful.firebasecrud.R;

/**
 * Created by Sourav on 3/18/2017.
 */

public class Youtube_Adapter extends RecyclerView.Adapter<Youtube_Adapter.VideoInfoHolder> {


    //these ids are the unique id for each video
    private String[] VideoID = { "g7BXIOLVVx4", "9za-6Tq-WVc","xscfyo6SVrk","SQpbTTGe_gk","b7_ix42ghCQ", "cQAWAO0kdts", "uXIS5S86jfc", "qSWm_nprfqE", "zMoatr5RQFs"};
    private Context ctx;

    public Youtube_Adapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public VideoInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_eachitem, parent, false);
        return new VideoInfoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VideoInfoHolder holder, final int position) {

        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                holder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }
        };

        holder.youTubeThumbnailView.initialize("AIzaSyBBavNPibQfVavJzrXbCJWZ2mhN2dkwWbo", new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(VideoID[position]);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return VideoID.length;
    }

    public class VideoInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        YouTubeThumbnailView youTubeThumbnailView;
        protected ImageView playButton;

        public VideoInfoHolder(View itemView) {
            super(itemView);

            playButton=(ImageView)itemView.findViewById(R.id.btnYoutube_player);
            playButton.setOnClickListener(this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);

        }


        @Override
        public void onClick(View view) {
            /*Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx, "AIzaSyBBavNPibQfVavJzrXbCJWZ2mhN2dkwWbo", VideoID[getLayoutPosition()]);
            ctx.startActivity(intent);*/

            Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) ctx,
                    "AIzaSyBBavNPibQfVavJzrXbCJWZ2mhN2dkwWbo",
                    VideoID[getLayoutPosition()],//video id
                    100,     //after this time, video will start automatically
                    true,               //autoplay or not
                    true);             //lightbox mode or not; show the video in a small box
            ctx.startActivity(intent);


        }
    }
}
