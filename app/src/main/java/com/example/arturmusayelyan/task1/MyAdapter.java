package com.example.arturmusayelyan.task1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * Created by artur.musayelyan on 28/11/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements OnMapReadyCallback {
    private static final int ME = 0;
    private static final int YOU = 1;
    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Message> messageData;


    private GoogleMap mMap;

    public MyAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        messageData = new ArrayList<>();

        //this.mMap = mMap;
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
            if ((currentMessage.getImageUri() == null) && (currentMessage.getLatLng() == null)) {

                holder.yourMessage_imgView.setVisibility(GONE);
                holder.yourMap_imgView.setVisibility(GONE);
                holder.yourMessage_tv.setVisibility(View.VISIBLE);
                holder.yourMessage_tv.setText(currentMessage.getMessageText());
                Log.d("Artur", "my adapter if worked");

            } else if ((currentMessage.getImageUri() != null) && (currentMessage.getLatLng() == null)) {
                holder.yourMessage_tv.setVisibility(GONE);
                holder.yourMap_imgView.setVisibility(GONE);
                holder.yourMessage_imgView.setVisibility(View.VISIBLE);
                holder.yourMessage_imgView.setImageURI(null);
                holder.yourMessage_imgView.setImageURI(currentMessage.getImageUri());
                Log.d("Artur", "my adapter else worked");
            } else if (currentMessage.getLatLng() != null) {
                holder.yourMessage_tv.setVisibility(GONE);
                holder.yourMessage_imgView.setVisibility(View.GONE);
                holder.yourMap_imgView.setVisibility(View.VISIBLE);

                //               URL url=new URL(https://www.google.com/maps/place/CenturyLink+Field/@47.5951518,-122.3316394,17z/data=!3m1!4b1!4m5!3m4!1s0x54906aa3b9f1182b:0xa636cd513bba22dc!8m2!3d47.5951518!4d-122.3316394);
//                holder.yourMap_imgView.setImageBitmap(getBitmapFromURL(url.toString()));
            }

        } else if (holder.getItemViewType() == YOU) {
            if ((currentMessage.getImageUri() == null) && (currentMessage.getLatLng() == null)) {
                holder.otherMessage_imgView.setVisibility(GONE);
                holder.otherMap_imgView.setVisibility(GONE);
                holder.otherMessage_tv.setVisibility(View.VISIBLE);
                holder.otherMessage_tv.setText(currentMessage.getMessageText());
                Log.d("Artur", "your adapter if worked");

            } else if ((currentMessage.getImageUri() != null) && (currentMessage.getLatLng() == null)) {
                holder.otherMessage_tv.setVisibility(GONE);
                holder.otherMap_imgView.setVisibility(GONE);
                holder.otherMessage_imgView.setVisibility(View.VISIBLE);
                holder.otherMessage_imgView.setImageURI(null);
                holder.otherMessage_imgView.setImageURI(currentMessage.getImageUri());
                Log.d("Artur", "your adapter else worked");
            } else if (currentMessage.getLatLng() != null) {
                holder.otherMessage_tv.setVisibility(GONE);
                holder.otherMessage_imgView.setVisibility(View.GONE);
                holder.otherMap_imgView.setVisibility(View.VISIBLE);
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
        ImageView yourMap_imgView;
        ImageView otherMap_imgView;

        FrameLayout layout_for_map;
        // View mapView;

        public MyViewHolder(View itemView) {
            super(itemView);
            yourMessage_tv = itemView.findViewById(R.id.your_message_tv);
            otherMessage_tv = itemView.findViewById(R.id.other_message_tv);
            yourMessage_imgView = itemView.findViewById(R.id.your_message_iv);
            otherMessage_imgView = itemView.findViewById(R.id.other_message_iv);

            yourMap_imgView = itemView.findViewById(R.id.your_map_iv);
            otherMap_imgView = itemView.findViewById(R.id.other_map_iv);

            //layout_for_map = itemView.findViewById(R.id.frame_layout_for_chat_map);
            //mapView = layout_for_map.findViewById(R.id.map_for_fragment);
        }
    }

    public void adapterDataClear() {
        messageData.clear();
        notifyDataSetChanged();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
