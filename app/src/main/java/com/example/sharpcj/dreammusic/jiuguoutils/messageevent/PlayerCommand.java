package com.example.sharpcj.dreammusic.jiuguoutils.messageevent;

/**
 * Created by joy on 2016/7/19.
 */
public class PlayerCommand {
    private int command; //100表示暂停，101表示播放

    public PlayerCommand(int command) {
        this.command = command;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }
}
