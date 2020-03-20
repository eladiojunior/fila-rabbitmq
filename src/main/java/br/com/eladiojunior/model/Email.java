package br.com.eladiojunior.model;

import com.google.gson.Gson;

public class Email {
    private String remetente;
    private String destinatario;
    private String assunto;
    private String mensagem;

    public String getRemetente() {
        return remetente;
    }

    public void setRemetente(String remetente) {
        this.remetente = remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Email(String remetente, String destinatario, String assunto, String mensagem) {
        setRemetente(remetente);
        setDestinatario(destinatario);
        setAssunto(assunto);
        setMensagem(mensagem);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
