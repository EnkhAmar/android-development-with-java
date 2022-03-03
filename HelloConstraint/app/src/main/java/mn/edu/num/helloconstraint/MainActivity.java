package mn.edu.num.helloconstraint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int mCount = 0;
    private TextView mShowCount;
    private AppCompatButton mButtonZero;
    private AppCompatButton mButtonCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
        mButtonZero = (AppCompatButton) findViewById(R.id.button_zero);
        mButtonCount = (AppCompatButton) findViewById(R.id.button_count);
    }

    /*
     * Shows a Toast when the TOAST button is clicked.
     *
     * @param view The view that triggered this onClick handler.
     *             Since a toast always shows on the top,
     *             the passed in view is not used.
     */
    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.toast_message,
                Toast.LENGTH_SHORT);
        toast.show();
    }

    /*
     * Increments the number in the TextView when the COUNT button is clicked.
     *
     * @param view The view that triggered this onClick handler.
     *             Since the count always appears in the TextView,
     *             the passed in view is not used.
     */
    public void countUp(View view) {
        mCount++;
        if (mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
            mButtonZero.setBackgroundColor(getResources().getColor(R.color.purple_200));
            mButtonCount.setBackgroundColor(getResources().getColor(R.color.teal_700));
        }
    }

    public void setZero(View view) {
        if (mShowCount != null && mButtonZero != null) {
            mCount = 0;
            mShowCount.setText("0");
            mButtonZero.setBackgroundColor(getResources().getColor(R.color.colorGray));
            mButtonCount.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }
}