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
import model.Camisa;

/**
 *
 * @author lefoly
 */
public class CamisaDaoJDBC implements InterfaceDao<Camisa> {

    private Connection conn;

    public CamisaDaoJDBC() throws Exception {
        this.conn = ConnFactory.getConnection();
    }

    @Override
    public void incluir(Camisa entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Camisas (timeId, nome, marca, tamanho,temporada, possui, localImagem) VALUES(?, ? , ?, ?, ?, ?, ?)");
            ps.setInt(1, entidade.getTimeId());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getMarca());
            ps.setString(4, entidade.getTamanho());
            ps.setString(5, entidade.getTemporada());
            ps.setBoolean(6, entidade.isPossui());
            ps.setString(7, entidade.getLocalImagem());
            System.out.println(entidade.getTimeId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void editar(Camisa entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Camisas SET timeId=?, nome=?, marca=?, tamanho=?,temporada=?, possui=?, localImagem=? WHERE id=?");
            ps.setInt(1, entidade.getTimeId());
            ps.setString(2, entidade.getNome());
            ps.setString(3, entidade.getMarca());
            ps.setString(4, entidade.getTamanho());
            ps.setString(5, entidade.getTemporada());
            ps.setBoolean(6, entidade.isPossui());
            ps.setString(7, entidade.getLocalImagem());
            ps.setInt(8, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public void excluir(Camisa entidade) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Camisas WHERE id=?");
            ps.setInt(1, entidade.getId());
            ps.execute();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Override
    public Camisa pesquisarPorId(int id) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM Camisas WHERE id= ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Camisa c = new Camisa();
                c.setTimeId(rs.getInt("timeId"));
                c.setNome(rs.getString("nome"));
                c.setMarca(rs.getString("marca"));
                c.setTamanho(rs.getString("tamanho"));
                c.setTemporada(rs.getString("temporada"));
                c.setPossui(true);
                c.setLocalImagem(rs.getString("localImagem"));
                return c;
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

    public List<Camisa> listarPorTime(String param, int timeId) throws SQLException {
        List<Camisa> camisas = new ArrayList<>();
        String sql = "SELECT * FROM camisas WHERE timeId = ? AND nome LIKE ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, timeId);
            st.setString(2, "%" + param + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Camisa camisa = new Camisa();
                    camisa.setId(rs.getInt("id"));
                    camisa.setNome(rs.getString("nome"));
                    camisa.setMarca(rs.getString("marca"));
                    camisa.setTamanho(rs.getString("tamanho"));
                    camisa.setTemporada(rs.getString("temporada"));
                    camisa.setPossui(rs.getBoolean("possui"));
                    camisa.setLocalImagem(rs.getString("localImagem"));
                    camisa.setTimeId(rs.getInt("timeId"));
                    camisas.add(camisa);
                }
            }
        }
        return camisas;
    }

    @Override
    public List<Camisa> listar(String param) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
