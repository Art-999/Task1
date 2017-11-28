package com.example.arturmusayelyan.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class RootActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private FrameLayout frameLayout2;
    private UsersListFragment usersListFragment;
    private ChildUsersListFragment childUsersListFragmnet;
    private ArrayList<String> allUsersList;

    private boolean allUsersButtonCheckClick;
    private boolean myUsersButtonCheckClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        frameLayout = findViewById(R.id.frame_layout);
        frameLayout2 = findViewById(R.id.frame_layout2);


//        MainActivity.parentUserName="Art";
//        Person person1 = new Person();
//        person1.setUserName("artur88");
//        DataBase.addPerson(person1);
//        Person person2 = new Person();
//        person2.setUserName("sevak91");
//        DataBase.addPerson(person2);
//        Person person3=new Person();
//        person3.setParentUserName("Art");
//        DataBase.addPerson(person3);
        allUsersButtonCheckClick = false;
        myUsersButtonCheckClick = false;

//        allUsersList = new ArrayList<>();
//        if (DataBase.personsList != null) {
//            for (int i = 0; i < DataBase.personsList.size(); i++) {
//                allUsersList.add(DataBase.personsList.get(i).getUserName());
//            }
//        }
    }

    public ArrayList<String> showAllUsersList() {
        ArrayList<String> allUsersList = new ArrayList<>();
        if (DataBase.personsList != null) {
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                allUsersList.add(DataBase.personsList.get(i).getUserName());
            }
            return allUsersList;
        }
        return allUsersList;
    }

    public ArrayList<String> showChildUsersList() {
        ArrayList<String> childUsersList = new ArrayList<>();
        if (DataBase.personsList != null) {
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                if (DataBase.personsList.get(i).getParentUserName() != null) {
                    if (DataBase.personsList.get(i).getParentUserName().equals(MainActivity.getParentUserName())) {
                        Log.d("Art", DataBase.personsList.get(i).getParentUserName());
                        childUsersList.add(DataBase.personsList.get(i).getUserName());
                    }
                }
            }
            return childUsersList;
        }
        return childUsersList;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.allUsers_btn:
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (!allUsersButtonCheckClick) {
                    Log.d("Art", allUsersButtonCheckClick + "");
                    usersListFragment = UsersListFragment.newInstance(showAllUsersList());
                    fragmentTransaction.add(R.id.frame_layout, usersListFragment);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    allUsersButtonCheckClick = true;
                } else if (allUsersButtonCheckClick) {
                    fragmentTransaction.remove(usersListFragment);
                    //fragmentTransaction.addToBackStack("stack");
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    allUsersButtonCheckClick = false;
                }
                fragmentTransaction.commit();

                break;
            case R.id.addUsers_btn:
                Intent intent = new Intent(this, Registration.class);
                intent.putExtra("addUsers", "parentChild");
                startActivity(intent);
                break;
            case R.id.myUsers_btn:
                Log.d("Art", showChildUsersList().toString());
                FragmentManager fragmentManager2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                if (!myUsersButtonCheckClick) {
                    childUsersListFragmnet = ChildUsersListFragment.newInstance(showChildUsersList());
                    fragmentTransaction2.add(R.id.frame_layout2, childUsersListFragmnet);
                    fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    myUsersButtonCheckClick = true;
                } else if (myUsersButtonCheckClick) {
                    fragmentTransaction2.remove(childUsersListFragmnet);
                    fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    myUsersButtonCheckClick = false;
                }
                fragmentTransaction2.commit();
                break;
        }
    }
}
