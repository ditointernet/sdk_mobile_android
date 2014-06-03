package br.com.ioasys.dito_sdk.api;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import br.com.ioasys.dito_sdk.api.utils.gcm.GCMManager;
import br.com.ioasys.dito_sdk.constants.Constants;
import br.com.ioasys.dito_sdk.constants.Constants.NETWORKS;
import br.com.ioasys.dito_sdk.constants.Constants.NETWORKS_ACRONYMS;
import br.com.ioasys.dito_sdk.exceptions.DitoSDKException;
import br.com.ioasys.dito_sdk.interfaces.GCMListener;
import br.com.ioasys.dito_sdk.interfaces.HttpConnectionListener;
import br.com.ioasys.dito_sdk.models.DitoSDKParameters;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Classe contendo os métodos da SDK
 * 
 * @author iOasys
 * @since 14-04-14
 * @version 1.0
 * 
 */
public class Services {
    private static AsyncHttpClient client = new AsyncHttpClient();
    private static DitoSDKParameters parameters;
    private static final String PARAMETERS_INVALID = "Parâmetros não informados, antes de prosseguir utilize o método \"setDitoSDKParameters\"";

    /**
     * Método para inicializar os parâmetros que seram utilizados em todos os
     * serviços disponibilizados na SDK
     * 
     * @param params
     *            Objeto contendo todos os parâmetros necessários para as
     *            requisições
     */
    public static void setDitoSDKParameters(DitoSDKParameters params) {
        parameters = params;
    }

