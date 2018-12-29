package com.yinmimoney.web.p2pnew.controller.base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yinmimoney.web.p2pnew.exception.BussinessException;
import com.yinmimoney.web.p2pnew.util.ResultCode;
import com.yinmimoney.web.p2pnew.util.StatusCode;

public class ExceptionBaseController {

	private static final Logger LOGGER = LogManager.getLogger(ExceptionBaseController.class);

	@ExceptionHandler(BussinessException.class)
	@ResponseBody
	public ResultCode handleBussinessException(BussinessException ex) {
		LOGGER.error("BussinessException", ex);
		return new ResultCode(ex.getCode(), ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResultCode handleException(Exception ex) {
		LOGGER.error("Exception", ex);
		return new ResultCode(StatusCode.ERROR);
	}
}
