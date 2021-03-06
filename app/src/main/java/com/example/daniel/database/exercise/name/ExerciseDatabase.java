package com.example.daniel.database.exercise.name;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.example.daniel.database.exercise.name.Exercise;
import com.example.daniel.database.exercise.name.ExerciseColumnNames;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Daniel on 2017-04-07.
 */

public class ExerciseDatabase extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "exercise.db";
    private static final int DATA_BASE_VERSION = 1;
    android.content.Context Context;

    public ExerciseDatabase(android.content.Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        Context = context;
    }
    public ExerciseDatabase(android.content.Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        Context = context;
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + ExerciseColumnNames.TABLE_NAME + " ( "+ ExerciseColumnNames._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ ExerciseColumnNames.EXERCISE_NAME + " TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ExerciseColumnNames.TABLE_NAME);
        onCreate(db);
    }

    public void addExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        if(isExerciseNameExists(exercise.getName())) return;
        ContentValues values = new ContentValues();
        values.put(ExerciseColumnNames.EXERCISE_NAME, exercise.getName());

        db.insert(ExerciseColumnNames.TABLE_NAME, null, values);
    }
    public int getIndex(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        if(!isExerciseNameExists(name)){
            addExercise(new Exercise(name));
            return getIndex(new Exercise(name));
        }
        String query = "SELECT " + ExerciseColumnNames._ID+" FROM "+ExerciseColumnNames.TABLE_NAME+" WHERE "+ ExerciseColumnNames.EXERCISE_NAME+" = '"+name+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }
    public int getIndex(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(!isExerciseNameExists(exercise.getName())){
            addExercise(new Exercise(exercise.getName()));
            return getIndex(exercise);
        }
        String query = "SELECT " + ExerciseColumnNames._ID+" FROM "+ExerciseColumnNames.TABLE_NAME+" WHERE "+ ExerciseColumnNames.EXERCISE_NAME+" = '"+exercise.getName()+"'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);

    }


    private boolean isExerciseNameExists(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(ExerciseColumnNames.TABLE_NAME, new String[] {ExerciseColumnNames._ID, ExerciseColumnNames.EXERCISE_NAME}, null,null,null,null, ExerciseColumnNames._ID);
        int number = cursor.getCount();
        cursor.moveToFirst();
        for(int i=0;i<number;i++){
            if(name.equals(cursor.getString(1))){
                return true;
            } else{
                cursor.moveToNext();
            }
        }
        return false;
    }

    public Exercise getExercise(int row, int column){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ExerciseColumnNames.TABLE_NAME, new String[] {ExerciseColumnNames._ID, ExerciseColumnNames.EXERCISE_NAME}, null,null,null,null, ExerciseColumnNames._ID);
        if (cursor!=null){
            cursor.moveToPosition(row);
        }
        Exercise exercise = new Exercise(cursor.getString(column));
        return  exercise;
    }
    public Exercise getExercise(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ExerciseColumnNames.TABLE_NAME, new String[] {ExerciseColumnNames._ID, ExerciseColumnNames.EXERCISE_NAME}, ExerciseColumnNames._ID+" = "+String.valueOf(ID),null,null,null, ExerciseColumnNames._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Exercise exercise = new Exercise(cursor.getString(1));
        return  exercise;
    }
    public File getCSV(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ExerciseColumnNames.TABLE_NAME,null);
        FileOutputStream outputStream;

        if(!cursor.moveToFirst()){
            // something went wrong - bad sql or no results
        }

        File file = new File(Context.getFilesDir(), "exerciseNames.csv");
        try {
            outputStream = new FileOutputStream(file, true);
            do{
                outputStream.write(cursor.getString(0).getBytes());
                outputStream.write(",".getBytes());
                outputStream.write(cursor.getString(1).getBytes());
                outputStream.write(",".getBytes());
                outputStream.write("\n".getBytes());


            } while(cursor.moveToNext());

            outputStream.flush();
            outputStream.close();


        } catch (Exception e){

            e.printStackTrace();
        }
        cursor.close();
        return file;
    }
    public Exercise[] getAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ExerciseColumnNames.TABLE_NAME, new String[] {ExerciseColumnNames._ID, ExerciseColumnNames.EXERCISE_NAME}, null,null,null,null, ExerciseColumnNames._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        Exercise[] exercises = new Exercise[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            exercises[i] = new Exercise(cursor.getInt(0),cursor.getString(1));
            cursor.moveToNext();
        }

        return  exercises;
    }
    public String getDB() {
        return Context.getDatabasePath("exercise.db").toString();

    }
    public void delete(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExerciseColumnNames.TABLE_NAME, ExerciseColumnNames._ID+" =?", new String[]{String.valueOf(ID)});

    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ExerciseColumnNames.TABLE_NAME,null,null);
    }
    public void deleteFile(){
        Context.deleteDatabase(ExerciseColumnNames.TABLE_NAME);
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + ExerciseColumnNames.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int result = cursor.getCount();
        // return count
        cursor.close();
        return result;
    }


}
