package br.com.caelum.tarefas.controller;

import java.sql.Connection;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
	public String adiciona(@Valid Tarefa tarefa, BindingResult result) {
		
		if(result.hasFieldErrors("descricao")) {
			return "tarefa/formulario";
		}
		
		Connection connection = new ConnectionFactory().getConnection();		
		
		TarefaDao dao = new TarefaDao(connection);
		dao.adiciona(tarefa);
				
		return "tarefa/adicionada";
	}
}
