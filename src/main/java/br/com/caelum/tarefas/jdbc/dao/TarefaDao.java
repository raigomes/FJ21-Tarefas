package br.com.caelum.tarefas.jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.caelum.tarefas.jdbc.modelo.Tarefa;

public class TarefaDao {
	private Connection connection;
	
	public TarefaDao(Connection connection) {
		this.connection = connection;
	}
	
	public void adiciona(Tarefa tarefa) {
		
		String query = "insert into tarefas"
				+ "(descricao, finalizado, dataFinalizacao)" 
				+ "values (?, ?, ?)";
		
		try {
			//Preparando statement
			PreparedStatement stmt = connection.prepareStatement(query);
			
			//Seta valores de inserção
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			
			if(tarefa.getDataFinalizacao() != null) 
				stmt.setDate(3, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
			else
				stmt.setDate(3, null);
			
			//Executa e fecha statement
			stmt.execute();
			stmt.close();
			
			connection.close();
		}
		catch(SQLException e) {
			throw new DaoException("TarefaDao", "adiciona()", e);
		}
	}
	
	public List<Tarefa> getLista() {
		try {
			List<Tarefa> tarefas = new ArrayList<>();
			//Prepara Statement com a query SELECT e executa-a, gurardando o resultado num ResultSet
			String query = "select * from tarefas";
			PreparedStatement stmt = this.connection.prepareStatement(query);
			ResultSet rs  = stmt.executeQuery();
			
			while(rs.next()) {
				//Cria objeto Tarefa
				Tarefa tarefa = new Tarefa();
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				//Insere dataFinalizacao no objeto
				Date date = rs.getDate("dataFinalizacao");
				
				if (date != null) {
					Calendar dataFinalizacao = Calendar.getInstance();
					SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
					java.util.Date dataFormatada = sf.parse(sf.format(date));
					dataFinalizacao.setTime(dataFormatada);
					tarefa.setDataFinalizacao(dataFinalizacao);
				}
				
				//Add objeto na lista
				tarefas.add(tarefa);
			}
			
			rs.close();
			stmt.close();
			connection.close();
			
			return tarefas;
		}
		catch (SQLException e) {
			throw new DaoException("TarefaDao", "getLista()", e);
		}
		catch (ParseException e) {
			throw new DaoException("TarefaDao", "getLista()", e);
		}
	}
	
	public Tarefa pesquisar(int id) {
		try {			
			String query = "select * from tarefas where id = ?";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, id);
			ResultSet rs = stmt.executeQuery();
			
			if(rs.next()) {
				Tarefa tarefa = new Tarefa();
				
				tarefa.setId(rs.getLong("id"));
				tarefa.setDescricao(rs.getString("descricao"));
				tarefa.setFinalizado(rs.getBoolean("finalizado"));
				
				Date date = rs.getDate("dataFinalizacao");
				
				if(date != null) {
					Calendar dataFinalizacao = Calendar.getInstance();
					dataFinalizacao.setTime(date);
					tarefa.setDataFinalizacao(dataFinalizacao);
				}
				
				return tarefa;
			}
			else {
				return null;
			}			
		}
		catch (SQLException e) {
			throw new DaoException("TarefaDao", "pesquisar("+id+")", e);
		}
	}
	
	public boolean altera (Tarefa tarefa) {
		String query = "update tarefas "
				+ "set descricao = ?, finalizado = ?, dataFinalizacao = ? "
				+ "where id = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, tarefa.getDescricao());
			stmt.setBoolean(2, tarefa.isFinalizado());
			
			if(tarefa.getDataFinalizacao() != null) 
				stmt.setDate(3, new Date(tarefa.getDataFinalizacao().getTimeInMillis()));
			else
				stmt.setDate(3, null);
			
			stmt.setLong(4, tarefa.getId());
			
			stmt.execute();
			stmt.close();
			
			return true;
		}
		catch(SQLException e) {
			throw new DaoException("TarefaDao", "altera(tarefa).\nTarefa ="+tarefa, e);
		}
		
	}
	
	public boolean remove(long id) {
		String query = "delete from tarefas where id = ?";
		
		try {
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setLong(1, id);			
			stmt.execute();
			stmt.close();
			return true;
		}
		catch(SQLException e) {
			throw new DaoException("TarefaDao", "remove("+id+")", e);
		}
	}
}
