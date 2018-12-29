package com.yinmimoney.web.p2pnew.controller.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import cc.s2m.util.Page;
import com.alibaba.fastjson.JSONObject;
import com.yinmimoney.web.p2pnew.controller.base.BaseController;
import com.yinmimoney.web.p2pnew.pojo.SysLog;
import com.yinmimoney.web.p2pnew.pojo.BallProcess;
import com.yinmimoney.web.p2pnew.service.IBallProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import cc.s2m.web.utils.webUtils.util.JSONResultCode;

@Controller("admin_BallProcessController")
@RequestMapping("/admin/ballProcess")
public class BallProcessController extends BaseController {
    private static final String MODULE_NAME = "ballProcess";// TODO

    @Autowired
    private IBallProcess ballProcessService;

    @RequestMapping(value = "/list")
    public String list(Model model, BallProcess bean, Integer page
			) {
        model.addAttribute("bean", bean);
		
        
        if (page == null) page = 1;
        if (bean == null) {
            bean = new BallProcess();
        }
        
        
        if (!"1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          bean.setPageNumber(page);
          bean.setPageSize(50);
        } else {
          bean.setPageNumber(1);
          bean.setPageSize(Integer.MAX_VALUE);
        }
        
        Page<BallProcess> pageBean = ballProcessService.getPage(bean, getRequest().getParameterMap());
        model.addAttribute("pageBean", pageBean);
        
        if ("1".equals(getRequest().getParameter("excel"))) {//导出 EXCEL
          return exportExcel(model, pageBean);
        }
        
        return "admin/ballProcess";
    }
    
    private String exportExcel (Model model, Page<BallProcess> pageBean) {
    	String[] titles = new String[]{
          "id",
          "processName",
          "processMoney",
          "flag"
    	};
    	List<String[]> paramList = Lists.newArrayList();
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	for (BallProcess bean : pageBean.getResult()) {
    		paramList.add(new String[]{
              bean.getId().toString(),
              bean.getProcessName(),
              bean.getProcessMoney().toString(),
              bean.getFlag().toString()
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
            BallProcess bean = ballProcessService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/ballProcess_add";
    }

    @RequestMapping(value = "/view",method=RequestMethod.GET)
    public String view(Model model, Integer id) {
        if(id != null){
            BallProcess bean = ballProcessService.selectByPrimaryKey(id);
            model.addAttribute("bean", bean);
        }
        return "admin/ballProcess_view";
    }
	
    @RequestMapping(value = "/info",method=RequestMethod.POST)
    @ResponseBody
    public BallProcess info(Integer id) {
    	if(id == null){
    		return new BallProcess();
    	}
    	BallProcess bean = ballProcessService.selectByPrimaryKey(id);
    	if(bean == null){
    		return new BallProcess();
    	}
    	return bean;
    }

    @RequestMapping(value = "/save",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode save (BallProcess bean) {
        if (bean == null) {
            return new JSONResultCode(100, "noData");
        }
        if (bean.getId() == null) {
            //添加
            ballProcessService.insertSelective(bean);
            addSysLog(MODULE_NAME, SysLog.OPRATE_ADD, JSONObject.toJSONString(bean));
        } else {
            //修改
            ballProcessService.updateByPrimaryKeySelective(bean);
            addSysLog(MODULE_NAME, SysLog.OPRATE_EDIT, JSONObject.toJSONString(bean));
        }
        return new JSONResultCode(0, "success");
    }

    @RequestMapping(value = "/del",method=RequestMethod.POST)
    @ResponseBody
    public JSONResultCode del (Integer id) {
        BallProcess bean = ballProcessService.selectByPrimaryKey(id);
        if (bean == null) {
          return new JSONResultCode(100, "noData");
        }
        addSysLog(MODULE_NAME, SysLog.OPRATE_DEL, JSONObject.toJSONString(bean));
        ballProcessService.deleteByPrimaryKey(id);
        return new JSONResultCode(0, "success");
    }
}