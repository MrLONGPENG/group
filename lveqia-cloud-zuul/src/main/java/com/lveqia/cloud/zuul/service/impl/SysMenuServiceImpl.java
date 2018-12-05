package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.zuul.mapper.SysMenuMapper;
import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.objeck.vo.MenuVo;
import com.lveqia.cloud.zuul.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

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
        if(id == 1) return getMenusByAdmin();
        List<SysMenu> list = sysMenuMapper.getMenusByUserId(id);
        Map<Integer, MenuVo> set = new LinkedHashMap<>();
        Map<Integer, List<MenuVo>> map = new LinkedHashMap<>();
        list.forEach(sysMenu -> addLinkMap(set, map, toMenuVo(sysMenu)));
        return new ArrayList<>(set.values());
    }

    private void addLinkMap(Map<Integer, MenuVo> set, Map<Integer, List<MenuVo>> map, MenuVo menuVo) {
        if(menuVo.getParentId() == null) {
            set.put(menuVo.getId(), menuVo);
        }else{
            MenuVo parent  = toMenuVo(sysMenuMapper.findById(menuVo.getParentId()));
            if(map.containsKey(menuVo.getParentId())){
                map.get(menuVo.getParentId()).add(menuVo);
            }else {
                map.put(menuVo.getParentId(), new ArrayList<>(Collections.singletonList(menuVo)));
            }
            parent.setChildren(map.get(parent.getId()));
            addLinkMap(set, map, parent);
        }
    }


    private MenuVo toMenuVo(SysMenu sysMenu) {
        MenuVo tree = new MenuVo();
        tree.setId(sysMenu.getId());
        tree.setPath(sysMenu.getPath());
        tree.setName(sysMenu.getName());
        tree.setIconCls(sysMenu.getIconCls());
        tree.setParentId(sysMenu.getParentId());
        tree.setComponent(sysMenu.getComponent());
        tree.setMeta(sysMenu.getKeepAlive(), sysMenu.getRequireAuth());
        return tree;
    }

    private List<MenuVo> getMenusByAdmin() {
        MenuVo tree;
        List<MenuVo> children;
        List<MenuVo> trees = new ArrayList<>();
        List<SysMenu> main = sysMenuMapper.getMainMenus(); // 获取一级菜单
        List<SysMenu> list = sysMenuMapper.getMenusByAdmin();
        for (SysMenu sysMenu : main){
            children = getChildren(list, sysMenu.getId());
            if (children != null && children.size() > 0) {
                tree = toMenuVo(sysMenu); // 当一级菜单拥有有权限的子菜单时候，放出一级菜单
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
                tree = toMenuVo(sysMenu);
                tree.setChildren(getChildren(list, sysMenu.getId()));
                trees.add(tree);
            }
        }
        return trees;
    }

}
