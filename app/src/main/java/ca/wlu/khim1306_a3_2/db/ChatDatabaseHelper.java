package ca.wlu.khim1306_a3_2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 1;

    public static final String TABLE_NAME = "messages";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "message";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    private static final String DATABASE_DESTROY = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);
        sqLiteDatabase.execSQL(DATABASE_DESTROY);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
        onUpgrade(db, oldVersion, newVersion);
    }
}
