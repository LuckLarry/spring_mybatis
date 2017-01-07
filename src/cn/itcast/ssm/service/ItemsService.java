package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.utils.Page;

public interface ItemsService {
	public List<ItemsCustom> findItemsList(ItemsQueryVo vo)throws Exception;
	public List<ItemsCustom> findItemsListPage(Page page)throws Exception;
}
