package com.dumptruckman.darkascendance.shared.messages;

import com.badlogic.gdx.utils.ObjectSet;

import java.util.Iterator;

public class AcknowledgementBatch implements Iterable<Acknowledgement> {

    private ObjectSet<Acknowledgement> acknowledgements = new ObjectSet<Acknowledgement>();

    @Override
    public Iterator<Acknowledgement> iterator() {
        return acknowledgements.iterator();
    }

    public void addAcknowledgement(Acknowledgement acknowledgement) {
        acknowledgements.add(acknowledgement);
    }

    public int size() {
        return acknowledgements.size;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Acknowledgement ack : acknowledgements) {
            if (buffer.length() > 0) {
                buffer.append(", ");
            }
            buffer.append(ack);
        }
        return "AcknowledgementBatch{" +
                "acknowledgements=[" + buffer +
                "]}";
    }
}
