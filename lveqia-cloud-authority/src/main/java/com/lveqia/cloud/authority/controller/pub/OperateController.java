package com.lveqia.cloud.authority.controller.pub;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/pub/operate")
public class OperateController
{

//	@Autowired
//	RoleOperateService operateService;
//	@Autowired
//	MenuService menuService;
//
//	@RequestMapping(value = "/list")
//	public String list(Model model)
//	{
//		return "pub/operate/list";
//	}
//
//	@RequestMapping(value = "/query")
//	@ResponseBody
//	public PageInfo<SysOperateVO> query(OperateInPacket inPacket)
//	{
//		return this.operateService.queryOperateForListPage(inPacket);
//	}
//
//	@RequestMapping(value = "/edit")
//	public String edit(SysOperate dto, Model model)
//	{
//		if (dto.getId() != null)
//		{
//			model.addAttribute("dto", this.operateService.selectByPrimaryKey(dto.getId()));
//		}else{
//			model.addAttribute("dto",new SysOperate());
//		}
//		model.addAttribute("menuList",this.menuService.selectAll());
//		return "pub/operate/edit";
//	}
//
//	@RequestMapping(value = "/save")
//	@ResponseBody
//	public Map<String, Object> save(@RequestBody SysOperate dto)
//	{
//		return this.operateService.save(dto);
//	}
//
//	@RequestMapping(value = "/remove")
//	@ResponseBody
//	public Map<String, Object> del(String[] ids)
//	{
//		return this.operateService.deleteByOperate(ids);
//	}

}
