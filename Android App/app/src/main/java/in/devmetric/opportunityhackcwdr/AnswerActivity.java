package in.devmetric.opportunityhackcwdr;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import in.devmetric.opportunityhackcwdr.Pojo.SearchPojo;

public class AnswerActivity extends AppCompatActivity {

    private EditText answer;
    private Button post;
    private TextView QuestionTitle, QuestionDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        QuestionTitle = (TextView) findViewById(R.id.QuestionTitle);
        QuestionTitle.setVisibility(View.GONE);
        QuestionDesc = (TextView) findViewById(R.id.QuestionDesc);

        final SearchPojo searchPojo = (SearchPojo) getIntent().getSerializableExtra("value");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(searchPojo.getSource().getTitle());
        }
//        QuestionTitle.setText(searchPojo.getSource().getTitle());
        QuestionDesc.setText(searchPojo.getSource().getDescription());

        answer = (EditText) findViewById(R.id.answer);
        post = (Button) findViewById(R.id.post);

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        params.put("content", answer.getText().toString());
                        params.put("operation", "answer");

                        return params;
                    }
                };
                request.setShouldCache(false);
                request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(request, "adapter");
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
