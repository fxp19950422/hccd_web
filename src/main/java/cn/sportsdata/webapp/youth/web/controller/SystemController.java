package cn.sportsdata.webapp.youth.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.sportsdata.webapp.youth.common.bo.UserPrivilege;
import cn.sportsdata.webapp.youth.common.constants.Constants;
import cn.sportsdata.webapp.youth.common.vo.Menu;
import cn.sportsdata.webapp.youth.common.vo.OrgVO;
import cn.sportsdata.webapp.youth.common.vo.login.LoginVO;
import cn.sportsdata.webapp.youth.common.vo.privilege.PrivilegeVO;
import cn.sportsdata.webapp.youth.service.account.AccountService;
import cn.sportsdata.webapp.youth.service.utraining.UtrainingService;

@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

	private static final Logger log = Logger.getLogger(SystemController.class);

	@Autowired
	private MessageSource messages;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UtrainingService utrainingService;
	
	@RequestMapping("/system/index")
	public Object index(HttpServletRequest request, HttpServletResponse resp, String menuCode) {

		LoginVO user = getCurrentUser(request);
		
//		List<OrgVO> orgs = accountService.getOrgsByAccount(user.getId());
		
		boolean needShowSwitch = false;
//		if (orgs.size() > 1){
//			needShowSwitch = true;
//		}
		
		UserPrivilege userPrivilegeMgr = getCurrentUserPrivilegeMgr(request);
		OrgVO orgVO = getCurrentOrg(request);
		String orgName = "";
		if (orgVO != null) {
			orgName = orgVO.getName();
		}

		user.setMenus(generateAllMenus(userPrivilegeMgr.getMenuPrivilegeVOs()));
		
		
		
		return new ModelAndView("/system/index").addObject("userMenu", user.getMenus()).
				addObject("user", user).addObject("needShowSwitch", needShowSwitch);
	}

	private Menu getMenuFromPrivilege(PrivilegeVO vo) {
		Menu menu = new Menu();
		menu.setCode(String.valueOf(vo.getId()));
		menu.setName(vo.getItemValue());
		menu.setIcon(vo.getIcon());
//		menu.setClassName(messages.getMessage(vo.getItemName() + "_class", new Object[] {}, "dashboard-menu", Locale.US));
		menu.setClassName("dashboard-menu");
		menu.setUrl(vo.getAction());
		Integer order = Constants.MENU_ORDER.get(vo.getItemName());
		int orderValue = 0;
		if (order != null) {
			orderValue = order.intValue();
		}
		menu.setOrder(orderValue);
		return menu;
	}

	private List<Menu> generateAllMenus(List<PrivilegeVO> privilegeVOs) {
		List<Menu> menuList = new ArrayList<Menu>();
		Map<Long, Menu> lookup = new HashMap<Long, Menu>();
		for (PrivilegeVO vo : privilegeVOs) {
			Menu menu = getMenuFromPrivilege(vo);
			lookup.put(vo.getId(), menu);
		}

		for (PrivilegeVO vo : privilegeVOs) {
			if (vo.getParentID() != 0) {
				if (lookup.get(vo.getParentID()).getSubMenus() == null) {
					lookup.get(vo.getParentID()).setSubMenus(new ArrayList<Menu>());
				}
				lookup.get(vo.getParentID()).getSubMenus().add(getMenuFromPrivilege(vo));
			} else {
				menuList.add(lookup.get(vo.getId()));
			}
		}
		return sortMenuList(menuList);
	}
	
	private List<Menu> sortMenuList(List<Menu> menuList) {
		if (menuList == null) {
			return menuList;
		}
		Collections.sort(menuList, new Comparator<Menu>() {
            public int compare(Menu arg0, Menu arg1) {
            	return (arg0.getOrder() < arg1.getOrder()) ? -1 : ((arg0.getOrder() == arg1.getOrder()) ? 0 : 1);
            }
        });
		for (int i = 0; i < menuList.size(); i++) {
			Menu menu = menuList.get(i);
			sortMenuList(menu.getSubMenus());
		}
		return menuList;
		
	}

	private List<Menu> generateAllMenus() {
		List<Menu> menus = new ArrayList<Menu>();
		Menu menu = new Menu();
		menu.setUrl("/system/system/test");
		menu.setName("首页");
		menus.add(menu);

		menu = new Menu();
		menu.setName("测试");
		List<Menu> submenus = new ArrayList<Menu>();

		Menu subMenu = new Menu();
		subMenu.setName("测试1");
		subMenu.setUrl("www.163.com");
		submenus.add(subMenu);

		subMenu = new Menu();
		subMenu.setName("测试2");
		subMenu.setUrl("www.163.com");
		submenus.add(subMenu);
		menu.setSubMenus(submenus);
		menus.add(menu);

		return menus;
	}

	@RequestMapping("/system/test")
	@ResponseBody
	public Object test(HttpServletRequest request, HttpServletResponse resp, String menuCode) {

		// UserVO user = getCurrentUser(request);
		// UserPrivilege userPrivilegeMgr = getCurrentUserPrivilegeMgr(request);
		//
		// user.setMenus(generateAllMenus(userPrivilegeMgr.getMenuPrivilegeVOs()));

		return new ModelAndView("/system/test");
	}
}
