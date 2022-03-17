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

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE_A1 = 1;
    public static final int REQUEST_CODE_A2 = 2;


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
                // handle
                return true;
            case R.id.item_4:
                // handle
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
}