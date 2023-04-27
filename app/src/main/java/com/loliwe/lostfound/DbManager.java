package com.loliwe.lostfound;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbManager extends SQLiteOpenHelper {
    private static final String dbname = "AllItems.db";
    private static final String tablename = "tbl_all";

    //public static final String _ID = "_id";
    //public static final String TYPE = "type";
    //public static final String NAME = "name";
    //public static final String PHONE = "phone";
    //public static final String DESCR = "description";
    //public static final String DATE = "date";
    //public static final String AREA = "location";

    SQLiteDatabase db = this.getWritableDatabase();
    static Context context;

    public DbManager(Context context) {
        super(context, dbname, null, 1);
    }

    public DbManager(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DbManager(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
        super(context, name, version, openParams);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry = "create table "+tablename+" (id integer primary key autoincrement, type text, name text, phone text, description text not null, date date not null, location text not null)";
        db.execSQL(qry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tablename);
        onCreate(db);
    }

    public String addRecord(String p1, String p2, String p3, String p4, String p5, String p6) {

        //SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("type",p1);
        cv.put("name",p2);
        cv.put("phone",p3);
        cv.put("description",p4);
        cv.put("date",p5);
        cv.put("location",p6);

        long res = db.insert(tablename, null, cv);

        if(res==-1) {
            return "Failed";
        } else {
            return "Successfully inserted";
        }
    }

    /*public SimpleCursorAdapter populateListViewFromDB() {
        //SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[] {"id", "type", "name", "description", "phone", "date", "location"};
        Cursor cursor = db.query(DbManager.tablename, columns, null, null, null, null, null);
        String[] fromFieldNames = new String[] {
                "type", "description", "date", "name", "phone"
        };
        int[] toViewIDs = new int[] {
                R.id.item_type, R.id.item_descr, R.id.item_date, R.id.item_name, R.id.item_phone
        };

        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
                context,
                R.layout.single_item,
                cursor,
                fromFieldNames,
                toViewIDs
        );
        return contactAdapter;
    }*/

    public DbManager open() {
        SQLiteDatabase db = this.getWritableDatabase();
        return this;
    }

    public Cursor getdata(String s) {
        //SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = new String[] {"id", "type", "name", "description", "phone", "date", "location"};
        Cursor cursor = db.query(DbManager.tablename, columns, null, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        //String qry = "select * from " + tablename + " where name = " + name;
        //Cursor crs = db.rawQuery(qry, null, null, null, null, null, null, null);
        return cursor;
    }

    public  Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + tablename;
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public boolean updateItem(String id, String type, String descr) {
        SQLiteDatabase db = this.getWritableDatabase();

        long res = 0;
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        if(type != null && type.length() > 3) cv.put("type",type);
        if(type != null && descr.length() > 3) cv.put("description",descr);

        if(cv.size() > 0) res = db.update(tablename, cv,"id=?",new String[]{id});
        //db.execSQL("update " + tablename + " set type ='" +  type + "' where description ='" + descr  + "'" );
        return true;
        //db.close();
    }

    public int deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //String qry = "delete from " +  tablename + " where descr ='" + descr + "'";

        return db.delete(tablename, "id = ?", new String[]{id});
        //db.close();
    }

    public void truncate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String qry1 = "delete from " + tablename;
        String qry2 = "delete from sqlite_sequence where descr='" + tablename + "'";
        db.execSQL(qry1);
        db.execSQL(qry2);
        db.close();
    }
}
