package com.example.ford_macbookair_1.sdlpractice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonPressResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnHashChange;
import com.smartdevicelink.proxy.rpc.OnInteriorVehicleData;
import com.smartdevicelink.proxy.rpc.OnKeyboardInput;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnLockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnStreamRPC;
import com.smartdevicelink.proxy.rpc.OnSystemRequest;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnTouchEvent;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.OnWayPointChange;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.transport.TransportConstants;

public class SdlService extends Service implements IProxyListenerALM {


    private SdlProxyALM proxy;


    public SdlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.createNotificationChannel(...);
//            Notification serviceNotification = new Notification.Builder(this, *Notification Channel*)
//            .setContentTitle(...)
//            .setSmallIcon(....)
//            .setLargeIcon(...)
//            .setContentText(...)
//            .setChannelId(channel.getId())
//                    .build();
//            startForeground(id, serviceNotification);
//        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        boolean forceConnect = intent !=null && intent.getBooleanExtra(TransportConstants.FORCE_TRANSPORT_CONNECTED, false);
        if (proxy == null) {
            try {
                proxy = new SdlProxyALM(this.getBaseContext(),this, "Hello SDL App", true, "8675309");
            } catch (SdlException e) {
                if (proxy == null) {
                    stopSelf();
                }
            }
        }else if(forceConnect){
            proxy.forceOnConnected();
        }


        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
//            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//            if(notificationManager!=null){ //If this is the only notification on your channel
//                notificationManager.deleteNotificationChannel(* Notification Channel*);
//            }
//            stopForeground(true);
//        }

