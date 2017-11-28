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
    private String chekAddUsers ;

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
        String value=null;
        if (extras != null) {
            value = extras.getString("addUsers");
        }
        chekAddUsers=value;
        Log.d("Art", chekAddUsers + "");
    }


    public void setIntentToSignIn() {
        Intent intent = new Intent(this, MainActivity.class);
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
        if (DataBase.personsList != null) {
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                if (et_userName.getText().toString().equals(DataBase.personsList.get(i).getUserName())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void personRegistration() {
        if (et_userName.getText().toString().equals("") || et_firstName.getText().toString().equals("") || et_lastName.getText().toString().equals("")
                || et_password.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_LONG).show();
        } else if (et_userName.getText().toString().length() > 10) {
            Toast.makeText(this, "username can contain max:10 characters", Toast.LENGTH_LONG).show();
        } else if (et_password.getText().toString().length() < 4) {
            Toast.makeText(this, "password field must be contain more than 4 Characters", Toast.LENGTH_LONG).show();
        } else if (checkUserName()) {
            Toast.makeText(this, "This username is busy please choose another one", Toast.LENGTH_LONG).show();
        } else {
            Person person = new Person();
            person.setUserName(et_userName.getText().toString());
            person.setFirstName(et_firstName.getText().toString());
            person.setLastName(et_lastName.getText().toString());
            person.setPassword(et_password.getText().toString());
            if (chekAddUsers!=null) {
                person.setParentUserName(MainActivity.getParentUserName());
                Log.d("Art", true + "");
            }
            DataBase.addPerson(person);

            Log.d("Art", person.getParentUserName() + " " + person.getUserName());

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
