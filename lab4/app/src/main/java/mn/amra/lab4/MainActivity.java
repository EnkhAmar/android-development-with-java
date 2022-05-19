package mn.amra.lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int mode;
    private TextView mMongolianText, mForeignText;
    private int currentIndex = 0;
    public SharedPreferences sp;
    private DatabaseHelper dbHelper;
    private Cursor cursor;

    private String word_id = "", word_foreign = "", word_mongolia = "", word_bookmarked = "";

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int DIRECTION_PREVIOUS = 1;
    private static final int DIRECTION_NEXT = 2;
    public static final int REQUEST_CODE = 1;
    public static final int REQUEST_CODE_UPDATE = 2;
    public static final String EXTRA_WORD_ID = "mn.amra.lab4.extra.WORD_ID";
    public static final String EXTRA_WORD_FOREIGN = "mn.amra.lab4.extra.WORD_FOREIGN";
    public static final String EXTRA_WORD_MONGOLIAN = "mn.amra.lab4.extra.WORD_MONGOLIAN";
    public static final String EXTRA_WORD_BOOKMARKED = "mn.amra.lab4.extra.WORD_BOOKMARKED";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            recreate();
        } else if (requestCode == REQUEST_CODE_UPDATE) {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getMode();
        mForeignText = findViewById(R.id.text_foreign);
        mMongolianText = findViewById(R.id.text_mongolian);

        dbHelper = new DatabaseHelper(MainActivity.this);
        cursor = populateWithData();

        updateTextViewWordPair();
    }

    public void navigateToAddWordActivity(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void navigateToEditWordActivity(View view) {
        Intent intent = new Intent(this, EditWordActivity.class);
        intent.putExtra(EXTRA_WORD_ID, word_id);
        intent.putExtra(EXTRA_WORD_FOREIGN, word_foreign);
        intent.putExtra(EXTRA_WORD_MONGOLIAN, word_mongolia);
        startActivityForResult(intent, REQUEST_CODE_UPDATE);
    }

    private void setWordPairData(Cursor cursor) {
        word_id = cursor.getString(0);
        word_foreign = cursor.getString(1);
        word_mongolia = cursor.getString(2);
        word_bookmarked = cursor.getString(3);
    }

    private void updateTextViewWordPair() {
        mForeignText.setText(word_foreign);
        mMongolianText.setText(word_mongolia);
    }

    private Cursor populateWithData() {
        Cursor cursor = dbHelper.getWords(DatabaseHelper.QUERY_ALl);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data ...", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToNext();
            setWordPairData(cursor);
        }
        return cursor;
    }

    private Cursor getWordPair(int direction) {
        if (direction == DIRECTION_PREVIOUS)
            cursor.moveToPrevious();
        else if (direction == DIRECTION_NEXT)
            cursor.moveToNext();
        try {
            setWordPairData(cursor);
        } catch (CursorIndexOutOfBoundsException exception) {
            if (direction == DIRECTION_NEXT) {
//                Toast.makeText(this, "This is the last word", Toast.LENGTH_SHORT).show();
//                cursor.moveToPrevious();
                cursor.moveToFirst();
            } else {
//                Toast.makeText(this, "This is the first word", Toast.LENGTH_SHORT).show();
//                cursor.moveToNext();
                cursor.moveToLast();
            }
            setWordPairData(cursor);
        } catch (Exception exception) {
            Log.d(LOG_TAG, exception.getMessage());
        }
        updateTextViewWordPair();
        return cursor;
    }

    public void handleButtonClick(View view) {
//        Intent intent = new Intent();
//        intent.setClass(getBaseContext());
        switch (view.getId()) {
            case R.id.prev_btn:
                getWordPair(DIRECTION_PREVIOUS);
                return;
            case R.id.next_btn:
                getWordPair(DIRECTION_NEXT);
                return;
            case R.id.edit_btn:
                navigateToEditWordActivity(view);
                return;
            case R.id.del_btn:
                dbHelper.deleteWord(word_id);
                recreate();
                return;
            default:
                return;
        }
    }
}