package com.example.jsnevent;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.jsnevent.model.SharedPreference;
import com.example.jsnevent.model.UserModel;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.IconCompat;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView show_password, hide_password;
    TextInputEditText inputUserEmail, inputUserPassword;
    Button btnLogin;
    TextView textSignUp;
    String userEmail, userPassword;
    DBHelper myDB;
    List<UserModel> userList = new ArrayList<>();
    boolean isValidLogin = false;
    SharedPreference sharedPreference;
    String userName = "";
    String userMobileNo = "";
    TextInputLayout textInputLayoutPass;
    boolean isShowpassClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUserEmail = findViewById(R.id.edittext_useremail);
        inputUserPassword = findViewById(R.id.edittext_userpassword);
        textInputLayoutPass = findViewById(R.id.input_Password);
        show_password = findViewById(R.id.show_password);
        hide_password = findViewById(R.id.hide_password);

        show_password.setOnClickListener(this);
        hide_password.setOnClickListener(this);
        btnLogin = findViewById(R.id.btn_Login);

        sharedPreference = new SharedPreference(getApplicationContext());


        textSignUp = findViewById(R.id.text_signup);
        textSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DBHelper(this);

        userList = new ArrayList<>();
        userList = myDB.getUserProfileDetails("select * from users");
        Log.e("usersCount~~~", String.valueOf(userList.size()));


        inputUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                show_password.setVisibility(View.VISIBLE);
//                if(isShowpassClicked) {
//                    hide_password.setVisibility(View.VISIBLE);
//                }else{
//                    show_password.setVisibility(View.GONE);
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = inputUserEmail.getText().toString();
                userPassword = inputUserPassword.getText().toString();

               /* if ((userEmail.equals("")) && (userPassword.equals("")) ) {
                    Toast.makeText(LoginActivity.this, "Please enter the UserEmail", Toast.LENGTH_SHORT).show();
                }
                else if(userPassword.equals("")){
                    Toast.makeText(LoginActivity.this, "Please Enter the UserPassword", Toast.LENGTH_SHORT).show();
                }else*/
                if (userEmail.isEmpty() && (userPassword.isEmpty())) {
                    inputUserEmail.setError("Please enter the UserEmail");
                } else if (userPassword.isEmpty()) {
                    inputUserPassword.setError("Please enter the User Password");
                    show_password.setVisibility(View.GONE);
                    //hide_password.setVisibility(View.GONE);
                } else if (userEmail.isEmpty()) {
                    inputUserEmail.setError("Please enter the UserEmail");
                } else {

                    for (int i = 0; i < userList.size(); i++) {
                        UserModel userModel = userList.get(i);
                        if (userEmail.equals(userModel.getEmail()) && userPassword.equals(userModel.getPassword())) {
                            userName = userModel.getName();
                            userMobileNo = userModel.getMobile();
                            isValidLogin = true;
                            break;
                        }
                    }
                    if (isValidLogin) {
                        userEmail = inputUserEmail.getText().toString();
                        userPassword = inputUserPassword.getText().toString();

                        sharedPreference.setLoggedIn(true);
                        sharedPreference.setUserEmail(userEmail);
                        sharedPreference.setPassword(userPassword);
                        sharedPreference.setUserName(userName);
                        sharedPreference.setUserMobileNO(userMobileNo);
//                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Crediantials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        if (sharedPreference.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    }

    @Override
    public void onClick(View view) {
        if (view == show_password) {
            isShowpassClicked = true;
            hide_password.setVisibility(View.VISIBLE);
            show_password.setVisibility(View.GONE);
            //edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            inputUserPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        } else if (view == hide_password) {
            isShowpassClicked = false;
            show_password.setVisibility(View.VISIBLE);
            hide_password.setVisibility(View.GONE);
            //edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            inputUserPassword.setInputType(InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }


    }
}

  /* private void setupPasswordToggleView() {
        final TextInputLayout textInputLayout = findViewById(R.id.input_Password);

        textInputLayout.getEditText().setTransformationMethod(null);

       textInputLayout.getEditText().setTransformationMethod(PasswordTransformationMethod.getInstance());
        // You can skip post-call and write directly the code which is inside run method.
        // But to be safe (as toggle-view is child of TextInputLayout, post call
        // has been added.
        textInputLayout.post(new Runnable() {
            @Override
            public void run() {
                CheckableImageButton passwordToggleView = textInputLayout.findViewById(R.id.edittext_userpassword);
                // passwordToggleView.toggle(); // Can not use as restricted to use same library group
                // passwordToggleView.setChecked(true); // Can not use as restricted to use same library group
                inputUserPassword.setTextInputLayoutFocusedRectEnabled(false);
                passwordToggleView.performClick();
            }
        });
    }*/






                  /* boolean result = myDB.checkUserEmailPassword(userEmail,userPassword);
                   if (result)
                   {
                       Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                   }
                   else
                   {
                       Toast.makeText(LoginActivity.this, "Invalid Crediantials", Toast.LENGTH_SHORT).show();
                   }*/


///                else {
//                    Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(LoginActivity.this, PhoneActivity.class);
//                    startActivity(i);
//                }
//
//
//                else {
//                    if (useremail.matches(useremail)) {
//                        Boolean usercheckResult = myDB.checkuseremail(useremail);
//
//                        if (usercheckResult == false) {
//                            Boolean regResult = myDB.insertData(useremail, userpassword);
//                            if (regResult == true) {
//                                Toast.makeText(LoginActivity.this, "Login Successful.", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(LoginActivity.this, PhoneActivity.class);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }
//                }
