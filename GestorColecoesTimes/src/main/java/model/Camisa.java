/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class Camisa {

    private int id;
    private int timeId;
    private String nome;
    private String marca;
    private String tamanho;
    private String temporada;
    private boolean possui;
    private String localImagem;

    public Camisa() {
    }

    public Camisa(int timeId, String temporada, String nome, String marca, String tamanho, boolean possui, String localImagem) {
        this.timeId = timeId;
        this.nome = nome;
        this.marca = marca;
        this.tamanho = tamanho;
        this.temporada = temporada;
        this.possui = possui;
        this.localImagem = localImagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTimeId() {
        return timeId;
    }

    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getTemporada() {
        return temporada;
    }

    public void setTemporada(String temporada) {
        this.temporada = temporada;
    }

    public boolean isPossui() {
        return possui;
    }

    public void setPossui(boolean possui) {
        this.possui = possui;
    }

    public String getLocalImagem() {
        return localImagem;
    }

    public void setLocalImagem(String localImagem) {
        this.localImagem = localImagem;
    }

    @Override
    public String toString() {
        return "Camisa{" + "timeId=" + timeId + ", nome=" + nome + ", marca=" + marca + ", tamanho=" + tamanho + ", possui=" + possui + ", localImagem=" + localImagem + '}';
    }
}
