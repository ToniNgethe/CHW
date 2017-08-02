package chapa.cm.chama;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class FirtsPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firts_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
getSupportActionBar().setTitle("CHW");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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

}
