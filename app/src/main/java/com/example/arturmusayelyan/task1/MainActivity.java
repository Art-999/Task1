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
        //ShortcutIcon();

        DataBase.getInstance().addPerson(new Person("Art", "0000", "Artur", "Musayelyan"));
        DataBase.getInstance().addPerson(new Person("Kar", "0000", "Karen", "Karapetyan"));
        DataBase.getInstance().addPerson(new Person("Nar", "0000", "Narek", "Minasyan"));
        DataBase.getInstance().addPerson(new Person("Rub", "0000", "Ruben", "Grigoryan"));
    }

    public static String getParentUserName() {
        return parentUserName;
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signin_btn:
                if (DataBase.getInstance().getPersonsList() != null) {
                    if (checkLogIn()) {
                        Toast.makeText(this, "You pass LogIn step successfully...", Toast.LENGTH_LONG).show();
                        parentUserName = et_userName.getText().toString();
                        Intent intent = new Intent(this, RootActivity.class);
                        startActivity(intent);
                        et_userName.setText("");
                        et_password.setText("");
                    } else {
                        setFalseLogInDialog();
                    }
                } else {
                    passRegistrationDialog();
                }
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

    public boolean checkLogIn() {
        for (int i = 0; i < DataBase.getInstance().getPersonsList().size(); i++) {
            if (DataBase.getInstance().getPersonsList().get(i).getUserName().equals(et_userName.getText().toString()) && DataBase.getInstance().getPersonsList().get(i).getPassword().equals(et_password.getText().toString())) {
                return true;
            }
        }
        return false;
    }

    private void ShortcutIcon() {

        Intent shortcutIntent = new Intent(getApplicationContext(), MainActivity.class);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Intent addIntent = new Intent();
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Test");
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.shortcut));
        addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(addIntent);
    }


}
