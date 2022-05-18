package mn.amra.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddWordActivity extends AppCompatActivity {
    EditText mForeignText, mMongolianText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        mForeignText = findViewById(R.id.edit_text_foreign);
        mMongolianText = findViewById(R.id.edit_text_mongolian);
    }

    public void handleCancel(View view) {
        finish();
    }

    public void handleInsertWord(View view) {
        if (validateForm()) {
            DatabaseHelper dbHelper = new DatabaseHelper(AddWordActivity.this);
            dbHelper.addWord(
                    mForeignText.getText().toString().trim(),
                    mMongolianText.getText().toString().trim()
            );
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