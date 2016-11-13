package in.devmetric.opportunityhackcwdr;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AskQuestionActivity extends AppCompatActivity {

    private TextInputEditText question, tags, description;
    private Button submit;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        question = (TextInputEditText) findViewById(R.id.question);
        tags = (TextInputEditText) findViewById(R.id.tags);
        description = (TextInputEditText) findViewById(R.id.descriprion);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.QUESTION, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(AskQuestionActivity.this, "Item has been updated successfully.", Toast.LENGTH_SHORT).show();
                        AskQuestionActivity.this.finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AskQuestionActivity.this, error.getLocalizedMessage() + "Error", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("email", sharedpreferences.getString("email", ""));
                        map.put("title", question.getText().toString());
                        map.put("tags", tags.getText().toString());
                        map.put("content", ""); //empty for questions
                        map.put("type", "question");
                        map.put("description", description.getText().toString());
                        Log.d("PARAM", map.toString());
                        return map;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest, "question");
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
