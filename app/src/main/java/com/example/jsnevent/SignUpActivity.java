package com.example.jsnevent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.jsnevent.model.UserModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText input_name,input_email,input_mobileNo,input_password,input_confirmPassword;
    Button btnSignUp;
    TextView textSignIn;
    String userName,userEmail,userMobileNo,userPassword,userConfirmPassword;
    DBHelper myDB;
    List<UserModel> userList = new ArrayList<>();
    boolean isUserExist = false;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        input_name = findViewById(R.id.edit_name);
        input_email = findViewById(R.id.edit_email);
        input_mobileNo = findViewById(R.id.edit_mobileno);
        input_password = findViewById(R.id.edit_password);
        input_confirmPassword = findViewById(R.id.edit_confirmpassword);

        btnSignUp = findViewById(R.id.btn_signup);
        textSignIn = findViewById(R.id.text_signin);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.input_Name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.input_Email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.input_Mobileno,"[5-9]{1}[0-9]{9}$",R.string.invalid_mobileno);
        awesomeValidation.addValidation(this,R.id.input_Password,".{8,}",R.string.invalid_password);
        awesomeValidation.addValidation(this,R.id.input_ConfirmPassword,R.id.input_Password,R.string.invalid_confirmpassword);

        textSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);
        userList = new ArrayList<>();
        userList = myDB.getUserProfileDetails("select * from users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = input_name.getText().toString();
                userEmail = input_email.getText().toString();
                userMobileNo = input_mobileNo.getText().toString();
                userPassword = input_password.getText().toString();
                userConfirmPassword = input_confirmPassword.getText().toString();



                if (userName.equals("") || userEmail.equals("") || userMobileNo.equals("") || userPassword.equals("") || userConfirmPassword.equals("")) {
//                    Toast.makeText(SignUpActivity.this, "Fill all the Field", Toast.LENGTH_SHORT).show();
                } else
                    for (int i = 0; i < userList.size(); i++) {
                        UserModel userModel = userList.get(i);
                        if (userEmail.equals(userModel.getEmail())) {
//                           userMobileNo = userModel.getMobile();  || userPassword.equalsIgnoreCase(userModel.getPassword())
                            isUserExist = true;
                            break;
                        }
                    }
                if (isUserExist) {
                    Toast.makeText(SignUpActivity.this, "User Exist.", Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean regResult = myDB.insertSignUpData(userName,userEmail,userMobileNo,userPassword);
                    if (regResult)
                    {
                        if (awesomeValidation.validate()) {

                            Toast.makeText(SignUpActivity.this, "SignUp Successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }
//                    else {
//                        Toast.makeText(SignUpActivity.this, "user already exists.\n Please Sign In", Toast.LENGTH_SHORT).show();
//                    }
                }

            }

        });

    }

  /* private boolean validateSignUp() {

       String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (userName.length() == 0) {
            input_name.setError("UserName is required");
            return false;
        }else if (userName.length() < 8) {
            input_name.setError("UserName must be minimum 8 characters");
            return false;
        }

        if (userEmail.length() == 0) {
            input_email.setError("Email is required");
            return false;
        }
          if (userEmail.matches(emailPattern))
                {
                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
                }


        if (userMobileNo.length() == 0) {
            input_mobileNo.setError("MobileNo is required");
            return false;
        }else if (userMobileNo.length() < 10) {
            input_mobileNo.setError("MobileNo must be minimum 10 characters");
            return false;
        }

        if (userPassword.length() == 0) {
            input_password.setError("Password is required");
            return false;
        } else if (userPassword.length() < 8) {
            input_password.setError("Password must be minimum 8 characters");
            return false;
        }
        return true;
    }*/
}



 /*else  {
                    Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                }*/
                /*
                || userPassword.equalsIgnoreCase(userModel.getPassword())*/
            /*    else
                {
                    if (inputUserPassword.equals(userConfirmPassword)){
                      boolean userCheckResult = myDB.checkUserEmail(inputUserEmail);

                      if (!userCheckResult)
                      {
                       boolean regResult = myDB.insertSignUpData(userName,inputUserEmail,userMobileNo,inputUserPassword,userConfirmPassword);
                       if (regResult)
                       {
                           Toast.makeText(SignUpActivity.this,"SignUp Successful.",Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                           startActivity(intent);
                       }*/

             /*   else {
                    Toast.makeText(SignUpActivity.this, "user already exists.\n Please Sign In", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SignUpActivity.this, "Password not matching", Toast.LENGTH_SHORT).show();
                }*/





//
//    EditText editEmail,editPhoneNo,editPassword,editConfirmPassword;
//    Button btnSignUp;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sign_up);
//
//        editEmail = findViewById(R.id.edittext_Email);
//        editPassword = findViewById(R.id.edittext_Password);
//        editPhoneNo = findViewById(R.id.edittext_PhoneNumber);
//        editConfirmPassword = findViewById(R.id.edittext_ConfirmPassword);
//        btnSignUp = findViewById(R.id.btn_SignUp);
//
//
//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent1 = new Intent(SignUpActivity.this,LoginActivity.class);
//                startActivity(intent1);
//            }
//        });
//
//
//    }
//}
//
///                myDB = openOrCreateDatabase("Login.db",MODE_PRIVATE,null);
////                myDB.execSQL("create table if not exists register (username text,useremail text,usermobileno text,userpassword text,userconfirmpassword text)");
//
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("username",name);
//                contentValues.put("useremail",email);
//                contentValues.put("usermobileno",mobileno);
//                contentValues.put("userpassword",password);
//                contentValues.put("userconfirmpassword",confirmpassword);



//                myDB.insert("register",null,contentValues);
//                Toast.makeText(SignUpActivity.this, "SignUpSuccess", Toast.LENGTH_SHORT).show();
//
//                Intent i = new Intent(SignUpActivity.this,LoginActivity.class);
//                startActivity(i);