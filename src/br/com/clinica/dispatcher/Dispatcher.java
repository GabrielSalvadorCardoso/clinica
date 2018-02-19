package br.com.clinica.dispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Dispatcher {
	String dispatch(HttpServletRequest request, HttpServletResponse response);
	String get(HttpServletRequest request, HttpServletResponse response);
	String get(HttpServletRequest request, HttpServletResponse response, long id);
	String post(HttpServletRequest request, HttpServletResponse response);
	String put(HttpServletRequest request, HttpServletResponse response);
	String delete (HttpServletRequest request, HttpServletResponse response);
}
