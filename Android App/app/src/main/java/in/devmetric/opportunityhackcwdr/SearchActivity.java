package in.devmetric.opportunityhackcwdr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Collections;

import in.devmetric.opportunityhackcwdr.Adapters.SampleCardAdapter;
import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;

/**
 * Created by Satyam on 13/11/2016.
 */

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private String query;
    private ArrayList<SearchPojo> searchPojos = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchPojos = new ArrayList<>();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        query = getIntent().getStringExtra("search");

        Toast.makeText(SearchActivity.this, query, Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getSearchPojo();
        adapter = new SampleCardAdapter(SearchActivity.this, searchPojos, "search");
        recyclerView.setAdapter(adapter);
    }

    void getSearchPojo() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppConfig.SEARCH + query, new Response.Listener<String>() {
            @Override
            public void onResponse(String response1) {
                System.out.println(response1 + "");
                JsonArray response = new JsonParser().parse(response1).getAsJsonArray();
                Toast.makeText(SearchActivity.this, response.size() + "", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(SearchActivity.this, error.getLocalizedMessage() + "No result found", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(stringRequest, "home");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
