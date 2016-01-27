package com.mygdx.game.invent;

import com.badlogic.gdx.utils.Array;

public class Slot {

    private enumPeces item;

    private int amount;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public Slot(enumPeces item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public boolean isEmpty() {
        return item == null || amount <= 0;
    }

    public interface SlotListener {

        /**
        * Will be called whenever the slot has changed.
        */
        void hasChanged(Slot slot);

    }  
    
    public boolean add(enumPeces item, int amount) {
        if (this.item == item || this.item == null) {
            this.item = item;
            this.amount += amount;
            notifyListeners();
            return true;
        }

        return false;
    }

    public boolean take(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            if (this.amount == 0) {
                item = null;
            }
            notifyListeners();
            return true;
        }

        return false;
    }

    public void addListener(SlotListener slotListener) {
        slotListeners.add(slotListener);
    }

    public void removeListener(SlotListener slotListener) {
        slotListeners.removeValue(slotListener, true);
    }

    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    public enumPeces getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Slot[" + item + ":" + amount + "]";
    }
}