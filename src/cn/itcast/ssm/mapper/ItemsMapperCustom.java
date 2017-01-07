package cn.itcast.ssm.mapper;

import java.util.List;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.utils.Page;


public interface ItemsMapperCustom extends ItemsMapper{
	public List<ItemsCustom> findItemsList(ItemsQueryVo vo)throws Exception;
	public List<ItemsCustom> findItemsListPage(Page vo)throws Exception;
}
