package com.example.arturmusayelyan.task1;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by artur.musayelyan on 28/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final int ME = 0;
    private static final int YOU = 1;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Message> messageData;


   // private GoogleMap mMap;

    public MyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        messageData = new ArrayList<>();

    }

    public void addMessage(Message msg) {
        messageData.add(msg);
        notifyDataSetChanged();
        Log.d("Artur", "addMessage method worked");
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = inflater.inflate(R.layout.row_layout, parent, false);
        if (viewType == ME) {
            View firstView = inflater.inflate(R.layout.row_layout_first, parent, false);
            return new MyViewHolder(firstView);
        } else {
            View secondView = inflater.inflate(R.layout.row_layout_second, parent, false);
            return new MyViewHolder(secondView);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        com.example.arturmusayelyan.task1.Message currentMessage = messageData.get(position);

        //Nareki orinakna
        if (holder.getItemViewType() == ME) {
            if (currentMessage.getImageUri() == null) {
                holder.yourMessage_imgView.setVisibility(View.GONE);
                holder.yourMessage_tv.setVisibility(View.VISIBLE);
                holder.yourMessage_tv.setText(currentMessage.getMessageText());

                //GoogleMapOptions options = new GoogleMapOptions().liteMode(true)
 //               holder.yourMessage_tv.setVisibility(View.GONE);
//                android.support.v4.app.FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                MapForChatActivity mapFragment=MapForChatActivity.newInstance();
//                fragmentTransaction.add(R.id.frame_layout_for_chat_with_users,mapFragment);
//                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                fragmentTransaction.commit();
                //holder.layout_for_map.addView(MapForChatActivity.newInstance());


                //holder.yourMessage_tv.setText(new LatLng(40.178613, 44.512654).toString());
                Log.d("Artur", "my adapter if worked");

            } else if (currentMessage.getImageUri() != null) {
                holder.yourMessage_tv.setVisibility(View.GONE);
                holder.yourMessage_imgView.setVisibility(View.VISIBLE);
                holder.yourMessage_imgView.setImageURI(null);
                holder.yourMessage_imgView.setImageURI(currentMessage.getImageUri());
                Log.d("Artur", "my adapter else worked");
            }

        } else if (holder.getItemViewType() == YOU) {
            if (currentMessage.getImageUri() == null) {
                holder.otherMessage_imgView.setVisibility(View.GONE);
                holder.otherMessage_tv.setVisibility(View.VISIBLE);
                holder.otherMessage_tv.setText(currentMessage.getMessageText());
                Log.d("Artur", "your adapter if worked");

            } else if (currentMessage.getImageUri() != null) {
                holder.otherMessage_tv.setVisibility(View.GONE);
                holder.otherMessage_imgView.setVisibility(View.VISIBLE);

                holder.otherMessage_imgView.setImageURI(null);
                holder.otherMessage_imgView.setImageURI(currentMessage.getImageUri());
                Log.d("Artur", "your adapter else worked");
            }

        }
    }


    @Override
    public int getItemCount() {
        return messageData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messageData.get(position).getSendFromUser().equals(MainActivity.getParentUserName()) ? ME : YOU;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView yourMessage_tv;
        TextView otherMessage_tv;
        ImageView yourMessage_imgView;
        ImageView otherMessage_imgView;

        FrameLayout layout_for_map;
        Fragment fragment_for_map;

        public MyViewHolder(View itemView) {
            super(itemView);
            yourMessage_tv = itemView.findViewById(R.id.your_message_tv);
            otherMessage_tv = itemView.findViewById(R.id.other_message_tv);
            yourMessage_imgView = itemView.findViewById(R.id.your_message_iv);
            otherMessage_imgView = itemView.findViewById(R.id.other_message_iv);

         //   layout_for_map = itemView.findViewById(R.id.frame_layout_for_chat_map);

        }
    }

    public void adapterDataClear() {
        messageData.clear();
        notifyDataSetChanged();
    }




}
