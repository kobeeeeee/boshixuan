package www.chendanfeng.com.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.wechat.utils.WXMediaMessage;
import cn.sharesdk.wechat.utils.WXTextObject;
import www.chendanfeng.com.boishixuan.AboutActivity;
import www.chendanfeng.com.boishixuan.LoginActivity;
import www.chendanfeng.com.boishixuan.MainActivity;
import www.chendanfeng.com.boishixuan.PasswordActivity;
import www.chendanfeng.com.boishixuan.R;
import www.chendanfeng.com.boishixuan.RegisterActivity;
import www.chendanfeng.com.boishixuan.UsehelpActivity;
import www.chendanfeng.com.util.CommonUtil;
import www.chendanfeng.com.view.CustomDialog;
import www.chendanfeng.com.view.SharePopupWindow;

/**
 * Created by Administrator on 2016/7/2 0002.
 */
public class SettingFragment extends BaseFragment{
    private View mView;
    public static final int TYPE_ABOUT = 1;
    public static final int TYPE_SHARE = 2;
    public static final int TYPE_PHONE = 3;
    public static final int TYPE_LOGOUT = 4;
    public static final int TYPE_USEHELP = 5;
    public static final int TYPE_CLEAN = 6;
    public static final int CALL_PHONE_REQUEST_CODE = 10;
    @Bind(R.id.tv_head)
    TextView mHeader;
    @Bind(R.id.shareLayout)
    RelativeLayout share;
    @Bind(R.id.aboutLayout)
    RelativeLayout aboutus;
    @Bind(R.id.phoneLayout)
    RelativeLayout phone;
    @Bind(R.id.phone)
    TextView call;
    @Bind(R.id.logout)
    ImageView logoutButton;
    @Bind(R.id.usehelpLayout)
    RelativeLayout usehelp;

    public String phoneNum ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.bind(this, this.mView);
        initHeader();
        phoneNum =  call.getText().toString();
        share.setOnClickListener(new MyOnClickListener(TYPE_SHARE));
        aboutus.setOnClickListener(new MyOnClickListener(TYPE_ABOUT));
        phone.setOnClickListener(new MyOnClickListener(TYPE_PHONE));
        logoutButton.setOnClickListener(new MyOnClickListener(TYPE_LOGOUT));
        usehelp.setOnClickListener(new MyOnClickListener(TYPE_USEHELP));
        return this.mView;
    }

    private void initHeader() {
        this.mHeader.setVisibility(View.VISIBLE);
        this.mHeader.setText("设置");
    }

    class MyOnClickListener implements  View.OnClickListener {
        public int mType;
        public MyOnClickListener(int type) {
            this.mType = type;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (this.mType) {
                case TYPE_ABOUT:
                    intent = new Intent(getActivity(),AboutActivity.class);
                    startActivity(intent);
                    break;
                case TYPE_SHARE:
//                    String text = "哈喽";
//                    WXTextObject textObject = new WXTextObject();
//                    textObject.text = text;
//
//                    WXMediaMessage msg = new WXMediaMessage();
//                    msg.mediaObject = textObject;
//                    msg.description = text;

             //       SendMessageToWX.Req req = new SendMessageTpWX.Req();
                    SharePopupWindow popupwindow = new SharePopupWindow(getActivity(),null);
                   popupwindow.showAtLocation(getActivity().findViewById(R.id.shareLayout), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
                    break;
                case TYPE_PHONE:
                  //  final String phoneNum =  call.getText().toString();
                    StringBuffer message = new StringBuffer();
                    message.append("确定要拨打").append(phoneNum).append("么？");
                    CustomDialog.Builder builder=new CustomDialog.Builder(getActivity());
                    builder.setTitle("温馨提示");
                    builder.setMessage(message.toString());
                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                       }
                   });
                    builder.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},CALL_PHONE_REQUEST_CODE);
                                }
                                else {
                                    callPhone();
                                }
                            }
                            else{
                                callPhone();
                            }

                        }
                    });
                    builder.create().show();
                    break;
                case TYPE_LOGOUT:
                    CustomDialog.Builder buildeLogout=new CustomDialog.Builder(getActivity());
                    buildeLogout.setTitle("提示");
                    buildeLogout.setMessage("真的要退出吗，亲");
                    buildeLogout.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    buildeLogout.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            getActivity().finish();
                        }
                    });
                    buildeLogout.create().show();
                    break;
                case TYPE_USEHELP:
                    intent = new Intent(getActivity(),UsehelpActivity.class);
                    startActivity(intent);
                    break;
                case TYPE_CLEAN:
                    break;
            }
        }
    }

    public void callPhone()
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNum));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,
                                           int[] grantResults) {
        if (requestCode == CALL_PHONE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                callPhone();
            } else
            {
                // Permission Denied
                CommonUtil.showToast("Permission Denied",getActivity());
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
