package in.devmetric.opportunityhackcwdr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {

    private static final String TAG = "PROFILE_EDIT";
    private Button bChange;
    private EditText etName, etBio, etInterest;
    private TextView tvEmail;
    private CircleImageView image;
    private final int GALLERY_INTENT = 1;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        progressDialog = new ProgressDialog(this);
        bChange = (Button) findViewById(R.id.bChange);
        etName = (EditText) findViewById(R.id.etName);
        etBio = (EditText) findViewById(R.id.etBio);
        etInterest = (EditText) findViewById(R.id.etInterest);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        image = (CircleImageView) findViewById(R.id.profile_image);

        bChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);

        etName.setText(sharedPreferences.getString("fullName", "default"));
        etBio.setText(sharedPreferences.getString("age", "default"));
        etInterest.setText(sharedPreferences.getString("preferences", "default"));
        tvEmail.setText(sharedPreferences.getString("email", "default"));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
           /* progressDialog.setMessage("Uploading...");
            progressDialog.show();*/

            Uri uri = data.getData(); //Image Uri

            Log.i("Uri", uri.toString());
            image.setImageURI(uri);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
           final String sname = etName.getText().toString();
            final String semail = tvEmail.getText().toString();
            final String sage = etBio.getText().toString();
            final String spreferences = etInterest.getText().toString();
            Toast.makeText(ProfileEditActivity.this, "Saving", Toast.LENGTH_SHORT).show();
            StringRequest request = new StringRequest(Request.Method.PUT, AppConfig.USER, new Response.Listener<String>() {
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
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("fullName", sname);
                    params.put("preferences", spreferences);
                    params.put("email", semail);
                    params.put("age", sage);
                    params.put("address", "");
                    params.put("qualification", "");
                    params.put("phone", "");
                    params.put("profilePicture", "");
                    params.put("operation", "update");

                    return params;
                }
            };
            request.setShouldCache(false);
            request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(request, TAG);
        } else if (item.getItemId() == R.id.cancel) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
