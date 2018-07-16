package com.lveqia.cloud.authority.controller.pub;


import org.springframework.stereotype.Controller;

@Controller
public class IndexController {
//    @Autowired
//    MenuService menuService;
//    @Autowired
//    RoleOperateService roleOperateService;
//
//    @RequestMapping("/op_{oper}_{id}")
//    public String op(@PathVariable String oper, @PathVariable Integer id, Model model) {
//        SysMenu menu = this.menuService.findMenuByPrimaryKey(id);// 获取对应的菜单对象
//        if (menu != null) {
//            OperateInPacket inPacket = new OperateInPacket();
//            inPacket.setRole(SessionUtil.getSession().getRole());
//            inPacket.setMenu(id);
//
//            List<SysOperateVO> op = this.roleOperateService.queryOperateForList(inPacket);
//            if (op.isEmpty()) return "denied";//无权限
//            Session session = SecurityUtils.getSubject().getSession();
//            session.setAttribute("operates",op);
//            session.setAttribute("OP", op.get(0));
//            session.setAttribute("MENU",this.menuService.findMenuByPrimaryKey(id));
////            model.addAttribute("operates", op);
////            model.addAttribute("OP", op.get(0));
////            model.addAttribute("MENU", this.menuService.findMenuByPrimaryKey(id));// 获取对应的菜单对象
//            System.out.println("forward:" + menu.getChannel() + "/" + oper);
//            return "redirect:" + menu.getChannel() + "/" + oper;
//        }
//        return "404";
//    }
}
