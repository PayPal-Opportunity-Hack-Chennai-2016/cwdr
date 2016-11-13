package in.devmetric.opportunityhackcwdr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import in.devmetric.opportunityhackcwdr.Adapters.CommentAdapter;
import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;

public class PostDescription extends AppCompatActivity {

    private static final String TAG = "POST_DESCRIPTION";
    private EditText etComments;
    private Button bSubmit;
    private RecyclerView commentList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SearchPojo searchPojo = (SearchPojo) getIntent().getSerializableExtra("value");

        etComments = (EditText) findViewById(R.id.comments);
        bSubmit = (Button) findViewById(R.id.bCommentSubmit);

        commentList = (RecyclerView) findViewById(R.id.commentList);
        commentList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        commentList.setLayoutManager(layoutManager);
        adapter = new CommentAdapter(searchPojo.getSource().getComments());
        commentList.setAdapter(adapter);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(searchPojo.getSource().getTitle() + "");
        }
        try {
            ((TextView) findViewById(R.id.description)).setText(Html.fromHtml(searchPojo.getSource().getData(), null, new MyTagHandler()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String comment = etComments.getText().toString();
                StringRequest request = new StringRequest(Request.Method.PUT, AppConfig.QUESTION, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response + "");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, error.getLocalizedMessage() + "");
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<String, String>();

                        map.put("comment", comment);
                        map.put("id", searchPojo.getId());
                        map.put("title", searchPojo.getSource().getTitle());
                        map.put("operation", "comment");
                        return map;
                    }
                };
                request.setShouldCache(false);
                request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(request, TAG);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
