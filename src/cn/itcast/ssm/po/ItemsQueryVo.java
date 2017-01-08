package cn.itcast.ssm.po;

import cn.itcast.utils.Page;

public class ItemsQueryVo extends Page{
	
	private Items items;
	private ItemsCustom itemsCustom;
	public Items getItems() {
		return items;
	}
	public void setItems(Items items) {
		this.items = items;
	}
	public ItemsCustom getItemsCustom() {
		return itemsCustom;
	}
	public void setItemsCustom(ItemsCustom itemsCustom) {
		this.itemsCustom = itemsCustom;
	}
}
