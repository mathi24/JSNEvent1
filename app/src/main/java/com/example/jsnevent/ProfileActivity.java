package com.example.jsnevent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.jsnevent.fragment.HomeFragment;
import com.example.jsnevent.model.SharedPreference;
import com.example.jsnevent.model.UserModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

//    TextView profile,tv_name,edit_email,edit_mobileNo,textChangePassword;
    TextInputEditText edittext_userName,edittext_userEmail,edittext_userMobileNo
            ,edittext_userTextName,edittext_userTextEmail,edittext_userTextMobileNo;
    TextView profile,textChangePassword;
    String userProfileName,userProfileEmail,userProfileMobileNo,userOldPassword,userNewPassword,userReEnterPassword;
    TextInputEditText  edit_OldPassword,edit_NewPassword,edit_ReenterNewPassword;
    ImageView image_EditLogoProfile;
    Button editDetails;
    BottomSheetBehavior mbottomSheetBehavior;
    View shadowView,shadowView1;
    RelativeLayout rl,relativeLayout;
    String userEmail = "",resetPassword = "",userPassword = "", strMobileNum = "",strName;
    DBHelper myDB;
    List<UserModel> userList;
    Button btnResetSubmit;
    AwesomeValidation mAwesomeValidation;
    List<UserModel> oldPassword;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        SharedPreference sharedPreference=new SharedPreference(this);
        userEmail = sharedPreference.getUserEmail();
        userPassword = sharedPreference.getPassword();
        strName = sharedPreference.getUseName();
        strMobileNum = sharedPreference.getUserMobileNO();
        resetPassword = sharedPreference.getUserResetPassword();

        myDB = new DBHelper(this);

        userList = new ArrayList<>();
        userList = myDB.getUserProfileDetails("select * from users");


        for (int i = 0; i < userList.size(); i++) {
            UserModel userModel = userList.get(i);
            if (userEmail.equals(userModel.getEmail()) && userPassword.equals(userModel.getPassword())) {
                strMobileNum = userModel.getMobile();
                strName = userModel.getName();
                break;
            }
        }


        profile = findViewById(R.id.profile);

        edittext_userTextName = findViewById(R.id.edittext_userTextName);
        edittext_userTextEmail = findViewById(R.id.edittext_userTextEmail);
        edittext_userTextMobileNo = findViewById(R.id.edittext_userTextMobileNo);
        image_EditLogoProfile = findViewById(R.id.image_EditLogoProfile);
        edittext_userName = findViewById(R.id.edittext_username);
        edittext_userEmail = findViewById(R.id.edittext_useremail);
        edittext_userMobileNo = findViewById(R.id.edittext_usermobileno);
        editDetails = findViewById(R.id.btnEditProfile);

        edit_ReenterNewPassword = findViewById(R.id.edit_ReenterNewPassword);
        edit_OldPassword = findViewById(R.id.edit_OldPassword);
        edit_NewPassword = findViewById(R.id.edit_NewPassword);
        shadowView1 = findViewById(R.id.view_shadow1);
        btnResetSubmit = findViewById(R.id.btn_resetSubmit);

        btnResetSubmit.setBackgroundColor(Color.parseColor("#1160A4"));

        edittext_userTextName.setText(strName);
        edittext_userTextEmail.setText(userEmail);
        edittext_userTextMobileNo.setText(strMobileNum);


       textChangePassword = findViewById(R.id.text_ChangePassword);
        LinearLayout linearBottomSheet = findViewById(R.id.btm_sheet_edit_profile);
        mbottomSheetBehavior = BottomSheetBehavior.from(linearBottomSheet);
        /*mbottomSheetBehavior.setPeekHeight(0);*/
        rl = findViewById(R.id.rl_text_view);
        relativeLayout = findViewById(R.id.rl_edittext);


        mAwesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mAwesomeValidation.addValidation(this,R.id.edit_OldPassword,R.id.input_Password,R.string.invalid_oldpassword);
        mAwesomeValidation.addValidation(this,R.id.edit_NewPassword,".{8,}",R.string.invalid_newpassword);
        mAwesomeValidation.addValidation(this,R.id.edit_ReenterNewPassword,R.id.edit_NewPassword,R.string.invalid_Reenterpassword);

        editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(ProfileActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                if(editDetails.getText().equals("Edit Details")) {//Action Edit
                    rl.setVisibility(View.GONE);
                    relativeLayout.setVisibility(View.VISIBLE);

                    edittext_userName.setText(edittext_userTextName.getText().toString());
                    edittext_userEmail.setText(edittext_userTextEmail.getText().toString());
                    edittext_userMobileNo.setText(edittext_userTextMobileNo.getText().toString());


                    editDetails.setText("Submit");


                }else{//Action Submit
                    myDB.updateProfileData(edittext_userName.getText().toString()
                            ,edittext_userEmail.getText().toString()
                            ,edittext_userMobileNo.getText().toString());

                    edittext_userTextName.setText(edittext_userName.getText().toString());
                    edittext_userTextEmail.setText( edittext_userEmail.getText().toString());
                    edittext_userTextMobileNo.setText(edittext_userMobileNo.getText().toString());

                    editDetails.setText("Edit Details");

                    rl.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }

            }
        });

        mbottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("SwitchIntDef")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                         break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        shadowView1.setVisibility(View.GONE);
                        break;

                    case BottomSheetBehavior.STATE_DRAGGING:
                         break;

                    case BottomSheetBehavior.STATE_HIDDEN:
                         break;

                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        textChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == textChangePassword){
                     oldPassword = new ArrayList<>();
    /*                oldPassword = myDB.getUserResetPassword("select userPassword from users where" +
                            " userName = "+sharedPreference.getUseName()+" and userPassword = "+sharedPreference.getPassword()+"");
           */         mbottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    shadowView1.setVisibility(View.VISIBLE);

                    //edit_OldPassword.setText(oldPassword.get(0).getPassword());

                }

            }
        });



        btnResetSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sharedPreference.setPassword(edit_OldPassword.getText().toString());
