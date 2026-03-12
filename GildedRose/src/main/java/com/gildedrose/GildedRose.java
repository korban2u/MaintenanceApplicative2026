package com.gildedrose;

class GildedRose {
    ItemWrapper[] items;

    public GildedRose(ItemWrapper[] items) {
        this.items = items;
    }

    public void updateQuality() {
        for (ItemWrapper item : items) {
            item.update();
        }
    }

}
