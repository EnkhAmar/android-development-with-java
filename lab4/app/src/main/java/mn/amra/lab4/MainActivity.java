package mn.amra.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private int mode;
    private TextView mMongolianText, mForeignText;
    private int currentIndex = 0;
    public SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getMode();
//        mMongolianText = findViewById(R.id.mongolian);
//        mForeignText = findViewById(R.id.foreign);
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