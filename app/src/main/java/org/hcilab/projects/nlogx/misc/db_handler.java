package org.hcilab.projects.nlogx.misc;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.hcilab.projects.nlogx.ui.not;

import java.util.ArrayList;


public class db_handler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE = "notif";
    private static final String LOG="muapp";
    public db_handler(Context context) {
        super(context, "notif.db", null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS notif");
        String CREATE_CONTACTS_TABLE = " CREATE TABLE notif(name TEXT PRIMARY KEY,count INT) ";
        Log.v(LOG,"creating");
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS notif");
        onCreate(db);
    }

    public void if_exist(String name)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int k=0;
        String q = "select * from notif where name = ?";
        Log.v(LOG,"app: "+name);
        //String query = "SELECT count FROM notif WHERE name =" + name ;
        Cursor cursor = db.rawQuery(q,new String [] {name});
        //Cursor  cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst())
        {
            //cursor.moveToFirst();
            k = cursor.getInt(1);
            k++;
            Log.v(LOG,"update "+k);
            ContentValues cv = new ContentValues();
            cv.put("name",name);
            cv.put("count",k);
            try
            {
                String[] args = new String[]{name};
                Log.v(LOG,name+k);
                db.update(TABLE, cv, "name =?", args);

            }catch (SQLiteException e) {

            }
        }
        else
        {
            k++;
            Log.v(LOG,"insert "+k);
            ContentValues contentValues = new ContentValues();
            contentValues.put("name",name);
            contentValues.put("count",k);
            db.insert(TABLE,null,contentValues);
        }

    }

    public ArrayList<not> get_list()
    {
        SQLiteDatabase dw  = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE;
        Cursor c      = dw.rawQuery(selectQuery,null);
        ArrayList<not> datalist=new ArrayList<not>();
        if (c.moveToFirst()) {
            do {
                // do what you need with the cursor here
                String a = c.getString(0);
                int b = c.getInt(1);
                not obj = new not(a);
                obj.setCount(b);
                datalist.add(obj);
                Log.v(LOG,"ddddd"+a+"   "+b);
            } while (c.moveToNext());
        }

        c.close();
        dw.close();
        return datalist;
    }
}