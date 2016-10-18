package br.com.dito.ditosdk;

import android.content.Context;
import android.util.Log;

import java.util.HashMap;
import java.util.List;

import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;
import br.com.dito.ditosdk.model.Configure;
import br.com.dito.ditosdk.model.DitoAccount;
import br.com.dito.ditosdk.model.DitoCredentials;
import br.com.dito.ditosdk.services.AliasService;
import br.com.dito.ditosdk.services.IdentifyService;
import br.com.dito.ditosdk.services.ReadNotification;
import br.com.dito.ditosdk.services.RegisterService;
import br.com.dito.ditosdk.services.RequestService;
import br.com.dito.ditosdk.services.TrackService;

/**
 * Created by gabriel.araujo on 01/04/15.
 */
public class DitoSDK {

    /**
     * Method para configuração do ambiente
     *
     * @param environment Enum de ambientes
     * @return void
     */
    public static void configureEnvironment(Constants.EnvironmentType environment) {
        Constants.DEV_MODE = environment;
    }

    /**
     * Method para configuração do objeto de configuração da Dito
     *
     * @param context Contexto da aplicação
     * @param apiKey  ApiKey do projeto
     * @param secret  Secret key do projeto
     * @return void
     * @throws DitoSDKException
     */
    public static void configure(Context context, String apiKey, String secret) throws DitoSDKException {
        if (context == null) {
            throw new DitoSDKException(Constants.Mensagens.NOT_FOUND_CONTEXT);
        }
        Constants.context = context;
        Constants.configure = new Configure(apiKey, secret);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param accessToken accessToken retornado por alguma rede social
     * @param data        Objeto a ser enviado para Dito
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void identify(DitoCredentials credentials, String accessToken, Object data, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        Constants.accessToken = accessToken;
        IdentifyService.identify(accessToken, data, completion);

        try {
            TrackService.verifyToSendEventOffline();
        } catch (DitoSDKException e) {
            Log.e("DITO_SDK", e.getMessage());
        }
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param deviceToken divice ID retornado pela Google
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void registerDevice(DitoCredentials credentials, String deviceToken, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        RegisterService.registerToken(deviceToken, completion);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param deviceToken divice ID retornado pela Google
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void unregisterDevice(DitoCredentials credentials, String deviceToken, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        RegisterService.unregisterToken(deviceToken, completion);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param message     Json recebido no push enviado pela Dito
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void notificationRead(DitoCredentials credentials, String message, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        ReadNotification.readNotification(message, completion);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param accounts    Lista de contas a ser associadas
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void alias(DitoCredentials credentials, List<DitoAccount> accounts, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        AliasService.alias(accounts, completion);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param accounts    Lista de contas a ser desassociadas
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void unalias(DitoCredentials credentials, List<DitoAccount> accounts, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        AliasService.unalias(accounts, completion);
    }

    /**
     * @param credentials Credentials do usuario corrente
     * @param event       Objecto event
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void track(DitoCredentials credentials, Object event, DitoSDKCallback completion) throws DitoSDKException {
        Constants.credentials = credentials;
        TrackService.track(event, completion);
    }

    /**
     * @param module
     * @param path
     * @param completion  Callback do serviço
     * @param params      Parametros body para a requisição
     * @param requestType Tipo da requisição
     * @param completion  Callback do serviço
     * @return void
     * @throws DitoSDKException
     */
    public static void request(String module, String path, HashMap<String, Object> params, Constants.HttpTypes requestType, DitoSDKCallback completion) throws DitoSDKException {
        RequestService.request(module, path, params, requestType, completion);
    }
}
