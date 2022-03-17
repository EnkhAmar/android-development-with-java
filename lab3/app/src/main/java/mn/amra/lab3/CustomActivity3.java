package mn.amra.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;

public class CustomActivity3 extends AppCompatActivity {
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button button_time;
    private Time pickedTime;

    public static final String LOG_TAG = CustomActivity3.class.getSimpleName();
    public static final String EXTRA_RADIO_BUTTON_MALE = "mn.amra.MALE";
    public static final String EXTRA_RADIO_BUTTON_FEMALE = "mn.amra.FEMALE";
    public static final String EXTRA_TIME = "mn.amra.exra.TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom3);

        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);
        button_time = findViewById(R.id.button_pick_time);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_button_male:
                if (checked)
                    checked = true;
                break;
            case R.id.radio_button_female:
                if (checked)
                    checked = true;
                break;
        }
    }

    public void returnValues(View view) {
        Intent intent = new Intent();

        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog (Bundle savedInstance) {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Log.d(LOG_TAG, "Hour: " + hourOfDay + " Minute: " + minute);
        }
    }

    public void showTimePickerDialog(View view) {
        DialogFragment timePickerFragment = new TimePickerFragment();
        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
    }
}