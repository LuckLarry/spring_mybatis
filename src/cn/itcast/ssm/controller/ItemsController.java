package cn.itcast.ssm.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

@Controller
@RequestMapping("/items")
public class ItemsController {
	
	@Autowired
	private ItemsService itemsService;
	
	@RequestMapping("/listitems")
	public ModelAndView queryItems(String name,Integer pageh)throws Exception{
		ItemsQueryVo vo= new ItemsQueryVo();
		ItemsCustom custom = new ItemsCustom();
		custom.setName(name);
		vo.setItemsCustom(custom);
		vo.setPage(pageh);
		vo.setRows(2);
//		List<ItemsCustom> itemList = itemsService.findItemsListPage(page);
		List<ItemsCustom> itemList = itemsService.findItemsListPage(vo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList",itemList);
		modelAndView.setViewName("items/itemsList");
//		StatementHandler a;
//		MetaObject.forObject(object, objectFactory, objectWrapperFactory)
		return modelAndView;
	}
}
