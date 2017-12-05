package com.example.arturmusayelyan.task1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity implements ChatWithUsersFragment.OnChatFragmentClickListener {
    private ArrayAdapter<String> adapterForUserslist;
    private RecyclerView recyclerView;
    private ListView listView;
    private RelativeLayout messageLayout;
    //1  private boolean booleanForUserList = true;

    private TextView tvSelectedUser;
    static String selectedUserName;
    private TextView tvYourUserName;
    static String yourUserName;

    private EditText et_Message;
    private MyAdapter adapter;

    ArrayList<String> usersList;
    ArrayList<Message> messagesList;


    private ChatWithUsersFragment chatWithUsersFragment;
    private boolean checkChatWithUsersListButton;

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

//        SignInActivity.ParentUsername = "Art88";
//        DataBase.addPerson(new Person("Art88", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Kar89", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Manvel99", "Artur", "Musayelyan", "89494"));
//        DataBase.addPerson(new Person("Rubo89", "Artur", "Musayelyan", "89494"));

        et_Message = (EditText) findViewById(R.id.message_ET);
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

        checkChatWithUsersListButton = false;

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
                //1
//                selectedUserName = null;
//                recyclerView.setVisibility(View.GONE);
//
//                if (booleanForUserList) {
//                    listView.setVisibility(View.VISIBLE);
//                    showAllUsersList();
//                    booleanForUserList = false;
//                } else {
//                    usersList.clear();
//                    adapterForUserslist.notifyDataSetChanged();
//                    booleanForUserList = true;
//                }

                android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                if (!checkChatWithUsersListButton) {
                    recyclerView.setVisibility(View.GONE);
                    chatWithUsersFragment = ChatWithUsersFragment.newInstance(showUsersListForChat());
                    chatWithUsersFragment.setOnChatFragmentClickListener(this);
                    fragmentTransaction.add(R.id.frame_layout_for_chat_with_users, chatWithUsersFragment);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    checkChatWithUsersListButton = true;
                } else if (checkChatWithUsersListButton) {
                    recyclerView.setVisibility(View.VISIBLE);
                    fragmentTransaction.remove(chatWithUsersFragment);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    checkChatWithUsersListButton = false;
                }
                fragmentTransaction.commit();

                Log.d("Artur", "fragmentTransaction worked");
                break;
            case R.id.user1_btn:
                addMessageToRecycler(et_Message.getText().toString());
                break;
//            case R.id.user2_btn:
//                addMessageToRecycler(et_Message.getText().toString(), false);
//                break;
            case R.id.gallery_btn:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
        }
    }

    //harmara sovorakan aranc fragmenti orinaki hamar
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

    public void showMessageHistory(String sendFromUser, String sendToUser) {
        recyclerView.setVisibility(View.VISIBLE);
        if (DataBase.getInstance().messageHistoryList != null) {
            for (int i = 0; i < DataBase.getInstance().messageHistoryList.size(); i++) {
                if (DataBase.getInstance().messageHistoryList.get(i).getSendFromUser().equals(sendFromUser) && DataBase.getInstance().messageHistoryList.get(i).getSendToUser().equals(sendToUser) && DataBase.messageHistoryList.get(i).getImageUri() == null) {
                    adapter.addMessage(new Message(DataBase.messageHistoryList.get(i).getMessageText(), sendFromUser, sendToUser, null, true));
                    Log.d("Artur", "1 if worked");
                } else if (DataBase.getInstance().messageHistoryList.get(i).getSendFromUser().equals(sendToUser) && DataBase.getInstance().messageHistoryList.get(i).getSendToUser().equals(sendFromUser) && DataBase.messageHistoryList.get(i).getImageUri() == null) {
                    adapter.addMessage(new Message(DataBase.messageHistoryList.get(i).getMessageText(), sendToUser, sendFromUser, null, false));
                    Log.d("Artur", "2 if worked");
                } else if (DataBase.getInstance().messageHistoryList.get(i).getImageUri() != null) {
                    adapter.addMessage(new Message("image", sendFromUser, sendToUser, DataBase.messageHistoryList.get(i).getImageUri(), true));
                    Log.d("Artur", "3 if worked");
                }
            }
        }
        //DataBase.getInstance().messageHistoryList.get(i).getSendFromUser().equals(sendFromUser) && DataBase.getInstance().messageHistoryList.get(i).getSendToUser().equals(sendToUser) &&
    }

    public ArrayList<String> showUsersListForChat() {
        ArrayList<String> dataList = new ArrayList<>();
        if (DataBase.personsList != null) {
            for (int i = 0; i < DataBase.personsList.size(); i++) {
                if (!(DataBase.personsList.get(i).getUserName().equals(MainActivity.getParentUserName()))) {
                    dataList.add(DataBase.personsList.get(i).getUserName());
                }
            }
            return dataList;
        }
        return dataList;
    }


    public void addMessageToRecycler(String msg) {
        if (selectedUserName != null) {
            if (!msg.equals("") && !selectedUserName.equals("")) {
                recyclerView.setVisibility(View.VISIBLE);
//                if (fromLeftUser) {
//                    adapter.addMessage(new Message(msg, true));
//                    DataBase.addMessageToHistory(new Message(msg, yourUserName, selectedUserName));
//                } else {
//                    adapter.addMessage(new Message(msg, false));
//                    DataBase.addMessageToHistory(new Message(msg, selectedUserName, yourUserName));
//                }
                adapter.addMessage(new Message(msg, yourUserName, selectedUserName, null, true));
                DataBase.getInstance().addMessageToHistory(new Message(msg, yourUserName, selectedUserName, null, true));
                if (adapter.getItemCount() > 3) {
                    recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
                }
                et_Message.setText("");
            }
        } else {
            Toast.makeText(this, "Choose user for chat", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    Log.d("Artur", selectedImageUri + "");

                    adapter.addMessage(new Message("image", yourUserName, selectedUserName, selectedImageUri, true));
                    DataBase.getInstance().addMessageToHistory(new Message("image", yourUserName, selectedUserName, selectedImageUri, true));
                } else {
                    Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onFragmentClick(String userName) {
        selectedUserName = userName;
        tvSelectedUser.setText(selectedUserName);

        // recyclerView.setVisibility(View.VISIBLE);
        MyAdapter.messageData.clear();
        showMessageHistory(yourUserName, selectedUserName);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(chatWithUsersFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();

        checkChatWithUsersListButton = false;
    }

}
