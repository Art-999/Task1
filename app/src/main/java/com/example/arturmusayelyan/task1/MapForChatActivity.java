package com.example.arturmusayelyan.task1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by artur.musayelyan on 08/12/2017.
 */

public class MapForChatActivity extends Fragment {
    public MapForChatActivity(){

    }

    public static MapForChatActivity newInstance() {

        Bundle args = new Bundle();

        MapForChatActivity fragment = new MapForChatActivity();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.row_layout_first,container,false);
        View resultView=view.findViewById(R.id.frame_layout_for_chat_map);
        return resultView;
    }
}
