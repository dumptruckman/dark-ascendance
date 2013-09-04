package com.dumptruckman.darkascendance.shared.messages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AcknowledgementBatch implements Iterable<Acknowledgement> {

    private List<Acknowledgement> acknowledgements = new ArrayList<Acknowledgement>();

    @Override
    public Iterator<Acknowledgement> iterator() {
        return acknowledgements.iterator();
    }

    public void addAcknowledgement(Acknowledgement acknowledgement) {
        acknowledgements.add(acknowledgement);
    }

    public int size() {
        return acknowledgements.size();
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
