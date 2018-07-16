package com.lveqia.cloud.authority.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author YaoGang
 * @Date 2017/6/28 17:49
 * @Description
 */
@Controller
@RequestMapping("/pub/sysback")
public class SysBackController {
//    @Autowired
//    private SysBackService sysBackService;
//
//    @RequestMapping(value = "/list")
//    public String list(Model model) {
//        return "pub/sysback/list";
//    }
//
//    @RequestMapping(value = "/edit", method = RequestMethod.POST)
//    public String edit(Integer id, Model model, HttpServletRequest request) throws AuthorityException {
//        if (id != null) {
//            model.addAttribute("dto", this.sysBackService.selectByPrimaryKey(id));
//        } else {
//            model.addAttribute("dto", new SysBack());
//        }
//        return "/pub/sysback/edit";
//    }
//
//    @RequestMapping(value = "/query")
//    @ResponseBody
//    public PageInfo<SysBackVO> query(@ModelAttribute(value = "inPacket") SysBackInPacket inPacket) {
//        return this.sysBackService.searchVOPage(inPacket);
//    }
//
//    @RequestMapping(value = "/save")
//    @ResponseBody
//    public Map<String, Object> save(@ModelAttribute(value = "dto") SysBack dto) throws Exception {
//        return this.sysBackService.save(dto);
//    }
//
//    @RequestMapping(value = "/remove")
//    @ResponseBody
//    public Map<String, Object> del(Integer[] ids) {
//        return this.sysBackService.batchDelete(ids);
//    }

}
