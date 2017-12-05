package com.example.arturmusayelyan.task1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 27/11/2017.
 */

public class UsersListFragment extends Fragment {
    private ArrayList<String> dataList;

    public UsersListFragment() {

    }

    public static UsersListFragment newInstance(ArrayList<String> dataList) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", dataList);
        UsersListFragment fragment = new UsersListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_users, container, false);
        final ListView listView = view.findViewById(R.id.list_view_all_users);
        ArrayList<String> list = getArguments().getStringArrayList("list");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dialogPosition = listView.getItemAtPosition(position).toString();
                setPersonDialog(dialogPosition);
                Log.d("Art", "click worked");
            }
        });
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.single_row, R.id.tv_for_single_row, list));
        return view;
    }

    public void setPersonDialog(final String position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);

        for (int i = 0; i < DataBase.getInstance().getPersonsList().size(); i++) {
            if (DataBase.getInstance().getPersonsList().get(i).getUserName().equals(position)) {
                builder.setMessage(DataBase.getInstance().getPersonsList().get(i).getFirstName() + " " + DataBase.getInstance().getPersonsList().get(i).getLastName());
                break;
            }
        }
        //hetoyi hamar
//        if (!position.equals(MainActivity.getParentUserName())) {
//            builder.setPositiveButton("Chat", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(getActivity(), ChatActivity.class);
//                    intent.putExtra("keyForChatWithSelectedUser",position);
//                    startActivity(intent);
//                }
//            });
//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.cancel();
//                }
//            });
//        }
        AlertDialog dialog = builder.create();
        dialog.setTitle("Person INFO");
        dialog.setIcon(R.drawable.personinfo);
        dialog.show();
    }
}

