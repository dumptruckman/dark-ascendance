package com.dumptruckman.darkascendance.network.messages;

import com.artemis.Component;
import com.artemis.Entity;
import com.dumptruckman.darkascendance.network.NetworkEntity;

public class ComponentMessage extends Message {

    private Component component;

    public ComponentMessage component(Component component) {
        this.component = component;
        return this;
    }

    public Component getComponent() {
        return component;
    }
}
