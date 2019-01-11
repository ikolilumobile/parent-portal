package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 27/08/2018.
 */

public class SubcriptionItem {
    private int id;
    private String itemName;
    private String itemCost;
    private String itemDesc;

    public SubcriptionItem(int id, String itemName, String itemCost, String itemDesc) {
        this.id = id;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemDesc = itemDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCost() {
        return itemCost;
    }

    public void setItemCost(String itemCost) {
        this.itemCost = itemCost;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
