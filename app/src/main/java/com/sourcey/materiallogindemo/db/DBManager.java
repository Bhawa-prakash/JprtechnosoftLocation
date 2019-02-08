package com.sourcey.materiallogindemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sourcey.materiallogindemo.Model.Student;
import com.sourcey.materiallogindemo.Utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import static com.sourcey.materiallogindemo.Utils.AppConstants.STD_TABLE;


public class DBManager extends SQLiteOpenHelper {


    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(AppConstants.STD_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

   public int saveStudent(Student student) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(AppConstants.LOC_LONGITUDE, student.getLongitude());
        contentValues.put(AppConstants.LOC_LATTTUDE, student.getLattitude());
        contentValues.put(AppConstants.LOC_TIME, student.getTime());

       return (int) sqLiteDatabase.insert(STD_TABLE, null, contentValues);


    }
/*
    public boolean isExists(String email) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();


        Cursor cursor = sqLiteDatabase.rawQuery(STD_ALREADY_EXIST_QUERY + " '"+email+"' ", null);

        if (cursor != null && cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }


    }
*/


    public List<Student> getAllLocation() {
        List<Student> list = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + STD_TABLE ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Student student = new Student();
                student.setLattitude(cursor.getColumnName(cursor.getColumnIndex(AppConstants.LOC_LATTTUDE)));
                student.setLongitude(cursor.getColumnName(cursor.getColumnIndex(AppConstants.LOC_LONGITUDE)));
                student.setTime(cursor.getColumnName(cursor.getColumnIndex(AppConstants.LOC_TIME)));
                list.add(student);

            } while (cursor.moveToNext());
        }
        // close db connection
        db.close();
        // return notes list
        return list;
    }
  /*public void getData(Student student1)
    {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        List<Student> students=new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + STD_TABLE, null);
        if(cursor.moveToFirst())
        {
            do{

                Student student=new Student();


            }while (cursor.moveToNext());
        }
        sqLiteDatabase.close();
    }*/
}
