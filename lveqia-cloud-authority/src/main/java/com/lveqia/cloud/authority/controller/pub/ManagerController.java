package com.lveqia.cloud.authority.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pub/manager")
public class ManagerController {
//    @Autowired
//    private SysManagerService managerService;
//
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private ProvinceCityService provinceCityService;
//    @Autowired
//    private SysBackService sysBackService;
//
//    @RequestMapping(value = "/list")
//    public String list(Model model) {
//        return "pub/manager/list";
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public String edit(Integer id, Model model, HttpServletRequest request) throws AuthorityException {
//        if (id != null) {
//            model.addAttribute("dto", this.managerService.selectByPrimaryKey(id));
//        } else {
//            model.addAttribute("dto", new SysManager());
//        }
//        List<SysRole> roleList = this.roleService.queryForList();
//        if (roleList.size() > 0) {
//            model.addAttribute("roleList", roleList);
//        }
//        model.addAttribute("sysbackList", this.sysBackService.searchVOByMap(new SysBackInPacket()));
//        model.addAttribute("provinceList", this.provinceCityService.queryCityForList(0));
//        return "/pub/manager/edit";
//    }
//
//    @RequestMapping(value = "/query")
//    @ResponseBody
//    public PageInfo<ManagerVO> query(ManagerInPacket inPacket, @ModelAttribute(value = "dto") SysManager dto) {
//        return this.managerService.queryManagerForList(inPacket);
//    }
//
//    @RequestMapping(value = "/queryForList")
//    @ResponseBody
//    public List<ManagerVO> queryForList(ManagerInPacket inPacket, @ModelAttribute(value = "dto") SysManager dto) {
//        return this.managerService.queryForList(inPacket);
//    }
//
//    @RequestMapping(value = "/save")
//    @ResponseBody
//    public Map<String, Object> save(@ModelAttribute(value = "dto") SysManager dto) throws Exception {
//        return this.managerService.save(dto);
//    }
//
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public Map<String, Object> del(Integer[] ids) {
//        return this.managerService.batchDelete(ids);
//    }


}