        if (proxy != null) {
            try {
                proxy.dispose();
            } catch (SdlException e) {
                e.printStackTrace();
            } finally {
                proxy = null;
            }
        }
    }





    @Override
    public void onOnHMIStatus(OnHMIStatus onHMIStatus) {
        switch(onHMIStatus.getHmiLevel()) {
            case HMI_FULL:
                //send welcome message, addcommands, subscribe to buttons ect
                break;
            case HMI_LIMITED:
                break;
            case HMI_BACKGROUND:
                break;
            case HMI_NONE:
                break;
            default:
                return;
        }
    }

    @Override
    public void onProxyClosed(String s, Exception e, SdlDisconnectedReason sdlDisconnectedReason) {
        stopSelf();
    }

    @Override
    public void onServiceEnded(OnServiceEnded onServiceEnded) {

    }

    @Override
    public void onServiceNACKed(OnServiceNACKed onServiceNACKed) {

    }

    @Override
    public void onOnStreamRPC(OnStreamRPC onStreamRPC) {

    }

    @Override
    public void onStreamRPCResponse(StreamRPCResponse streamRPCResponse) {

    }

    @Override
    public void onError(String s, Exception e) {

    }

    @Override
    public void onGenericResponse(GenericResponse genericResponse) {

    }

    @Override
    public void onOnCommand(OnCommand onCommand) {

    }

    @Override
    public void onAddCommandResponse(AddCommandResponse addCommandResponse) {

    }

    @Override
    public void onAddSubMenuResponse(AddSubMenuResponse addSubMenuResponse) {

    }

    @Override
    public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse createInteractionChoiceSetResponse) {

    }

    @Override
    public void onAlertResponse(AlertResponse alertResponse) {

    }

    @Override
    public void onDeleteCommandResponse(DeleteCommandResponse deleteCommandResponse) {

    }

    @Override
    public void onDeleteInteractionChoiceSetResponse(DeleteInteractionChoiceSetResponse deleteInteractionChoiceSetResponse) {

    }

    @Override
    public void onDeleteSubMenuResponse(DeleteSubMenuResponse deleteSubMenuResponse) {

    }

    @Override
    public void onPerformInteractionResponse(PerformInteractionResponse performInteractionResponse) {

    }

    @Override
    public void onResetGlobalPropertiesResponse(ResetGlobalPropertiesResponse resetGlobalPropertiesResponse) {

    }

    @Override
    public void onSetGlobalPropertiesResponse(SetGlobalPropertiesResponse setGlobalPropertiesResponse) {

    }

    @Override
    public void onSetMediaClockTimerResponse(SetMediaClockTimerResponse setMediaClockTimerResponse) {

    }

    @Override
    public void onShowResponse(ShowResponse showResponse) {

    }

    @Override
    public void onSpeakResponse(SpeakResponse speakResponse) {

    }

    @Override
    public void onOnButtonEvent(OnButtonEvent onButtonEvent) {

    }

    @Override
    public void onOnButtonPress(OnButtonPress onButtonPress) {

    }

    @Override
    public void onSubscribeButtonResponse(SubscribeButtonResponse subscribeButtonResponse) {

    }

    @Override
    public void onUnsubscribeButtonResponse(UnsubscribeButtonResponse unsubscribeButtonResponse) {

    }

    @Override
    public void onOnPermissionsChange(OnPermissionsChange onPermissionsChange) {

    }

    @Override
    public void onSubscribeVehicleDataResponse(SubscribeVehicleDataResponse subscribeVehicleDataResponse) {

    }

    @Override
    public void onUnsubscribeVehicleDataResponse(UnsubscribeVehicleDataResponse unsubscribeVehicleDataResponse) {

    }

    @Override
    public void onGetVehicleDataResponse(GetVehicleDataResponse getVehicleDataResponse) {

    }

    @Override
    public void onOnVehicleData(OnVehicleData onVehicleData) {

    }

    @Override
    public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse performAudioPassThruResponse) {

    }

    @Override
    public void onEndAudioPassThruResponse(EndAudioPassThruResponse endAudioPassThruResponse) {

    }

    @Override
    public void onOnAudioPassThru(OnAudioPassThru onAudioPassThru) {

    }

    @Override
    public void onPutFileResponse(PutFileResponse putFileResponse) {

    }

    @Override
    public void onDeleteFileResponse(DeleteFileResponse deleteFileResponse) {

    }

    @Override
    public void onListFilesResponse(ListFilesResponse listFilesResponse) {

    }

    @Override
    public void onSetAppIconResponse(SetAppIconResponse setAppIconResponse) {

    }

    @Override
    public void onScrollableMessageResponse(ScrollableMessageResponse scrollableMessageResponse) {

    }

    @Override
    public void onChangeRegistrationResponse(ChangeRegistrationResponse changeRegistrationResponse) {

    }

    @Override
    public void onSetDisplayLayoutResponse(SetDisplayLayoutResponse setDisplayLayoutResponse) {

    }

    @Override
    public void onOnLanguageChange(OnLanguageChange onLanguageChange) {

    }

    @Override
    public void onOnHashChange(OnHashChange onHashChange) {

    }

    @Override
    public void onSliderResponse(SliderResponse sliderResponse) {

    }

    @Override
    public void onOnDriverDistraction(OnDriverDistraction onDriverDistraction) {

    }

    @Override
    public void onOnTBTClientState(OnTBTClientState onTBTClientState) {

    }

    @Override
    public void onOnSystemRequest(OnSystemRequest onSystemRequest) {

    }

    @Override
    public void onSystemRequestResponse(SystemRequestResponse systemRequestResponse) {

    }

    @Override
    public void onOnKeyboardInput(OnKeyboardInput onKeyboardInput) {

    }

    @Override
    public void onOnTouchEvent(OnTouchEvent onTouchEvent) {

    }

    @Override
    public void onDiagnosticMessageResponse(DiagnosticMessageResponse diagnosticMessageResponse) {

    }

    @Override
    public void onReadDIDResponse(ReadDIDResponse readDIDResponse) {

    }

    @Override
    public void onGetDTCsResponse(GetDTCsResponse getDTCsResponse) {

    }

    @Override
    public void onOnLockScreenNotification(OnLockScreenStatus onLockScreenStatus) {

    }

    @Override
    public void onDialNumberResponse(DialNumberResponse dialNumberResponse) {

    }

    @Override
    public void onSendLocationResponse(SendLocationResponse sendLocationResponse) {

    }

    @Override
    public void onShowConstantTbtResponse(ShowConstantTbtResponse showConstantTbtResponse) {

    }

    @Override
    public void onAlertManeuverResponse(AlertManeuverResponse alertManeuverResponse) {

    }

    @Override
    public void onUpdateTurnListResponse(UpdateTurnListResponse updateTurnListResponse) {

    }

    @Override
    public void onServiceDataACK(int i) {

    }

    @Override
    public void onGetWayPointsResponse(GetWayPointsResponse getWayPointsResponse) {

    }

    @Override
    public void onSubscribeWayPointsResponse(SubscribeWayPointsResponse subscribeWayPointsResponse) {

    }

    @Override
    public void onUnsubscribeWayPointsResponse(UnsubscribeWayPointsResponse unsubscribeWayPointsResponse) {

    }

    @Override
    public void onOnWayPointChange(OnWayPointChange onWayPointChange) {

    }

    @Override
    public void onGetSystemCapabilityResponse(GetSystemCapabilityResponse getSystemCapabilityResponse) {

    }

    @Override
    public void onGetInteriorVehicleDataResponse(GetInteriorVehicleDataResponse getInteriorVehicleDataResponse) {

    }

    @Override
    public void onButtonPressResponse(ButtonPressResponse buttonPressResponse) {

    }

    @Override
    public void onSetInteriorVehicleDataResponse(SetInteriorVehicleDataResponse setInteriorVehicleDataResponse) {

    }

    @Override
    public void onOnInteriorVehicleData(OnInteriorVehicleData onInteriorVehicleData) {

    }

    @Override
    public void onSendHapticDataResponse(SendHapticDataResponse sendHapticDataResponse) {

    }
}
