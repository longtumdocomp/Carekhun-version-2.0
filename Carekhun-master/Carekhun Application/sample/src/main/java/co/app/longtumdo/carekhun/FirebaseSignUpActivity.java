package co.app.longtumdo.carekhun;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import co.app.longtumdo.carekhun.Helper.Helper;

public class FirebaseSignUpActivity extends AppCompatActivity {

    private static final String TAG = FirebaseSignUpActivity.class.getSimpleName();

    private EditText emailInput;

    private EditText passwordInput;

    private TextView signUpText;

    private TextView loginError;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }

        mAuth = ((FirebaseApplication)getApplication()).getFirebaseAuth();
        ((FirebaseApplication)getApplication()).checkUserLogin(FirebaseSignUpActivity.this);

        loginError = (TextView)findViewById(R.id.login_error);

        emailInput = (EditText)findViewById(R.id.email);
        passwordInput = (EditText)findViewById(R.id.password);

        signUpText = (TextView)findViewById(R.id.register);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpIntent = new Intent(FirebaseSignUpActivity.this, FirebaseLoginProfileActivity.class);
                startActivity(signUpIntent);
            }
        });

        final Button loginButton = (Button)findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredEmail = emailInput.getText().toString();
                String enteredPassword = passwordInput.getText().toString();

                if(TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)){
                    Helper.displayMessageToast(FirebaseSignUpActivity.this, "Login fields must be filled");
                    return;
                }
                if(!Helper.isValidEmail(enteredEmail)){
                    Helper.displayMessageToast(FirebaseSignUpActivity.this, "Invalidate email entered");
                    return;
                }

                ((FirebaseApplication)getApplication()).createNewUser(FirebaseSignUpActivity.this, enteredEmail, enteredPassword, loginError);
            }
        });
    }
}
