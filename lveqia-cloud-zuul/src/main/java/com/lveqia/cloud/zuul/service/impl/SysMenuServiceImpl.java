package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysMenuMapper;
import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVo;
import com.lveqia.cloud.zuul.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leolaurel
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {


    private final SysMenuMapper sysMenuMapper;

    @Autowired
    public SysMenuServiceImpl(SysMenuMapper sysMenuMapper) {
        this.sysMenuMapper = sysMenuMapper;
    }


    @Override
    public List<SysMenu> getAllMenuByLength() {
        return sysMenuMapper.getAllMenuByLength();
    }

    @Override
    public List<MenuVo> getMenusByUserId(Long id) {
        MenuVo tree;
        List<MenuVo> children;
        List<MenuVo> trees = new ArrayList<>();
        List<SysMenu> main = sysMenuMapper.getMainMenus(); // 获取一级菜单
        List<SysMenu> list = id == 1 ? sysMenuMapper.getMenusByAdmin() : sysMenuMapper.getMenusByUserId(id);
        for (SysMenu sysMenu : main){
            tree = new MenuVo(); // 当一级菜单拥有有权限的子菜单时候，放出一级菜单
            children = getChildren(list, sysMenu.getId());
            if (children != null && children.size() > 0) {
                tree.setId(sysMenu.getId());
                tree.setPath(sysMenu.getPath());
                tree.setName(sysMenu.getName());
                tree.setIconCls(sysMenu.getIconCls());
                tree.setComponent(sysMenu.getComponent());
                tree.setMeta(sysMenu.getKeepAlive(), sysMenu.getRequireAuth());
                tree.setChildren(children);
                trees.add(tree);
            }

        }
        return trees;
    }

    private List<MenuVo> getChildren(List<SysMenu> list, Integer parentId) {
        MenuVo tree;
        List<MenuVo> trees = new ArrayList<>();
        for (SysMenu sysMenu : list) {
            if (parentId.equals(sysMenu.getParentId())){
                tree = new MenuVo();
                tree.setId(sysMenu.getId());
                tree.setName(sysMenu.getName());
                tree.setPath(sysMenu.getPath());
                tree.setIconCls(sysMenu.getIconCls());
                tree.setComponent(sysMenu.getComponent());
                tree.setMeta(sysMenu.getKeepAlive(), sysMenu.getRequireAuth());
                trees.add(tree);
            }
        }
        return trees;
    }

}
