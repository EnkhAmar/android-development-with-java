package mn.num.edu.counterhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private int mCount = 0;
    private TextView textView;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mCount != 0) {
            outState.putInt("count", mCount);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text_count);

        if (savedInstanceState != null) {
            mCount = savedInstanceState.getInt("count");
            textView.setText(String.format(Locale.getDefault(), "%d", mCount));
        }
    }

    public void countUp(View view) {
        mCount++;
//        textView.setText(Integer.toString(mCount));
        textView.setText(String.format(Locale.getDefault(), "%d", mCount));
    }
}