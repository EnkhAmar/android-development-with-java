package mn.amra.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Todo.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "todos";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TASK = "task";
//    public static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_DUE_DATE = "due_date";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
        public void onCreate(SQLiteDatabase db) {
            String query =
                    "CREATE TABLE " + TABLE_NAME +
                            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            COLUMN_TASK + " VARCHAR(255), " +
                            COLUMN_STATUS + " BOOLEAN, " +
                            COLUMN_DUE_DATE + " DATE DEFAULT NULL);";
            db.execSQL(query);
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addTask(String task, Boolean status, Date due_date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TASK, task);
        contentValues.put(COLUMN_STATUS, status);
        contentValues.put(COLUMN_DUE_DATE, String.valueOf(due_date));
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Saved successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
