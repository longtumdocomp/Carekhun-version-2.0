package co.app.longtumdo.carekhun;

import android.app.AlertDialog;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import model.FirebaseQueryQRCode;

public class MainActivityQRCodeScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler, View.OnClickListener, TextToSpeech.OnInitListener {
    private ZXingScannerView mScannerView;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    String customerId = "";
    static final String TAG = "Main Acvity";
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scan_qr_code);

        //**********************************************************************************************
        tts = new TextToSpeech(this, this, "com.google.android.tts");
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    //******************************************************************************************************************
    public void QrScanner(View view){
        mScannerView = new ZXingScannerView(this);      // Programmatically initialize the scanner view
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);            // Register ourselves as a handler for scan results.
        mScannerView.startCamera();                     // Start camera
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();                      // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here

        Log.e("handler", rawResult.getText());                          // Prints scan results
        Log.e("handler", rawResult.getBarcodeFormat().toString());      // Prints the scan format (qrcode)

        // show the scanner result into dialog box.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(rawResult.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

        insertDataFirebase(rawResult);

        finish();

    }

    //SELECT DATA TO FIREBASE
    public void selectDataFirebase(){
        DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();

        DatabaseReference mUsersRef = mRootRef.child("users");
        DatabaseReference mMessagesRef = mRootRef.child("messages");
        mUsersRef.child("id-12345").setValue("toppee");

        /*
        //Structure
        demo
           -users
              -id12345: "toppee"
        */
    }

    /*
        demo
            -messages
                -message-id-unique-key
                    -text:"test"
                    -username:"toppee"
            -users
                -id12345:"toppee"
     */

    //INSERT DATA TO FIREBASE
    public void insertDataFirebase(Result rawResult){
        DatabaseReference usersRef = database.child("users");
        Map<String, FirebaseQueryQRCode> users = new HashMap<String, FirebaseQueryQRCode>();

        customerId = UUID.randomUUID().toString();
        String message = rawResult.getText();
        users.put("elderly_profile", new FirebaseQueryQRCode(customerId,message,"N","N","N","N","N","N","N","N"));
        usersRef.setValue(users);
    }

    //UPDATE DATA TO FIREBASE
    public void updateDataFirebase(){
        DatabaseReference mMessagesRef = database.child("users");
        String key = mMessagesRef.push().getKey();

        HashMap<String, Object> postValues = new HashMap<>();
        postValues.put("username", "toppee");
        postValues.put("text", "test");

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/messages/" + key, postValues);
        childUpdates.put("/user-messages/toppee/" + key, postValues);

        mMessagesRef.updateChildren(childUpdates);
    }

    //DELETE DATA TO FIREBASE
    public void deleteDataFirebase(){
        DatabaseReference mMessageRef = database.child("users");
        mMessageRef.removeValue();
        mMessageRef.setValue(null);

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/messages/", null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.speak("ระบบตรวจสอบข้อมูลผู้สูงอายุผ่านคิวอาร์โค้ด", TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
