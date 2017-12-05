package com.example.arturmusayelyan.task1;

import android.net.Uri;

/**
 * Created by artur.musayelyan on 28/11/2017.
 */

public class Message {
    private String messageText;
    private boolean fromLeftUser;
    private String sendFromUser;
    private String sendToUser;
    private boolean sendFromMe;

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

    public Message(String messageText, boolean fromLeftUser, String sendFromUser, String sendToUser, Uri imageUri) {
        this.messageText = messageText;
        this.fromLeftUser = fromLeftUser;
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
        this.imageUri = imageUri;
    }

    public Message(String messageText, String sendFromUser, String sendToUser, Uri imageUri, boolean sendFromMe) {
        this.messageText = messageText;
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
        this.imageUri = imageUri;
        this.sendFromMe = sendFromMe;
    }

    public Message(String messageText, boolean fromLeftUser, Uri image) {
        this.messageText = messageText;
        this.fromLeftUser = fromLeftUser;
        this.imageUri = image;
    }

    //avelacrac
    public Message(String messageText, String sendFromUser, String sendToUser) {
        this.messageText = messageText;
        this.sendFromUser = sendFromUser;
        this.sendToUser = sendToUser;
    }

    public Message(String messageText, boolean fromLeftUser) {
        this.messageText = messageText;
        this.fromLeftUser = fromLeftUser;
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
        return "Message{" +
                "messageText='" + messageText + '\'' +
                ", sendFromUser='" + sendFromUser + '\'' +
                ", sendToUser='" + sendToUser + '\'' +
                ", imageUri=" + imageUri +
                '}';
    }
}
