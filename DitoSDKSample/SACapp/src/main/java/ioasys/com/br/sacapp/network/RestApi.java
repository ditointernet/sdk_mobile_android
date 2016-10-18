package ioasys.com.br.sacapp.network;

import ioasys.com.br.sacapp.constants.Constantes;
import ioasys.com.br.sacapp.services.model.ClientSatQuests;
import ioasys.com.br.sacapp.services.model.NewInteraction;
import ioasys.com.br.sacapp.services.model.NewSessionResponse;
import ioasys.com.br.sacapp.services.model.NewTokenResponse;
import retrofit.http.GET;
import retrofit.Callback;
import retrofit.http.Path;


/**
 * Created by ioasys on 24/08/15.
 */
public interface RestApi {

    @GET(Constantes.Services.NEW_TOKEN)
    void getToken(@Path("usuario") String token,
                  @Path("senha") String password,
                  Callback<NewTokenResponse> callBack);

    @GET(Constantes.Services.NEW_INTERACTION)
    void getNewInteraction(@Path("Token") String token,
                           @Path("app_id") int appId,
                           @Path("Ani") String tellUser,
                           @Path("Uuid") String widgetId,
                           @Path("platform") String platform,
                           @Path("version") String version,
                           @Path("model") String model,
                           Callback<NewInteraction> callback);

    @GET(Constantes.Services.NEW_SESSION)
    void getNewSession(@Path("token") String token,
                       @Path("interaction_id") int interactionId,
                       @Path("app_id") int appId,
                       @Path("routing_id") int routingId,
                       @Path("channel_id") int channelId,
                       @Path("custom_info1") String customInf1,
                       @Path("custom_info2") String customInf2,
                       @Path("custom_info3") String customInf3,
                       @Path("custom_info4") String customInf4,
                       @Path("custom_info5") String customInf5,
                       @Path("custom_info6") String customInf6,
                       @Path("custom_info7") String customInf7,
                       @Path("custom_info8") String customInf8,
                       @Path("custom_info9") String customInf9,
                       @Path("custom_info10") String customInf10,
                       Callback<NewSessionResponse> callback);

    @GET(Constantes.Services.CLIENT_SATISFACTION)
    void getClientSat(@Path("Token") String token,
                      @Path("app_id") int appId,
                      Callback<ClientSatQuests> callback);
}
