package br.com.ioasys.dito_sdk.api;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import br.com.ioasys.dito_sdk.constants.Constants;
import br.com.ioasys.dito_sdk.exceptions.DitoSDKException;
import br.com.ioasys.dito_sdk.interfaces.HttpConnectionListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import android.annotation.SuppressLint;
import android.util.Base64;

/**
 * Classe contendo os m�todos da SDK
 * 
 * @author iOasys
 * @since 14-04-14
 * @version 1.0
 * 
 */
public class Services {
    private static AsyncHttpClient client = new AsyncHttpClient();

    /**
     * M�todo para recuperar os dados do usu�rio
     * 
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param reference
     *            Refer�ncia retornada no processo de <b>signup</b>
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void getDataForUserReference(String apiKey, String domain, String reference, String sign,
    		final HttpConnectionListener listener) throws DitoSDKException {
        try {
            RequestParams params = new RequestParams();

            params.put(Constants.PLATFORM_API_KEY, apiKey);
            params.put(Constants.SIGNATURE, sign);
            params.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            String url = Constants.SERVICES_URL_BASE + reference;
            client.addHeader(Constants.ORIGIN, domain);

            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * M�todo para adicionar e/ou atualizar informa��es do perfil de um usu�rio
     * 
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param reference
     *            Refer�ncia retornada no processo de <b>signup</b>
     * @param dataToUpLoad
     *            Dados para serem adicionados e/ou atualizados no perfil do
     *            usu�rio
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void putData(String apiKey, String domain, String reference, JSONObject dataToUpLoad, String sign,
    		final HttpConnectionListener listener) throws DitoSDKException {
        try {
            RequestParams params = new RequestParams();
            params.put(Constants.PLATFORM_API_KEY, apiKey);
            JSONObject userData = new JSONObject();
            StringEntity stringEntity = null;

            userData.put(Constants.USER_DATA, dataToUpLoad);
            userData.put(Constants.SIGNATURE, sign);
            userData.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            stringEntity = new StringEntity(userData.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.SERVICES_URL_BASE + reference + "?" + params.toString();
            client.addHeader(Constants.ORIGIN, domain);

            client.put(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * M�todo para criar um evento que ir� ser monitorado
     * 
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param reference
     *            Refer�ncia retornada no processo de <b>signup</b>
     * @param event
     *            Evento a ser monitorado. Deve ser enviado em formato em json
     * @param sign
     *            Assinatura a ser utilizada para assinatura da requisi��o
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void createEventWithData(String apiKey, String domain, String reference, String event, String sign, final HttpConnectionListener listener) throws DitoSDKException {
        try {
            JSONObject jsonBody = new JSONObject();
            StringEntity stringEntity = null;

            jsonBody.put(Constants.PLATFORM_API_KEY, apiKey);
            jsonBody.put(Constants.SIGNATURE, sign);
            jsonBody.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonBody.put(Constants.EVENT, event);

            stringEntity = new StringEntity(jsonBody.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.EVENTS_BASE_URL + reference;
            client.addHeader(Constants.ORIGIN, domain);

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }
    /**
     * M�todo para listar todos os amigos em comum que realizaram um determinado
     * evento em comum.
     * 
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param reference
     *            Refer�ncia retornada no processo de <b>signup</b>
     * @param sign
     *            Assinatura a ser utilizada para assinatura da requisi��o
     * @param events 
     * 			  Array de eventos que serão retornados os usuários. Cada item do array deverá ser um objeto no formato JSON,
     * 			  contendo os seguintes atributos: action_name, target_type e target_id. Ex:
     * 			  [{"action_name":"want_to_watch","target_type":"movie","target_id":4112},
     * 			   {"action_name":"review","target_type":"movie","target_id":4112},
     * 			   {"action_name":"rate","target_type":"movie","target_id":4112},
     * 			   {"action_name":"buy","target_type":"movie","target_id":4112}]
     * 
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void friendsWhoDidEvents(String apiKey, String domain, String reference, String sign, JSONArray events, final HttpConnectionListener listener) throws DitoSDKException {
        try {
            JSONObject jsonBody = new JSONObject();
            StringEntity stringEntity = null;

            jsonBody.put(Constants.SIGNATURE, sign);
            jsonBody.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonBody.put(Constants.PLATFORM_API_KEY, apiKey);
            jsonBody.put(Constants.EVENTS, events);

            stringEntity = new StringEntity(jsonBody.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.EVENTS_BASE_URL + reference + "/friends/did";
            client.addHeader(Constants.ORIGIN, domain);

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    // FIXME marcos.lacerda gerar metodo com passagem de JSON nao obrigatorio
    /**
     * M�todo que retorna os feeds dos amigos de um determinado usu�rio
     * 
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param reference
     *            Refer�ncia retornada no processo de <b>signup</b>
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void eventsFeedForUserReference(String apiKey, String domain, String reference, String sign,
    		final HttpConnectionListener listener) throws DitoSDKException {
        try {
            RequestParams params = new RequestParams();
            params.put(Constants.PLATFORM_API_KEY, apiKey);
            params.put(Constants.SIGNATURE, sign);
            params.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            
            String url = Constants.EVENTS_BASE_URL + reference + "/friends";

            client.addHeader(Constants.ORIGIN, domain);
            client.get(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * M�todo para realizar o login/inscri��o na plataforma da DITO
     * 
     * @param networkURLParam
     *            Par�metro contendo um dos valores dispon�veis para o m�todo:
     *            facebook, twitter, plus ou portal
     * @param network
     *            Par�metro que ir� no corpo da requisi��o contendo �
     *            abreviatura da rede social informada no par�metro anterior:
     *            fb, tw, pl ou pt
     * @param apiKey
     *            Chave de indentifica��o na plataforma da DITO.
     * @param socialID
     *            ID indentificador do usu�rio na rede social
     * @param accessToken
     *            Token de acesso fornecido ao usu�rio no momento do login em
     *            alguma rede social
     * @param domain
     *            Dom�nio Registrado na plataforma web
     * @param listener
     *            Refer�ncia da interface HttpConnectionListener que ser� usada
     *            como callback da requisi��o
     */
    public static void signup(String networkURLParam, String network, String apiKey, String socialID, String accessToken, String domain, String sign,
    		final HttpConnectionListener listener) throws DitoSDKException {
        try {
            JSONObject jsonParams = new JSONObject();
            StringEntity stringEntity = null;

            jsonParams.put(Constants.SIGNATURE, sign);
            jsonParams.put(Constants.ENCODING, Constants.ENCODING_TYPE);
            jsonParams.put(Constants.NETWORK_NAME, network);
            jsonParams.put(Constants.PLATFORM_API_KEY, apiKey);
            jsonParams.put(Constants.ACCESS_TOKEN, accessToken);

            stringEntity = new StringEntity(jsonParams.toString());
            stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, Constants.CONTENT_TYPE));

            String url = Constants.SERVICES_URL_BASE + networkURLParam + "/" + socialID + "/signup";

            client.addHeader("ORIGIN", domain);

            client.post(null, url, stringEntity, Constants.CONTENT_TYPE, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    if (listener != null) {
                        String sJSON = new String(responseBody);
                        listener.onSuccess(sJSON);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    if (listener != null) {
                        listener.onError(error.getMessage() + " status code: " + statusCode);
                    }
                }
            });
        } catch (Exception ex) {
            throw new DitoSDKException(ex.getMessage());
        }
    }

    /**
     * M�todo para gerar a assinatura que dever� ser utilizada nos m�todos do
     * SDK
     * 
     * @param secretKey
     *            Chave secreta fornecida pela API da DITO
     * @param pubKeyString
     *            Conte�do do arquivo .pem sem os cabe�alhos
     * @return Assinatura que dever� ser enviada ao servidor nas requisi��es
     */
    @SuppressLint("TrulyRandom")
    @SuppressWarnings("resource")
    public static byte[] getSignature(byte[] secretKey, String pubKeyString) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signature;
    }
}