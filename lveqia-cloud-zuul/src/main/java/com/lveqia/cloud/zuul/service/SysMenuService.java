package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVO;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysMenuService {

    List<SysMenu> getAllMenuByLength();

    List<MenuVO> getMenusByUserId(Long id);
}
