package com.lveqia.cloud.authority.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/pub/role")
public class RoleController {
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private MenuService menuService;
//    @Autowired
//    private SysBackService sysBackService;
//
//
//    @RequestMapping(value = "/list")
//    public String list(RoleInPacket inPacket) {
//        return "/pub/role/list";
//    }
//
//    @RequestMapping(value = "/edit")
//    public String edit(Integer id, Model model) {
//        if (id != null) {
//            this.roleService.getRoleOperate(model, id);
//        } else {
//            List<JSONObject> list = new ArrayList<>();
//            model.addAttribute("dto", new SysRole());
//            model.addAttribute("ops", list);
//        }
//        model.addAttribute("sysbackList", this.sysBackService.searchVOByMap(new SysBackInPacket()));
//        model.addAttribute("menus", menuService.getAll());
//        return "/pub/role/edit";
//    }
//
//    @RequestMapping(value = "/query")
//    @ResponseBody
//    public PageInfo<SysRole> query(RoleInPacket inPacket) {
//        return this.roleService.queryRoleForList(inPacket);
//    }
//
//    @RequestMapping(value = "/save")
//    @ResponseBody
//    public Map<String, Object> save(Integer[] operates, @ModelAttribute("dto") SysRole dto) {
//        return this.roleService.save(dto, operates);
//    }
//
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public Map<String, Object> del(Integer[] ids) {
//        return this.roleService.batchDelete(ids);
//    }
//

}
