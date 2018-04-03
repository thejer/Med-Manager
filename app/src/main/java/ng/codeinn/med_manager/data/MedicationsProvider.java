package ng.codeinn.med_manager.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static ng.codeinn.med_manager.data.MedicationsDbContract.MedicationsEntry.*;

/**
 * Created by Jer on 02/04/2018.
 */

public class MedicationsProvider extends ContentProvider {


    public static final int CODE_MEDICATIONS = 100,
            CODE_MEDICATIONS_WITH_MONTH = 101,
            CODE_MEDICATIONS_WITH_ID = 102;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MedicationsDbHelper mDbHelper;


    public static UriMatcher buildUriMatcher(){

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        final String authority = MedicationsDbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MedicationsDbContract.PATH_MEDICATIONS ,CODE_MEDICATIONS);

        matcher.addURI(authority, MedicationsDbContract.PATH_MEDICATIONS + "/*", CODE_MEDICATIONS_WITH_MONTH);

        matcher.addURI(authority, MedicationsDbContract.PATH_MEDICATIONS + "/#", CODE_MEDICATIONS_WITH_ID);

        return matcher;

    }


    @Override
    public boolean onCreate() {
        mDbHelper = new MedicationsDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match){
            case(CODE_MEDICATIONS):
                long id = db.insert(TABLE_NAME, null, values);
                if (id > 0){
                    returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
                }else {
                    throw new SQLException("Failed to insert row into " + uri);

                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                            @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor cursor;

        switch (match){
            case CODE_MEDICATIONS:{
                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_MEDICATIONS_WITH_ID:{
                selection = _ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};

                cursor = db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case CODE_MEDICATIONS_WITH_MONTH:{
                String month = uri.getLastPathSegment();

                String [] selectionArguments = new String[]{month};

                cursor = db.query(TABLE_NAME,
                        projection,
                        START_MONTH + " = ? ",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;

            }
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int rowsDeleted;

        if (null == selection) selection = "1";

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match){
            case CODE_MEDICATIONS:
                rowsDeleted = db.delete(TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case CODE_MEDICATIONS_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String whereClause = "_id=?";
                String[] whereArgs = new String[]{id};
                rowsDeleted = db.delete(TABLE_NAME, whereClause, whereArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        if (rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
