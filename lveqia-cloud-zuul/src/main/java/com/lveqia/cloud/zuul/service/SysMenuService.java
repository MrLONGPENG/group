package com.lveqia.cloud.zuul.service;

import com.lveqia.cloud.zuul.model.SysMenu;

import java.util.List;

/**
 * @author leolaurel
 */
public interface SysMenuService {
    String test();

    List<SysMenu> getAllMenu();

    List<SysMenu> getAllMenuByLength();
}
