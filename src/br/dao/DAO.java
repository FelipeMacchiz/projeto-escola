package br.dao;

import java.util.List;

public interface DAO<T> {

  T atualizar(T entidade);
  T buscarPorId(Integer id);
  List<T> listar();
  void salvar(T entidade);
  void apagar(Integer id);

}
