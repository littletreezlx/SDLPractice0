package com.example.ford_macbookair_1.sdlpractice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import android.os.Environment;
import android.os.IBinder;
import android.util.Log;


import com.smartdevicelink.exception.SdlException;
import com.smartdevicelink.proxy.LockScreenManager;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SdlProxyALM;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.callbacks.OnServiceEnded;
import com.smartdevicelink.proxy.callbacks.OnServiceNACKed;
import com.smartdevicelink.proxy.interfaces.IProxyListenerALM;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.AlertManeuverResponse;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
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
import com.smartdevicelink.proxy.rpc.DialNumber;
import com.smartdevicelink.proxy.rpc.DialNumberResponse;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetSystemCapabilityResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.GetWayPointsResponse;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.NavigationCapability;
import com.smartdevicelink.proxy.rpc.OasisAddress;
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
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SendHapticDataResponse;
import com.smartdevicelink.proxy.rpc.SendLocation;
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
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.SubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.SystemRequestResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeWayPointsResponse;
import com.smartdevicelink.proxy.rpc.UpdateTurnListResponse;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.PRNDL;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SdlDisconnectedReason;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnMultipleRequestListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.CorrelationIdGenerator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SdlService extends Service implements IProxyListenerALM {


    private static String TAG="SdlService";

    private boolean isFirstHMIFull = true, isChoiceSetSend = false;

    private int nowPictureId = 0;

    private int menuItemId = 0;

    private int lastPageCmdId=-1, nextPageCmdId=-1, listCmdId=-1, listChoiceSetId=-1;

    private List<PutFileInfo> putFileInfos=new ArrayList();

    private Map<Integer,String> cmdIdAndFileNameMap = new HashMap<>();

    private SdlProxyALM proxy=null;

    private LockScreenManager lockScreenManager = new LockScreenManager();

    private int enterMenuTimes = 0;


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
                proxy = new SdlProxyALM(this.getBaseContext(),this, "First SDL!!!", true, "8675309");
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


    private void sendRpcRequest(RPCRequest request){
        request.setCorrelationID(CorrelationIdGenerator.generateId());
        try {
            proxy.sendRPCRequest(request);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onOnHMIStatus(OnHMIStatus onHMIStatus) {
        switch (onHMIStatus.getSystemContext()){
            case SYSCTXT_MENU:

//                enterMenuTimes++;
//                if (enterMenuTimes%2 == 1){
//                    sendChoiceSetRequest();
//                }

                int i=1;
                break;
                default:break;
        }

        switch(onHMIStatus.getHmiLevel()) {
            case HMI_FULL:
                if (isFirstHMIFull){
                    setDisplayLayout();
                    setButton();
                    setButtonVR();


//                    addSubMenuRequest();
//                sendTextShowRPC();

                    initFileFromDCIM();
                    listFiles(putFileInfos);
                    addListCommand();
                    setListChoiceSet();
                    }
                    isFirstHMIFull =false;

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

    //接收head-unit请求
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


    public void setDisplayLayout() {
        SetDisplayLayout setDisplayLayoutRequest = new SetDisplayLayout();
        setDisplayLayoutRequest.setDisplayLayout("LARGE_GRAPHIC_WITH_SOFTBUTTONS");
        setDisplayLayoutRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (((SetDisplayLayoutResponse) response).getSuccess()) {
                    Log.i("SdlService", "Display layout set successfully.");
                    // Proceed with more user interface RPCs
                } else {
                    Log.i("SdlService", "Display layout request rejected.");
                }
            }
        });
        sendRpcRequest(setDisplayLayoutRequest);
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
        sendRpcRequest(show);

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


        public void initFileFromDCIM(){
//            File sdFile = getApplicationContext().getExternalCacheDir();
//            String s = Environment.getExternalStorageState();
            File dcimFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//            String path=dcimFile.getPath();
//            File file=new File(path);
            File files[] = dcimFile.listFiles();
            if(files != null)
                for(File f:files){
                if (!f.isDirectory()){
                    PutFileInfo putFileInfo = new PutFileInfo();
                    putFileInfo.setFilePath(f.getPath());
                    putFileInfo.setFileName(f.getName());
                    putFileInfos.add(putFileInfo);
                }
                }
        }

    public byte[] readFileDate(int id){
        File file=new File(putFileInfos.get(id).getFilePath());

        FileInputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            byte[] buffer = new byte[4096];
            int len = 0;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while( (len = in.read(buffer))!= -1){
                out.write(buffer, 0, len);
            }
            data = out.toByteArray();
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }



    public void putFile(final int id){
        final PutFile putFileRequest = new PutFile();
        putFileRequest.setSdlFileName(putFileInfos.get(id).getFileName());
        putFileRequest.setFileType(FileType.GRAPHIC_JPEG);
        putFileRequest.setFileData(readFileDate(id));
//        putFileRequest.setFileData(contentsOfResource(R.drawable.ic_launcher_background));
        putFileRequest.setCorrelationID(CorrelationIdGenerator.generateId());
        //持久化
        putFileRequest.setPersistentFile(true);
        putFileRequest.setOnRPCResponseListener(new OnRPCResponseListener() {

            @Override
            public void onResponse(int correlationId, RPCResponse response) {
//                setListenerType(UPDATE_LISTENER_TYPE_PUT_FILE); // necessary for PutFile requests
                if(response.getSuccess()){
                    PutFileInfo putFileInfo=putFileInfos.get(id);
                    putFileInfo.setReady(true);
                    if (id == 0 ){
                        showGraphic(putFileInfos.get(0).getFileName());
                    }
                }
            }
        });
        sendRpcRequest(putFileRequest);
    }

    @Override
    public void onPutFileResponse(PutFileResponse response) {
//        if(response.getSuccess()){
//            PutFileInfo putFileInfo=putFileInfos.get(id);
//            putFileInfo.setReady(true);
//
//            if (id == 0 ){
//                showGraphic(putFileInfos.get(0).getFileName());
//            }
//        }else{
//            Log.i("SdlService", "Unsuccessful app icon upload.");
//        }
    }




    //发送图片，先用PutFileRPC,再用ShowRPC。注：有的汽车终端不支持图片
    public void showGraphic(String fileName){
        Image image = new Image();
        image.setImageType(ImageType.DYNAMIC);
        image.setValue(fileName); // a previously uploaded filename using PutFile RPC

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

        sendRpcRequest(show);
    }



    public void sendDisorderedRequests(){
        List<RPCRequest> rpcs= new ArrayList<>();



        try {
            proxy.sendRequests(rpcs, new OnMultipleRequestListener() {
                @Override
                public void onUpdate(int i) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onError(int i, Result result, String s) {

                }

                @Override
                public void onResponse(int i, RPCResponse rpcResponse) {

                }
            });
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    public void sendSequentialRequests(){
        List<RPCRequest> rpcs = new ArrayList<>();

        try {
            proxy.sendSequentialRequests(rpcs, new OnMultipleRequestListener() {
                @Override
                public void onUpdate(int i) {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onError(int i, Result result, String s) {

                }

                @Override
                public void onResponse(int i, RPCResponse rpcResponse) {

                }
            });
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }


    //Check if a File Has Already Been Uploaded
    public void listFiles(final List<PutFileInfo> putFileInfos){
        ListFiles listFiles = new ListFiles();
        sendRpcRequest(listFiles);
    }

    @Override
    public void onListFilesResponse(ListFilesResponse response) {
        if(response.getSuccess()){
//            response.getSpaceAvailable();
            List<String> filenames = response.getFilenames();
            for (int i =0; i<putFileInfos.size(); i++) {
                PutFileInfo putFileInfo = putFileInfos.get(i);
                if (filenames.contains(putFileInfo.getFileName())) {
                    putFileInfo.setReady(true);
                } else {
                    putFile(i);
                }
            }
            if (nowPictureId == 0){
                showGraphic(putFileInfos.get(0).getFileName());
            }
        }
    }

    public void deleteFileWithFileName(String fileName){
        DeleteFile deleteFileRequest = new DeleteFile();
        deleteFileRequest.setSdlFileName(fileName);
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

        sendRpcRequest(deleteFileRequest);
    }


    public void setButton(){
//        Image cancelImage = new Image();
//        cancelImage.setImageType(ImageType.DYNAMIC);
//        cancelImage.setValue("cancel.jpeg");
        List<SoftButton> softButtons = new ArrayList<>();

        SoftButton lastPageBtn = new SoftButton();
        lastPageBtn.setType(SoftButtonType.SBT_TEXT);
        lastPageBtn.setText("Last Page");
//        lastPageBtn.setText("上一张");
        lastPageBtn.setSoftButtonID(1);
//        lastPageBtn.setIsHighlighted(true);


        SoftButton nextPageBtn = new SoftButton();
        nextPageBtn.setType(SoftButtonType.SBT_TEXT);
        nextPageBtn.setText("Next Page");
//        lastPageBtn.setText("下一张");
        nextPageBtn.setSoftButtonID(2);

        softButtons.add(lastPageBtn);
        softButtons.add(nextPageBtn);
        Show show = new Show();
        show.setSoftButtons(softButtons);
        sendRpcRequest(show);
    }


    //
    public void sendSubscribeButtonRPC(){
        SubscribeButton subscribeButtonRequest = new SubscribeButton();
        subscribeButtonRequest.setButtonName(ButtonName.SEEKRIGHT);

        sendRpcRequest(subscribeButtonRequest);
    }

    @Override
    public void onOnButtonEvent(OnButtonEvent notification) {
        switch(notification.getButtonName()){
            case CUSTOM_BUTTON:
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

    public void setButtonVR(){
        AddCommand addCommand = new AddCommand();
        lastPageCmdId = CorrelationIdGenerator.generateId();
        addCommand.setCmdID(lastPageCmdId);
        addCommand.setVrCommands(Arrays.asList("Last Page"));
        sendRpcRequest(addCommand);
        AddCommand addCommand2 = new AddCommand();
        nextPageCmdId = CorrelationIdGenerator.generateId();
        addCommand2.setCmdID(nextPageCmdId);
        addCommand2.setVrCommands(Arrays.asList("Next Page"));

        sendRpcRequest(addCommand2);
    }

    @Override
    public void onOnCommand(OnCommand onCommand) {
        int cmdID = onCommand.getCmdID();
        if (cmdID == lastPageCmdId){
            showLastPage();
        }else if (cmdID == nextPageCmdId){
            showLastPage();
        }else if (cmdID == listCmdId){
            performListChoiceset(listChoiceSetId);
        }

    }

    @Override
    public void onOnButtonPress(OnButtonPress notification) {
        switch(notification.getButtonName()){
            case CUSTOM_BUTTON:
                //Custom buttons are the soft buttons previously added.
                int ID = notification.getCustomButtonName();
                Log.d("SdlService", "Button event received for button " + ID);
                switch (ID) {
                    case 1:
                       showLastPage();
                        break;
                    case 2:
                        showNextPage();
                        break;
                    default:break;
                }
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

    public void showLastPage(){

        if (nowPictureId == 0) {
            // 显示到顶
            showAlert();
        } else {
            nowPictureId--;
            PutFileInfo putFileInfo = putFileInfos.get(nowPictureId);
            if (putFileInfo.getReady()) {
                showGraphic(putFileInfo.getFileName());
            }
        }
    }


    public void showNextPage() {
        if (nowPictureId == putFileInfos.size() - 1) {
            // 显示到底
        } else {
            nowPictureId++;
            PutFileInfo putFileInfo = putFileInfos.get(nowPictureId);
            if (putFileInfo.getReady()) {
                showGraphic(putFileInfo.getFileName());
            }
        }
    }


    public void addListCommand(){
        MenuParams menuParams = new MenuParams();
        menuParams.setParentID(0);
        menuParams.setPosition(0);
        menuParams.setMenuName("Memu");

        AddCommand addCommand = new AddCommand();
        listCmdId = CorrelationIdGenerator.generateId();
        addCommand.setCmdID(listCmdId);
        addCommand.setMenuParams(menuParams);
        addCommand.setVrCommands(Arrays.asList("List"));

        sendRpcRequest(addCommand);
    }


    @Override
    public void onAddCommandResponse(AddCommandResponse addCommandResponse) {

    }


    public void setMenuItem(String fileName){

        MenuParams menuParams = new MenuParams();
        menuParams.setParentID(0);
        menuParams.setPosition(menuItemId++);
        menuParams.setMenuName(fileName);

        Image image = new Image();
        image.setImageType(ImageType.DYNAMIC);
        image.setValue(fileName);

        AddCommand addCommand = new AddCommand();
        int cmdId = CorrelationIdGenerator.generateId();
        addCommand.setCmdID(cmdId);
        addCommand.setMenuParams(menuParams);
        addCommand.setCmdIcon(image);
        addCommand.setVrCommands(Arrays.asList(fileName));

        if (!cmdIdAndFileNameMap.containsValue(fileName)){
            cmdIdAndFileNameMap.put(cmdId,fileName);

        }
        sendRpcRequest(addCommand);
    }


    public void deleteMenuItem(int cmdId){

        DeleteCommand deleteCommand = new DeleteCommand();
        deleteCommand.setCmdID(cmdId);

        try {
            proxy.sendRPCRequest(deleteCommand);
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    //先收到SubMenu的Resqonse才能再addItem
    public void addSubMenuRequest(){
//        int unique_id = CorrelationIdGenerator.generateId();
        AddSubMenu addSubMenu = new AddSubMenu();
        addSubMenu.setPosition(0);
        addSubMenu.setMenuID(1);
        addSubMenu.setMenuName("list");
        addSubMenu.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(((AddSubMenuResponse) response).getSuccess()){
                    // The submenu was created successfully, start adding the submenu items
                    // Use unique_id

//                    for (int i=0 ;i<putFileInfos.size(); i++){
//                        setMenuItems(putFileInfos.get(i).getFileName());
//                    }
                }else{
                    Log.i("SdlService", "AddSubMenu request rejected.");
                }
            }
        });

        sendRpcRequest(addSubMenu);
    }

    public void deleteSubMenuRequest(int submenuID_to_delete){
        DeleteSubMenu deleteSubMenu = new DeleteSubMenu();
        deleteSubMenu.setMenuID(submenuID_to_delete); // Replace with submenu ID to delete
    }

    public void setListChoiceSet(){
        CreateInteractionChoiceSet choiceSet = new CreateInteractionChoiceSet();
        List<Choice> choiceList = new ArrayList<>();

        for (int i=0; i<putFileInfos.size(); i++){
            String fileName = putFileInfos.get(i).getFileName();
            Image image = new Image();
            image.setImageType(ImageType.DYNAMIC);
            image.setValue(fileName);
            Choice choice = new Choice();
            choice.setChoiceID(i);
            choice.setImage(image);
            choice.setMenuName(fileName);
            choice.setVrCommands(Arrays.asList(fileName));
            choiceList.add(choice);
        }

        choiceSet.setChoiceSet(choiceList);
        listChoiceSetId = CorrelationIdGenerator.generateId();
        choiceSet.setInteractionChoiceSetID(listChoiceSetId);

        sendRpcRequest(choiceSet);
    }

    @Override
    public void onCreateInteractionChoiceSetResponse(CreateInteractionChoiceSetResponse response) {
        if(response.getSuccess()){
            isChoiceSetSend = true;
        }else{
            // The request was unsuccessful
        }
    }


    //在menu item发送后，再发送interaction RPC使item显示出来
    public void performListChoiceset(int uniqueIntChoiceSetID){
        List<Integer> interactionChoiceSetIDList = new ArrayList<>();
        interactionChoiceSetIDList.add(uniqueIntChoiceSetID);

        PerformInteraction performInteraction = new PerformInteraction();
        performInteraction.setInitialText("Perform");
        performInteraction.setInteractionChoiceSetIDList(interactionChoiceSetIDList);

        performInteraction.setInteractionLayout(LayoutMode.LIST_ONLY);
        performInteraction.setInteractionMode(InteractionMode.BOTH);

//        performInteraction.setInitialPrompt(TTSChunkFactory.createSimpleTTSChunks("hello"));
        performInteraction.setTimeout(8000);

        sendRpcRequest(performInteraction);
    }

    @Override
    public void onPerformInteractionResponse(PerformInteractionResponse piResponse) {
        if(piResponse.getSuccess()){
            if(piResponse.getResultCode().equals(Result.TIMED_OUT)){
            }else if(piResponse.getResultCode().equals(Result.SUCCESS)){
                Integer userChoice = piResponse.getChoiceID();
                showGraphic(putFileInfos.get(userChoice).getFileName());
            }
        }else{
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


    //Depending the platform, an showAlert can have up to three lines of text,
    // a progress indicator (e.g. a spinning wheel or hourglass),
    // and up to four soft buttons.
    public void showAlert(){
        Alert alert = new Alert();
        alert.setAlertText1("已到顶");

// Maximum time showAlert appears before being dismissed
// Timeouts are must be between 3-10 seconds
// Timeouts may not work when soft buttons are also used in the showAlert
        alert.setDuration(3000);

// A progress indicator (e.g. spinning wheel or hourglass)
// Not all head units support the progress indicator
        alert.setProgressIndicator(true);

//Text to speech
//        showAlert.setTtsChunks(TTS_list); // TTS_list populated elsewhere

// Special tone played before the tts is spoken
        alert.setPlayTone(true);

// Soft buttons
//        showAlert.setSoftButtons(softButtons); // softButtons populated elsewhere

        sendRpcRequest(alert);

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
    public void onAddSubMenuResponse(AddSubMenuResponse addSubMenuResponse) {

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
    public void onEndAudioPassThruResponse(EndAudioPassThruResponse endAudioPassThruResponse) {

    }

    @Override
    public void onDeleteFileResponse(DeleteFileResponse deleteFileResponse) {

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
    //
    public void getVehicleData(){
        GetVehicleData vdRequest = new GetVehicleData();
        vdRequest.setPrndl(true);
        vdRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    PRNDL prndl = ((GetVehicleDataResponse) response).getPrndl();
                    Log.i("SdlService", "PRNDL status: " + prndl.toString());
                }else{
                    Log.i("SdlService", "GetVehicleData was rejected.");
                }
            }
        });
        sendRpcRequest(vdRequest);
    }

    //订阅车辆信息，大概每秒返回一次
    public void subscribeVehicleData() {
        SubscribeVehicleData subscribeRequest = new SubscribeVehicleData();
        subscribeRequest.setPrndl(true);
        subscribeRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if (response.getSuccess()) {
                    Log.i("SdlService", "Successfully subscribed to vehicle data.");
                } else {
                    Log.i("SdlService", "Request to subscribe to vehicle data was rejected.");
                }
            }
        });
        sendRpcRequest(subscribeRequest);
    }

    @Override
    public void onOnVehicleData(OnVehicleData notification) {
        PRNDL prndl = notification.getPrndl();
        Log.i("SdlService", "PRNDL status was updated to: "+prndl.toString());
    }

    //取消订阅车辆信息
    public void unsubscribeVehicleData(){
        UnsubscribeVehicleData unsubscribeRequest = new UnsubscribeVehicleData();
        unsubscribeRequest.setPrndl(true); // unsubscribe to PRNDL data
        unsubscribeRequest.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                if(response.getSuccess()){
                    Log.i("SdlService", "Successfully unsubscribed to vehicle data.");
                }else{
                    Log.i("SdlService", "Request to unsubscribe to vehicle data was rejected.");
                }
            }
        });

        sendRpcRequest(unsubscribeRequest);
    }


    //检查导航仪是否可用
    public void checkIsNavigationAvailable(){
        try {
            if(proxy.getHmiCapabilities().isNavigationAvailable()){
                // SendLocation supported
            }else{
                // SendLocation is not supported
            }
        } catch (SdlException e) {
            e.printStackTrace();
        }
    }

    //标记目的地
    public void  sendLocation(){
        SendLocation sendLocation = new SendLocation();
        sendLocation.setLatitudeDegrees(42.877737);
        sendLocation.setLongitudeDegrees(-97.380967);
        sendLocation.setLocationName("The Center");
        sendLocation.setLocationDescription("Center of the United States");

// Create Address
        OasisAddress address = new OasisAddress();
        address.setSubThoroughfare("900");
        address.setThoroughfare("Whiting Dr");
        address.setLocality("Yankton");
        address.setAdministrativeArea("SD");
        address.setPostalCode("57078");
        address.setCountryCode("US-SD");
        address.setCountryName("United States");

        sendLocation.setAddress(address);

// Monitor response
        sendLocation.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Result result = response.getResultCode();
                if(result.equals(Result.SUCCESS)){
                    // SendLocation was successfully sent.
                }else if(result.equals(Result.INVALID_DATA)){
                    // The request you sent contains invalid data and was rejected.
                }else if(result.equals(Result.DISALLOWED)){
                    // Your app does not have permission to use SendLocation.
                }
            }
        });

        sendRpcRequest(sendLocation);
    }



    //检查打电话是否可用
    public void checkIsPhoneCallAvailable() {
        try {
            if (proxy.getHmiCapabilities().isPhoneCallAvailable()) {
                // DialNumber supported
            } else {
                // DialNumber is not supported
            }
        } catch (SdlException e) {
            e.printStackTrace();
        }

    }

    public void dialNumber(){
        DialNumber dialNumber = new DialNumber();
        dialNumber.setNumber("1238675309");
        dialNumber.setOnRPCResponseListener(new OnRPCResponseListener() {
            @Override
            public void onResponse(int correlationId, RPCResponse response) {
                Result result = response.getResultCode();
                if(result.equals(Result.SUCCESS)){
                    // `DialNumber` was successfully sent, and a phone call was initiated by the user.
                }else if(result.equals(Result.REJECTED)){
                    // `DialNumber` was sent, and a phone call was cancelled by the user. Also, this could mean that there is no phone connected via Bluetooth.
                }else if(result.equals(Result.DISALLOWED)){
                    // Your app does not have permission to use DialNumber.
                }
            }
        });

        sendRpcRequest(dialNumber);
    }


    //
    public void performAudioPassThru(){
        PerformAudioPassThru performAPT = new PerformAudioPassThru();
        performAPT.setAudioPassThruDisplayText1("Ask me \"What's the weather?\"");
        performAPT.setAudioPassThruDisplayText2("or \"What's 1 + 2?\"");

        performAPT.setInitialPrompt(TTSChunkFactory
                .createSimpleTTSChunks("Ask me What's the weather? or What's 1 plus 2?"));
        performAPT.setSamplingRate(SamplingRate._22KHZ);
        performAPT.setMaxDuration(7000);
        performAPT.setBitsPerSample(BitsPerSample._16_BIT);
        performAPT.setAudioType(AudioType.PCM);
        performAPT.setMuteAudio(false);

        sendRpcRequest(performAPT);
    }

    public void endAudioPassThru(){
        EndAudioPassThru endAPT = new EndAudioPassThru();
        sendRpcRequest(endAPT);
    }

    @Override
    public void onOnAudioPassThru(OnAudioPassThru notification) {

        byte[] dataRcvd;
        dataRcvd = notification.getAPTData();

        // Do something with audio data
    }

    @Override
    public void onPerformAudioPassThruResponse(PerformAudioPassThruResponse response) {
        Result result = response.getResultCode();
        if(result.equals(Result.SUCCESS)){
            // We can use the data
        }else{
            // Cancel any usage of the data
            Log.e("SdlService", "Audio pass thru attempt failed.");
        }
    }


    public void getNavigationCapabilities(){
        // First you can check to see if the capability is supported on the module
        if (proxy.isCapabilitySupported(SystemCapabilityType.NAVIGATION)){
            // Since the module does support this capability we can query it for more information
            proxy.getCapability(SystemCapabilityType.NAVIGATION, new OnSystemCapabilityListener(){

                @Override
                public void onCapabilityRetrieved(Object capability){
                    NavigationCapability navCapability = (NavigationCapability) capability;
                    // Now it is possible to get details on how this capability
                    // is supported using the navCapability object
                }

                @Override
                public void onError(String info){
                    Log.i(TAG, "Capability could not be retrieved: "+ info);
                }
            });
        }
    }

    public void getButtonCapabilities() {

        proxy.getCapability(SystemCapabilityType.BUTTON, new OnSystemCapabilityListener(){

            @Override
            public void onCapabilityRetrieved(Object capability){
                List<ButtonCapabilities> buttonCapabilityList = SystemCapabilityManager.convertToList(capability, ButtonCapabilities.class);

            }

            @Override
            public void onError(String info){
                Log.i(TAG, "Capability could not be retrieved: "+ info);
            }
        });
    }




}
