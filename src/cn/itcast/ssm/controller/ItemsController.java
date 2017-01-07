package cn.itcast.ssm.controller;

import java.util.List;
import java.util.Properties;

import javax.interceptor.Interceptors;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;

@Controller
public class ItemsController {
	
	@Autowired
	private ItemsService itemsService;
	
	@RequestMapping("/listitems")
	public ModelAndView queryItems(String name)throws Exception{
		ItemsQueryVo vo= new ItemsQueryVo();
		ItemsCustom custom = new ItemsCustom();
		custom.setName(name);
		vo.setItemsCustom(custom);
		List<ItemsCustom> itemList = itemsService.findItemsList(vo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList",itemList);
		modelAndView.setViewName("items/itemsList");
//		StatementHandler a;
//		MetaObject.forObject(object, objectFactory, objectWrapperFactory)
		return modelAndView;
	}
	class myState implements Interceptor{

		public Object intercept(Invocation arg0) throws Throwable {
			// TODO Auto-generated method stub
			return null;
		}

		public Object plugin(Object arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		public void setProperties(Properties arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
