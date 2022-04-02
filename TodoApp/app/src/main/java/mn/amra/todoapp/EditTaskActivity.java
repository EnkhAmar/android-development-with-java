package mn.amra.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTaskActivity extends AppCompatActivity {

    private EditText textTask;
    private SwitchCompat completedStatus;
    private Button buttonDueDate;
    private Date pickedDate = null;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private String task_id;

    public static final String LOG_TAG = EditTaskActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        textTask = findViewById(R.id.edit_text_task_update);
        completedStatus = findViewById(R.id.switch_status_update);
        buttonDueDate = findViewById(R.id.button_due_date_update);

        populateWithData();
    }

    private void populateWithData() {
        Intent intent = getIntent();
        task_id = intent.getStringExtra(CustomAdapter.EXTRA_TASK_ID);
        String task_task = intent.getStringExtra(CustomAdapter.EXTRA_TASK_TASK);
        Boolean task_status = intent.getBooleanExtra(CustomAdapter.EXTRA_TASK_STATUS, false);
        String task_due_date = intent.getStringExtra(CustomAdapter.EXTRA_TASK_DUE_DATE);

        textTask.setText(task_task);
        completedStatus.setChecked(task_status);
        Date date = null;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy").parse(task_due_date);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, e.getLocalizedMessage());
        }
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            String date_string = year + "-" + (++month) + "-" + dayOfMonth;
            buttonDueDate.setText(date_string);
            pickedDate = date;
        }
    }

    public void handleSave(View view) {
        DatabaseHelper dbHelper = new DatabaseHelper(EditTaskActivity.this);
        Log.d(LOG_TAG, "task id: " + task_id);
        dbHelper.updateTodo(
                task_id,
                textTask.getText().toString().trim(),
                completedStatus.isChecked(),
                pickedDate
        );
        finish();
    }

    public void openDatePickerDialog(View view) {
        if (calendar == null) {
            calendar = Calendar.getInstance();
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
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

    public void handleDelete(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Delete confirmation")
                .setMessage("Do you really want to delete?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        DatabaseHelper dbHelper = new DatabaseHelper(EditTaskActivity.this);
                        dbHelper.deleteTodo(task_id);
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }
}