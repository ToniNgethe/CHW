package chapa.cm.chama;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by toni on 8/2/17.
 */

public class Conditions extends Fragment {

    private FloatingActionButton add, next;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_conditions, container, false);

        add = view.findViewById(R.id.fab_add);
        next = view.findViewById(R.id.fab_foward);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Page refreshed, enter more details or choose another condition", Toast.LENGTH_SHORT).show();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
                sweetAlertDialog.setTitleText("Loading");
                sweetAlertDialog.setContentText("Submitting...");

                sweetAlertDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sweetAlertDialog.dismissWithAnimation();

                        SweetAlertDialog d = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
                        d.setTitleText("Successfully submitted");
                        d.show();
                    }
                }, 3000);


            }
        });

        return view;
    }
}
