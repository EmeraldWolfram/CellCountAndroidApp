package com.example.foong.cellcount.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by FOONG on 6/2/2017.
 */

public class DatabaseLoader {

    private static final int DATABASE_VERSION       = 2;
    private static final String DATABASE_NAME       = "CellCount";
    private static final String CELL_COUNT_TABLE    = "CellCountTable";

    private static SQLiteDatabase database;
    private static DatabaseOpener dbOpener;

    public DatabaseLoader(Context context){
        dbOpener    = new DatabaseOpener(context);
        database    = dbOpener.getWritableDatabase();
    }

    public static void setDatabase(SQLiteDatabase database) {
        DatabaseLoader.database = database;
    }

    public boolean isDatabaseEmpty(){
        Boolean status = true;
        //database.beginTransaction();

        Cursor ptr = database.rawQuery("SELECT * FROM " + CELL_COUNT_TABLE, null);
        Log.d("DATABASE", "EMPTY CHECKING");
        if(ptr.moveToFirst())
            status = false;

        ptr.close();
        //database.endTransaction();

        return status;
    }

    public void clearDatabase(){
        //database.beginTransaction();
        database.execSQL("DELETE FROM " + CELL_COUNT_TABLE);
        database.execSQL("VACUUM");
        //database.endTransaction();
    }

    public void deleteFromDatabase(String crName){
        //database.beginTransaction();

        database.execSQL("DELETE FROM " + CELL_COUNT_TABLE +
                         " WHERE " + CountRecord.CR_DB_NAME + "='" + crName + "'");

        //database.endTransaction();
    }

    public void saveToDatabase(CountRecord cr){
        String SAVE_RECORD  = "INSERT OR REPLACE INTO " + CELL_COUNT_TABLE
                + " (" + CountRecord.CR_DB_NAME         + ", "  + CountRecord.CR_DB_V_COUNT
                + ", " + CountRecord.CR_DB_NV_COUNT     + ", "  + CountRecord.CR_DB_D_FACTOR
                + ", " + CountRecord.CR_DB_SQUARE_NUM   + ") VALUES ('";

        //database.beginTransaction();
        Log.d("DATABASE", "SAVING RECORD");
        database.execSQL(SAVE_RECORD
                + cr.getRecordName()        + "', "
                + cr.getViableCount()       + ", "
                + cr.getNonViableCount()    + ", "
                + cr.getdFactor()           + ", "
                + cr.getNumOfSquare()       + ")");

        Cursor ptr = database.rawQuery("SELECT * FROM " + CELL_COUNT_TABLE, null);
        Log.d("DATABASE", "EMPTY CHECKING");
        if(ptr.moveToFirst()){
            Log.d("DATABASE", "INSERTED");
        } else {
            Log.d("DATABASE", "STILL EMPTY");
        }
        ptr.close();

        //database.endTransaction();


    }

    public List<CountRecord> queryRecordsInDatabase(){
        List<CountRecord> records = new ArrayList<>();

        //database.beginTransaction();
        Cursor ptr = database.rawQuery("SELECT * FROM "  + CELL_COUNT_TABLE, null);

        if(ptr.moveToFirst()){
            do{
                CountRecord record = new CountRecord();

                record.setRecordName(ptr.getString(ptr.getColumnIndex(CountRecord.CR_DB_NAME)));
                record.setViableCount(ptr.getInt(ptr.getColumnIndex(CountRecord.CR_DB_V_COUNT)));
                record.setNonViableCount(ptr.getInt(ptr.getColumnIndex(CountRecord.CR_DB_NV_COUNT)));
                record.setdFactor(ptr.getDouble(ptr.getColumnIndex(CountRecord.CR_DB_D_FACTOR)));
                record.setNumOfSquare(ptr.getInt(ptr.getColumnIndex(CountRecord.CR_DB_SQUARE_NUM)));

                records.add(record);
            }while (ptr.moveToNext());
        }

        ptr.close();
        //database.endTransaction();
        Log.d("DATABASE", "Query " + records.size() + " data!");
        return records;
    }

    private class DatabaseOpener extends SQLiteOpenHelper {
        DatabaseOpener(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + CELL_COUNT_TABLE  + "( "
                    + CountRecord.CR_DB_ID          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + CountRecord.CR_DB_NAME        + " TEXT    NOT NULL, "
                    + CountRecord.CR_DB_V_COUNT     + " INTEGER NOT NULL, "
                    + CountRecord.CR_DB_NV_COUNT    + " INTEGER NOT NULL, "
                    + CountRecord.CR_DB_D_FACTOR    + " REAL    NOT NULL, "
                    + CountRecord.CR_DB_SQUARE_NUM  + " INTEGER NOT NULL)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + CELL_COUNT_TABLE);
            onCreate(db);
        }
    }

}
