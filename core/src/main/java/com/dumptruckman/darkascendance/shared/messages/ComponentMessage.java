package com.dumptruckman.darkascendance.shared.messages;

public class ComponentMessage extends Message {

    private Object component;

    public ComponentMessage component(Object component) {
        this.component = component;
        return this;
    }

    public Object getComponent() {
        return component;
    }
}