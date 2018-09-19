package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysMenuMapper;
import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVO;
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
    public List<SysMenu> getAllMenu() {
        return sysMenuMapper.findListAll();
    }

    @Override
    public List<SysMenu> getAllMenuByLength() {
        return sysMenuMapper.getAllMenuByLength();
    }

    @Override
    public List<MenuVO> getMenusByUserId(Long id) {
        MenuVO tree;
        List<MenuVO> children;
        List<MenuVO> trees = new ArrayList<>();
        List<SysMenu> list = id == 1 ? sysMenuMapper.getMenusByAdmin() : sysMenuMapper.getMenusByUserId(id);
        for (SysMenu sysMenu : list){
            if (sysMenu.getParentId() == null){
                tree = new MenuVO();
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
        }
        return trees;
    }
    private List<MenuVO> getChildren(List<SysMenu> list, Integer parentId) {
        MenuVO tree;
        List<MenuVO> trees = new ArrayList<>();
        for (SysMenu sysMenu : list) {
            if (parentId.equals(sysMenu.getParentId())){
                tree = new MenuVO();
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
