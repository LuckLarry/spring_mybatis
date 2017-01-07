package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.ssm.mapper.ItemsMapperCustom;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;
import cn.itcast.utils.Page;


@Service
public class ItemsServiceImpl implements ItemsService{
	@Autowired
	private ItemsMapperCustom itemsMapperCustom;
	
	public List<ItemsCustom> findItemsList(ItemsQueryVo vo)throws Exception {
		List<ItemsCustom> list= itemsMapperCustom.findItemsList(vo);
		return list;
	}

	@Override
	public List<ItemsCustom> findItemsListPage(Page page) throws Exception {
		return itemsMapperCustom.findItemsListPage(page);
	}

}
