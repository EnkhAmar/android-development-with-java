package mn.amra.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    DatabaseHelper dbHelper;
    ArrayList<String> task_id, task_task, task_status, task_due_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                startActivity(intent);
            }
        });

        dbHelper = new DatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_task = new ArrayList<>();
        task_status = new ArrayList<>();
        task_due_date = new ArrayList<>();
    }

    private void storeDataInArray () {
        Cursor cursor = dbHelper.getTodos();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data...", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                task_id.add(cursor.getString(0));
                task_task.add(cursor.getString(1));
                task_status.add(cursor.getString(2));
                task_due_date.add(cursor.getString(3));
            }
        }
    }
}