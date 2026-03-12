package com.gildedrose;

public class AgedBrie extends ItemWrapper {
    public AgedBrie(int sellIn, int quality) {
        super("Aged Brie", sellIn, quality);
    }

    public void update(){
        if (this.quality < 50) {
            this.quality++;
        }
        this.sellIn--;

        if (this.sellIn < 0 && this.quality < 50) {
            this.quality++;
        }
    }

}
