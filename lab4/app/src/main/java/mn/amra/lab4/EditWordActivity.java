package mn.amra.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditWordActivity extends AppCompatActivity {
    EditText mForeignText, mMongolianText;
    String word_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);

        mForeignText = findViewById(R.id.edit_text_foreign);
        mMongolianText = findViewById(R.id.edit_text_mongolian);

        Intent intent = getIntent();
        word_id = intent.getStringExtra(MainActivity.EXTRA_WORD_ID);
        Toast.makeText(this, "" + intent.getStringExtra(MainActivity.EXTRA_WORD_FOREIGN), Toast.LENGTH_SHORT).show();
        mForeignText.setText(intent.getStringExtra(MainActivity.EXTRA_WORD_FOREIGN));
        mMongolianText.setText(intent.getStringExtra(MainActivity.EXTRA_WORD_MONGOLIAN));
    }

    public void handleCancel(View view) {
        finish();
    }

    public void handleUpdateWord(View view) {
        if (validateForm()) {
            DatabaseHelper dbHelper = new DatabaseHelper(EditWordActivity.this);
            dbHelper.updateWord(
                    word_id,
                    mForeignText.getText().toString().trim(),
                    mMongolianText.getText().toString().trim()
            );
            setResult(RESULT_OK);
            finish();
        }
    }

    private boolean validateForm() {
        Boolean foreignText = mForeignText.getText().toString().trim().length() > 0;
        Boolean mongolianText = mMongolianText.getText().toString().trim().length() > 0;
        if (!foreignText) mForeignText.setError(getString(R.string.error_text_empty_foreign));
        if (!mongolianText) mMongolianText.setError(getString(R.string.error_text_empty_mongolian));
        return foreignText && mongolianText;
    }
}