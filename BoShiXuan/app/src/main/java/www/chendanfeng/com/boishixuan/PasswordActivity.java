package www.chendanfeng.com.boishixuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.util.LogUtil;

/**
 * Created by yangln10784 on 2016/7/9.
 */
public class PasswordActivity extends BaseActivity {
    public static final int TYPE_MODIFY = 1;
    public static final int TYPE_GETCODE =2;
    @Bind(R.id.inputPhone)
    EditText phoneEditText;
    @Bind(R.id.inputPassword)
    EditText passwordEditText;
    @Bind(R.id.confirmPassword)
    EditText confirmPasswordEditText;
    @Bind(R.id.inputCode)
    EditText CodeEditText;
    @Bind(R.id.buttonCode)
    ImageView codeButton;
    @Bind(R.id.modify)
    ImageView modifyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        ButterKnife.bind(this);
        modifyButton.setOnClickListener(new MyOnClickListener(TYPE_MODIFY));
        codeButton.setOnClickListener(new MyOnClickListener(TYPE_GETCODE));
    }

    public static  boolean checkPhoneNumber(String phoneNumber){
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static  boolean checkPassword(String password){
        Pattern pattern = Pattern.compile(".*[a-zA-Z].*[0-9]|.*[0-9].*[a-zA-Z]");
        //Pattern pattern = Pattern.compile("^[A-Za-z0-9]{6,20}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    class MyOnClickListener implements  View.OnClickListener {
        public int mType;
        public MyOnClickListener(int type) {
            this.mType = type;
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Toast toast = null;
            switch (mType){
                case TYPE_MODIFY:
                    String password = passwordEditText.getText().toString();
                    String confirmPassword = confirmPasswordEditText.getText().toString();
                    if(!password.equals(confirmPassword)){
                        toast = Toast.makeText(PasswordActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    if(!checkPassword(password)){
                         toast = Toast.makeText(PasswordActivity.this,"密码长度为6-20位字母或有效数字组成",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    //TODO:判断输入框的验证码是否一致
                    toast = Toast.makeText(PasswordActivity.this,"登录密码修改成功！",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    intent.setClass(PasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                case TYPE_GETCODE:
                    String phoneNumber = phoneEditText.getText().toString();
                    if(!checkPhoneNumber(phoneNumber)) {
                        toast = Toast.makeText(PasswordActivity.this,"无效的手机号码",Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        break;
                    }
                    //TODO:调用接口获取验证码

            }
        }
    }
}
