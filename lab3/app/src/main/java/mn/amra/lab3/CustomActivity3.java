package mn.amra.lab3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomActivity3 extends AppCompatActivity {
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button button_time;
    private Date pickedTime;
    private Calendar calendar;
    private TimePickerDialog timePickerDialog;

    public static final String LOG_TAG = CustomActivity3.class.getSimpleName();
    public static final String EXTRA_RADIO_BUTTON_MALE = "mn.amra.extra.MALE";
    public static final String EXTRA_RADIO_BUTTON_FEMALE = "mn.amra.extra.FEMALE";
    public static final String EXTRA_TIME = "mn.amra.exra.TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom3);

        radioButtonMale = findViewById(R.id.radio_button_male);
        radioButtonFemale = findViewById(R.id.radio_button_female);
        button_time = findViewById(R.id.button_pick_time);

        Intent intent = getIntent();
        Boolean option1 = intent.getBooleanExtra(EXTRA_RADIO_BUTTON_MALE, false);
        Boolean option2 = intent.getBooleanExtra(EXTRA_RADIO_BUTTON_FEMALE, false);
        Date date = (Date) intent.getSerializableExtra(EXTRA_TIME);

        radioButtonMale.setChecked(option1);
        radioButtonFemale.setChecked(option2);
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String date_string = hour + ":" + minute;
            button_time.setText(date_string);
            pickedTime = date;
        }
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
        intent.putExtra(EXTRA_RADIO_BUTTON_MALE, radioButtonMale.isChecked());
        intent.putExtra(EXTRA_RADIO_BUTTON_FEMALE, radioButtonFemale.isChecked());
        intent.putExtra(EXTRA_TIME, pickedTime);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    public void showTimePickerDialog(View view) {
//        DialogFragment timePickerFragment = new TimePickerFragment();
//        timePickerFragment.show(getSupportFragmentManager(), "timePicker");
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(CustomActivity3.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int pHour, int pMinute) {
                String time_string = pHour + ":" + pMinute;
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

                button_time.setText(time_string);
                try {
                    pickedTime = formatter.parse(time_string);
                } catch (ParseException e) {
                    pickedTime = null;
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        }, hour, minute, DateFormat.is24HourFormat(CustomActivity3.this));
        timePickerDialog.show();
    }
}