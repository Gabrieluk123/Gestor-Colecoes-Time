/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

/**
 *
 * @author lefoly
 */
public class DaoFactory {

    public static CamisaDaoJDBC novaCamisaDAO() throws Exception {
        return new CamisaDaoJDBC();
    }

    public static TimeDaoJDBC novoTimeDAO() throws Exception {
        return new TimeDaoJDBC();
    }
}
