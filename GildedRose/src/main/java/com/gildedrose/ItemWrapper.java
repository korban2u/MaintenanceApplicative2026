package com.gildedrose;

public abstract class ItemWrapper extends Item {
    public ItemWrapper(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public abstract void update();
}
