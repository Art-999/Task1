package com.example.arturmusayelyan.task1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText et_userName;
    private EditText et_password;
    private EditText et_firstName;
    private EditText et_lastName;
    private Button btn_register;

    private boolean chekRegisterMain = false;
    private String chekAddUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        et_userName = findViewById(R.id.register_username_et);
        et_password = findViewById(R.id.register_password_et);
        et_firstName = findViewById(R.id.register_name_et);
        et_lastName = findViewById(R.id.register_surname_et);
        btn_register = findViewById(R.id.register_btn2);
        btn_register.setOnClickListener(this);

//        Bundle bundle = new Bundle();
//        chekRegisterMain = bundle.getBoolean("register");

        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
            value = extras.getString("addUsers");
        }
        chekAddUsers = value;
        Log.d("Art", chekAddUsers + "");
    }


    public void setIntentToSignIn() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void setSuccessSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("You pass registration step successfully,now you can Sign In");
        builder.setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setIntentToSignIn();
               // finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setTitle("Great ");
        dialog.setIcon(R.drawable.succes);
        dialog.show();
    }

    public boolean checkUserName() {
        if (DataBase.getInstance().getPersonsList() != null) {
            for (int i = 0; i < DataBase.getInstance().getPersonsList().size(); i++) {
                if (et_userName.getText().toString().equals(DataBase.getInstance().getPersonsList().get(i).getUserName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void personRegistration() {

        String firstName = et_firstName.getText().toString();
        String lastName = et_lastName.getText().toString();
        String userName = et_userName.getText().toString();
        String password = et_password.getText().toString();
        //String parentUserName = null;
        if (userName.equals("") || firstName.equals("") || lastName.equals("") || password.equals("")) {
            Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_LONG).show();
        } else if (userName.length() > 10) {
            Toast.makeText(this, "username can contain max:10 characters", Toast.LENGTH_LONG).show();
        } else if (password.length() < 4) {
            Toast.makeText(this, "password field must be contain more than 4 Characters", Toast.LENGTH_LONG).show();

        } else if (checkUserName()) {
            Toast.makeText(this, "This username is busy please choose another one", Toast.LENGTH_LONG).show();
        } else {
            Person person = new Person();
            person.setUserName(userName);
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setPassword(password);
            if (chekAddUsers != null) {
                person.setParentUserName(MainActivity.getParentUserName());
                Log.d("Art", true + "");
            }
            DataBase.getInstance().addPerson(person);
            Log.d("Artur",person.toString());


//            DatabaseOperations databaseOperations = new DatabaseOperations(this);
//            if (databaseOperations.checkUserName(databaseOperations, userName)) {
//                Toast.makeText(this, "This username is busy please choose another one", Toast.LENGTH_LONG).show();
//
//            } else if(!(databaseOperations.checkUserName(databaseOperations,userName))){
//                databaseOperations.putPersonToDB(databaseOperations, firstName, lastName, userName, password, parentUserName);
//
//
//            }
            setSuccessSignInDialog();

            et_userName.setText("");
            et_firstName.setText("");
            et_lastName.setText("");
            et_password.setText("");

        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_btn2) {
            personRegistration();
        }
    }
}
