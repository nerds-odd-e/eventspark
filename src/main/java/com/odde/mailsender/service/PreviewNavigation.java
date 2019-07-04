package com.odde.mailsender.service;

public class PreviewNavigation {
    private final int index;
    private final int maxIndex;

    private int getIndex() {
        return index;
    }

    public PreviewNavigation(int index, int maxIndex) {
        this.index = index;
        this.maxIndex = maxIndex;
    }

    public boolean canShowNext() {
        return getIndex() < this.maxIndex;
    }

    public boolean canShowPrevious() {
        return getIndex() > 0;
    }

    public int getNextIndex() {
        return getIndex() + 1;
    }

    public int getPreviousIndex() {
        return getIndex() - 1;
    }
}
