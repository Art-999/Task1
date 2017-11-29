package com.example.arturmusayelyan.task1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by USER on 29.11.2017.
 */

public class ChatWithUsersFragment extends Fragment {

    private OnChatFragmentClickListener onChatFragmentClickListener;

    public ChatWithUsersFragment() {

    }

    public static ChatWithUsersFragment newInstance(ArrayList<String> dataList) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", dataList);
        ChatWithUsersFragment fragment = new ChatWithUsersFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnChatFragmentClickListener(OnChatFragmentClickListener onChatFragmentClickListener){
        this.onChatFragmentClickListener = onChatFragmentClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_with_chat, container, false);
        final ListView listView = view.findViewById(R.id.list_view_chat_users);
        ArrayList<String> list = getArguments().getStringArrayList("list");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Example example = new Example("Hello from Fragment", position);
//                onChatFragmentClickListener.onFragmentClick(example);
                onChatFragmentClickListener.onFragmentClick(listView.getItemAtPosition(position).toString());
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.single_row, R.id.tv_for_single_row, list));
        return view;
    }




    interface OnChatFragmentClickListener{
       // void onFragmentClick(Example example);
        void onFragmentClick(String userName);
    }
}
