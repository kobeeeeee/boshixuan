package www.chendanfeng.com.boishixuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.adapter.SelectBankAdapter;
import www.chendanfeng.com.bean.UserInfoBean;
import www.chendanfeng.com.config.Config;
import www.chendanfeng.com.network.RequestListener;
import www.chendanfeng.com.network.RequestManager;
import www.chendanfeng.com.network.model.BankDetailModel;
import www.chendanfeng.com.network.model.BankListResponse;
import www.chendanfeng.com.network.model.WithDrawResponse;
import www.chendanfeng.com.util.LogUtil;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class BankCardSelectActivity extends BaseActivity{
    @Bind(R.id.tv_head)
    TextView mHeader;
    @Bind(R.id.bar_left_btn)
    RelativeLayout mBackBtn;
    @Bind(R.id.addLayout)
    RelativeLayout mAddBtn;
    @Bind(R.id.selectBankCardListView)
    ListView mSelectBankListView;
    private SelectBankAdapter mAdapter;
    private NetWorkCallBack mNetWorkCallBack;
    private List<BankDetailModel> mBankDetailModelList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        initHeader();
        initListView();
        getData();
    }
    public void initHeader() {
        this.mHeader.setText("选择银行卡");
        this.mHeader.setVisibility(View.VISIBLE);
        this.mBackBtn.setVisibility(View.VISIBLE);
        this.mAddBtn.setVisibility(View.VISIBLE);
        this.mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankCardSelectActivity.this,BankCardAddActivity.class);
                startActivity(intent);
            }
        });
    }
    public void initListView() {

        this.mAdapter = new SelectBankAdapter(this,this.mBankDetailModelList);
        this.mSelectBankListView.setAdapter(this.mAdapter);
    }
    private void getData() {
        this.mNetWorkCallBack = new NetWorkCallBack();
        //传入参数
        Map<String,Object> map = new HashMap<>();
        RequestManager.getInstance().post(Config.URL + Config.SLASH, Config.BSX_BANK_LIST,map,BankCardSelectActivity.this.mNetWorkCallBack, BankListResponse.class);

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
            if(object instanceof BankListResponse) {
                BankListResponse bankListResponse = (BankListResponse)object;
                LogUtil.i(this,"bankListResponse = " + bankListResponse);
                BankCardSelectActivity.this.mBankDetailModelList = bankListResponse.bank_list;
                BankCardSelectActivity.this.mAdapter.setList(BankCardSelectActivity.this.mBankDetailModelList);
                BankCardSelectActivity.this.mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFailure(Object message) {

        }
    }
}
