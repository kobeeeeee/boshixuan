package www.chendanfeng.com.boishixuan;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.bean.UserInfoBean;
import www.chendanfeng.com.config.Config;
import www.chendanfeng.com.network.RequestListener;
import www.chendanfeng.com.network.RequestManager;
import www.chendanfeng.com.network.model.AccountBalanceResponse;
import www.chendanfeng.com.network.model.MessageListResponse;
import www.chendanfeng.com.network.model.OrderResponse;
import www.chendanfeng.com.network.model.RegularBuyResponse;
import www.chendanfeng.com.util.CommonUtil;
import www.chendanfeng.com.util.LogUtil;
import www.chendanfeng.com.view.CustomDialog;

/**
 * Created by Administrator on 2016/7/17 0017.
 */
public class RegularBuyActivity extends BaseActivity{
    @Bind(R.id.checkProtocolBtn)
    ImageView mCheckProtocolBtn;
    @Bind(R.id.tv_head)
    TextView mHeader;
    @Bind(R.id.bar_left_btn)
    RelativeLayout mBackBtn;
    @Bind(R.id.regularCheckbox)
    ImageView mRegularCheckbox;
    @Bind(R.id.regularBuyBtn)
    ImageView mRegularBuyBtn;
    @Bind(R.id.inputBuyMoney)
    EditText mInputBuyMoney;
    private boolean isChecked = false;
    private NetWorkCallBack mNetWorkCallBack;
    @Bind(R.id.regularBuyMin)
    TextView mRegularBuyMin;
    @Bind(R.id.regularYearIncome)
    TextView mRegularYearIncome;
    @Bind(R.id.regularBuyDay)
    TextView mRegularBuyDay;
    @Bind(R.id.accountBalance)
    TextView mAccountBalance;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_buy);
        ButterKnife.bind(this);
        initHeader();
        initClick();
        initData();
        getAccountBalance();
    }
    private void initHeader() {
        this.mHeader.setVisibility(View.VISIBLE);
        this.mHeader.setText("购买");
        this.mBackBtn.setVisibility(View.VISIBLE);
        this.mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData() {
        Intent intent = getIntent();
        String interestRate = intent.getStringExtra("interestRate");
        String investDay = intent.getStringExtra("investDay");
        String investMoney = intent.getStringExtra("investMoney");
        this.mRegularBuyDay.setText(investDay);
        this.mRegularBuyMin.setText(investMoney);
        this.mRegularYearIncome.setText(interestRate);
    }
    private void getAccountBalance() {
        Map<String,Object> map = new HashMap<>();
        UserInfoBean userInfoBean = UserInfoBean.getUserInfoBeanInstance();
        String userId = userInfoBean.getCustId();
        String userPhone = userInfoBean.getCustMobile();
        map.put("user_id",userId);
        map.put("user_phone",userPhone);
        RequestManager.getInstance().post(Config.URL + Config.SLASH, Config.BSX_BALANCE_STATISTIC,map,this.mNetWorkCallBack, AccountBalanceResponse.class);
    }
    private void initClick() {
        this.mNetWorkCallBack = new NetWorkCallBack();
        this.mCheckProtocolBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegularBuyActivity.this,PayProtocolActivity.class);
                startActivity(intent);
            }
        });
        this.mRegularCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isChecked) {
                    isChecked = false;
                    RegularBuyActivity.this.mRegularCheckbox.setImageResource(R.drawable.regular_checkbox_normal);
                } else {
                    String investDay = RegularBuyActivity.this.mRegularBuyDay.getText().toString();
                    String buyMoney = RegularBuyActivity.this.mInputBuyMoney.getText().toString();
                    String interestRate = RegularBuyActivity.this.mRegularYearIncome.getText().toString();
                    float dayNum = 0.0f;
                    float rate = 0.0f;
                    float money = 0.0f;
                    if(!TextUtils.isEmpty(investDay)) {
                        dayNum = Float.valueOf(investDay.substring(0,investDay.length()-1));
                    }
                    if(!TextUtils.isEmpty(interestRate)) {
                        rate = Float.valueOf(interestRate.substring(0,interestRate.length()-1));
                    }
                    if(!TextUtils.isEmpty(buyMoney)) {
                        money = Float.valueOf(buyMoney);
                    }
                    float income = dayNum/360 * rate * money/100;
                    String toastString = investDay + "后到期预计收益为" + String.valueOf(income) + "元";
                    CommonUtil.showToast(toastString,RegularBuyActivity.this);
                    isChecked = true;
                    RegularBuyActivity.this.mRegularCheckbox.setImageResource(R.drawable.regular_checkbox_press);
                }
            }
        });
        this.mRegularBuyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isChecked) {
                    CommonUtil.showToast("请先同意支付协议",RegularBuyActivity.this);
                    return;
                }
                String inputNumber = RegularBuyActivity.this.mInputBuyMoney.getText().toString();
                if(TextUtils.isEmpty(inputNumber)) {
                    CommonUtil.showToast("请输入购买金额",RegularBuyActivity.this);
                    return;
                }
                String accountBalance = RegularBuyActivity.this.mAccountBalance.getText().toString();
                if(Float.valueOf(inputNumber) > Float.valueOf(accountBalance)) {
                    StringBuffer message = new StringBuffer();
                    message.append("亲，请问需要充值吗");
                    CustomDialog.Builder builder=new CustomDialog.Builder(RegularBuyActivity.this);
                    builder.setTitle("账户余额不足");
                    builder.setMessage(message.toString());
                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("立刻充值",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(RegularBuyActivity.this,RechargeActivity.class);
                            startActivity(intent);
                        }
                    });
                    builder.create().show();
                    return;
                }
                confirmBuy();
            }
        });
    }
    private void confirmBuy() {
        Intent intent = getIntent();
        String productId = intent.getStringExtra("productId");
        String productName = intent.getStringExtra("productName");

        UserInfoBean userInfoBean = UserInfoBean.getUserInfoBeanInstance();
        String userId = userInfoBean.getCustId();
        String userPhone = userInfoBean.getCustMobile();
        String userName = userInfoBean.getUserName();
        String payPsw = userInfoBean.getPayPsw();
        String financeMoney = this.mInputBuyMoney.getText().toString();
        String investDay = this.mRegularBuyDay.getText().toString();
        String investMoney = this.mRegularBuyMin.getText().toString();
        String interestRate = this.mRegularYearIncome.getText().toString();

        //传入参数
        Map<String,Object> map = new HashMap<>();
        map.put("product_id",productId);
        map.put("finance_money",financeMoney);
        map.put("pay_passwd",payPsw);
        map.put("product_name",productName);
        map.put("interest_rate",interestRate);
        map.put("user_name",userName);
        map.put("invest_days",investDay);
        map.put("user_id",userId);
        map.put("user_phone",userPhone);
        RequestManager.getInstance().post(Config.URL + Config.SLASH, Config.BSX_PURCHASE_FINANCE,map,RegularBuyActivity.this.mNetWorkCallBack, RegularBuyResponse.class);

    }
    private class NetWorkCallBack implements RequestListener {

        @Override
        public void onBegin() {

        }

        @Override
        public void onResponse(Object object) {
            if(object == null) {
                return;
            }
            if(object instanceof RegularBuyResponse) {
                RegularBuyResponse regularBuyResponse = (RegularBuyResponse)object;
                LogUtil.i(this,"regularBuyResponse = " + regularBuyResponse);
            }

            if (object instanceof AccountBalanceResponse) {
                AccountBalanceResponse accountBalanceResponse = (AccountBalanceResponse)object;
                RegularBuyActivity.this.mAccountBalance.setText(accountBalanceResponse.balance);
            }
        }

        @Override
        public void onFailure(Object message) {
            String msg = (String) message;
            CommonUtil.showToast(msg,RegularBuyActivity.this);
        }
    }
}
