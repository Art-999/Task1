package com.example.arturmusayelyan.task1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_signIn;
    private Button btn_register;
    private EditText et_userName;
    private EditText et_password;
    private static String parentUserName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_signIn = findViewById(R.id.signin_btn);
        btn_register = findViewById(R.id.register_btn);
        et_userName = findViewById(R.id.username_et);
        et_password = findViewById(R.id.password_et);

//        Bundle bundle = new Bundle();
//        bundle.putBoolean("register", false);
    }

    public static String getParentUserName() {
        return parentUserName;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn:
                checkLogIn();
                break;
            case R.id.register_btn:
                setIntentForGoingRegisterActivity();
                et_userName.setText("");
                et_password.setText("");
                break;
        }
    }

    public void setFalseLogInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage("username or password has been incorrect");
        builder.setTitle(" ");
        builder.setIcon(R.drawable.error);
        builder.setPositiveButton("Log In again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                et_userName.setText("");
                et_password.setText("");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setIntentForGoingRegisterActivity() {
        Intent intent = new Intent(this, Registration.class);
        this.startActivity(intent);
    }

    public void passRegistrationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("At first you must pass the registration step");
        builder.setPositiveButton("Registration", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                setIntentForGoingRegisterActivity();
                et_userName.setText("");
                et_password.setText("");
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                et_userName.setText("");
                et_password.setText("");
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle(" ");
        dialog.setIcon(R.drawable.stop);
        dialog.show();
    }

    public void checkLogIn() {
        if (DataBase.personsList != null) {
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                if (DataBase.personsList.get(i).getUserName().equals(et_userName.getText().toString()) && DataBase.personsList.get(i).getPassword().equals(et_password.getText().toString())) {
                    Toast.makeText(this, "You pass LogIn step successfully", Toast.LENGTH_LONG).show();
                    parentUserName = et_userName.getText().toString();
                    Intent intent = new Intent(this, RootActivity.class);
                    // intent.putExtra("register",true);
                    startActivity(intent);
                    et_userName.setText("");
                    et_password.setText("");
                    return;
                } else {
                    setFalseLogInDialog();
                }
            }
        } else if (DataBase.personsList == null) {
            passRegistrationDialog();
        }
    }
}
