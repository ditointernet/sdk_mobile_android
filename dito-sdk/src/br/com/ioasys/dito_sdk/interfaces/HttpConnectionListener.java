package br.com.ioasys.dito_sdk.interfaces;

/**
 * Interface utilizada para efetuar os callbacks das requisições
 * 
 * @version 1.0
 * @author iOasys
 * @since 14-04-14
 * 
 */
public interface HttpConnectionListener {
    /**
     * 
     * @param response
     *            Retorna o json de resposta da requisição
     */
    public void onSuccess(String response);

    /**
     * 
     * @param error
     *            Retorna a possível mensagem de erro ocorrida durante a
     *            requisição
     */
    public void onError(String error);
}
