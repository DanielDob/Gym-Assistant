package com.example.daniel.database.trainings.trainingvalues;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.daniel.database.exercise.values.ExerciseValuesColumns;
import com.example.daniel.database.trainings.names.TrainingNamesDatabase;
import com.example.daniel.gymassistant.R;

public class TrainingValuesDatabase extends SQLiteOpenHelper {
    private static final String DATA_BASE_NAME = "trainings.db";
    private static final int DATA_BASE_VERSION = 2;
    private Context context;
    public TrainingValuesDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public TrainingValuesDatabase(Context context){

        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        onCreate(db);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TrainingValuesColumns.TABLE_NAME + " ( "+ TrainingValuesColumns._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ TrainingValuesColumns.TRAINING_ID+" INTEGER, "+TrainingValuesColumns.WEEK_DAYS+" TEXT, "
                + TrainingValuesColumns.TRAINING_MODE+" TEXT, "+ TrainingValuesColumns.SCHEDULE+" TEXT, "+ TrainingValuesColumns.ROUNDS_NUMBER+" INTEGER, "+TrainingValuesColumns.EXERCISE_NUMBER+" INTEGER, "+TrainingValuesColumns.ADD_DATE+" TEXT, "
                +TrainingValuesColumns.FIRST_DAY_DATE+" TEXT, "+TrainingValuesColumns.LAST_TRAINING_DAY_DATE+" TEXT, "+TrainingValuesColumns.REPETITION+" INTEGER, "+TrainingValuesColumns.AVERAGE_TIME+" INTEGER "+");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TrainingValuesColumns.TABLE_NAME);
        onCreate(db);
    }

    public void add(TrainingValue training){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TrainingValuesColumns.TRAINING_ID,training.getTrainingId());
        values.put(TrainingValuesColumns.WEEK_DAYS, training.getWeekDays());
        values.put(TrainingValuesColumns.TRAINING_MODE, training.getTrainingMode());
        values.put(TrainingValuesColumns.SCHEDULE, training.getSchedule());
        values.put(TrainingValuesColumns.ROUNDS_NUMBER,training.getRoundsNumber());
        values.put(TrainingValuesColumns.EXERCISE_NUMBER,training.getExerciseNumber());
        values.put(TrainingValuesColumns.ADD_DATE,training.getAddDate());
        values.put(TrainingValuesColumns.FIRST_DAY_DATE,training.getFirstDayTraining());
        values.put(TrainingValuesColumns.LAST_TRAINING_DAY_DATE,training.getLastTrainingDayDate());
        values.put(TrainingValuesColumns.REPETITION,training.getRepetition());
        values.put(TrainingValuesColumns.AVERAGE_TIME,training.getAverageTime());




