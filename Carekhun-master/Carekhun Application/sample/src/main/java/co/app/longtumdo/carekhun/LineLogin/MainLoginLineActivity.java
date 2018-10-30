package co.app.longtumdo.carekhun.LineLogin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import co.app.longtumdo.carekhun.R;

public class MainLoginLineActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_login_main);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        final TextView a2aButton = (TextView) findViewById(R.id.login_button);
        a2aButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {
                    // App to App Login
                    Intent LoginIntent = LineLoginApi.getLoginIntent(v.getContext(), Constants.CHANNEL_ID);
                    startActivityForResult(LoginIntent, REQUEST_CODE);

                } catch (Exception e) {
                    Log.e("ERROR", e.toString());
                }
            }
        });

        final TextView browserLoginButton = (TextView) findViewById(R.id.browser_login_button);
        browserLoginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                try {
                    // Browser Login
                    Intent LoginIntent = LineLoginApi.getLoginIntentWithoutLineAppAuth(v.getContext(), Constants.CHANNEL_ID);
                    startActivityForResult(LoginIntent, REQUEST_CODE);

                } catch (Exception e) {
                    Log.e("ERROR", e.toString());
                }
            }

        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REQUEST_CODE) {
            Log.e("ERROR", "Unsupported Request");
            return;
        }

        LineLoginResult result = LineLoginApi.getLoginResultFromIntent(data);

        switch (result.getResponseCode()) {

            case SUCCESS:

                Intent transitionIntent = new Intent(this, PostLoginActivity.class);
                transitionIntent.putExtra("line_profile", result.getLineProfile());
                transitionIntent.putExtra("line_credential", result.getLineCredential());
                startActivity(transitionIntent);
                break;

            case CANCEL:
                Log.e("ERROR", "LINE Login Canceled by user!!");
                break;

            default:
                Log.e("ERROR", "Login FAILED!");
                Log.e("ERROR", result.getErrorData().toString());
        }
    }
}
