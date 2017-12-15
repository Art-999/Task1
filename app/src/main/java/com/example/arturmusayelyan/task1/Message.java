package com.example.arturmusayelyan.task1;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by artur.musayelyan on 28/11/2017.
 */

public class Message {
    private String messageText;
    private boolean fromLeftUser;
    private String sendFromUser;
    private String sendToUser;
    private boolean sendFromMe;
    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public boolean isSendFromMe() {
        return sendFromMe;
    }

    public void setSendFromMe(boolean sendFromMe) {
        this.sendFromMe = sendFromMe;
    }

    private Uri imageUri;

    public Message() {

    }

    public Message(String messageText, boolean fromLeftUser, String sendFromUser, String sendToUser) {
        this.messageText = messageText;
        this.fromLeftUser = fromLeftUser;
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
    }

    public Message(String messageText, String sendFromUser, String sendToUser, Uri imageUri, boolean sendFromMe, LatLng latLng) {
        this.messageText = messageText;
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
        this.imageUri = imageUri;
        this.sendFromMe = sendFromMe;
        this.latLng = latLng;
    }


    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean isFromLeftUser() {
        return fromLeftUser;
    }

    public void setFromLeftUser(boolean fromLeftUser) {
        this.fromLeftUser = fromLeftUser;
    }

    public String getSendFromUser() {
        return sendFromUser;
    }

    public void setSendFromUser(String sendFromUser) {
        this.sendFromUser = sendFromUser;
    }

    public String getSendToUser() {
        return sendToUser;
    }

    public void setSendToUser(String sendToUser) {
        this.sendToUser = sendToUser;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return "Message{" + "messageText='" + messageText + '\'' + ", sendFromUser='" + sendFromUser + '\'' + ", sendToUser='" + sendToUser + '\'' + ", imageUri=" + imageUri + '}';
    }
}
