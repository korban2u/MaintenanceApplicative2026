package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    @Test
    void foo() {
        ItemWrapper[] items = new ItemWrapper[] { new Default("foo", 0, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals("foo", app.items[0].name);
    }

    @Test
    void testNormalItemBeforeSellDate() {
        ItemWrapper[] items = new ItemWrapper[] { new Default("foo", 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(19, app.items[0].quality);
    }

    @Test
    void testNormalItemOnOrAfterSellDate() {
        ItemWrapper[] items = new ItemWrapper[] { new Default("foo", 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(18, app.items[0].quality);
    }

    @Test
    void testNormalItemQualityNeverNegative() {
        ItemWrapper[] items = new ItemWrapper[] { new Default("foo", 10, 0) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void testAgedBrieBeforeSellDate() {
        ItemWrapper[] items = new ItemWrapper[] { new AgedBrie(10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    void testAgedBrieAfterSellDate() {
        ItemWrapper[] items = new ItemWrapper[] { new AgedBrie( 0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);
    }

    @Test
    void testAgedBrieQualityNeverExceedsFifty() {
        ItemWrapper[] items = new ItemWrapper[] { new AgedBrie(10, 50) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void testSulfurasNeverChanges() {
        ItemWrapper[] items = new ItemWrapper[] { new Sulfuras(10, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(10, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void testSulfurasAfterSellDateNeverChanges() {
        ItemWrapper[] items = new ItemWrapper[] { new Sulfuras(-1, 80) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(80, app.items[0].quality);
    }

    @Test
    void testBackstagePassesFarAway() {
        ItemWrapper[] items = new ItemWrapper[] { new Backstage(15, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(14, app.items[0].sellIn);
        assertEquals(21, app.items[0].quality);
    }

    @Test
    void testBackstagePassesTenDaysOrLess() {
        ItemWrapper[] items = new ItemWrapper[] { new Backstage( 10, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(9, app.items[0].sellIn);
        assertEquals(22, app.items[0].quality);
    }

    @Test
    void testBackstagePassesFiveDaysOrLess() {
        ItemWrapper[] items = new ItemWrapper[] { new Backstage(5, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(23, app.items[0].quality);
    }

    @Test
    void testBackstagePassesAfterConcert() {
        ItemWrapper[] items = new ItemWrapper[] { new Backstage(0, 20) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(-1, app.items[0].sellIn);
        assertEquals(0, app.items[0].quality);
    }

    @Test
    void testBackstagePassesMaxQualityCap() {
        ItemWrapper[] items = new ItemWrapper[] { new Backstage(5, 49) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(4, app.items[0].sellIn);
        assertEquals(50, app.items[0].quality);
    }

    @Test
    void testItemToString() {
        Item item = new Default("Elixir of the Mongoose", 5, 7);
        assertEquals("Elixir of the Mongoose, 5, 7", item.toString());
    }

    @Test
    void testEmptyArrayDoesNotCrash() {
        ItemWrapper[] items = new ItemWrapper[] {};
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(0, app.items.length);
    }

    @Test
    void testQualityUpper50(){
        ItemWrapper[] items = new ItemWrapper[] { new AgedBrie(-5, 52) };
        GildedRose app = new GildedRose(items);
        app.updateQuality();
        assertEquals(52,app.items[0].quality);
    }

}
