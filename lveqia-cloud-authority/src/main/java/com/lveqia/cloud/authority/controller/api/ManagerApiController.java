package com.lveqia.cloud.authority.controller.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author YaoGang
 * @Date 2017/6/28 10:37
 * @Description 提供给第三方调用的运营人员管理类
 */
@RestController
@RequestMapping("/api/manager")
public class ManagerApiController {

//    @Autowired
//    private SysManagerService managerService;
//
//    /**
//     * 根据传参查询运营人员信息
//     *
//     * @param inPacket
//     * @return List<ManagerVO>
//     */
//    @RequestMapping(value = "/queryManager")
//    public List<ManagerVO> queryManager(@RequestBody ManagerInPacket inPacket) {
//        return this.managerService.queryForList(inPacket);
//    }

}
