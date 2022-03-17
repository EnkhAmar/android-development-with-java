package mn.amra.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CustomActivity1 extends AppCompatActivity {
    private EditText mName;
    private EditText mAge;

    public static final String EXTRA_NAME = "mn.amra.lab5.extra.NAME";
    public static final String EXTRA_AGE = "mn.amra.lab5.extra.AGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom1);
        mName = findViewById(R.id.edit_text_name);
        mAge = findViewById(R.id.edit_text_age);

        Intent intent = getIntent();
        String name = intent.getStringExtra(EXTRA_NAME);
        String age = intent.getStringExtra(EXTRA_AGE);

        mName.setText(name);
        mAge.setText(age);
    }

    public void cancel(View view) {
        finish();
    }

    public void returnValues(View view) {
        String name = mName.getText().toString();
        String age = mAge.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(EXTRA_NAME, name);
        intent.putExtra(EXTRA_AGE, age);
        setResult(RESULT_OK, intent);
        finish();
    }
}