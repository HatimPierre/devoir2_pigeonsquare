package pigeonsquare.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Message {
    public List<String> commands;
    public int x, y;
    public UUID id;

    public Message(){
        commands = new ArrayList<>();
        int x = 0;
        int y = 0;
        id = null;
    }
}
