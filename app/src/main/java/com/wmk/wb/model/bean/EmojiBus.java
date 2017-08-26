package com.wmk.wb.model.bean;

/**
 * Created by wmk on 2017/7/30.
 */

public class EmojiBus {
    private int emoji;
    private String emojiName;

    public String getEmojiName() {
        return emojiName;
    }

    public void setEmojiName(String emojiName) {
        this.emojiName = emojiName;
    }

    public EmojiBus(int emoji, String emojiName) {
        this.emoji = emoji;
        this.emojiName = emojiName;
    }

    public int getEmoji() {
        return emoji;
    }

    public void setEmoji(int emoji) {
        this.emoji = emoji;
    }
}
