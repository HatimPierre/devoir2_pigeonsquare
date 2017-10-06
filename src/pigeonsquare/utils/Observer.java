package pigeonsquare.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public interface Observer {
    void receive_msg(Message msg);
}
