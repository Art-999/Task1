package com.example.arturmusayelyan.task1;

import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ArrayAdapter<String> adapterForUserslist;
    private RecyclerView recyclerView;
    private ListView listView;
    private RelativeLayout messageLayout;
    private boolean booleanForUserList = true;

    private TextView tvSelectedUser;
    static String selectedUserName;
    private TextView tvYourUserName;
    static String yourUserName;

    private EditText et_Message;
    private String messageText;
    private MyAdapter adapter;

    ArrayList<String> usersList;
    ArrayList<Message> messagesList;


    private ChatWithUsersFragment chatWithUsersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        messageLayout = (RelativeLayout) findViewById(R.id.messageLayout);


        listView = new ListView(this);
        messageLayout.addView(listView);
        usersList = new ArrayList<>();
       //1 adapterForUserslist = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usersList);
       //1 listView.setAdapter(adapterForUserslist);
        et_Message = (EditText) findViewById(R.id.message_ET);
//        SignInActivity.ParentUsername = "Art88";
//        DataBase.addPerson(new Person("Art88", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Kar89", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Manvel99", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Rubo89", "Artur", "Musayelyan", "89494"));

        tvYourUserName = (TextView) findViewById(R.id.yourUserName_TV);
        tvYourUserName.setText(MainActivity.getParentUserName());
        yourUserName = tvYourUserName.getText().toString();
        tvSelectedUser = (TextView) findViewById(R.id.selectedUser_TV);

        Bundle extras = getIntent().getExtras();
        String value = null;
        if (extras != null) {
            value = extras.getString("keyForChatWithSelectedUser");
        }
        selectedUserName = value;
        tvSelectedUser.setText(selectedUserName);

        //1
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                selectedUserName = listView.getItemAtPosition(position).toString();
//                tvSelectedUser.setText(selectedUserName);
//                listView.setVisibility(View.GONE);
//                usersList.clear();
//                adapterForUserslist.notifyDataSetChanged();
//                booleanForUserList = true;
//                MyAdapter.messageData.clear();
//                showMessageHistory(yourUserName, selectedUserName);
//            }
//        });

        messagesList = new ArrayList<>();
        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.showAllUsersList_Btn:
                selectedUserName = null;
                recyclerView.setVisibility(View.GONE);
                //1
//                if (booleanForUserList) {
//                    listView.setVisibility(View.VISIBLE);
//                    showAllUsersList();
//                    booleanForUserList = false;
//                } else {
//                    usersList.clear();
//                    adapterForUserslist.notifyDataSetChanged();
//                    booleanForUserList = true;
//                }
                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                chatWithUsersFragment=ChatWithUsersFragment.newInstance(usersList);

                Log.d("Art_Log", "worked");
                break;
            case R.id.user1_btn:
                addMessageToRecycler(et_Message.getText().toString(), true);
                break;
            case R.id.user2_btn:
                addMessageToRecycler(et_Message.getText().toString(), false);
                break;
            case R.id.gallery_btn:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
        }
    }

    public void showAllUsersList() {
        if (DataBase.personsList != null) {
            //recyclerView.setVisibility(View.VISIBLE);
            tvSelectedUser.setText("");
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                if (!(DataBase.personsList.get(i).getUserName()).equals(MainActivity.getParentUserName())) {
                    adapterForUserslist.add(DataBase.personsList.get(i).getUserName());
                    adapterForUserslist.notifyDataSetChanged();
                }
            }
        }
    }


    public void addMessageToRecycler(String msg, boolean fromLeftUser) {
        if (selectedUserName != null) {
            if (!msg.equals("") && !selectedUserName.equals("")) {
                recyclerView.setVisibility(View.VISIBLE);
                if (fromLeftUser) {
                    adapter.addMessage(new Message(msg, true));
                    DataBase.addMessageToHistory(new Message(msg, yourUserName, selectedUserName));
                } else {
                    adapter.addMessage(new Message(msg, false));
                    DataBase.addMessageToHistory(new Message(msg, selectedUserName, yourUserName));
                }
                if (adapter.getItemCount() > 3) {
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
                et_Message.setText("");
            }
        } else {
            Toast.makeText(this, "Choose user for chat", Toast.LENGTH_SHORT).show();
        }
    }

    public void showMessageHistory(String sendFromUser, String sendToUser) {
        recyclerView.setVisibility(View.VISIBLE);
        if (DataBase.messageHistoryList != null) {
            for (int i = 0; i < DataBase.messageHistoryList.size(); i++) {
                if (DataBase.messageHistoryList.get(i).getSendFromUser().equals(sendFromUser) && DataBase.messageHistoryList.get(i).getSendToUser().equals(sendToUser)) {
                    adapter.addMessage(new Message(DataBase.messageHistoryList.get(i).getMessageText(), true));
                } else if (DataBase.messageHistoryList.get(i).getSendFromUser().equals(sendToUser) && DataBase.messageHistoryList.get(i).getSendToUser().equals(sendFromUser)) {
                    adapter.addMessage(new Message(DataBase.messageHistoryList.get(i).getMessageText(), false));
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
//                fileManagerString=selectedImageUri.getPath();
//                selectedImagePath=getPath(selectedImageUri);
                if (selectedImageUri != null) {
                    Log.d("Art", selectedImageUri + "");

                    adapter.addMessage(new Message("", true, selectedImageUri));
                    DataBase.addMessageToHistory(new Message("", yourUserName, selectedUserName, selectedImageUri));
                } else {
                    Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
