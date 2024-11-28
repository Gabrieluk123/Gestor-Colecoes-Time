/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author aguia
 */
public class Time {

    private int id;
    private String nome;
    private String ligaTime;
    private String sigla;

    public Time() {
    }

    public Time(int id, String nome, String ligaTime, String sigla) {
        this.id = id;
        this.nome = nome;
        this.ligaTime = ligaTime;
        this.sigla = sigla;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLigaTime() {
        return ligaTime;
    }

    public void setLigaTime(String ligaTime) {
        this.ligaTime = ligaTime;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Override
    public String toString() {
        return "Time{" + "nome=" + nome + ", ligaTime=" + ligaTime + ", sigla=" + sigla + '}';
    }
}
