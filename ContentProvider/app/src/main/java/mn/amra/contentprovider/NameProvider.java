package mn.amra.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class NameProvider extends ContentProvider {
    public static final String AUTHORITY = "mn.amra.contentprovider.name.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/names");

    // MIME TYPES
    public static final String CONTENT_TYPE = "vnd.sdsu.dir/vnd.sdsu.name";
    public static final String CONTENT_ITEM_TYPE = "vnd.sdsu.cursor.item/vnd.sdsu.name";

    private DatabaseHelper nameHelper;
    private static UriMatcher nameUriMatcher = null;
    private static final int NAMES = 1;
    private static final int NAMES_ID = 2;

    static {
        nameUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        nameUriMatcher.addURI(AUTHORITY, "names", NAMES);
        nameUriMatcher.addURI(AUTHORITY, "names/#", NAMES_ID);
    }

    public NameProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        try {
            int match = nameUriMatcher.match(uri);
            switch (match) {
                case NAMES:
                    return CONTENT_TYPE;
                case NAMES_ID:
                    return CONTENT_ITEM_TYPE;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri);
            }
        } catch (Exception exception) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        try {
            if (nameUriMatcher.match(uri) != NAMES) {
                throw new IllegalArgumentException("Unknown URI " + uri);
            }

            SQLiteDatabase db = nameHelper.getWritableDatabase();
            long rowId = db.insert(DatabaseHelper.NAMES_TABLE, DatabaseHelper.NAME, values);
            if (rowId > 0) {
                Uri noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
                getContext().getContentResolver().notifyChange(noteUri, null);
                return noteUri;
            }
            throw new SQLException("Failed to insert row into " + uri);
        } catch (Exception exception) {
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        nameHelper = (new DatabaseHelper(getContext()));
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        String sqlSelection = null;
        switch (nameUriMatcher.match(uri)) {
            case NAMES:
                sqlSelection = selection;
                break;
            case NAMES_ID:
                sqlSelection = DatabaseHelper.NAMES_TABLE + "." + DatabaseHelper.ID + "=" + uri.getPathSegments().get(1);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        SQLiteDatabase db = nameHelper.getWritableDatabase();
        Cursor result = db.query(DatabaseHelper.NAMES_TABLE, projection, sqlSelection, selectionArgs, null, null, sortOrder);
        result.setNotificationUri(getContext().getContentResolver(), uri);
        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}