package com.gildedrose;

public class Default extends ItemWrapper {
    public Default(String name, int sellIn, int quality) {
        super(name, sellIn, quality);
    }

    public void update(){
        if (this.quality > 0) {
            this.quality--;
        }
        this.sellIn--;

        if (this.sellIn < 0 && this.quality > 0) {
            this.quality--;
        }
    }
}
