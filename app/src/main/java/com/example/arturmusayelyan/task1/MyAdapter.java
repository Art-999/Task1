package com.example.arturmusayelyan.task1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    static ArrayList<Message> messageData;

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
        View view = inflater.inflate(R.layout.row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        com.example.arturmusayelyan.task1.Message currentMessage = messageData.get(position);

        if (holder.getItemViewType() == ME){

        }else if (holder.getItemViewType() == YOU){

        }

        if (currentMessage.getImageUri() == null && currentMessage.isSendFromMe()) {
            holder.imageView.setVisibility(View.GONE);
            holder.tvYourMessage.setText(currentMessage.getMessageText());
            holder.tvOtherMessage.setVisibility(View.INVISIBLE);
        } else if (currentMessage.getImageUri() == null && !(currentMessage.isSendFromMe())) {
            holder.imageView.setVisibility(View.GONE);
            holder.tvOtherMessage.setText(currentMessage.getMessageText());
            holder.tvYourMessage.setVisibility(View.INVISIBLE);
        } else if (currentMessage.getImageUri() != null) {
            holder.tvYourMessage.setVisibility(View.GONE);
            holder.tvOtherMessage.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageURI(currentMessage.getImageUri());
        }

//        if (currentMessage.isFromLeftUser() && currentMessage.getImageUri()==null) {
//            holder.tvYourMessage.setText(currentMessage.getMessageText());
//            holder.tvOtherMessage.setVisibility(View.INVISIBLE);
//        } else if (!currentMessage.isFromLeftUser() && currentMessage.getImageUri()==null) {
//            holder.tvOtherMessage.setText(currentMessage.getMessageText());
//            holder.tvYourMessage.setVisibility(View.INVISIBLE);
//        } else if (currentMessage.getImageUri() != null) {
//            Log.d("Art", "ImageUri worked");
//            holder.tvYourMessage.setVisibility(View.GONE);
//            holder.tvOtherMessage.setVisibility(View.GONE);
//
//            holder.imageView.setVisibility(View.VISIBLE);
//            holder.imageView.setImageURI(currentMessage.getImageUri());
//        }
    }


    @Override
    public int getItemCount() {
        return messageData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return messageData.get(position).isSendFromMe() ? ME : YOU;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvYourMessage;
        TextView tvOtherMessage;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvYourMessage = itemView.findViewById(R.id.tv_yourMessage);
            tvOtherMessage = itemView.findViewById(R.id.tv_otherMessage);
            imageView = itemView.findViewById(R.id.imageView1);
        }
    }
}
