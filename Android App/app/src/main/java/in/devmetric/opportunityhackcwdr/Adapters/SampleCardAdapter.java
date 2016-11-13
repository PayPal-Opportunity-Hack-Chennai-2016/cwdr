package in.devmetric.opportunityhackcwdr.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.devmetric.opportunityhackcwdr.AnswerActivity;
import in.devmetric.opportunityhackcwdr.AppConfig;
import in.devmetric.opportunityhackcwdr.AppController;
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
    private String page;

    public SampleCardAdapter(Context mContext, ArrayList<SearchPojo> searchPojos, String page) {
        this.mContext = mContext;
        this.searchPojos = searchPojos;
        this.page = page;
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
        new SampleFeedHolder(holder.itemView).bindData(mContext, searchPojos.get(pos), page);
    }

    @Override
    public int getItemCount() {
        return searchPojos.size();
    }

    private class SampleFeedHolder extends RecyclerView.ViewHolder {

        private ImageView wallpaper, imgFrwd, comment, like;
        private TextView postTitle, postDescription, userName, likeCount, timestamp, comment_count;
        private VideoView videoView;
        private int hrs;

        SampleFeedHolder(View itemView) {
            super(itemView);
            //initialise variables
            wallpaper = (ImageView) itemView.findViewById(R.id.wallpaper);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
            postDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            imgFrwd = (ImageView) itemView.findViewById(R.id.share);
            userName = (TextView) itemView.findViewById(R.id.userName);
            videoView = (VideoView) itemView.findViewById(R.id.video);
            comment = (ImageView) itemView.findViewById(R.id.comment);
            like = (ImageView) itemView.findViewById(R.id.like);
            likeCount = (TextView) itemView.findViewById(R.id.likeCount);
            timestamp = (TextView) itemView.findViewById(R.id.timeStamp);
            comment_count = (TextView) itemView.findViewById(R.id.comment_count);
        }


        public void bindData(final Context mContext, final SearchPojo searchPojo, final String page) {//perform operations here
            if (page.equals("question")) {
                wallpaper.setVisibility(View.GONE);
                imgFrwd.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
                like.setVisibility(View.GONE);
                likeCount.setVisibility(View.GONE);
            } else if (page.equals("blog")) {
                wallpaper.setVisibility(View.GONE);
            }

            if (searchPojo.getSource().getComments() != null && searchPojo.getSource().getComments().size() > 0) {
                comment_count.setText(searchPojo.getSource().getComments().size() + "");
            }
            long diff = System.currentTimeMillis() - searchPojo.getSource().getTimeCreated();//as given

            long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);

            if (minutes > 60) {
                minutes -= 60;
                hrs++;
            }
            if (hrs > 0) {
                timestamp.setText(hrs + " hrs ago");
            } else {
                timestamp.setText(minutes + " mins ago");
            }

            likeCount.setText(searchPojo.getSource().getLikes() + "");
            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    like.setImageResource(R.drawable.like_selected);
                    searchPojo.getSource().setLikes(searchPojo.getSource().getLikes() + 1);
                    likeCount.setText(searchPojo.getSource().getLikes() + "");
                    StringRequest request = new StringRequest(Request.Method.PUT, AppConfig.QUESTION, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("adapter", response + "");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("adapter", error.getLocalizedMessage() + "");
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();

                            params.put("id", searchPojo.getId());
                            params.put("title", searchPojo.getSource().getTitle());
                            params.put("content", searchPojo.getSource().getData());
                            params.put("operation", "like");

                            return params;
                        }
                    };
                    request.setShouldCache(false);
                    request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    AppController.getInstance().addToRequestQueue(request, "adapter");
                }
            });
            wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon(searchPojo, page);
                }
            });
            postTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon(searchPojo, page);
                }
            });
            postDescription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCommon(searchPojo, page);
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
            if (searchPojo.getSource().getData() != null && !searchPojo.getSource().getData().startsWith("<img")) {
                if (page.equals("question"))
                    postDescription.setText(Html.fromHtml(searchPojo.getSource().getDescription()));
                else
                    postDescription.setText(Html.fromHtml(searchPojo.getSource().getData() + ""));
            } else postDescription.setText("See more description");
            userName.setText(searchPojo.getSource().getCreatedBy() + "");
//            if (searchPojo.getSource().getUrl() != null && !TextUtils.isEmpty(searchPojo.getSource().getUrl())) {
//                wallpaper.setVisibility(View.GONE);
//                videoView.setVisibility(View.VISIBLE);
//                MediaController mediaController = new MediaController(mContext);
//                mediaController.setAnchorView(videoView);
//                Uri video = Uri.parse(searchPojo.getSource().getUrl());
//                videoView.setMediaController(mediaController);
//                videoView.setVideoURI(video);
//                videoView.requestFocus();
//                videoView.start();
//            }
        }

        private void mCommon(SearchPojo searchPojo, String page) {
            Intent intent = null;
            if (page.equals("question")) {
                intent = new Intent(mContext, AnswerActivity.class);

            } else {
                intent = new Intent(mContext, PostDescription.class);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("value", searchPojo);
            mContext.startActivity(intent);
        }

    }


}