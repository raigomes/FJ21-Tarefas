package br.com.caelum.tarefas.controller;

import java.sql.Connection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.caelum.tarefas.jdbc.ConnectionFactory;
import br.com.caelum.tarefas.jdbc.dao.TarefaDao;
import br.com.caelum.tarefas.jdbc.modelo.Tarefa;

@Controller
public class TarefasController {
	
	@RequestMapping("novaTarefa")
	public String form() {
		return "tarefa/formulario";
	}
	
	@RequestMapping("adicionaTarefa")
	public String adiciona(Tarefa tarefa) {
		Connection connection = new ConnectionFactory().getConnection();		
		
		TarefaDao dao = new TarefaDao(connection);
		dao.adiciona(tarefa);
		
		return "tarefa/adicionada";
	}
}
