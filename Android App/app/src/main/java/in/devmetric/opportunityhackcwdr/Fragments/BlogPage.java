package in.devmetric.opportunityhackcwdr.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import in.devmetric.opportunityhackcwdr.Adapters.SampleCardAdapter;
import in.devmetric.opportunityhackcwdr.Add_New_Post;
import in.devmetric.opportunityhackcwdr.AppConfig;
import in.devmetric.opportunityhackcwdr.AppController;
import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;
import in.devmetric.opportunityhackcwdr.R;
import in.devmetric.opportunityhackcwdr.ViewHolders.MainFeedHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlogPage extends Fragment {
    private MainFeedHolder mainFeedHolder;

    private ArrayList<SearchPojo> searchPojos;
    private SampleCardAdapter adapter;

    public BlogPage() {
        mainFeedHolder = new MainFeedHolder();
        searchPojos = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog_page, container, false);
        //add blog
        mainFeedHolder.addLayout = (RelativeLayout) view.findViewById(R.id.addLayout);
        //Recyclerview
        mainFeedHolder.recyclerView = (RecyclerView) view.findViewById(R.id.home_feeds);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mainFeedHolder.recyclerView.setLayoutManager(linearLayoutManager);
        mainFeedHolder.recyclerView.setHasFixedSize(true);
        mainFeedHolder.recyclerView.setItemViewCacheSize(20);
        mainFeedHolder.recyclerView.setDrawingCacheEnabled(true);
        mainFeedHolder.recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

//        getContent();
        //adapter
        adapter = new SampleCardAdapter(getContext(), searchPojos, "blog");
        mainFeedHolder.recyclerView.setAdapter(adapter);


        mainFeedHolder.recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    mainFeedHolder.addLayout.setVisibility(View.GONE);
                } else {
                    mainFeedHolder.addLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        mainFeedHolder.addLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Add_New_Post.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        return view;
    }

    private void getContent() {
        try {
            searchPojos.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.CONTENTS + "blog", new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                System.out.println(response1 + "");
                JsonArray response = new JsonParser().parse(response1).getAsJsonArray();
                for (int i = 0; i < response.size(); i++) {
                    SearchPojo item = new Gson().fromJson(response.get(i).getAsJsonObject().toString(), SearchPojo.class);
                    searchPojos.add(item);
                }
                Collections.reverse(searchPojos);
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getLocalizedMessage() + "No result found", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, "blog");
    }

    @Override
    public void onResume() {
        super.onResume();
        searchPojos.clear();
        getContent();
    }
}
