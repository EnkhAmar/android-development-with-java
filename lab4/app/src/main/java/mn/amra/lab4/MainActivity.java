package mn.amra.lab4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int mode;
    private TextView mMongolianText, mForeignText;
    private LinearLayout mLayoutMongolian, mLayoutForeign;
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
    public static final int REQUEST_CODE_FILE = 3;
    public static final int MODE_DEFAULT = 1;
    public static final int MODE_FOREIGN_ONLY = 2;
    public static final int MODE_MN_ONLY = 3;
    public static final String EXTRA_WORD_ID = "mn.amra.lab4.extra.WORD_ID";
    public static final String EXTRA_WORD_FOREIGN = "mn.amra.lab4.extra.WORD_FOREIGN";
    public static final String EXTRA_WORD_MONGOLIAN = "mn.amra.lab4.extra.WORD_MONGOLIAN";
    public static final String EXTRA_WORD_BOOKMARKED = "mn.amra.lab4.extra.WORD_BOOKMARKED";
    public static final String PREFERENCE_FILE_KEY = "mn.amra.lab4.PREFERENCE_FILE_KEY";


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
//            recreate();
            cursor = populateWithData();
            updateTextViewWordPair();
        } else if (requestCode == REQUEST_CODE_UPDATE) {
//            recreate();
            cursor = populateWithData();
            updateTextViewWordPair();
        } else if (requestCode == REQUEST_CODE_FILE) {
            Uri file_uri = data.getData();
            BufferedReader fReader;
            InputStream inputStream = null;
            String line;
            while ((line = fReader.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
            try {

            } catch (Exception exception) {

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mForeignText = findViewById(R.id.text_foreign);
        mMongolianText = findViewById(R.id.text_mongolian);
        mLayoutForeign = findViewById(R.id.foreign_layout);
        mLayoutMongolian = findViewById(R.id.mongolian_layout);

        dbHelper = new DatabaseHelper(MainActivity.this);
        cursor = populateWithData();

        updateTextViewWordPair();
        handleModeUpdate();
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

    private void initializeMode() {
        sp = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        mode = sp.getInt("mode", MODE_DEFAULT);
    }

    private void setMode(int mode) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("mode", mode);
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mode_default:
                setMode(MODE_DEFAULT);
                break;
            case R.id.mode_only_foreign:
                setMode(MODE_FOREIGN_ONLY);
                break;
            case R.id.mode_only_mongolian:
                setMode(MODE_MN_ONLY);
                break;
            case R.id.option_upload_file:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                try {
                    startActivityForResult(intent, REQUEST_CODE_FILE);
                } catch (ActivityNotFoundException exception) {
                    Toast.makeText(this, "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
        handleModeUpdate();
        return true;
    }

    private void handleModeUpdate() {
        initializeMode();
        switch (mode) {
            case MODE_FOREIGN_ONLY:
                mLayoutForeign.setVisibility(View.VISIBLE);
                mLayoutMongolian.setVisibility(View.INVISIBLE);
                return;
            case MODE_MN_ONLY:
                Log.d(LOG_TAG, "mn only");
                mLayoutForeign.setVisibility(View.INVISIBLE);
                mLayoutMongolian.setVisibility(View.VISIBLE);
                return;
            default:
                mLayoutForeign.setVisibility(View.VISIBLE);
                mLayoutMongolian.setVisibility(View.VISIBLE);
                return;
        }
    }
}