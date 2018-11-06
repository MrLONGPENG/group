package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVo;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysMenuService {

    List<SysMenu> getAllMenuByLength();

    List<MenuVo> getMenusByUserId(Long id);
}
