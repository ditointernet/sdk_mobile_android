package br.com.ioasys.dito_sdk.interfaces;

// FIXME documentar os métodos desta classe
public interface GCMListener {
    void OnSuccess(String data);

    void OnFailure(String error);
}
