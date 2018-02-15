package br.com.clinica.dao;

import java.util.List;

public interface Dao<T> {
	void adiciona(T object);
	void altera(T object);
	void remove(T object);
	List<T> lista();
	T pesquisa(long id);
}
