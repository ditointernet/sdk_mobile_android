package ioasys.com.br.sacapp.services.Requests;

import android.util.Log;

import ioasys.com.br.sacapp.network.RestClient;
import ioasys.com.br.sacapp.services.model.ClientSatQuests;
import ioasys.com.br.sacapp.services.model.NewInteraction;
import ioasys.com.br.sacapp.services.model.NewSessionResponse;
import ioasys.com.br.sacapp.services.model.NewTokenResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ioasys on 26/08/15.
 */
public class GeneralRequest {

    /**
     * @param token
     * @param appId
     * @param telUser
     * @param widgetId --> Id that uniquelly identify the widget
     * @param platform --> Optional
     * @param version --> Optional
     * @param model --> Optional
     */
    public static void newInteraction(String token,
                                      int appId,
                                      String telUser,
                                      String widgetId,
                                      String platform,
                                      String version,
                                      String model) {

        RestClient.get().getNewInteraction(token, appId, telUser, widgetId, platform, version, model, new Callback<NewInteraction>() {
            @Override
            public void success(NewInteraction newInteraction, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public static void requestToken(String user, String password){
        RestClient.get().getToken(user, password, new Callback<NewTokenResponse>() {
            @Override
            public void success(NewTokenResponse tokenResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public static void newSession(String token,
                                  int interactionId,
                                  int appId,
                                  int routingId,
                                  int channelId,
/*           */                   String customInf1,
/* \       / */                   String customInf2,
/*  \     /  */                   String customInf3,
/*   \   /   */                   String customInf4,
/*    \ /    */                   String customInf5,
/*     0     */                   String customInf6,
/*    / \    */                   String customInf7,
/*   /   \   */                   String customInf8,
/*  /     \  */                   String customInf9,
/* /       \ */                   String customInf10){

        RestClient.get().getNewSession(token, interactionId,
                appId,
                routingId,
                channelId,
                customInf1,
                customInf2,
                customInf3,
                customInf4,
                customInf5,
                customInf6,
                customInf7,
                customInf8,
                customInf9,
                customInf10,
                new Callback<NewSessionResponse>() {
                    @Override
                    public void success(NewSessionResponse newSessionResponse, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
    }

    public static void getClientSat(String token, int appId){
        RestClient.get().getClientSat(token, appId, new Callback<ClientSatQuests>() {
            @Override
            public void success(ClientSatQuests clientSatQuests, Response response) {
                Log.d("teste", "blabla");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("teste", "blabla");
            }
        });
    }


}
