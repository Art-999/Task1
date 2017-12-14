package com.example.arturmusayelyan.task1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    Bitmap bitmap;

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

                final LatLng latLng = currentMessage.getLatLng();
                try {
                    holder.yourMap_imgView.setImageBitmap(new BackgroundTask(latLng.latitude, latLng.longitude).execute().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                holder.yourMap_imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmnIntentUri = Uri.parse("geo:" + latLng.latitude + "," + latLng.longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmnIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        context.startActivity(mapIntent);
                    }
                });
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

                final LatLng latLng = currentMessage.getLatLng();
                try {
                    holder.otherMap_imgView.setImageBitmap(new BackgroundTask(latLng.latitude, latLng.longitude).execute().get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                holder.otherMap_imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Uri gmnIntentUri = Uri.parse("geo:" + latLng.latitude + "," + latLng.longitude);
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmnIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        context.startActivity(mapIntent);
                    }
                });
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
        // TextView yourMap_tv;

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

            //yourMap_tv=itemView.findViewById(R.id.your_map_tv);
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

    public static Bitmap getGoogleMapThumbnail(double lati, double longi) {
        String URL = "http://maps.google.com/maps/api/staticmap?center=" + lati + "," + longi + "&zoom=15&size=200x150&sensor=false";
        Bitmap bmp = null;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(URL);

        InputStream in = null;
        try {
            in = httpclient.execute(request).getEntity().getContent();
            bmp = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp;
    }

    class BackgroundTask extends AsyncTask<Void, Void, Bitmap> {
        Double latitude;
        Double longitude;

        public BackgroundTask(Double latitude, Double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            String URL = "http://maps.google.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=14&size=300x200&sensor=false" + "&markers=color:blue%7Clabel:S%7C" + latitude + "," + longitude;
            Bitmap bmp = null;
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet(URL);

            InputStream in = null;
            try {
                in = httpclient.execute(request).getEntity().getContent();
                bmp = BitmapFactory.decodeStream(in);
                in.close();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return bmp;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }
}
