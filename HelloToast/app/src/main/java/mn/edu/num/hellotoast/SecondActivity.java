package mn.edu.num.hellotoast;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String mCount = intent.getStringExtra(MainActivity.EXTRA_COUNT);

        TextView textCount = findViewById(R.id.text_count);
        textCount.setText(mCount);
    }
}