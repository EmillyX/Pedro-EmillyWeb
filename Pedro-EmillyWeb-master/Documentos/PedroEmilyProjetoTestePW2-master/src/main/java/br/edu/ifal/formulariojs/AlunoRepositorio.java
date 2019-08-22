package br.edu.ifal.formulariojs;

import org.springframework.data.repository.CrudRepository;

public interface AlunoRepositorio extends CrudRepository <Aluno,Long>{


    public Iterable<Aluno> findByNomeContaining(String name);
}