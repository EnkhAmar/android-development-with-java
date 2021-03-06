package mn.amra.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton addButton;

    DatabaseHelper dbHelper;
    ArrayList<String> task_id, task_task, task_status, task_due_date;
    CustomAdapter customAdapter;
    private String query = DatabaseHelper.QUERY_ALL;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            recreate();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        dbHelper = new DatabaseHelper(MainActivity.this);
        task_id = new ArrayList<>();
        task_task = new ArrayList<>();
        task_status = new ArrayList<>();
        task_due_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, task_id, task_task, task_status, task_due_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    private void storeDataInArrays () {
        Cursor cursor = dbHelper.getTodos(query);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_1:
                query = DatabaseHelper.QUERY_ALL;
                break;
            case R.id.item_2:
                query = DatabaseHelper.QUERY_ONLY_COMPLETED;
                break;
            case R.id.item_3:
                query = DatabaseHelper.QUERY_ONLY_NOT_COMPLETED;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
//        recreate();

        task_id = new ArrayList<>();
        task_task = new ArrayList<>();
        task_status = new ArrayList<>();
        task_due_date = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, this, task_id, task_task, task_status, task_due_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        return true;
    }
}