package mn.amra.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomActivity2 extends AppCompatActivity {
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private Button button_date;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private Date pickedDate;

    public static final String LOG_TAG = CustomActivity2.class.getSimpleName();
    public static final String EXTRA_OPTION1 = "mn.edu.num.extra.OPTION1";
    public static final String EXTRA_OPTION2 = "mn.edu.num.extra.OPTION2";
    public static final String EXTRA_OPTION3 = "mn.edu.num.extra.OPTION3";
    public static final String EXTRA_DATE = "mn.edu.mn.extra.DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom2);

        checkbox1 = findViewById(R.id.checkbox_todo1);
        checkbox2 = findViewById(R.id.checkbox_todo2);
        checkbox3 = findViewById(R.id.checkbox_todo3);
        button_date = findViewById(R.id.button_date);

        Intent intent = getIntent();
        Boolean option1 = intent.getBooleanExtra(EXTRA_OPTION1, false);
        Boolean option2 = intent.getBooleanExtra(EXTRA_OPTION2, false);
        Boolean option3 = intent.getBooleanExtra(EXTRA_OPTION3, false);
        Date date = (Date) intent.getSerializableExtra(EXTRA_DATE);


        checkbox1.setChecked(option1);
        checkbox2.setChecked(option2);
        checkbox3.setChecked(option3);
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String date_string = year + "-" + (++month) + "-" + dayOfMonth;
            button_date.setText(date_string);
            pickedDate = date;
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            ((CheckBox) view).setTextColor(getResources().getColor(R.color.colorGray));
        } else {
            ((CheckBox) view).setTextColor(getResources().getColor(R.color.black));
        }
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_todo1:
                checkbox1.setChecked(checked);
                break;
            case R.id.checkbox_todo2:
                checkbox2.setChecked(checked);
                break;
            case R.id.checkbox_todo3:
                checkbox3.setChecked(checked);
                break;
        }
    }

    public void returnValues(View view) {
        Intent intent = new Intent();
        Log.d(MainActivity.class.getSimpleName(), "checkbox1: ");
        intent.putExtra(EXTRA_OPTION1, checkbox1.isChecked());
        intent.putExtra(EXTRA_OPTION2, checkbox2.isChecked());
        intent.putExtra(EXTRA_OPTION3, checkbox3.isChecked());
        intent.putExtra(EXTRA_DATE, pickedDate);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }

    public void openDatePickerDialog(View view) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(CustomActivity2.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pYear, int pMonth, int pDay) {
                String date_string = pYear + "-" + (++pMonth) + "-" + pDay;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                button_date.setText(date_string);
                try {
                    pickedDate = formatter.parse(date_string);
                } catch (ParseException e) {
                    pickedDate = null;
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}