        db.insert(TrainingValuesColumns.TABLE_NAME, null, values);
        db.close();
    }
    public void update(TrainingValue trainingValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TrainingValuesColumns.TRAINING_ID, trainingValue.getTrainingId());
        values.put(TrainingValuesColumns.WEEK_DAYS, trainingValue.getWeekDays());
        values.put(TrainingValuesColumns.TRAINING_MODE, trainingValue.getTrainingMode());
        values.put(TrainingValuesColumns.SCHEDULE, trainingValue.getSchedule());
        values.put(TrainingValuesColumns.ROUNDS_NUMBER, trainingValue.getRoundsNumber());
        values.put(TrainingValuesColumns.EXERCISE_NUMBER, trainingValue.getExerciseNumber());
        values.put(TrainingValuesColumns.ADD_DATE, trainingValue.getAddDate());
        values.put(TrainingValuesColumns.FIRST_DAY_DATE, trainingValue.getFirstDayTraining());
        values.put(TrainingValuesColumns.LAST_TRAINING_DAY_DATE, trainingValue.getLastTrainingDayDate());
        values.put(TrainingValuesColumns.REPETITION, trainingValue.getRepetition());
        values.put(TrainingValuesColumns.AVERAGE_TIME, trainingValue.getAverageTime());

        db.update(TrainingValuesColumns.TABLE_NAME, values, TrainingValuesColumns.TRAINING_ID + " = ? ", new String[]{String.valueOf(trainingValue.getTrainingId())});
        db.close();
    }

    public TrainingValue get(int row){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME}, null,null,null,null,TrainingValuesColumns._ID);
        if (cursor!=null){
            cursor.moveToPosition(row);
        }
        TrainingValue trainingValue = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11));
        db.close();
        return  trainingValue;
    }

    public TrainingValue getByTrainingID(int trainingID){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME}, TrainingValuesColumns.TRAINING_ID +" = "+trainingID,null,null,null,TrainingValuesColumns._ID);
        cursor.moveToFirst();
        TrainingValue training = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11),context);
        return  training;
    }
    public TrainingValue get(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        TrainingNamesDatabase tnd = new TrainingNamesDatabase(context);
        int ID = tnd.getIndex(name);
        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME}, TrainingValuesColumns.TRAINING_ID +" = '"+ID+"'",null,null,null,TrainingValuesColumns._ID);
        if(cursor.getCount()==0) return null;
        cursor.moveToFirst();
        TrainingValue trainingValue = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11),context);
        db.close();
        return  trainingValue;
    }

    public int[] getAllID(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns.TRAINING_ID},null,null,null,null,TrainingValuesColumns.TRAINING_ID);
        cursor.moveToFirst();
        int[] training= new int[cursor.getCount()];
        int i=0;
        for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext(),i++) {
            training[i] = cursor.getInt(0);
        }
        db.close();
        return  training;
    }
    public TrainingValue[] getAll(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME}, null,null,null,null,TrainingValuesColumns._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        TrainingValue[] trainingValues = new TrainingValue[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            trainingValues[i] = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11),context);
            cursor.moveToNext();
        }
        db.close();
        return  trainingValues;
    }

    public TrainingValue[] getAllTrainings(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME},  TrainingValuesColumns.TRAINING_MODE+" <> '"+context.getResources().getStringArray(R.array.training_mode_choose)[2]+"'",null,null,null,TrainingValuesColumns._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        TrainingValue[] trainingValues = new TrainingValue[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            trainingValues[i] = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11),context);
            cursor.moveToNext();
        }
        db.close();
        return  trainingValues;
    }
    public TrainingValue[] getAllModules(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TrainingValuesColumns.TABLE_NAME, new String[] {TrainingValuesColumns._ID,TrainingValuesColumns.TRAINING_ID,TrainingValuesColumns.WEEK_DAYS,TrainingValuesColumns.TRAINING_MODE,TrainingValuesColumns.SCHEDULE, TrainingValuesColumns.ROUNDS_NUMBER, TrainingValuesColumns.EXERCISE_NUMBER,TrainingValuesColumns.ADD_DATE, TrainingValuesColumns.FIRST_DAY_DATE, TrainingValuesColumns.LAST_TRAINING_DAY_DATE,TrainingValuesColumns.REPETITION, TrainingValuesColumns.AVERAGE_TIME},  TrainingValuesColumns.TRAINING_MODE+" = '"+context.getResources().getStringArray(R.array.training_mode_choose)[2]+"'",null,null,null,TrainingValuesColumns._ID);
        if (cursor!=null){
            cursor.moveToFirst();
        }
        TrainingValue[] trainingValues = new TrainingValue[cursor.getCount()];
        for(int i=0;i<cursor.getCount();i++){
            trainingValues[i] = new TrainingValue(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8),cursor.getString(9),cursor.getInt(10),cursor.getLong(11),context);
            cursor.moveToNext();
        }
        db.close();
        return  trainingValues;
    }
    public String getDB() {
        return context.getDatabasePath("trainings.db").toString();
    }
    public void deleteTrainingValueByID(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingValuesColumns.TABLE_NAME, TrainingValuesColumns._ID+" =?", new String[]{String.valueOf(ID)});
        db.close();
    }
    public void deleteTrainingValueByTrainingID(int trainingID){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingValuesColumns.TABLE_NAME, TrainingValuesColumns.TRAINING_ID+" =?", new String[]{String.valueOf(trainingID)});
        db.close();
    }
    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TrainingValuesColumns.TABLE_NAME, null, null);
        db.close();
    }
    public void deleteFile(Context context){
        context.deleteDatabase(TrainingValuesColumns.TABLE_NAME+".db");
    }
    public int getCount() {
        String countQuery = "SELECT  * FROM " + TrainingValuesColumns.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //db.close();
        // return count
        return cursor.getCount();
    }



}
