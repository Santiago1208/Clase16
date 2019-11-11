package appmoviles.com.preclase13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import appmoviles.com.preclase13.view.activities.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText login_email;
    private EditText login_password;
    private Button login_signin;
    private TextView noUserYetTV;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_signin = findViewById(R.id.login_signin);
        noUserYetTV = findViewById(R.id.noUserYetTV);


        login_signin.setOnClickListener(
                (v) -> {
                    auth.signInWithEmailAndPassword(
                            login_email.getText().toString().trim(),
                            login_password.getText().toString().trim()
                    ).addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }
                    );


                }
        );


        noUserYetTV.setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
        );


    }
}
