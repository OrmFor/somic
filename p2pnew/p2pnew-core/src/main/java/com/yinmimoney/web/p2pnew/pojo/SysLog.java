package com.yinmimoney.web.p2pnew.pojo;

import com.yinmimoney.web.p2pnew.pojo.entity.SysLogEntity;

public class SysLog extends SysLogEntity {

    /** serialVersionUID */
    private static final long  serialVersionUID = 1L;

    /* ------------------------- 模块相关常量 ----------------------------------- */
    public static final String MODULE_DEFAULT   = "-";    //默认的
    public static final String MODULE_SELF      = "个人信息";
    public static final String MODULE_ACTIONS   = "权限设置";
    public static final String MODULE_ROLES     = "角色管理";
    public static final String MODULE_ADMINS    = "管理员管理";
    public static final String MODULE_CONFIG    = "系统参数";
    public static final String MODULE_TASK      = "系统任务";
    public static final String MODULE_IDS       = "序列号管理";

    /* ------------------------- 操作动作相关常量 ----------------------------------- */
    public static final String OPRATE_DEFAULT   = "-";    //默认的
    public static final String OPRATE_LOGIN     = "登陆";
    public static final String OPRATE_LOGIN_OUT = "退出";
    public static final String OPRATE_ADD       = "添加";
    public static final String OPRATE_EDIT      = "更新";
    public static final String OPRATE_DEL       = "删除";
    public static final String OPRATE_LOCK      = "锁定";
    public static final String OPRATE_UNLOCK    = "解锁";
    public static final String OPRATE_EXPORT    = "导出";
    public static final String OPRATE_VET       = "审核";
    public static final String OPRATE_INT       = "拦截";
    public static final String PRE_CANCEL = "预处理取消";
    public static final String BACKROLL_RECHARGE = "手动充值回退";
    public static final String DOWNLOAD_FILE = "下载文件";
}