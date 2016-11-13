package in.devmetric.opportunityhackcwdr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText etEmail;
    private Button bSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etEmail = (EditText) findViewById(R.id.etEmail);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etEmail.getText().toString();
                Toast.makeText(ForgotPasswordActivity.this, email, Toast.LENGTH_SHORT).show();
                if(email.equals("satyam@gmail.com")) {
                    Intent i = new Intent(ForgotPasswordActivity.this, ResetPasswordActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