//                sharedPreference.setUserResetPassword(edit_NewPassword.getText().toString());
                if(validationReset()){
                    boolean isUpdate = myDB.updateProfilePassword(edit_NewPassword.getText().toString(),sharedPreference.getUserEmail());

                    if(isUpdate){

                        if (mAwesomeValidation.validate()) {
//                            Toast.makeText(ProfileActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                            sharedPreference.setPassword(edit_NewPassword.getText().toString());
                            mbottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            shadowView1.setVisibility(View.GONE);
                            finishAffinity();
                            new SharedPreference(getApplicationContext()).setLoggedIn(false);
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }
                    }
                }
            }
        });
    }


    private boolean validationReset() {
        if (edit_NewPassword.getText().toString().equals("") || edit_OldPassword.getText().toString().equals("")
                || edit_ReenterNewPassword.getText().toString().equals("")) {
            Toast.makeText(this, "Field does be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!edit_NewPassword.getText().toString().equals(edit_ReenterNewPassword.getText().toString())) {
            Toast.makeText(this, " Password does not match", Toast.LENGTH_SHORT).show();
            return false;
        }else if (!new SharedPreference(getApplicationContext()).getPassword().equals(edit_OldPassword.getText().toString())){
            Toast.makeText(this, "Old password does not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}










//                    boolean checkinsertResetPassword = myDB.insertResetPassword(userOldPassword,userNewPassword,userReEnterPassword);

           /*         if (userOldPassword.equals("") || userNewPassword.equals("") || userReEnterPassword.equals("")){
                        Toast.makeText(ProfileActivity.this, "Fill All the Fields", Toast.LENGTH_SHORT).show();
                    }
                    else if (btnResetSubmit) {

                        *//*    if (userNewPassword.equals(userReEnterPassword)) {
                                Toast.makeText(ProfileActivity.this, "Password Doesn't same", Toast.LENGTH_SHORT).show();
                                btnResetSubmit.setVisibility(View.GONE);
                            } else {*//*

                        if (edit_NewPassword != edit_ReenterNewPassword) {
                            Toast.makeText(ProfileActivity.this, "password doesn't same", Toast.LENGTH_SHORT).show();
                            btnResetSubmit.setVisibility(View.VISIBLE);

                        }

                    *//*    if (edit_OldPassword == edit_NewPassword){
                            Toast.makeText(ProfileActivity.this, "match old password", Toast.LENGTH_SHORT).show();
                        }*//* else {
                            if (edit_NewPassword == edit_ReenterNewPassword) {
                                Toast.makeText(ProfileActivity.this, "ResetPasswordSuccessful", Toast.LENGTH_SHORT).show();
                                mbottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                                shadowView1.setVisibility(View.GONE);
                                relativeLayout.setVisibility(View.VISIBLE);

                            }
                        }
                    }
                     else
                            Toast.makeText(ProfileActivity.this, "ResetPasswordUnSuccessful", Toast.LENGTH_SHORT).show();
*/
