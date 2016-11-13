package in.devmetric.opportunityhackcwdr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashSet;

import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;
import in.devmetric.opportunityhackcwdr.PostDescription;
import in.devmetric.opportunityhackcwdr.R;

/**
 * Created by @silverFoxA on 12/11/16.
 */

public class SampleCardAdapter extends RecyclerView.Adapter {


    private final Context mContext;
    private final ArrayList<SearchPojo> searchPojos;
    private HashSet list;

    public SampleCardAdapter(Context mContext, ArrayList<SearchPojo> searchPojos) {
        this.mContext = mContext;
        this.searchPojos = searchPojos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder vh;

        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_list_item, parent, false);

        vh = new SampleFeedHolder(view);

        return vh;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        new SampleFeedHolder(holder.itemView).bindData(mContext, searchPojos.get(pos));
    }

    @Override
    public int getItemCount() {
        return searchPojos.size();
    }

    private class SampleFeedHolder extends RecyclerView.ViewHolder {

        private ImageView wallpaper, imgFrwd;
        private TextView postTitle, postDescription, userName;
        private VideoView videoView;

        SampleFeedHolder(View itemView) {
            super(itemView);
            //initialise variables
            wallpaper = (ImageView) itemView.findViewById(R.id.wallpaper);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            imgFrwd = (ImageView) itemView.findViewById(R.id.share);
            userName = (TextView) itemView.findViewById(R.id.userName);
            videoView = (VideoView) itemView.findViewById(R.id.video);
        }


        public void bindData(final Context mContext, SearchPojo searchPojo) {//perform operations here
            wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon();
                }
            });
            postTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon();
                }
            });
            postDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon();
                }
            });
            imgFrwd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    view.getContext().startActivity(Intent.createChooser(sendIntent, "Share the post "));
                }
            });

            postTitle.setText(searchPojo.getSource().getTitle() + "");
            postDescription.setText(searchPojo.getSource().getData() + "");
            userName.setText(searchPojo.getSource().getCreatedBy() + "");
            if (searchPojo.getSource().getUrl() != null && !TextUtils.isEmpty(searchPojo.getSource().getUrl())) {
                wallpaper.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                MediaController mediaController = new MediaController(mContext);
                mediaController.setAnchorView(videoView);
                Uri video = Uri.parse(searchPojo.getSource().getUrl());
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.start();
            }
        }

        private void mCommon() {
            mContext.startActivity(new Intent(mContext, PostDescription.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }

    }


}