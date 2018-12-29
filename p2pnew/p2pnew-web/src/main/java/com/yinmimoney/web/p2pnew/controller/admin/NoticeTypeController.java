package com.yinmimoney.web.p2pnew.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.pojo.NoticeType;
import com.yinmimoney.web.p2pnew.service.INoticeType;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.base.Strings;
import cc.s2m.web.utils.webUtils.vo.Expressions;
import cc.s2m.web.utils.webUtils.util.JSONResultCode;

@Controller("admin_NoticeTypeController")
@RequestMapping("/admin/noticeType")
public class NoticeTypeController extends BaseController {
    private static final String MODULE_NAME = "noticeType";// TODO

    @Autowired
    private INoticeType noticeTypeService;

    @RequestMapping(value = "/list")
    public String list(Model model, NoticeType bean, Integer page
			, String addTimeBegin, String addTimeEnd
			, String updateTimeBegin, String updateTimeEnd
			) {
        model.addAttribute("bean", bean);
		model.addAttribute("addTimeBegin", addTimeBegin);
		model.addAttribute("addTimeEnd", addTimeEnd);
		model.addAttribute("updateTimeBegin", updateTimeBegin);
		model.addAttribute("updateTimeEnd", updateTimeEnd);
		
        
        if (page == null) page = 1;
        if (bean == null) {
            bean = new NoticeType();
        }
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
          if (!Strings.isNullOrEmpty(addTimeBegin)) {
            bean.and(Expressions.ge("add_time", format.parse(addTimeBegin)));
          }
          if (!Strings.isNullOrEmpty(addTimeEnd)) {
            bean.and(Expressions.lt("add_time", DateUtils.addDays(format.parse(addTimeEnd), 1)));
          }
          if (!Strings.isNullOrEmpty(updateTimeBegin)) {
            bean.and(Expressions.ge("update_time", format.parse(updateTimeBegin)));
          }
          if (!Strings.isNullOrEmpty(updateTimeEnd)) {
            bean.and(Expressions.lt("update_time", DateUtils.addDays(format.parse(updateTimeEnd), 1)));
          }
        } catch (Exception e) {}
        
        if (!"1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          bean.setPageNumber(page);
          bean.setPageSize(50);
        } else {
          bean.setPageNumber(1);
          bean.setPageSize(Integer.MAX_VALUE);
        }
        
        Page<NoticeType> pageBean = noticeTypeService.getPage(bean, getRequest().getParameterMap());
        model.addAttribute("pageBean", pageBean);
        
        if ("1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          return exportExcel(model, pageBean);
        }
        
        return "admin/noticeType";
    }
    
    private String exportExcel (Model model, Page<NoticeType> pageBean) {
    	String[] titles = new String[]{
          "id",
          "type",
          "nid",
          "noticeType",
          "name",
          "titleTemplet",
          "templet",
          "send",
          "canSwitch",
          "remark",
          "addTime",
          "addIp",
          "updateTime",
          "updateIp"
    	};
    	List<String[]> paramList = Lists.newArrayList();
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for (NoticeType bean : pageBean.getResult()) {
    		paramList.add(new String[]{
              bean.getId().toString(),
              bean.getType().toString(),
              bean.getNid(),
              bean.getNoticeType().toString(),
              bean.getName(),
              bean.getTitleTemplet(),
              bean.getTemplet(),
              bean.getSend().toString(),
              bean.getCanSwitch().toString(),
              bean.getRemark(),
            format.format(bean.getAddTime()),
              bean.getAddIp(),
            format.format(bean.getUpdateTime()),
              bean.getUpdateIp()
    		});
		}
    	model.addAttribute("fileName", MODULE_NAME);
    	model.addAttribute("titles", titles);
    	model.addAttribute("paramList", paramList);
      addSysLog(MODULE_NAME, SysLog.OPRATE_EXPORT, "EXCEL");
    	return "ExcelviewResolver";
    }

    @RequestMapping(value = "/add",method=RequestMethod.GET)
    public String add(Model model, Integer id) {
        if(id != null){
            NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/noticeType_add";
    }

    @RequestMapping(value = "/view",method=RequestMethod.GET)
    public String view(Model model, Integer id) {
        if(id != null){
            NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/noticeType_view";
    }
	
    @RequestMapping(value = "/info",method=RequestMethod.POST)
    @ResponseBody
    public NoticeType info(Integer id) {
    	if(id == null){
    		return new NoticeType();
    	}
    	NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
    	if(bean == null){
    		return new NoticeType();
    	}
    	return bean;
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode save (NoticeType bean) {
        if (bean == null) {
            return new JSONResultCode(100, "noData");
        }
        if (bean.getId() == null) {
            //添加
            noticeTypeService.insertSelective(bean);
            addSysLog(MODULE_NAME, SysLog.OPRATE_ADD, JSONObject.toJSONString(bean));
        } else {
            //修改
            noticeTypeService.updateByPrimaryKeySelective(bean);
            addSysLog(MODULE_NAME, SysLog.OPRATE_EDIT, JSONObject.toJSONString(bean));
        }
        return new JSONResultCode(0, "success");
    }

    @RequestMapping(value = "/del",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode del (Integer id) {
        NoticeType bean = noticeTypeService.selectByPrimaryKey(id);
        if (bean == null) {
          return new JSONResultCode(100, "noData");
        }
        addSysLog(MODULE_NAME, SysLog.OPRATE_DEL, JSONObject.toJSONString(bean));
        noticeTypeService.deleteByPrimaryKey(id);
        return new JSONResultCode(0, "success");
    }
}