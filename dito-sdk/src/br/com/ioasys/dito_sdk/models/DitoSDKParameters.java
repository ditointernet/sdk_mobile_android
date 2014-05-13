package br.com.ioasys.dito_sdk.models;

import java.io.Serializable;

/**
 * Classe responsável por armazenar os parâmetros comuns a todos os métodos dos serviços
 * 
 * @author iOasys
 * @since 14-05-2014
 * @version 1.0
 *
 */
public class DitoSDKParameters implements Serializable {
    private static final long serialVersionUID = 9146966663155208957L;

    /**
     * Flag para realizar consultas na base de Produção ou Desenvolvimento
     */
    private boolean inDevMode;

    /**
     * Número do projeto reistrado no "Google Console"
     * Para detalhes acesse: http://goo.gl/HfIICp
     */
    private String senderID;

    /**
     * Chave de indentificação na plataforma da DITO.
     */
    private String apiKey;

    /**
     * Domínio registrado na plataforma web da DITO.
     */
    private String domain;

    /**
     * Código de referência retornado no processo de <b>signup</b>
     */
    private String reference;

    /**
     * Chave criptografada a ser utilizada na assinatura das requisições
     */
    private String sign;

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getSenderID() {
        return senderID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public boolean isInDevMode() {
        return inDevMode;
    }

    public void setInDevMode(boolean inDevMode) {
        this.inDevMode = inDevMode;
    }
}
