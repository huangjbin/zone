package com.zone.quartz_module.exception;

import com.zone.quartz_module.common.BaseResult;
import com.zone.quartz_module.common.ResLanguage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

/**
 * 全局异常捕获
 *
 *
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 * NoSuchMethodException,IOException,IndexOutOfBoundsException
 * 以及springmvc自定义异常等，如下： SpringMVC自定义异常对应的status code Exception HTTP Status Code
 * ConversionNotSupportedException 500 (Internal Server Error)
 * HttpMessageNotWritableException 500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException 415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException 406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException 405 (Method Not Allowed)
 * NoSuchRequestHandlingMethodException 404 (Not Found) TypeMismatchException
 * 400 (Bad Request) HttpMessageNotReadableException 400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 *
 */
@ControllerAdvice
public class RestExceptionHandler {

	public static Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

	public BaseResult returnException(Exception e){

		e.printStackTrace();

		BaseResult baseResult = new BaseResult(ResLanguage.RES_SYSTEM_ERR_CODE,
				ResLanguage.getRes_system_err_mes(""), null);
		return baseResult;
	}

	// 自定义异常
	@ExceptionHandler(CustomException.class)
	@ResponseBody
	public BaseResult customExceptionHandler(CustomException customException) {

		BaseResult baseResult = new BaseResult(customException.getCode(),
				customException.getMessage(), null);
		return baseResult;
	}

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public BaseResult runtimeExceptionHandler(RuntimeException runtimeException) {
		return returnException(runtimeException);
	}

	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	public BaseResult nullPointerExceptionHandler(NullPointerException nullPointerException) {
		return returnException(nullPointerException);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	@ResponseBody
	public BaseResult classCastExceptionHandler(ClassCastException classCastException) {
		return returnException(classCastException);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public BaseResult iOExceptionHandler(IOException ioException) {
		return returnException(ioException);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	@ResponseBody
	public BaseResult noSuchMethodExceptionHandler(NoSuchMethodException noSuchMethodException) {
		return returnException(noSuchMethodException);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	@ResponseBody
	public BaseResult indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException indexOutOfBoundsException) {
		return returnException(indexOutOfBoundsException);
	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	public BaseResult requestNotReadable(HttpMessageNotReadableException httpMessageNotReadableException) {
		return returnException(httpMessageNotReadableException);
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	@ResponseBody
	public BaseResult requestTypeMismatch(TypeMismatchException typeMismatchException) {
		return returnException(typeMismatchException);
	}

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	@ResponseBody
	public BaseResult requestMissingServletRequest(MissingServletRequestParameterException missingServletRequestParameterException) {
		return returnException(missingServletRequestParameterException);
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	public BaseResult request405(HttpRequestMethodNotSupportedException e) {
		return returnException(e);
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	@ResponseBody
	public BaseResult request406(HttpMediaTypeNotAcceptableException e) {
		return returnException(e);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	@ResponseBody
	public BaseResult server500(RuntimeException runtimeException) {
		return returnException(runtimeException);
	}

	public BaseResult resultInfo(Map<String, String[]> map){
		String[] language = map.get("language");
		BaseResult baseResult = new BaseResult(ResLanguage.RES_SYSTEM_ERR_CODE,
				ResLanguage.getRes_system_err_mes(language.length > 0 ? language[0] : ""), null);
		return baseResult;
	}
}