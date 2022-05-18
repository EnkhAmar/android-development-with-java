package mn.amra.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
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
    private String word_id = "", word_foreign = "", word_mongolia = "", word_bookmarked = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getMode();
        mForeignText = findViewById(R.id.text_foreign);
        mMongolianText = findViewById(R.id.text_mongolian);

        dbHelper = new DatabaseHelper(MainActivity.this);

        populateWithData();
    }

    public void navigateToAddWordActivity(View view) {
        Intent intent = new Intent(this, AddWordActivity.class);
        startActivity(intent);
    }

    private void populateWithData() {
        Cursor cursor = dbHelper.getWords(DatabaseHelper.QUERY_ALl);
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            word_id = cursor.getString(0);
            word_foreign = cursor.getString(1);
            word_mongolia = cursor.getString(2);
            word_bookmarked = cursor.getString(3);

//            mForeignText.setText(word_foreign);
//            mMongolianText.setText(word_mongolia);
        }
    }



//    public void handleButtonClick(View view) {
//        Intent intent = new Intent();
////        intent.setClass(getBaseContext());
//        switch (view.getId()) {
//            case R.id.previousButton:
//                return;
//            case R.id.nextButton:
//                return;
//            case R.id.deleteButton:
//                return;
//            case R.id.editButton:
//                return;
//            case R.id.addButton:
//                return;
//            default:
//                return;
//        }
//    }
}