package com.crewcloud.apps.crewnotice.base;

import com.crewcloud.apps.crewnotice.event.MenuEvent;

/**
 * Created by mb on 3/26/16
 */
public class BaseEvent {
    public BaseEvent() {

    }

    public abstract class EventType {
        public static final int MENU = 1;
        public static final int SETTING = 2;
        public static final int NOTICE_DETAIL = 3;
    }

    private int type;
    private MenuEvent menuEvent;

    public BaseEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public MenuEvent getMenuEvent() {
        return menuEvent;
    }

    public void setMenuEvent(MenuEvent menuEvent) {
        this.menuEvent = menuEvent;
    }
}
