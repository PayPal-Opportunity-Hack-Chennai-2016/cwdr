package in.devmetric.opportunityhackcwdr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    private Button register;
    private EditText name;
    private EditText contact;
    private EditText email;
    private CheckBox agree;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = (Button)findViewById(R.id.register);
        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        contact = (EditText)findViewById(R.id.contact);
        agree = (CheckBox)findViewById(R.id.agree);
        password = (EditText)findViewById(R.id.password1);
            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast ;
                    toast = Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_LONG);
                    Boolean flag = false;
                    if(name.getText().length() > 0&&contact.getText().length() > 0&&email.getText().length() > 0&&password.getText().length() >0){
                        flag = true;
                    }
                    else {
                        flag = false;
                        //toast.cancel();
                       toast.setText("Please Enter all Fields");
                        toast.show();
                    }

                    if(flag == true){
                        if(agree.isChecked()){
                            //toast.cancel();
                            /*Intent in = new intent(RegisterActivity.this,MainActivity.class);
                 startActivity(in);
                  */
                        }
                        else
                        {
                           // toast.cancel();
                            toast.setText("Please check the T&C");
                            toast.show();
                        }
                    }

                }
            });

    }
}
