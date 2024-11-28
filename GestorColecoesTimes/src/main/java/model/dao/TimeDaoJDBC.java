/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Time;

/**
 *
 * @author lefoly
 */
public class TimeDaoJDBC implements InterfaceDao<Time> {

    private Connection conn;

    public TimeDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Time entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Times (nome, ligaTime, sigla) VALUES(?, ?, ?)");
            ps.setString(1, entidade.getNome());
            ps.setString(2, entidade.getLigaTime());
            ps.setString(3, entidade.getSigla());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void editar(Time time) throws SQLException {
        String sql = "UPDATE times SET nome = ?, ligaTime = ?, sigla = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, time.getNome());
            ps.setString(2, time.getLigaTime());
            ps.setString(3, time.getSigla());
            ps.setInt(4, time.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void excluir(Time entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Times WHERE id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Time pesquisarPorId(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Times WHERE id= ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Time t = new Time();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setLigaTime(rs.getString("ligaTime"));
                t.setSigla(rs.getString("sigla"));
                return t;
            } else {
                return null;
            }
        } finally {
            if (conn != null) {
                ps.close();
                rs.close();
            }
        }
    }

    @Override
    public List<Time> listar(String param) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            if (param.equals("")) {
                ps = conn.prepareStatement("SELECT * FROM Times");
            } else {
                ps = conn.prepareStatement("SELECT * FROM Times WHERE nome like '%" + param + "%'");
            }
            rs = ps.executeQuery();
            List<Time> lista = new ArrayList();
            while (rs.next()) {
                Time t = new Time();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setLigaTime(rs.getString("ligaTime"));
                t.setSigla(rs.getString("sigla"));
                lista.add(t);
            }
            return lista;
        } finally {
            if (conn != null) {
                ps.close();
                rs.close();
            }
        }
    }
}
