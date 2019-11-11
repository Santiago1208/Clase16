package appmoviles.com.preclase13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import appmoviles.com.preclase13.model.entity.Friend;
import appmoviles.com.preclase13.model.entity.User;
import appmoviles.com.preclase13.view.activities.MainActivity;
import appmoviles.com.preclase13.view.custom.ScrolledDatePicker;

public class SignupActivity extends AppCompatActivity {

    private EditText signin_email;
    private EditText signin_name;
    private EditText signin_username;
    private EditText signin_mobile;
    private ScrolledDatePicker signin_age;
    private EditText signin_password;
    private EditText signin_repassword;
    private Button login_signin;
    private TextView alreadyUserTV;

    //NPM
    //NODE.JS

    FirebaseAuth auth;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        signin_email = findViewById(R.id.signin_email);
        signin_name = findViewById(R.id.signin_name);
        signin_username = findViewById(R.id.signin_username);
        signin_mobile = findViewById(R.id.signin_mobile);
        signin_age = findViewById(R.id.signin_age);
        signin_password = findViewById(R.id.signin_password);
        signin_repassword = findViewById(R.id.signin_repassword);
        login_signin = findViewById(R.id.login_signin);
        alreadyUserTV = findViewById(R.id.alreadyUserTV);

        login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (signin_email.getText().toString().trim().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "El campo de email esta vacio", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!signin_password.getText().toString()
                        .equals(signin_repassword.getText().toString())) {
                    Toast.makeText(SignupActivity.this, "Las contraseñas NO coinciden", Toast.LENGTH_LONG).show();
                    return;
                }

                if (signin_password.getText().toString().trim().length() < 10) {
                    Toast.makeText(SignupActivity.this, "Las contraseñas debe tener mínimo 10 carácteres", Toast.LENGTH_LONG).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(
                        signin_email.getText().toString().trim(),
                        signin_password.getText().toString().trim()
                ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        try {
                            if (task.isSuccessful()) {
                                //Ya estamos logeados

                                String birth = signin_age.getYear()
                                        + "-" + (signin_age.getMonth() + 1)
                                        + "-" + signin_age.getDayOfMonth();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                                Date date = sdf.parse(birth);


                                User user = new User(
                                        auth.getCurrentUser().getUid(),
                                        signin_name.getText().toString(),
                                        signin_email.getText().toString(),
                                        signin_username.getText().toString(),
                                        signin_mobile.getText().toString(),
                                        birth,
                                        date.getTime(),
                                        signin_password.getText().toString()
                                );

                                db.getReference().child("users").child(user.getUid())
                                        .setValue(user);

                                Friend friend = new Friend(

                                        user.getUid(),
                                        user.getName(),
                                        user.getUsername(),
                                        user.getPhone(),
                                        user.getEmail()

                                );
                                db.getReference().child("friends")
                                        .child(friend.getUid())
                                        .setValue(friend);


                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();

                            } else {
                                Toast.makeText(SignupActivity.this, "" + task.getException(), Toast.LENGTH_LONG).show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        alreadyUserTV.setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });

    }
}
