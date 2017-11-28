package com.example.arturmusayelyan.task1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 28/11/2017.
 */

public class ChildUsersListFragment extends Fragment {
    public ChildUsersListFragment() {

    }

    public static ChildUsersListFragment newInstance(ArrayList<String> dataList) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", dataList);
        ChildUsersListFragment fragment = new ChildUsersListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_users_child, container, false);
        final ListView listView = view.findViewById(R.id.list_view_for_child_users);
        ArrayList<String> list = getArguments().getStringArrayList("list");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dialogPosition = listView.getItemAtPosition(position).toString();
                setChildPersonsDialog(dialogPosition);
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.single_row, R.id.tv_for_single_row, list));
        return view;
    }

    public void setChildPersonsDialog(String position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);

        for (int i = 0; i < DataBase.personsList.size(); i++) {
            if (DataBase.personsList.get(i).getUserName().equals(position)) {
                builder.setMessage(DataBase.personsList.get(i).getFirstName() + " " + DataBase.personsList.get(i).getLastName());
                break;
            }
        }
        builder.setPositiveButton("Chat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setTitle("Person INFO");
        dialog.setIcon(R.drawable.personinfo);
        dialog.show();
    }
}
