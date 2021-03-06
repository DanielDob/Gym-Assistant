package com.example.daniel.database.trainings.names;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.daniel.database.exercise.name.ExerciseColumnNames;
import com.example.daniel.database.trainings.trainingvalues.TrainingValue;
import com.example.daniel.database.trainings.trainingvalues.TrainingValuesColumns;
import com.example.daniel.database.trainings.trainingvalues.TrainingValuesDatabase;


public class TrainingNamesDatabase extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "trainings.db";
    private static final int DATA_BASE_VERSION = 2;
    Context context;

    public TrainingNamesDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public TrainingNamesDatabase(Context context){
        super(context,DATA_BASE_NAME,null,DATA_BASE_VERSION);
        this.context = context;
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TrainingNamesColumns.TABLE_NAME + " ( "+TrainingNamesColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ TrainingNamesColumns.TRAINING_NAME+ " TEXT );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TrainingNamesColumns.TABLE_NAME);
        onCreate(db);
    }
    public void addTrainingName(TrainingName exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrainingNamesColumns.TRAINING_NAME, exercise.getName());

        db.insert(TrainingNamesColumns.TABLE_NAME, null, values);
        db.close();
    }

    public void addTrainingName(String training){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrainingNamesColumns.TRAINING_NAME, training);

        db.insert(TrainingNamesColumns.TABLE_NAME, null, values);
        db.close();
    }

    public boolean isTrainingNameRepeated(String trainingName) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TrainingNamesColumns.TABLE_NAME, new String[] {TrainingNamesColumns._ID, TrainingNamesColumns.TRAINING_NAME}, TrainingNamesColumns.TRAINING_NAME+" = '"+trainingName+"'",null,null,null,TrainingNamesColumns._ID);
        int count = cursor.getCount();
        if(count==0){
            return false;
        } else{
            return true;
        }
    }

    public int getIndex(String name) {
        if(!isTrainingNameRepeated(name)){
            addTrainingName(name);
            return getIndex(name);
        }
        String countQuery = "SELECT  "+TrainingNamesColumns._ID+" FROM " + TrainingNamesColumns.TABLE_NAME+" WHERE "+TrainingNamesColumns.TRAINING_NAME+" = '"+name+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }


    public TrainingName getTrainingName(int row, int column){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingNamesColumns.TABLE_NAME, new String[] {TrainingNamesColumns._ID, TrainingNamesColumns.TRAINING_NAME}, null,null,null,null,TrainingNamesColumns._ID);
        if (cursor!=null){
            cursor.moveToPosition(row);
        }
        TrainingName trainingName = new TrainingName(cursor.getString(column));
        db.close();
        return  trainingName;
    }
    public TrainingName getTrainingName(int ID){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingNamesColumns.TABLE_NAME, new String[] {TrainingNamesColumns._ID, TrainingNamesColumns.TRAINING_NAME},  TrainingNamesColumns._ID+" = "+String.valueOf(ID),null,null,null,TrainingNamesColumns._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        TrainingName trainingName = new TrainingName(cursor.getString(1));
        db.close();
        return  trainingName;
    }
    public TrainingName[] getTrainingNames(TrainingValue[] trainingValues){
        SQLiteDatabase db = this.getReadableDatabase();
        TrainingName[] trainingName= new TrainingName[trainingValues.length];
        for(int i=0;i<trainingValues.length;i++) {
            Cursor cursor = db.query(TrainingNamesColumns.TABLE_NAME, new String[]{TrainingNamesColumns._ID, TrainingNamesColumns.TRAINING_NAME}, TrainingNamesColumns._ID + " = " + String.valueOf(trainingValues[i].getTrainingId()), null, null, null, TrainingNamesColumns._ID);
            if (cursor != null) {
                cursor.moveToFirst();
            }
            trainingName[i] = new TrainingName(cursor.getString(1),cursor.getInt(0));
            db.close();
        }
        return  trainingName;
    }

    public TrainingName[] getAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingNamesColumns.TABLE_NAME, new String[] {TrainingNamesColumns._ID, TrainingNamesColumns.TRAINING_NAME}, null,null,null,null,TrainingNamesColumns._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        TrainingName[] training = new TrainingName[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            training[i] = new TrainingName(cursor.getString(1),cursor.getInt(0));
            cursor.moveToNext();
        }
        db.close();
        return  training;
    }

    public void deleteTrainingName(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingNamesColumns.TABLE_NAME, TrainingNamesColumns._ID+" =?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void deleteTrainingName(String trainingName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingNamesColumns.TABLE_NAME, TrainingNamesColumns.TABLE_NAME+" =?", new String[]{trainingName});
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingNamesColumns.TABLE_NAME,null,null);
        db.close();
    }
    public void deleteDb(Context context){
        context.deleteDatabase(TrainingNamesColumns.TABLE_NAME+".db");
    }

    public int getCount() {
        String countQuery = "SELECT  * FROM " + TrainingNamesColumns.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        return cursor.getCount();

    }


}
