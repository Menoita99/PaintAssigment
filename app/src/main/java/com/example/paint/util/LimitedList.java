package com.example.paint.util;

import java.util.ArrayList;
import java.util.Collection;

public class LimitedList<E> extends ArrayList<E> {

    private final int maxSize;

    public LimitedList(int maxSize) {
        this.maxSize = maxSize;
    }


    public LimitedList(Collection<E> collection, int maxSize) {
        this.maxSize = maxSize;
        this.addAll(collection);
    }


    public LimitedList(Collection<E> collection) {
        this.maxSize = collection.size();
        this.addAll(collection);
    }


    @Override
    public boolean add(E e) {
        if(size()>=maxSize)
            removeFirst();
        return super.add(e);
    }

    private void removeFirst() {
        if(size()>0)
            remove(0);
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean output = super.addAll(c);
        while(size()>maxSize)
            removeFirst();
        return output;
    }


    public boolean isFull() {
        return size() == maxSize;
    }
}
