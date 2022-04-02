package mn.amra.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    private EditText textTask;
    private SwitchCompat completedStatus;
    private Button buttonDueDate;
    private Date pickedDate = null;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;

    public static final String LOG_TAG = TaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        textTask = findViewById(R.id.edit_text_task);
        completedStatus = findViewById(R.id.switch_status);
        buttonDueDate = findViewById(R.id.button_due_date);
    }

    public void handleSave(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(TaskActivity.this);
        dbHelper.addTask(
                textTask.getText().toString().trim(),
                completedStatus.isChecked(),
                pickedDate
        );
    }

    public void openDatePickerDialog(View view) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(TaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int pYear, int pMonth, int pDay) {
                String date_sting = pYear + "-" + (++pMonth) + "-" + pDay;
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

                buttonDueDate.setText(date_sting);
                try {
                    pickedDate = formatter.parse(date_sting);
                } catch (ParseException e) {
                    pickedDate = null;
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        }, year, month, dayOfMonth);
        datePickerDialog.show();
    }
}