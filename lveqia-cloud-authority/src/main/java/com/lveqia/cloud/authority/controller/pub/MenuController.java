package com.lveqia.cloud.authority.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pub/menu")
public class MenuController {
//    @Autowired
//    private MenuService menuService;
//    @Autowired
//    private SysBackService sysBackService;
//
//    @RequestMapping(value = "/list")
//    public String list(Model model, MenuInPacket inPacket) throws Exception {
//        PageInfo<SysMenu> page = this.menuService.queryForDataGrid(inPacket);
//        model.addAttribute("page", page);
//        model.addAttribute("sysbackList", this.sysBackService.searchVOByMap(new SysBackInPacket()));
//        return "pub/menu/list";
//    }
//
//    @RequestMapping(value = "/query")
//    @ResponseBody
//    public PageInfo<SysMenu> query(MenuInPacket inPacket) {
//        PageInfo<SysMenu> list = null;
//        list = menuService.queryForDataGrid(inPacket);
//        return list;
//    }
//
//    @RequestMapping(value = "/edit")
//    public String edit(Integer id, ModelMap model) throws Exception {
//        if (id != null) {
//            model.addAttribute("dto", this.menuService.findMenuByPrimaryKey(id));
//        } else {
//            model.addAttribute("dto", new SysMenu());
//        }
//        model.addAttribute("sysbackList", this.sysBackService.searchVOByMap(new SysBackInPacket()));
//        return "pub/menu/edit";
//    }
//
//    @RequestMapping(value = "/save")
//    @ResponseBody
//    public Map<String, Object> save(@RequestBody SysMenu entity) {
//        return this.menuService.saveOrUpdate(entity);
//    }
//
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public Map<String, Object> del(Integer[] ids) {
//        return this.menuService.batchDeleteMenu(ids);
//    }
//
//    @RequestMapping(value = "/queryByMap")
//    @ResponseBody
//    public List<SysMenu> queryByMap(MenuInPacket inPacket) {
//        List<SysMenu> list = null;
//        list = menuService.queryMenuByMap(inPacket);
//        return list;
//    }
}
