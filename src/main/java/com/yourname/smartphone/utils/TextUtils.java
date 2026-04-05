package com.yourname.smartphone.utils;

public class TextUtils {
    
    private static final String NORMAL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String SMALLCAPS = "ᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢᴀʙᴄᴅᴇғɢʜɪᴊᴋʟᴍɴᴏᴘǫʀsᴛᴜᴠᴡxʏᴢ0123456789";
    
    public static String toSmallCaps(String text) {
        StringBuilder result = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = NORMAL.indexOf(c);
            if (index >= 0 && index < SMALLCAPS.length()) {
                result.append(SMALLCAPS.charAt(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
    
    public static String format(String message) {
        return message.replace("&", "§");
    }
}
