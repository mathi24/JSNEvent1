package com.example.jsnevent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jsnevent.model.EventModel;
import com.example.jsnevent.model.UserModel;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "users";
    private static final String TABLE_Users = "userdetails";
    private static final String TABLE_Events = "eventdetails";
//    private static final String TABLE_Profiles = "profiledetails";


    /*private static final String COLUMN_EMAIL="email";
    private static final String COLUMN_PASSWORD="password";*/



    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

      /*  private static final String TABLE_CREATE = "create table users (email text not null," +
                " password text not nul,)";*/

        db.execSQL("create Table users(userName text,userEmail text,userMobileNo text,userPassword text)");

        db.execSQL("create Table events(eventname text,date text,venue text,description text)");

//        db.execSQL("create Table profiles(userUpdateName text,userUpdateEmail text,userUpdateMobileNo text)");

//        String CREATE_TABLE = " CREATE TABLE " + TABLE_Users + "("+KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +KEY_NAME1 + " TEXT, "
//                +KEY_NAME2 + " TEXT, " +KEY_EMAIL + " TEXT, " +KEY_PASSWORD + " TEXT, " +KEY_VENUE + " TEXT, " +KEY_DATE + " TEXT, "
//                +KEY_DESCRIPTION + " TEXT, " +KEY_CONFIRM + " TEXT " + " )";
//        db.execSQL(CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
//        onCreate(db);
//
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Events);
//        onCreate(db);

        db.execSQL("drop Table if exists users");

        db.execSQL("drop Table if exists events");

//        db.execSQL("drop Table if exists profiles");
    }

   public boolean insertEventData(String eventname, String date, String venue, String description)
   {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventname",eventname);
        contentValues.put("date",date);
        contentValues.put("venue",venue);
        contentValues.put("description",description);


        long newRowId = db.insert("events",null,contentValues);
//       db.close();

        if (newRowId == -1){
            return false;
        }else{
            return true;
        }

    }

      public ArrayList<EventModel> getAllEvents(String query){

        ArrayList<EventModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                EventModel eventModel = new EventModel();
                eventModel.setEventName(cursor.getString(0));
                eventModel.setDate(cursor.getString(1));
                eventModel.setVenue(cursor.getString(2));
                eventModel.setDescription(cursor.getString(3));

                arrayList.add(eventModel);
            }while (cursor.moveToNext());
        }cursor.close();

        return arrayList;
    }

    public ArrayList<UserModel> getUserProfileDetails(String query){

        ArrayList<UserModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                UserModel userModel = new UserModel();
                userModel.setName(cursor.getString(0));
                userModel.setEmail(cursor.getString(1));
                userModel.setMobile(cursor.getString(2));
                userModel.setPassword(cursor.getString(3));

                arrayList.add(userModel);
            }while (cursor.moveToNext());
        }cursor.close();

        return arrayList;
    }


  /*  public ArrayList<UserModel> getEventFromDate(String query){

        ArrayList<UserModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
      //  String selectQuery = "SELECT  * FROM " + users + " WHERE " + KEY_DATE + " LIKE " + "'%" + mDate + "'";

        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                UserModel userModel = new UserModel();
                userModel.setName(cursor.getString(0));
                userModel.setEmail(cursor.getString(1));
                userModel.setMobile(cursor.getString(2));
                userModel.setPassword(cursor.getString(3));

                arrayList.add(userModel);
            }while (cursor.moveToNext());
        }cursor.close();

        return arrayList;
    }*/






    public ArrayList<UserModel> getUserResetPassword(String query){

        ArrayList<UserModel> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                UserModel reEnterModel = new UserModel();
                reEnterModel.setPassword(cursor.getString(0));

                arrayList.add(reEnterModel);
            }while (cursor.moveToNext());
        }cursor.close();

        return arrayList;
    }





    //insert SignUpData
    public boolean insertSignUpData(String userName,String userEmail,String userMobileNo,String userPassword){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",userName);
        contentValues.put("userEmail",userEmail);
        contentValues.put("userMobileNo",userMobileNo);
        contentValues.put("userPassword",userPassword);
        long result = myDB.insert("users",null,contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }

    }
