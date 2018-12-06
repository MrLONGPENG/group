package com.lveqia.cloud.zuul.service.impl;

import com.lveqia.cloud.common.config.Constant;
import com.lveqia.cloud.common.exception.BaseException;
import com.lveqia.cloud.common.exception.ParamException;
import com.lveqia.cloud.common.util.StringUtil;
import com.lveqia.cloud.zuul.mapper.SysMenuMapper;
import com.lveqia.cloud.zuul.model.SysMenu;
import com.lveqia.cloud.zuul.model.SysRole;
import com.lveqia.cloud.zuul.objeck.vo.AddMenuVo;
import com.lveqia.cloud.zuul.objeck.vo.MenuVo;
import com.lveqia.cloud.zuul.objeck.vo.MetaVo;
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
    public boolean addMenu(long uid, AddMenuVo addVo) throws BaseException {
        if (uid != 1) throw new ParamException("必须为Admin才能添加按钮");
        return sysMenuMapper.insert(checkAddVo(addVo));
    }

    private SysMenu checkAddVo(AddMenuVo addVo) throws ParamException {
        if (null == addVo.getParentId()) {//当前菜单为一级菜单
            if (!"/home".equals(addVo.getPath()) || !"/".equals(addVo.getUrl())
                    || !"Home".equals(addVo.getComponent()))
                throw new ParamException("主菜单格式为{url:/ path:/home component:Home}");
        } else {
            if (sysMenuMapper.isExistPath(addVo.getPath()) > 0) throw new ParamException("当前路径已存在,请重新输入");
            SysMenu model = sysMenuMapper.getSysMenuById(addVo.getParentId());
            if (model == null) throw new ParamException("该菜单不存在,请重新选择");
            if (null == model.getParentId()) {//二级菜单
                if (getCount(addVo.getPath()) != 2) throw new ParamException("次菜单路径输入错误!");
            } else {//三级菜单
                if (getCount(addVo.getPath()) != 3 && addVo.getPath().startsWith(model.getPath()))
                    throw new ParamException("按钮路径输入错误!");
            }
        }
        return bindSysMenu(addVo);
    }

    /**
     * 统计path中"/"出现的次数
     */
    private int getCount(String text) throws ParamException {
        if (!text.startsWith("/")) throw new ParamException("Path必须以'/'开头");
        if (text.endsWith("/")) throw new ParamException("Path不能以'/'结尾");
        String[] arr = text.split("/");
        for (int i = 1; i < arr.length; i++) {
            if (StringUtil.isEmpty(arr[i])) throw new ParamException("Path路径异常");
        }
        return arr.length - 1;
    }


    private SysMenu bindSysMenu(AddMenuVo addVo) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setComponent(addVo.getComponent());
        sysMenu.setIconCls(addVo.getIconCls());
        sysMenu.setUrl(addVo.getUrl());
        sysMenu.setPath(addVo.getPath());
        sysMenu.setName(addVo.getName());
        sysMenu.setRequireAuth(true);
        return sysMenu;
    }


    @Override
    public List<MenuVo> getMenusByUserId(Long id) {
        if (id == 1) return getMenusByAdmin();
        List<SysMenu> list = sysMenuMapper.getMenusByUserId(id);
        Map<Integer, MenuVo> set = new LinkedHashMap<>();
        Map<Integer, List<MenuVo>> map = new LinkedHashMap<>();
        list.forEach(sysMenu -> addLinkMap(set, map, toMenuVo(sysMenu)));
        return new ArrayList<>(set.values());
    }

    private void addLinkMap(Map<Integer, MenuVo> set, Map<Integer, List<MenuVo>> map, MenuVo menuVo) {
        if (menuVo.getParentId() == null) {
            set.put(menuVo.getId(), menuVo);
        } else {
            MenuVo parent  = toMenuVo(sysMenuMapper.findById(menuVo.getParentId()));
            if(map.containsKey(parent.getId())){
                List<MenuVo> list = map.get(parent.getId());
                if(list.stream().noneMatch(vo-> vo.getId().equals(menuVo.getId()))){
                    map.get(parent.getId()).add(menuVo);
                }
            }else {
                map.put(parent.getId(), new ArrayList<>(Collections.singletonList(menuVo)));
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
        tree.setUrl(sysMenu.getUrl());
        tree.setSort(sysMenu.getSort());
        tree.setMeta(toMetaVo(sysMenu));
        return tree;
    }

    private MetaVo toMetaVo(SysMenu menu) {
        MetaVo metaVo = new MetaVo();
        metaVo.setKeepAlive(menu.getKeepAlive() != null ? menu.getKeepAlive() : false);
        metaVo.setRequireAuth(menu.getRequireAuth() != null ? menu.getRequireAuth() : false);
        metaVo.setRoles(toRoles(menu.getRoles()));
        return metaVo;
    }

    private List<String> toRoles(List<SysRole> roles) {
        List<String> list = new ArrayList<>();
        list.add(Constant.ROLE_ADMIN.substring(5));
        if (roles == null) return list;
        for (SysRole sysRole : roles) {
            list.add(sysRole.getName().substring(5));
        }
        return list;
    }

    private List<MenuVo> getMenusByAdmin() {
        MenuVo tree;
        List<MenuVo> children;
        List<MenuVo> trees = new ArrayList<>();
        List<SysMenu> main = sysMenuMapper.getMainMenus(); // 获取一级菜单
        List<SysMenu> list = sysMenuMapper.getMenusByAdmin();
        for (SysMenu sysMenu : main) {
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
            if (parentId.equals(sysMenu.getParentId())) {
                tree = toMenuVo(sysMenu);
                tree.setChildren(getChildren(list, sysMenu.getId()));
                trees.add(tree);
            }
        }
        return trees;
    }

}
