package br.com.clinica.dao;

import java.util.List;

import br.com.clinica.modelo.Convenio;

public class ConvenioDao implements Dao<Convenio> {

	@Override
	public void adiciona(Convenio convenio) {
		String sql = "insert into convenio (codigo, nome, concedente) values (?, ?, ?, ?)";
	}

	@Override
	public void altera(Convenio convenio) {
		String sql = "update convenio set codigo = ?, nome = ?, concedente = ? where id_convenio = ?";		
	}

	@Override
	public void remove(Convenio convenio) {
		String sql = "delete from convenio where id_convenio =?";		
	}

	@Override
	public List<Convenio> lista() {
		String sql = "select * from convenio";
		return null;
	}

	@Override
	public Convenio pesquisa(long id) {
		String sql = "select * from convenio where id_convenio = ?";
		return null;
	}

}
