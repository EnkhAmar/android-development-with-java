package mn.amra.lab4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "lab4.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "words";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_FOREIGN = "word_foreign";
    private static final String COLUMN_MONGOLIAN = "word_mongolian";
    private static final String COLUMN_BOOKMARKED = "bookmarked";
    public static final String QUERY_ALl = "SELECT * FROM " + TABLE_NAME;
    public static final String QUERY_ONLY_BOOKMARKED = "SELECT * FROM " + TABLE_NAME + " WHERE bookmarked = 1";
    public static final String QUERY_FIRST = "SELECT * FROM " + TABLE_NAME + " LIMIT 1";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_FOREIGN + " VARCHAR(255), " +
                        COLUMN_MONGOLIAN + " VARCHAR(255), " +
                        COLUMN_BOOKMARKED + " BOOLEAN DEFAULT FALSE);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void addWord(String foreign, String mongolian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FOREIGN, foreign);
        contentValues.put(COLUMN_MONGOLIAN, mongolian);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1) {
            Toast.makeText(context, "Алдаа гарлаа", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Амжилттай хадгаллаа", Toast.LENGTH_SHORT).show();
        }
    }

    void updateWord(String primary_key, String foreign, String mongolian) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FOREIGN, foreign);
        contentValues.put(COLUMN_MONGOLIAN, mongolian);
        long result = db.update(TABLE_NAME, contentValues, "_id=?", new String[]{primary_key});
        if (result == -1) {
            Toast.makeText(context, "Алдаа гарлаа", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Амжилттай хадгаллаа", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteWord(String primary_key) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{primary_key});
        if (result == -1) {
            Toast.makeText(context, "Алдаа гарлаа", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Амжилттай", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor getWords(String query) {
        if (query == null)
            query = QUERY_ALl;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor getFirstWord(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
