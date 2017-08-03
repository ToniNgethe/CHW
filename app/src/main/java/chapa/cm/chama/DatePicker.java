package chapa.cm.chama;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**Dia
 * Created by toni on 8/3/17.
 */

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private ChoosenDate choosenDate;
    private Context ctx;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.choosenDate = (ChoosenDate)context;
        this.ctx = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(c.getTime());
        Log.d("sdknfjsdfnsjkfnjkds", formattedDate);
        this.choosenDate.thisDate(formattedDate);
    }

    public interface ChoosenDate{
        void thisDate(String date);
    }
}
