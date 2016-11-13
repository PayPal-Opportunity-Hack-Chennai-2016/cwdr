package in.devmetric.opportunityhackcwdr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button bSubmit;
    private EditText etNewPassword, etConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        bSubmit = (Button) findViewById(R.id.bSubmit);
        etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPass = etNewPassword.getText().toString();
                String confirmPass = etConfirmPassword.getText().toString();

                if(newPass.equals(confirmPass)) {
                    Toast.makeText(ResetPasswordActivity.this, "Cool! You did it :D", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Useless fellow can't copy the same password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
