package com.ahsp.utils;

import com.ahsp.po.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {
    public static String getCurrentTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateStr = sdp.format(date);
        return dateStr;
    }

    public static Message configMessage(String message_time,String message_type,String message_content){
        Message message = new Message();
        message.setMessage_time(message_time);
        message.setMessage_type(message_type);
        message.setMessage_content(message_content);
        return message;
    }

    public static String uuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
