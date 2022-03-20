package mn.amra.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomActivity4 extends AppCompatActivity {
    private Date pickedTime;
    private Calendar calendar;
    private TimePicker timePicker;
    private SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
    private RatingBar ratingBar;
    private float current_rating = 0;

    public static final String LOG_TAG = CustomActivity4.class.getSimpleName();
    public static final String EXTRA_RATING = "mn.amra.extra.RATING";
    public static final String EXTRA_TIME = "mn.amra.extra.TIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom4);

        Intent intent = getIntent();
        Float rating = intent.getFloatExtra(EXTRA_RATING, (float) 0.0);
        Date date = (Date) intent.getSerializableExtra(EXTRA_TIME);

        timePicker = (TimePicker) findViewById(R.id.time_picker);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);

        if (rating != 0.0) {
            ratingBar.setRating(rating);
        }

        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            String time_string = hour + ":" + minute;
            Toast.makeText(CustomActivity4.this, "Time: " + time_string, Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(hour);
                timePicker.setMinute(minute);
            } else  {
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
            }
            pickedTime = date;
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int pHour, int pMinute) {
                String time_string = pHour + ":" + pMinute;
                try {
                    pickedTime = formatter.parse(time_string);
                } catch (ParseException e) {
                    pickedTime = null;
                    Log.e(LOG_TAG, e.getLocalizedMessage());
                }
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float currentRating, boolean fromUser) {
                current_rating = currentRating;
            }
        });

    }

    public void returnValues(View view) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RATING, current_rating);
        intent.putExtra(EXTRA_TIME, pickedTime);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancel(View view) {
        finish();
    }
}