    /**
     * Método para recuperar os dados de um determinado usuário
     * 
     * @param listener
     *            Referência da interface <b>HttpConnectionListener<b> que será
     *            utilizada como callback da requisição
     * @exception DitoSDKException
     */
    public static void getDataForUserReference(final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            RequestParams params = new RequestParams();

            params.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            params.put(Constants.SIGNATURE, parameters.getSign());
            params.put(Constants.ENCODING, Constants.ENCODING_TYPE);

            String url = Constants.getServicesURLBase(parameters.isInDevMode()) + parameters.getReference();
            client.addHeader(Constants.ORIGIN, parameters.getDomain());

            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para adicionar e/ou atualizar informações do perfil de um
     * determinado usuário
     * 
     * @param dataToUpLoad
     *            Dados para serem adicionados e/ou atualizados no perfil do
     *            usuário
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será
     *            usada como callback da requisição
     */
    public static void putData(JSONObject dataToUpLoad, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            RequestParams params = new RequestParams();

            params.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());

            JSONObject userData = new JSONObject();
            StringEntity stringEntity = null;

            userData.put(Constants.USER_DATA, dataToUpLoad);
            userData.put(Constants.SIGNATURE, parameters.getSign());
            userData.put(Constants.ENCODING, Constants.ENCODING_TYPE);

            stringEntity = new StringEntity(userData.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getServicesURLBase(parameters.isInDevMode()) + parameters.getReference() + "?" + params.toString();
            client.addHeader(Constants.ORIGIN, parameters.getDomain());

            client.put(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para criar um evento que irá ser monitorado
     * 
     * @param event
     *            Evento a ser monitorado. Deve ser enviado em formato em json
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será
     *            usada como callback da requisição
     */
    public static void createEventWithData(String event, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            JSONObject jsonBody = new JSONObject();
            StringEntity stringEntity = null;

            jsonBody.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonBody.put(Constants.SIGNATURE, parameters.getSign());
            jsonBody.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonBody.put(Constants.EVENT, event);

            stringEntity = new StringEntity(jsonBody.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getEventsURLBase(parameters.isInDevMode()) + parameters.getReference();
            client.addHeader(Constants.ORIGIN, parameters.getDomain());

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para listar todos os amigos que realizaram um determinado
     * evento em comum.
     * 
     * @param events
     *            Array de eventos que serão retornados os usuários. Cada item
     *            do array deverá ser um objeto no formato JSON, contendo os
     *            seguintes atributos: action_name, target_type e target_id. 
     *            Ex:
     *            [{"action_name":"want_to_watch","target_type":"movie", "target_id":4112},
     *             {"action_name":"review","target_type":"movie","target_id":4112},
     *             {"action_name":"rate","target_type":"movie","target_id":4112},
     *             {"action_name":"buy","target_type":"movie","target_id":4112}]
     * 
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será usada
     *            como callback da requisição
     */
    public static void friendsWhoDidEvents(JSONArray events, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            JSONObject jsonBody = new JSONObject();
            StringEntity stringEntity = null;

            jsonBody.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonBody.put(Constants.SIGNATURE, parameters.getSign());
            jsonBody.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonBody.put(Constants.EVENTS, events);

            stringEntity = new StringEntity(jsonBody.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getEventsURLBase(parameters.isInDevMode()) + parameters.getReference() + "/friends/did";
            client.addHeader(Constants.ORIGIN, parameters.getDomain());

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método que retorna os feeds dos amigos de um determinado usuário
     * 
     * @param json
     *           Dados para ordenação e paginação no formato json:
     *           Ex:
     *              {
     *                  "limit" : 50, // Número de eventos
     *                  "page" : 1, // Página atual para ser exibida
     *                  "order" : "asc" // Ordenação "asc" ou "desc"
     *              }
     * 
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será usada
     *            como callback da requisição
     */
    public static void eventsFeedForUserReference(String json, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            RequestParams params = new RequestParams();

            params.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            params.put(Constants.SIGNATURE, parameters.getSign());
            params.put(Constants.ENCODING, Constants.ENCODING_TYPE);

            if (json != null && !TextUtils.isEmpty(json)) {
                JSONObject jsonParams = new JSONObject(json);

                params.put("limit", jsonParams.get("limit"));
                params.put("page", jsonParams.get("page"));
                params.put("order", jsonParams.get("order"));
            }

            String url = Constants.getEventsURLBase(parameters.isInDevMode()) + parameters.getReference() + "/friends";

            client.addHeader(Constants.ORIGIN, parameters.getDomain());
            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para realizar o login/inscrição na plataforma da DITO
     * 
     * @param network
     *            Referência ao enumerando NETWORKS
     *
     * @param networkAcronym
     *            Refência ao enumerando NETWORKS_ACRONYMS
     *
     * @param socialID
     *            ID indentificador do usuário na rede social
     *
     * @param accessToken
     *            Token de acesso fornecido ao usuário no momento do login em
     *            alguma rede social
     *
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será usada
     *            como callback da requisição
     */
    public static void signup(NETWORKS network, NETWORKS_ACRONYMS networkAcronym, String socialID, String accessToken, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            JSONObject jsonParams = new JSONObject();
            StringEntity stringEntity = null;

            jsonParams.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonParams.put(Constants.SIGNATURE, parameters.getSign());
            jsonParams.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonParams.put(Constants.NETWORK_NAME, networkAcronym.getValue());

            if (networkAcronym != NETWORKS_ACRONYMS.PORTAL) {
                jsonParams.put(Constants.ACCESS_TOKEN, accessToken);
            } else {
                jsonParams.put(Constants.USER_DATA, accessToken);
            }

            stringEntity = new StringEntity(jsonParams.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getServicesURLBase(parameters.isInDevMode()) + network.getValue() + "/" + socialID + "/signup";

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para registrar um dispositivo na Plataforma Dito para recebimento de
     * notificações
     * 
     * @param registrationID
     *            Valor retornado ao registrar o dispositivo no GCM
     *
     * @param listener
     *            Referência da interface <b>HttpConnectionListener<b> que será usada
     *            como callback da requisição
     */
    public static void registerToken(String registrationID, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            JSONObject jsonParams = new JSONObject();
            StringEntity stringEntity = null;

            jsonParams.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonParams.put(Constants.SIGNATURE, parameters.getSign());
            jsonParams.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonParams.put(Constants.TOKEN, registrationID);

            stringEntity = new StringEntity(jsonParams.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getNotificationUserURLBase(parameters.isInDevMode()) + parameters.getReference() + "/mobile-tokens";

            client.addHeader("ORIGIN", parameters.getDomain());

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para desativar o dispositivo na Plataforma Dito para recebimento
     * de notificações
     * 
     * @param registrationID
     *            Valor retornado ao registrar o dispositivo no GCM
     *
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será usada
     *            como callback da requisição
     */
    public static void unregisterToken(String registrationID, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            JSONObject jsonParams = new JSONObject();
            StringEntity stringEntity = null;

            jsonParams.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonParams.put(Constants.SIGNATURE, parameters.getSign());
            jsonParams.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonParams.put(Constants.TOKEN, registrationID);

            stringEntity = new StringEntity(jsonParams.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getNotificationUserURLBase(parameters.isInDevMode()) + parameters.getReference() + "/mobile-tokens/disable";

            client.addHeader("ORIGIN", parameters.getDomain());

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * 
     * @param message
     *            Conteúdo da notificação
     *
     * @param listener
     *            Referência da interface <b>HttpConnectionListener</b> que será usada
     *            como callback da requisição
     */
    public static void onNotificationReceived(String message, final HttpConnectionListener listener) throws DitoSDKException {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        try {
            String identifier = null;
            JSONObject json = new JSONObject(message);

            if (message != null && message.contains("data")) {
                identifier = json.getJSONObject("data").getString("notification");
            } else {
                if (listener != null) {
                    listener.onError(message);
                    return;
                }
            }

            JSONObject jsonParams = new JSONObject();
            StringEntity stringEntity = null;

            jsonParams.put(Constants.PLATFORM_API_KEY, parameters.getApiKey());
            jsonParams.put(Constants.SIGNATURE, parameters.getSign());
            jsonParams.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonParams.put(Constants.REFERENCE, parameters.getReference());
            jsonParams.put(Constants.IDENTIFIER, identifier);

            stringEntity = new StringEntity(jsonParams.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.getNotificationURLBase(parameters.isInDevMode()) + identifier + "/open";

            client.addHeader("ORIGIN", parameters.getDomain());

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String json = new String(responseBody);
                        listener.onSuccess(json);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError("Status Code: " + statusCode + " " + error.getMessage());
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * Método para gerar a assinatura que deverá ser utilizada nos métodos do
     * SDK
     * 
     * @param secretKey
     *            Chave secreta fornecida pela API da DITO
     * @param pubKeyString
     *            Conteúdo do arquivo .pem <b>SEM</b> os cabeçalhos
     * @return Chave assinada
     */
    @SuppressLint("TrulyRandom")
    @SuppressWarnings("resource")
    public static String getSignature(byte[] secretKey, String pubKeyString) {
        byte[] signature = null;

        try {
            byte[] decodedPublicKey = Base64.decode(pubKeyString, 0);

            ASN1InputStream in = new ASN1InputStream(decodedPublicKey);
            ASN1Primitive obj = in.readObject();

            org.bouncycastle.asn1.pkcs.RSAPublicKey keyStruct = org.bouncycastle.asn1.pkcs.RSAPublicKey.getInstance(obj);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(keyStruct.getModulus(), keyStruct.getPublicExponent());

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);

            signature = cipher.doFinal(secretKey);

            if (signature != null) {
                String sign = Base64.encodeToString(signature, Base64.DEFAULT);

                return sign;
            }

            return null;
        } catch (Exception ex) {
            Log.e("ERROR_GENERATE_SIGNATURE", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * Método para registrar um determinado dispositivo no GCM
     * 
     * @param context
     *            Contexto da aplicação
     *
     * @param listener
     *            Referência da interface <b>GCMListener</b> que será usada como
     *            callback da requisição
     */
    public static void registerOnGCM(Context context, GCMListener listener) {
        if (parameters == null) {
            throw new RuntimeException(PARAMETERS_INVALID);
        }

        GCMManager.registerOnGCM(context, parameters.getSenderID(), listener);
    }

    /**
     * Método para desativar recebimento de notificações no GCM
     * 
     * @param context
     *            Contexto da aplicação
     * @param listener
     *            Referência da interface <b>GCMListener<b> que será usada como
     *            callback da requisição
     */
    public static void unregisterOnGCM(Context context, GCMListener listener) {
        GCMManager.unregisterOnGCM(context, listener);
    }
}