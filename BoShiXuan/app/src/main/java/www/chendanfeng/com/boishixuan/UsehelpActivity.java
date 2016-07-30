package www.chendanfeng.com.boishixuan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import www.chendanfeng.com.adapter.MainTabFragmentAdapter;
import www.chendanfeng.com.fragment.ChargeFragment;
import www.chendanfeng.com.fragment.FinancingFragment;
import www.chendanfeng.com.fragment.WithdrawFragment;
import www.chendanfeng.com.util.LogUtil;

/**
 * Created by yangln10784 on 2016/7/30.
 */
public class UsehelpActivity extends BaseActivity {
    @Bind(R.id.tab_use)
    RadioGroup useGroup;
    @Bind(R.id.tab_charge)
    RadioButton chargeTab;
    @Bind(R.id.tab_financing)
    RadioButton financingTab;
    @Bind(R.id.tab_withdraw)
    RadioButton withdrawTab;

    private FragmentManager mFragmentManager;
    private ChargeFragment mChargeFragment;
    private FinancingFragment mFinancingFragment;
    private WithdrawFragment mWithdrawFragment;
    private List<Fragment> mFragmentList = new ArrayList<>();
    MainTabFragmentAdapter mTabFragmentAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usehelp);
        ButterKnife.bind(this);
        mFragmentManager = getSupportFragmentManager();
        initFragments();
    }
    private void initFragments(){
        mChargeFragment = new ChargeFragment();
        mFinancingFragment = new FinancingFragment();
        mWithdrawFragment = new WithdrawFragment();

        mFragmentList.add(mChargeFragment);
        mFragmentList.add(mFinancingFragment);
        mFragmentList.add(mWithdrawFragment);
        chargeTab.setChecked(true);
        chargeTab.setTextColor(getResources().getColor(R.color.coffee));
        mTabFragmentAdapter = new MainTabFragmentAdapter(this, mFragmentList, R.id.main_tab, useGroup,1);

    }
}
