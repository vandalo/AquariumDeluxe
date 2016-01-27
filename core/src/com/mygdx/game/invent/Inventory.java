package com.mygdx.game.invent;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Inventory {

    private Array<Slot> slots;

    public Inventory() {
        slots = new Array<Slot>(25);
        for (int i = 0; i < 25; i++) {
            slots.add(new Slot(null, 0));
        }

        // create some random items
        for (Slot slot : slots) {
            slot.add(enumPeces.values()[MathUtils.random(0, enumPeces.values().length - 1)], 1);
        }

        // create a few random empty slots
        for (int i = 0; i < 3; i++) {
            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
            randomSlot.take(randomSlot.getAmount());
        }
    }

    public int checkInventory(enumPeces item) {
        int amount = 0;

        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }

    public boolean store(enumPeces item, int amount) {
        // first check for a slot with the same item type
        Slot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item, amount);
            return true;
        } else {
            // now check for an available empty slot
            Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(item, amount);
                return true;
            }
        }

        // no slot to add
        return false;
    }

    public Array<Slot> getSlots() {
        return slots;
    }

    private Slot firstSlotWithItem(enumPeces item) {
        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }

        return null;
    }

}