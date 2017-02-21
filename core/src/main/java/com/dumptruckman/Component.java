package com.dumptruckman;

public class Component extends recs.Component implements Cloneable {

    public Component clone() {
        try {
            return (Component) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
