package www.chendanfeng.com.boishixuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.adapter.SelectBankAdapter;

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
    private List<String> mBankNameList;
    private List<String> mBankNoList;
    private SelectBankAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        ButterKnife.bind(this);
        initHeader();
        initListView();
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
        this.mBankNameList = new ArrayList<>();
        this.mBankNameList.add("中国银行");
        this.mBankNameList.add("建设银行");
        this.mBankNameList.add("交通银行");
        this.mBankNameList.add("工商银行");
        this.mBankNameList.add("农业银行");

        this.mBankNoList = new ArrayList<>();
        this.mBankNoList.add("6217***********9442");
        this.mBankNoList.add("4581***********4900");
        this.mBankNoList.add("6123***********4567");
        this.mBankNoList.add("6890***********1234");
        this.mBankNoList.add("6456***********7890");
        this.mBankNoList.add("6123***********2345");

        this.mAdapter = new SelectBankAdapter(this,this.mBankNameList,this.mBankNoList);
        this.mSelectBankListView.setAdapter(this.mAdapter);
    }
}