/*
    public boolean insertResetPassword(String userOldPassword,String userNewPassword,String userReEnterPassword){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userOldPassword",userOldPassword);
        contentValues.put("userNewPassword",userNewPassword);
        contentValues.put("userReEnterPassword",userReEnterPassword);
        long result = myDB.insert("users",null,contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }

    }*/



/*    public boolean insertData(String userEmail,String userPassword){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userEmail",userEmail);
         contentValues.put("userPassword",userPassword);

        long result = myDB.insert("users",null,contentValues);

        if (result == -1){
            return false;
        }else{
            return true;
        }

    }*/

    public Boolean checkUserEmailPassword(String inputUserEmail,String inputUserPassword) {

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where ? = ?"
                , new String[]{"userPassword",inputUserPassword});

        if (cursor.getCount()>0){
            return true;
        }else {
            return  false;
        }

    }

    public Boolean checkUserEmail(String inputUserEmail) {

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where userEmail = "+inputUserEmail+"", null);
        if (cursor.getCount()>0 )
        {
            return true;
        }else{
            return false;
        }
    }

   /* public boolean insertProfileData(String profileUserName,String profileUserEmail,String profileUserMobileNo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("profileUserName",profileUserName);
        contentValues.put("profileUserEmail",profileUserEmail);
        contentValues.put("profileUserMobileNo",profileUserMobileNo);

        long newRowId = db.insert("profiles",null,contentValues);

        if (newRowId == -1){
            return false;
        }else{
            return true;
        }
    }*/

   /* public ArrayList<ProfileModel> getAllProfile(String query){

        ArrayList<ProfileModel> profileList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                ProfileModel profileModel = new ProfileModel();
                profileModel.setProfileUserName(cursor.getString(0));
                profileModel.setProfileUserEmail(cursor.getString(1));
                profileModel.setProfileUserMobileNo(cursor.getString(2));
              *//*  eventModel.setImagePicker(cursor.getString(4));
                eventModel.setImageLocation(cursor.getString(5));*//*
                profileList.add(profileModel);
            }while (cursor.moveToNext());
        }cursor.close();

        return profileList;
    }*/

    public boolean updateProfileData(String userUpdateName, String userUpdateEmail, String userUpdateMobileNo)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",userUpdateName);
        contentValues.put("userEmail",userUpdateEmail);
        contentValues.put("userMobileNo",userUpdateMobileNo);

        db.update("users", contentValues, "userEmail = ? ", new String[] { userUpdateEmail } );

            return true;
        }




    public boolean updateProfilePassword( String userNewPassword,String userEmail)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userPassword",userNewPassword);


        db.update("users", contentValues, "userEmail = ? ", new String[] {userEmail } );

        return true;
    }

}







//    public DBHelper(Context context) {
//        super(context,"users", null, 1);
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase myDB) {
//        myDB.execSQL("create Table users(useremail text,userpassword text)");
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
//        myDB.execSQL("drop Table if exists users");
//
//    }
////
////    public boolean insertCreateEvent(String eventname,String date,String venue,String description){
////
////        return true;
////    }
//
//    public boolean insertData(String useremail,String userpassword){
//        SQLiteDatabase myDB = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("useremail",useremail);
//        contentValues.put("userpassword",userpassword);
//        long result = myDB.insert("users",null,contentValues);
//
//        if (result == -1){
//            return false;
//        }else{
//            return true;
//        }
//
//    }
//
//    public boolean checkuseremail(String useremail){
//
//        SQLiteDatabase myDB = this.getWritableDatabase();
//        Cursor cursor = myDB.rawQuery("select * from users where useremail = ?", new String[]{useremail});
//        if (cursor.getCount()>0 )
//        {
//            return true;
//        }else{
//            return false;
//        }
//
//    }
//
//    public boolean checkuseremailpassword(String useremail,String userpassword){
//        SQLiteDatabase myDB = this.getWritableDatabase();
//        Cursor cursor = myDB.rawQuery("select * from users where useremail = ? and userpassword = ?", new String[]{useremail,userpassword});
//
//        if (cursor.getCount()>0){
//            return true;
//        }else {
//            return  false;
//        }
//
//    }
//}
