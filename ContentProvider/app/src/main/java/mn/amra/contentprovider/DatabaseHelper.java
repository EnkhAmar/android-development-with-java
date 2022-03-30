package mn.amra.contentprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mn.amra.contentprovider.database";
    private static final int DATABASE_VERSION = 1;

    public static final String ID = "_ID";
    public static final String NAME = "NAME";
    public static final String NAMES_TABLE = "NAMES";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + NAMES_TABLE + "" +
                "("
                    + ID + " INTEGER PRIMARY KEY,"
                    + NAME + " TEXT"
                    + ");");
        sqLiteDatabase.execSQL("INSERT INTO NAMES (name) VALUES ('Roger');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }
}
