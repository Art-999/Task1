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
            case R.id.gallery_btn:
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;
            case R.id.map_btn:
                Intent intent1 = new Intent(this, MapsActivity.class);
                startActivity(intent1);
                break;
        }
    }

    public ArrayList<String> showUsersListForChat() {
        ArrayList<String> dataList = new ArrayList<>();
        if (DataBase.getInstance().getPersonsList() != null) {
            for (int i = 0; i < DataBase.getInstance().getPersonsList().size(); i++) {
                if (!(DataBase.getInstance().getPersonsList().get(i).getUserName().equals(MainActivity.getParentUserName()))) {
                    dataList.add(DataBase.getInstance().getPersonsList().get(i).getUserName());
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

                    Message msg = new Message("image", yourUserName, selectedUserName, selectedImageUri, true);
                    adapter.addMessage(msg);
                    DataBase.getInstance().addMessageToHistory(msg);
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
        adapter.adapterDataClear();
        showMessageHistory(yourUserName, selectedUserName);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(chatWithUsersFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.commit();

        checkChatWithUsersListButton = false;
    }

    public void showMessageHistory(String sender, String receiver) {
        recyclerView.setVisibility(View.VISIBLE);
        if (DataBase.getInstance().getMessageHistoryList() != null) {
            for (int i = 0; i < DataBase.getInstance().getMessageHistoryList().size(); i++) {
                if ((DataBase.getInstance().getMessageHistoryList().get(i).getSendFromUser().equals(sender) && DataBase.getInstance().getMessageHistoryList().get(i).getSendToUser().equals(receiver)) ||
                        (DataBase.getInstance().getMessageHistoryList().get(i).getSendFromUser().equals(receiver) && DataBase.getInstance().getMessageHistoryList().get(i).getSendToUser().equals(sender))) {
                    adapter.addMessage(DataBase.getInstance().getMessageHistoryList().get(i));
                }
            }
        }
    }

}
