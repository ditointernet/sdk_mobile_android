package br.com.dito.ditosdk.services;

import android.content.Context;
import android.text.TextUtils;

import br.com.dito.ditosdk.R;
import br.com.dito.ditosdk.constant.Constants;
import br.com.dito.ditosdk.exception.DitoSDKException;
import br.com.dito.ditosdk.interfaces.DitoSDKCallback;

/**
 * Created by gabriel.araujo on 06/04/15.
 */
public class BaseService {

    protected static boolean verifyConfigure(DitoSDKCallback callback) throws DitoSDKException{
        if (Constants.configure == null || (TextUtils.isEmpty(Constants.configure.getApiKey()) || TextUtils.isEmpty(Constants.configure.getSecret()))){
            if (verifyCallback(callback)){
                callback.onError(new Exception(Constants.Mensagens.CONFIGURE_NOT_FOUND));
            }else{
                throw new DitoSDKException(Constants.Mensagens.CONFIGURE_NOT_FOUND);
            }
            return false;
        }else{
            return true;
        }
    }

    protected static boolean verifyCallback(DitoSDKCallback callback){
        if (callback == null){
            return false;
        }else{
            return true;
        }
    }

    protected static boolean verifyReference() throws DitoSDKException{
        if (Constants.getNetworks().equalsIgnoreCase("reference")){
            throw new DitoSDKException(Constants.Mensagens.ERROR_REFERENCE);
        }else{
            return true;
        }
    }
}
