package mn.amra.lab3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private String mActivity1Name = "Default Name";
    private String mActivity1Age = "0";
    private Boolean mOption1 = false;
    private Boolean mOption2 = false;
    private Boolean mOption3 = false;
    private Date mDatePicked = null;
    private Boolean mRadio1 = false;
    private Boolean mRadio2 = false;
    private Date mTimePicked = null;
    private Float mRating = (float) 0.0;
    private Date mTimePicked2 = null;

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE_A1 = 1;
    public static final int REQUEST_CODE_A2 = 2;
    public static final int REQUEST_CODE_A3 = 3;
    public static final int REQUEST_CODE_A4 = 4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_1:
                callActivity1ViaOption();
                return true;
            case R.id.item_2:
                callActivity2ViaOption();
                return true;
            case R.id.item_3:
                callActivity3ViaOption();
                return true;
            case R.id.item_4:
                callActivity4ViaOption();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void callActivity1(View view) {
        Intent intent = new Intent(this, CustomActivity1.class);
        intent.putExtra(CustomActivity1.EXTRA_NAME, mActivity1Name);
        intent.putExtra(CustomActivity1.EXTRA_AGE, mActivity1Age);
        startActivityForResult(intent, REQUEST_CODE_A1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(LOG_TAG, "Request Code: " + requestCode + "\nResult Code: " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_A1:
                if (resultCode == RESULT_OK) {
                    mActivity1Name = data.getStringExtra(CustomActivity1.EXTRA_NAME);
                    mActivity1Age = data.getStringExtra(CustomActivity1.EXTRA_AGE);
                }
                break;
            case REQUEST_CODE_A2:
                if (resultCode == RESULT_OK) {
                    mOption1 = data.getBooleanExtra(CustomActivity2.EXTRA_OPTION1, mOption1);
                    mOption2 = data.getBooleanExtra(CustomActivity2.EXTRA_OPTION2, mOption2);
                    mOption3 = data.getBooleanExtra(CustomActivity2.EXTRA_OPTION3, mOption3);
                    mDatePicked = (Date) data.getSerializableExtra(CustomActivity2.EXTRA_DATE);
                    Log.d(LOG_TAG, "Date picked: " + mDatePicked);
                }
                break;
            case REQUEST_CODE_A3:
                if (resultCode == RESULT_OK) {
                    mRadio1 = data.getBooleanExtra(CustomActivity3.EXTRA_RADIO_BUTTON_MALE, mRadio1);
                    mRadio2 = data.getBooleanExtra(CustomActivity3.EXTRA_RADIO_BUTTON_FEMALE, mRadio2);
                    mTimePicked = (Date) data.getSerializableExtra(CustomActivity3.EXTRA_TIME);
                    Log.d(LOG_TAG, "Time picked: " + mTimePicked);
                }
                break;
            case REQUEST_CODE_A4:
                if (resultCode == RESULT_OK) {
                    mRating = data.getFloatExtra(CustomActivity4.EXTRA_RATING, mRating);
                    mTimePicked2 = (Date) data.getSerializableExtra(CustomActivity4.EXTRA_TIME);
                }
                break;
        }
    }

    private void callActivity1ViaOption() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(CustomActivity1.EXTRA_NAME, mActivity1Name);
        intent.putExtra(CustomActivity1.EXTRA_AGE, mActivity1Age);
        try {
            startActivityForResult(intent, REQUEST_CODE_A1);
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(this, "There are no app installed to perform this request.", Toast.LENGTH_SHORT).show();
        }
    }

    public void callActivity2(View view) {
        Intent intent = new Intent(this, CustomActivity2.class);
        intent.putExtra(CustomActivity2.EXTRA_OPTION1, mOption1);
        intent.putExtra(CustomActivity2.EXTRA_OPTION2, mOption2);
        intent.putExtra(CustomActivity2.EXTRA_OPTION3, mOption3);
        intent.putExtra(CustomActivity2.EXTRA_DATE, mDatePicked);
        startActivityForResult(intent, REQUEST_CODE_A2);
    }

    private void callActivity2ViaOption() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(CustomActivity2.EXTRA_OPTION1, mOption1);
        intent.putExtra(CustomActivity2.EXTRA_OPTION2, mOption2);
        intent.putExtra(CustomActivity2.EXTRA_OPTION3, mOption3);
        intent.putExtra(CustomActivity2.EXTRA_DATE, mDatePicked);
        try {
            startActivityForResult(intent, REQUEST_CODE_A2);
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(this, "There are no app installed to perform this request.", Toast.LENGTH_SHORT).show();
        }
    }

    public void callActivity3(View view) {
        Intent intent = new Intent(this, CustomActivity3.class);
        intent.putExtra(CustomActivity3.EXTRA_RADIO_BUTTON_MALE, mRadio1);
        intent.putExtra(CustomActivity3.EXTRA_RADIO_BUTTON_FEMALE, mRadio2);
        intent.putExtra(CustomActivity3.EXTRA_TIME, mTimePicked);
        startActivityForResult(intent, REQUEST_CODE_A3);
    }

    public void callActivity3ViaOption() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(CustomActivity3.EXTRA_RADIO_BUTTON_MALE, mRadio1);
        intent.putExtra(CustomActivity3.EXTRA_RADIO_BUTTON_FEMALE, mRadio2);
        intent.putExtra(CustomActivity3.EXTRA_TIME, mTimePicked);
        try {
            startActivityForResult(intent, REQUEST_CODE_A3);
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(this, "There are no app installed to perform this request.", Toast.LENGTH_SHORT).show();
        }
    }

    public void callActivity4(View view) {
        Intent intent = new Intent(this, CustomActivity4.class);
        intent.putExtra(CustomActivity4.EXTRA_RATING, mRating);
        intent.putExtra(CustomActivity4.EXTRA_TIME, mTimePicked2);
        startActivityForResult(intent, REQUEST_CODE_A4);
    }

    public void callActivity4ViaOption() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.putExtra(CustomActivity4.EXTRA_RATING, mRating);
        intent.putExtra(CustomActivity4.EXTRA_TIME, mTimePicked2);
        try {
            startActivityForResult(intent, REQUEST_CODE_A4);
        } catch (android.content.ActivityNotFoundException exception) {
            Toast.makeText(this, "There are no app installed to perform this request.", Toast.LENGTH_SHORT).show();
        }
    }
}