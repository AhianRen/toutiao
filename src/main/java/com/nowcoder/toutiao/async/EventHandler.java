package com.nowcoder.toutiao.async;

import java.util.List;

public interface EventHandler {

    void doHandler(EventModel eventModel);
    List<EventType> getSupportType();

















}
