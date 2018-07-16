package com.lveqia.cloud.authority.controller.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LoginController {
//    Logger logger = Logger.getLogger(LoginController.class);
//
//    @Autowired
//    MenuService menuService;
//
//    @Autowired
//    RoleService roleService;
//
//
//    @Autowired
//    private SysManagerLogService managerlogService;
//
//    @Autowired
//    private SysManagerService managerService;
//
//
//
//    @Autowired
//    private DiscoveryClient client;
//
//    @Autowired
//    private RoleOperateService roleOperateService;
//
//    @Value("${backgroundCode}")
//    public String backCode;
//
//    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "text/html; charset=utf-8")
//    public String login(HttpServletRequest request) {
//        return "login";
//    }
//
//    @RequestMapping("main")
//    public String main() {
//        return "main";
//    }
//
//    @RequestMapping("logout")
//    public String logout(@RequestBody SysManager dto) {
//        Subject sub = SecurityUtils.getSubject();
//        sub.logout();
//
//        if(dto!=null) {
//            // 当验证都通过后，加入登录日志
//            SysManagerLog sysManagerLog = managerlogService.toSysManagerLog(dto);
//            sysManagerLog.setSysType(4);
//            managerlogService.save(sysManagerLog);
//        }
//        return "login";
//    }
//
//    @RequestMapping("logoutApp")
//    @ResponseBody
//    public Map<String, Object> logoutApp(@RequestBody SysManager dto) {
//        SysManager manager=  SessionUtil  .getSession();
//        if(manager!=null&&manager.getManagerid()==dto.getManagerid()) {
//            Subject sub = SecurityUtils.getSubject();
//            sub.logout();
//        }
//
//        // 当验证都通过后，加入登录日志
//        SysManagerLog sysManagerLog= managerlogService.toSysManagerLog(dto);
//        sysManagerLog.setSysType(4);
//        managerlogService.save(sysManagerLog);
//      return ResultType.getSuccessMap(null);
//    }
//
//
//    @RequestMapping("index")
//    public String index(Model model, HttpServletRequest request) {
//        String userName = (String) SecurityUtils.getSubject().getPrincipal();
//        if (userName == null) {
//            return "redirect:/";
//        }
//        SysManager sysManager = SessionUtil.getSession();
//        SysManagerVO sysManagerVO = new SysManagerVO();
//        sysManagerVO.setRole(sysManager.getRole());
//        sysManagerVO.setBackground(backCode);
//        model.addAttribute("list", menuService.listTree(sysManagerVO));
//
//        SysRole role = roleService.selectByPrimaryKey(Long.parseLong(SessionUtil.getSession().getRole().toString()));
//
//        request.getSession().setAttribute("roleCode", role != null ? role.getCode() : "");
//        return "index";
//    }
//
//    @ResponseBody
//    @RequestMapping(value = "thridLogin", method = RequestMethod.POST)
//    public Map<String, Object> thridLogin(@RequestBody SysManagerVO dto) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//        SysManager manager = new SysManager();
//        manager.setAccount(dto.getAccount());
//        manager.setPassword(dto.getPassword());
//        manager.setClientId(dto.getClientId());
//        manager.setSystems(dto.getSystems());
//        manager.setAndroidId(dto.getAndroidId());
//        manager.setSubType(dto.getSubType());
//        String result = this.login(request, response, manager);
//        if (result.equals("login")) {
//            return ResultType.getFailMap(Constent.LOGIN_ERROR_MESSAGE_USERERROR);
//        }
//        SysManager sysManager = SessionUtil.getSession();
//        dto.setRole(sysManager.getRole());
//        List<TreeNode> list = this.menuService.listTree(dto);
//        List<Object> opList = new ArrayList<Object>();
//        for (TreeNode treeNode :list){
//            for (TreeNode treeNode1 :treeNode.getChildren()){
//                OperateInPacket inPacket = new OperateInPacket();
//                inPacket.setRole(SessionUtil.getSession().getRole());
//                inPacket.setMenu(Integer.valueOf(treeNode1.getId()));
//                List<SysOperateVO> op = this.roleOperateService.queryOperateForList(inPacket);
//                opList.add(op);
//            }
//        }
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("menuList", list);
//        map.put("sysManager", sysManager);
//        map.put("opList",opList);
//        return ResultType.getSuccessMap(map);
//    }
//
//
//    @RequestMapping(value = "login", method = RequestMethod.POST, produces = "text/html; charset=utf-8")
//    public String login(HttpServletRequest request, HttpServletResponse response, SysManager dto) {
//        // 想要得到 SecurityUtils.getSubject() 的对象．．访问地址必须跟shiro的拦截地址内．不然后会报空指针
//        Subject sub = SecurityUtils.getSubject();
//        // 用户输入的账号和密码,,存到UsernamePasswordToken对象中..然后由shiro内部认证对比,
//        // 认证执行者交由ShiroDbRealm中doGetAuthenticationInfo处理
//        // 当以上认证成功后会向下执行,认证失败会抛出异常
//        UsernamePasswordToken token = new UsernamePasswordToken(dto.getAccount(), dto.getPassword());
//
//        try {
//            sub.login(token);
//            // 当验证都通过后，加入登录日志
//            SysManager user = managerService.selectByAccount(dto.getAccount());
//            dto.setManagerid(user.getManagerid());
//            SysManagerLog sysManagerLog= managerlogService.toSysManagerLog(dto);
//            sysManagerLog.setSysType(1);
//            managerlogService.save(sysManagerLog);
//
//        } catch (LockedAccountException lae) {
//            token.clear();
//            request.setAttribute("LOGIN_ERROR_CODE", Constent.LOGIN_ERROR_CODE_100002);
//            request.setAttribute("LOGIN_ERROR_MESSAGE", Constent.LOGIN_ERROR_MESSAGE_SYSTEMERROR);
//            return "login";
//        } catch (ExcessiveAttemptsException e) {
//            token.clear();
//            request.setAttribute("LOGIN_ERROR_CODE", Constent.LOGIN_ERROR_CODE_100003);
//            request.setAttribute("LOGIN_ERROR_MESSAGE", "账号：" + dto.getName() + Constent.LOGIN_ERROR_MESSAGE_MAXERROR);
//            return "login";
//        } catch (AuthenticationException e) {
//            token.clear();
//            if (e.getMessage() != null && !e.getMessage().equals("")) {
//                request.setAttribute("LOGIN_ERROR_MESSAGE", e.getMessage());
//            } else {
//                request.setAttribute("LOGIN_ERROR_CODE", Constent.LOGIN_ERROR_CODE_100001);
//                request.setAttribute("LOGIN_ERROR_MESSAGE", Constent.LOGIN_ERROR_MESSAGE_USERERROR);
//            }
//            return "login";
//        }
//
//        return "redirect:/index";
//    }
//
//    @RequestMapping("denied")
//    public String denied() {
//        return "denied";
//    }



}
