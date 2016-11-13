package in.devmetric.opportunityhackcwdr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {

    private Button bChange;
    private EditText etName, etEmail, etBio, etInterest;
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
        etEmail = (EditText) findViewById(R.id.etEmail);
        etBio = (EditText) findViewById(R.id.etBio);
        etInterest = (EditText) findViewById(R.id.etInterest);
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
        if(item.getItemId() == R.id.save) {

        } else if(item.getItemId() == R.id.cancel) {
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
