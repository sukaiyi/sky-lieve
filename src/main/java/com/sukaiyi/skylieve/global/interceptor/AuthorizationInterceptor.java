package com.sukaiyi.skylieve.global.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sukaiyi.skylieve.common.vo.ResultVO;
import com.sukaiyi.skylieve.global.annotation.APIKeyRequired;
import com.sukaiyi.skylieve.global.constants.Constants;
import com.sukaiyi.skylieve.global.exception.exceptions.code.ExceptionCode;
import com.sukaiyi.skylieve.global.service.APIKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @author sukaiyi
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private APIKeyService apiKeyService;

	@Autowired
	ObjectMapper mapper;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		APIKeyRequired apiKeyRequired = method.getAnnotation(APIKeyRequired.class);
		if (apiKeyRequired == null) {
			return true;
		}
		String key = request.getHeader(Constants.API_KEY);
		if (apiKeyService.validate(key)) {
			return true;
		} else {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			ResultVO result = ResultVO.error(
					ExceptionCode.AUTHORIZATION_FAILED,
					"无权限访问该资源");
			try {
				out = response.getWriter();
				out.write(mapper.writeValueAsString(result));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
			return false;
		}
	}
}
