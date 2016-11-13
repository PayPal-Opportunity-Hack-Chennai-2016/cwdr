package in.devmetric.opportunityhackcwdr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;

import in.devmetric.opportunityhackcwdr.Pojo.UserDetails;

public class LoginActiviy extends AppCompatActivity
        implements View.OnClickListener {

    private Button bLogin;
    private EditText etEmail, etPassword;
    private TextView tvInvalid, tvForgotPssword, tvRegister;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private String TAG = "LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        if (sharedpreferences.getBoolean("logged", false)) {
            //user logged in
            startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        bLogin = (Button) findViewById(R.id.bLogin);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvInvalid = (TextView) findViewById(R.id.tvInvalid);
        tvForgotPssword = (TextView) findViewById(R.id.tvForgotPassowrd);
        tvRegister = (TextView) findViewById(R.id.tvRegister);

        bLogin.setOnClickListener(this);
        tvForgotPssword.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bLogin:
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                Log.d("PARAMS", email + "--" + password);
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            JsonObject jsonObject = null;

                            try {
                                jsonObject = new JsonParser().parse(response).getAsJsonObject();
                                if (!jsonObject.has("error")) {
                                    Log.d("RESPONSE", response + "");
                                    try {
                                        UserDetails userDetails = new Gson().fromJson(response, UserDetails.class);
                                        editor.putString("email", userDetails.getEmail());
                                        editor.putString("age", userDetails.getAge());
                                        editor.putString("qualification", userDetails.getQualification());
                                        editor.putString("phone", userDetails.getPhone());
                                        editor.putString("fullName", userDetails.getFullName());
                                        StringBuilder sb = new StringBuilder();

                                        /*StringBuilder sb = new StringBuilder();
>>>>>>> origin/BlogComment
                                        for (int x = 0; x < userDetails.getTags().size(); x++) {
                                            sb.append(userDetails.getTags().get(x));
                                            if (x < userDetails.getTags().size() - 1)
                                                sb.append(',');
                                        }
                                        editor.putString("tags", sb.toString());*/
                                    } catch (Exception e) {
                                    }
                                    editor.putBoolean("logged", true);
                                    editor.commit();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                } else {
                                    try {
                                        Toast.makeText(LoginActiviy.this, jsonObject.get("error").getAsString() + "", Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (JsonSyntaxException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActiviy.this, error.getLocalizedMessage() + "Unable to process your request. Please re-check the details", Toast.LENGTH_SHORT).show();
                            tvInvalid.setVisibility(View.VISIBLE);
                            tvForgotPssword.setVisibility(View.VISIBLE);
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("email", email);
                            map.put("password", password);
                            return map;
                        }
                    };

                    AppController.getInstance().addToRequestQueue(stringRequest, TAG);
                } else {
                    Toast.makeText(this, "Please enter the details", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvForgotPassowrd:
                Intent i = new Intent(LoginActiviy.this, ForgotPasswordActivity.class);
                startActivity(i);
                break;
            case R.id.tvRegister:
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
        }
    }
}
