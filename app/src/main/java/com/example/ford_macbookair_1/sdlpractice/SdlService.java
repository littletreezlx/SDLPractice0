package com.example.ford_macbookair_1.sdlpractice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.ford_macbookair_1.myapplication.R;
import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonPressResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.DiagnosticMessageResponse;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.MenuParams;
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
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowConstantTbtResponse;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.StreamRPCResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SdlService extends Service implements IProxyListenerALM {


    private static String TAG="SdlService";

    public int autoIncCorrId = 0;

    private SdlProxyALM proxy=null;

    private LockScreenManager lockScreenManager = new LockScreenManager();



    public SdlService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("id0","channel0", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            Notification serviceNotification = new Notification.Builder(this,"id0")
            .setContentTitle("TestTitle")
            .setSmallIcon(R.mipmap.ic_launcher)
//            .setLargeIcon(R.mipmap.ic_launcher_round)
            .setContentText("TestText")
            .setChannelId(channel.getId())
                    .build();
            startForeground(0, serviceNotification);
        }
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
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            if(notificationManager!=null){ //If this is the only notification on your channel
                notificationManager.deleteNotificationChannel("id0");
            }
            stopForeground(true);
        }

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
                setDisplayLayout();
                sendTextShowRPC();
                sendAddCommandRequest();




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


    //下载锁屏图像监听
    private class LockScreenDownloadedListener implements LockScreenManager.OnLockScreenIconDownloadedListener{

        @Override
        public void onLockScreenIconDownloaded(Bitmap icon) {
            Log.i(TAG, "Lock screen icon downloaded successfully");
        }

        @Override
        public void onLockScreenIconDownloadError(Exception e) {
            Log.e(TAG, "Couldn't download lock screen icon, resorting to default.");
        }
    }

    //接收head-unit请求下载lockScreenIcon
    @Override
    public void onOnSystemRequest(OnSystemRequest notification) {
        if(notification.getRequestType().equals(RequestType.LOCK_SCREEN_ICON_URL)){
            if(notification.getUrl() != null && lockScreenManager.getLockScreenIcon() == null){
                lockScreenManager.downloadLockScreenIcon(notification.getUrl(), new LockScreenDownloadedListener());
            }
        }
    }

    @Override
    public void onOnLockScreenNotification(OnLockScreenStatus notification) {
        if(notification.getHMILevel() == HMILevel.HMI_FULL && notification.getShowLockScreen() == LockScreenStatus.REQUIRED) {
            Intent showLockScreenIntent = new Intent(this, LockScreenActivity.class);
            showLockScreenIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if(lockScreenManager.getLockScreenIcon() != null){
                showLockScreenIntent.putExtra(LockScreenActivity.LOCKSCREEN_BITMAP_EXTRA, lockScreenManager.getLockScreenIcon());
            }
            startActivity(showLockScreenIntent);
        }else if(notification.getShowLockScreen() == LockScreenStatus.OFF){
            sendBroadcast(new Intent(LockScreenActivity.CLOSE_LOCK_SCREEN_ACTION));
        }
    }


    public void setDisplayLayout(){
        SetDisplayLayout setDisplayLayoutRequest = new SetDisplayLayout();
        setDisplayLayoutRequest.setDisplayLayout("LARGE_GRAPHIC_WITH_SOFTBUTTONS");
        setDisplayLayoutRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(((SetDisplayLayoutResponse) response).getSuccess()){
                    Log.i("SdlService", "Display layout set successfully.");
                    // Proceed with more user interface RPCs
                }else{
                    Log.i("SdlService", "Display layout request rejected.");
                }
            }
        });

        try{
            proxy.sendRPCRequest(setDisplayLayoutRequest);
        }catch (SdlException e){
            e.printStackTrace();
        }
    }


    //发送文本，超出显示范围则自动忽略
    public void sendTextShowRPC(){
        Show show = new Show();
        show.setMainField1("1");
        show.setMainField2("22");
        show.setMainField3("333");
        show.setMainField4("go");
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (((ShowResponse) response).getSuccess()) {
                    Log.i("SdlService", "Successfully showed.");
                } else {
                    Log.i("SdlService", "Show request was rejected.");
                }
            }
        });
        try {
            proxy.sendRPCRequest(show);
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }


        private byte[] contentsOfResource(int resource) {
            InputStream is = null;
            try {
                is = getResources().openRawResource(resource);
                ByteArrayOutputStream os = new ByteArrayOutputStream(is.available());
                final int bufferSize = 4096;
                final byte[] buffer = new byte[bufferSize];
                int available;
                while ((available = is.read(buffer)) >= 0) {
                    os.write(buffer, 0, available);
                }
                return os.toByteArray();
            } catch (IOException e) {
                Log.w(TAG, "Can't read icon file", e);
                return null;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }



    public void putFileRequest(){
        PutFile putFileRequest = new PutFile();
        putFileRequest.setSdlFileName("appIcon.jpeg");
        putFileRequest.setFileType(FileType.GRAPHIC_JPEG);
        putFileRequest.setPersistentFile(true);

        putFileRequest.setFileData(contentsOfResource(R.drawable.ic_launcher_background));
        putFileRequest.setCorrelationID(CorrelationIdGenerator.generateId());

        //持久化
        putFileRequest.setPersistentFile(true);

        putFileRequest.setOnRPCResponseListener(new OnRPCResponseListener() {

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                setListenerType(UPDATE_LISTENER_TYPE_PUT_FILE); // necessary for PutFile requests

                if(response.getSuccess()){
                    try {
                        proxy.setappicon("appIcon.jpeg", CorrelationIdGenerator.generateId());
                    } catch (SdlException e) {
                        e.printStackTrace();
                    }
                }else{
                    Log.i("SdlService", "Unsuccessful app icon upload.");
                }
            }
        });
        try {
            proxy.sendRPCRequest(putFileRequest);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }



    //发送图片，先用PutFileRPC,再用ShowRPC。注：有的汽车终端不支持图片
    public void sendGraphicShowRPC(){
        Image image = new Image();
        image.setImageType(ImageType.DYNAMIC);
        image.setValue("appImage.jpeg"); // a previously uploaded filename using PutFile RPC

        Show show = new Show();
        show.setGraphic(image);
        show.setCorrelationID(CorrelationIdGenerator.generateId());
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (((ShowResponse) response).getSuccess()) {
                    Log.i("SdlService", "Successfully showed.");
                } else {
                    Log.i("SdlService", "Show request was rejected.");
                }
            }
        });
        try {
            proxy.sendRPCRequest(show);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    //Check if a File Has Already Been Uploaded
    public void checkIsFileRepeated(){
        ListFiles listFiles = new ListFiles();
        listFiles.setCorrelationID(CorrelationIdGenerator.generateId());
        listFiles.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    List<String> filenames = ((ListFilesResponse) response).getFilenames();
                    if(filenames.contains("appIcon.jpeg")){
                        Log.i("SdlService", "App icon is already uploaded.");
                    }else{
                        Log.i("SdlService", "App icon has not been uploaded.");
                    }
                }else{
                    Log.i("SdlService", "Failed to request list of uploaded files.");
                }
            }
        });
        try{
            proxy.sendRPCRequest(listFiles);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    //Check the Amount of File Storage
    public void checkLeftFileStorage(){
        ListFiles listFiles = new ListFiles();
        listFiles.setCorrelationID(CorrelationIdGenerator.generateId());
        listFiles.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    Integer spaceAvailable = ((ListFilesResponse) response).getSpaceAvailable();
                    Log.i("SdlService", "Space available on Core = " + spaceAvailable);
                }else{
                    Log.i("SdlService", "Failed to request list of uploaded files.");
                }
            }
        });
        try{
            proxy.sendRPCRequest(listFiles);
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }

    public void deleteFileWithFileName(){
        DeleteFile deleteFileRequest = new DeleteFile();
        deleteFileRequest.setSdlFileName("appIcon.jpeg");
        deleteFileRequest.setCorrelationID(CorrelationIdGenerator.generateId());
        deleteFileRequest.setOnRPCResponseListener(new OnRPCResponseListener() {

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    Log.i("SdlService", "App icon deleted.");
                }else{
                    Log.i("SdlService", "Unable to delete app icon.");
                }
            }
        });
        try{
            proxy.sendRPCRequest(deleteFileRequest);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    public void setImageButton(){
        Image cancelImage = new Image();
        cancelImage.setImageType(ImageType.DYNAMIC);
        cancelImage.setValue("cancel.jpeg");

        List<SoftButton> softButtons = new ArrayList<>();

        SoftButton yesButton = new SoftButton();
        yesButton.setType(SoftButtonType.SBT_TEXT);
        yesButton.setText("Yes");
        yesButton.setSoftButtonID(0);

        SoftButton cancelButton = new SoftButton();
        cancelButton.setType(SoftButtonType.SBT_IMAGE);
        cancelButton.setImage(cancelImage);
        cancelButton.setSoftButtonID(1);

        softButtons.add(yesButton);
        softButtons.add(cancelButton);

        Show show = new Show();
        show.setSoftButtons(softButtons);
        show.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (((ShowResponse) response).getSuccess()) {
                    Log.i("SdlService", "Successfully showed.");
                } else {
                    Log.i("SdlService", "Show request was rejected.");
                }
            }
        });
        try {
            proxy.sendRPCRequest(show);
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }


    //
    public void sendSubscribeButtonRPC(){
        SubscribeButton subscribeButtonRequest = new SubscribeButton();
        subscribeButtonRequest.setButtonName(ButtonName.SEEKRIGHT);
        try {
            proxy.sendRPCRequest(subscribeButtonRequest);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOnButtonEvent(OnButtonEvent notification) {
        switch(notification.getButtonName()){
            case CUSTOM_BUTTON:
                //Custom buttons are the soft buttons previously added.
                int ID = notification.getCustomButtonID();
                Log.d("SdlService", "Button event received for button " + ID);
                break;
            case OK:
                break;
            case SEEKLEFT:
                break;
            case SEEKRIGHT:
                break;
            case TUNEUP:
                break;
            case TUNEDOWN:
                break;
            default:
                break;
        }
    }

    @Override
    public void onOnButtonPress(OnButtonPress notification) {
        switch(notification.getButtonName()){
            case CUSTOM_BUTTON:
                //Custom buttons are the soft buttons previously added.
                int ID = notification.getCustomButtonName();
                Log.d("SdlService", "Button press received for button " + ID);
                break;
            case OK:
                break;
            case SEEKLEFT:
                break;
            case SEEKRIGHT:
                break;
            case TUNEUP:
                break;
            case TUNEDOWN:
                break;
            default:
                break;
        }
    }

    public void sendAddCommandRequest(){
        MenuParams menuParams = new MenuParams();
        menuParams.setParentID(0);
        menuParams.setPosition(0);
        menuParams.setMenuName("aaaaaaaaaaaaaa");

        AddCommand addCommand = new AddCommand();
        addCommand.setCmdID(autoIncCorrId++);
        addCommand.setMenuParams(menuParams);

        try {
            proxy.sendRPCRequest(addCommand);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    public void sendDeleteCommandRequest(){
        int cmdID_to_delete = 1;

        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setCmdID(cmdID_to_delete);

        try {
            proxy.sendRPCRequest(deleteCommand);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    //先收到SubMenu的Resqonse才能再addItem
    public void addSubMenuRequest(){
        int unique_id = autoIncCorrId++;
        AddSubMenu addSubMenu = new AddSubMenu();
        addSubMenu.setPosition(0);
        addSubMenu.setMenuID(unique_id);
        addSubMenu.setMenuName("SubMenu");
        addSubMenu.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(((AddSubMenuResponse) response).getSuccess()){
                    // The submenu was created successfully, start adding the submenu items
                    // Use unique_id
                }else{
                    Log.i("SdlService", "AddSubMenu request rejected.");
                }
            }
        });
    }

    public void deleteSubMenuRequest(int submenuID_to_delete){
        DeleteSubMenu deleteSubMenu = new DeleteSubMenu();
        deleteSubMenu.setMenuID(submenuID_to_delete); // Replace with submenu ID to delete
    }



    public void sentChoiceSetRequest(){
        CreateInteractionChoiceSet choiceSet = new CreateInteractionChoiceSet();

        Choice choice = new Choice();
        int uniqueChoiceID=autoIncCorrId++;
        choice.setChoiceID(uniqueChoiceID);
        choice.setMenuName("ChoiceA");
        choice.setVrCommands(Arrays.asList("ChoiceA"));

        List<Choice> choiceList = new ArrayList<>();
        choiceList.add(choice);

        choiceSet.setChoiceSet(choiceList);
        int uniqueIntChoiceSetID = autoIncCorrId++;
        choiceSet.setInteractionChoiceSetID(uniqueIntChoiceSetID);
        choiceSet.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(((CreateInteractionChoiceSetResponse) response).getSuccess()){
                    // The request was successful, now send the SDLPerformInteraction RPC
                }else{
                    // The request was unsuccessful
                }
            }
        });
        try {
            proxy.sendRPCRequest(choiceSet);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    //在menu item发送后，再发送interaction RPC使item显示出来
    public void sendInteractionRequest(){
        List<Integer> interactionChoiceSetIDList = new ArrayList<>();
        int uniqueIntChoiceSetID=autoIncCorrId++;
        interactionChoiceSetIDList.add(uniqueIntChoiceSetID);

        PerformInteraction performInteraction = new PerformInteraction();
        performInteraction.setInitialText("Initial text.");
        performInteraction.setInteractionChoiceSetIDList(interactionChoiceSetIDList);

        performInteraction.setInteractionLayout(LayoutMode.LIST_ONLY);

        //语音提示
        performInteraction.setInitialPrompt(
                TTSChunkFactory.createSimpleTTSChunks("Hello, welcome."));
        //
        performInteraction.setTimeout(3000);
        performInteraction.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                PerformInteractionResponse piResponse = (PerformInteractionResponse) response;
                if(piResponse.getSuccess()){
                    // Successful request
                    if(piResponse.getResultCode().equals(Result.TIMED_OUT)){
                        // Interaction timed out without user input
                    }else if(piResponse.getResultCode().equals(Result.SUCCESS)){
                        Integer userChoice = piResponse.getChoiceID();
                    }
                }else{
                    // Unsuccessful request
                }
            }
        });

        try {
            proxy.sendRPCRequest(performInteraction);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    //删除交互选择的item
    public void deleteInteractionRequest(){
        DeleteInteractionChoiceSet deleteInteractionChoiceSet = new DeleteInteractionChoiceSet();
        int interactionChoiceSetID_to_delete=102;
        deleteInteractionChoiceSet.setInteractionChoiceSetID(interactionChoiceSetID_to_delete);

        try {
            proxy.sendRPCRequest(deleteInteractionChoiceSet);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    //Depending the platform, an alert can have up to three lines of text,
    // a progress indicator (e.g. a spinning wheel or hourglass),
    // and up to four soft buttons.
    public void alert(){
        Alert alert = new Alert();
        alert.setAlertText1("Alert Text 1");
        alert.setAlertText2("Alert Text 2");
        alert.setAlertText3("Alert Text 3");

// Maximum time alert appears before being dismissed
// Timeouts are must be between 3-10 seconds
// Timeouts may not work when soft buttons are also used in the alert
        alert.setDuration(5000);

// A progress indicator (e.g. spinning wheel or hourglass)
// Not all head units support the progress indicator
        alert.setProgressIndicator(true);

//Text to speech
//        alert.setTtsChunks(TTS_list); // TTS_list populated elsewhere

// Special tone played before the tts is spoken
        alert.setPlayTone(true);

// Soft buttons
//        alert.setSoftButtons(softButtons); // softButtons populated elsewhere

// Send alert
        try {
            proxy.sendRPCRequest(alert);
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }


    //检查是否支持图像
    public void checkGraphicSupported(){
        DisplayCapabilities displayCapabilities = null;
        try {
            displayCapabilities = proxy.getDisplayCapabilities();
        } catch (SdlException e) {
            e.printStackTrace();
        }
        Boolean graphicsSupported = displayCapabilities.getGraphicSupported();
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
