package co.app.longtumdo.carekhun;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FirebaseProfileActivity extends AppCompatActivity{

    private FragmentManager fragmentManager;

    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ImageView imgFallDetection = (ImageView) findViewById(R.id.img_add_profile);
        imgFallDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent mainActivityAddFirebaseData = new Intent(getApplicationContext(), MainActivityAddFirebaseData.class);
                    mainActivityAddFirebaseData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityAddFirebaseData);
                } catch (Exception e) {
                    Log.e("MyTag", e.toString());
                }
            }
        });

        ImageView imgWearable = (ImageView) findViewById(R.id.img_wearable);
        imgWearable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent sensorDeviceScanActivity = new Intent(getApplicationContext(), SensorDeviceScanActivity.class);
                    sensorDeviceScanActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(sensorDeviceScanActivity);
                } catch (Exception e) {
                    Log.e("MyTag", e.toString());
                }
            }
        });

        ImageView imgNotification = (ImageView) findViewById(R.id.img_fall_detection);
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent mainActivityUpdateNotificationFallDetectionFirebaseData = new Intent(getApplicationContext(), MainActivityUpdateNotificationFallDetectionFirebaseData.class);
                    mainActivityUpdateNotificationFallDetectionFirebaseData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityUpdateNotificationFallDetectionFirebaseData);
                } catch (Exception e) {
                    Log.e("MyTag", e.toString());
                }
            }
        });

        ImageView imgChat = (ImageView) findViewById(R.id.img_chat);
        imgChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("jp.naver.line.android");
                    startActivity(launchIntent);
                } catch (Exception e) {
                    Log.e("MyTag", e.toString());
                }
            }
        });

        fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment = new FirebaseProfileFragment();
        fragmentTransaction.replace(R.id.main_container_wrapper, fragment);
        fragmentTransaction.commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        disableNavigationViewScrollbars(navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_activity) {
                    fragment = new FirebaseProfileFragment();
                } else if (id == R.id.nav_qr_code) {
                    Intent mainActivityQRCodeScanner = new Intent(getApplicationContext(), MainActivityQRCodeScanner.class);
                    mainActivityQRCodeScanner.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityQRCodeScanner);
                } else if(id == R.id.nav_edit_data){
                    Intent mainActivityUpdateAndDeleteFirebaseData = new Intent(getApplicationContext(), MainActivityUpdateAndDeleteFirebaseData.class);
                    mainActivityUpdateAndDeleteFirebaseData.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityUpdateAndDeleteFirebaseData);
                } else if (id == R.id.nav_send_email) {
                    Intent mainActivitySendEmailBackground = new Intent(getApplicationContext(), MainActivitySendEmailBackground.class);
                    mainActivitySendEmailBackground.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivitySendEmailBackground);
                } else if (id == R.id.nav_wearable) {
                    Intent sensorDeviceScanActivity = new Intent(getApplicationContext(), SensorDeviceScanActivity.class);
                    sensorDeviceScanActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(sensorDeviceScanActivity);
                } else if (id == R.id.nav_repair) {
                    Intent mainActivityRequestRepair = new Intent(getApplicationContext(),MainActivityRequestRepair.class);
                    mainActivityRequestRepair.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    getApplicationContext().startActivity(mainActivityRequestRepair);
                } else if (id == R.id.nav_settings) {
                    Intent manageUserActivity = new Intent(getApplicationContext(),ManageUserActivity.class);
                    startActivity(manageUserActivity);
                } else if (id == R.id.nav_logout) {
                    finish();
                    System.exit(0);
                }

                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container_wrapper, fragment);
                transaction.commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                assert drawer != null;
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void disableNavigationViewScrollbars(NavigationView navigationView) {
        if (navigationView != null) {
            NavigationMenuView navigationMenuView = (NavigationMenuView) navigationView.getChildAt(0);
            if (navigationMenuView != null) {
                navigationMenuView.setVerticalScrollBarEnabled(false);
            }
        }
    }
}
