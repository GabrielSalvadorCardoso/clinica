package br.com.clinica.dao;

import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public interface Dao<T> {
	// É importante que os métodos que alteram o estado (adiciona(), altera() e remove())
	// do banco de dados, repassem a exceção para que uma mensagem explicativa possa
	// ser exibida para o usuario através das views
	void adiciona(T object) throws SQLException;
	void altera(T object);
	void remove(T object);
	List<T> lista();
	T pesquisa(long id);
}
