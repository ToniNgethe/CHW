package chapa.cm.chama;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static chapa.cm.chama.R.id.loading;

public class FirtsPageActivity extends AppCompatActivity {

    private static final int PERMISSIONS_MULTIPLE_REQUEST = 100;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 11;
    private EditText _syb, _divi, _district,_week;


    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firts_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        _syb = (EditText) findViewById(R.id.et_first_sub);
        _divi = (EditText) findViewById(R.id.et_first_division);
        _district = (EditText) findViewById(R.id.et_first_district);
        _week = (EditText) findViewById(R.id.et_first_week);

        _week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showDialog(DATE_DIALOG_ID);
            }
        });

        getLocation();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(FirtsPageActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("Loading");
                sweetAlertDialog.setContentText("Submitting...");

                sweetAlertDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sweetAlertDialog.dismissWithAnimation();

                        SweetAlertDialog d = new SweetAlertDialog(FirtsPageActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                        d.setTitleText("Successfully submitted");
                        d.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                                startActivity(new Intent(FirtsPageActivity.this, SecondFormActivity.class));
                            }
                        });
                        d.show();
                    }
                }, 3000);

            }
        });
    }

    private void getLocation() {
        checkAndroidVersion();
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        } else {
            displayLocation();
        }

    }

    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (this, Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {
            displayLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {
                    boolean cameraPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && readExternalFile) {
                        // write your logic here
                        displayLocation();
                    } else {

                        Toast.makeText(this, "Please Grant permission to get Your location", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                break;
        }
    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.

        } else {

            GpsTracker gps = new GpsTracker(this);

            if (gps.canGetLocation()) {

                // displayLocation();
                if (Geocoder.isPresent()) {
                    Geocoder geocoder;
                    List<Address> addresses;

                    // displayLocation();

                    geocoder = new Geocoder(this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(gps.getLatitude(), gps.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        // address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()


                        String loc = addresses.get(0).getLocality();

                        _syb.setText(addresses.get(0).getSubLocality());
                        _district.setText(addresses.get(0).getAddressLine(1));
                        _divi.setText(addresses.get(0).getAdminArea());

                        Log.d("sdfdffsdfsdfsdf", "LOCATION :" + addresses.get(0).getAdminArea() + "Others:" + addresses.get(0).getSubLocality() + " :" + addresses.get(0).getPremises());

                        // loading.setText("Current location: " +loc);
                        // Log.d(TAG, String.valueOf(longitude) + " ," + String.valueOf(latitude));


                    } catch (IOException e) {
                        Toast.makeText(FirtsPageActivity.this, "Oops!! Exception e:"+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();

                        Toast.makeText(this, "Current Location : Unknown location, try restarting app", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    Toast.makeText(this, "Current Location : Unknown location, try restarting app", Toast.LENGTH_SHORT).show();
                }
            } else {
                gps.showSettingsAlert();
            }
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener,
                        year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            _week.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };

}
