package com.dayi.dy_rate.ui.activity;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.presenter.ProjectUpdatePresenter;
import com.dayi.dy_rate.utils.DatePickerHelper;
import com.dayi.dy_rate.utils.TeamPickerHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.BottomPopEntity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.beans.KeyValueEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.pop.BottomCommonPop;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.pickers.picker.DatePicker;
import com.dayi35.qx_widget.pickers.picker.LinkagePicker;
import com.dayi35.qx_widget.titlebar.TitleBar;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/9 10:24
 * 描    述: 类        项目创建/修改 type 1.新增  2.修改
 * 修订历史:
 * =========================================
 */
@Route(path = ARouterPath.Rate.ProjectUpdate.PARH)
public class ProjectUpdateActivity extends BaseStateActivity<ProjectUpdatePresenter> implements UserContract.ProjectContract.CreateView {

    @BindView(R2.id.rate_tb_project_edit) TitleBar mTbTitle;            //title
    @BindView(R2.id.rate_btn_save) Button mBtnAdd;                      //添加/修改
    @BindView(R2.id.rate_et_name) EditText mEtName;                     //项目名称
    @BindView(R2.id.rate_et_belong) TextView mEtBelong;                 //项目归属
    @BindView(R2.id.rate_tv_os) TextView mTvOs;
    @BindView(R2.id.rate_et_starttime) TextView mEtStartTime;           //项目开始时间
    @BindView(R2.id.rate_et_endtime) TextView mEtEndTime;               //项目结束时间
    @BindView(R2.id.rate_et_note) EditText mEtNoted;                   //项目备注

    private LinkagePicker mPickerTeam;          //团队成员选择
    private String mBelongId;                   //归属者ID
    private String mTeamId;                     //团队ＩＤ
    private String mOS = "1";                   //端(默认Android)
    private DatePicker startPicker;             //开始选择器
    private DatePicker endPicker;               //结束选择器

    private List<BottomPopEntity> mListOs = new ArrayList<>();           //os
    private BottomCommonPop.Builder mPopOSSelect;       //OS选择弹窗


    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_project_edit;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ProjectUpdatePresenter createPresent() {
        return new ProjectUpdatePresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        EventBusHelper.register(this);
        //添加
        mBtnAdd.setOnClickListener(v -> {
            if (verifyInput()){
                getP().projectCreate(1, mEtName.getText().toString().trim(),
                        mBelongId,
                        mEtStartTime.getText().toString().trim(),
                        mEtEndTime.getText().toString().trim(),
                        Integer.valueOf(mOS),
                        mTeamId,
                        mEtNoted.getText().toString().trim());
            }
        });
        //开始时间选择
        mEtStartTime.setOnClickListener(v -> {
            if (null == startPicker) {
                startPicker = onTimeSelected(1);
            }
            startPicker.show();
        });
        //结束时间选择
        mEtEndTime.setOnClickListener(v -> {
            if (null == endPicker) {
                endPicker = onTimeSelected(2);
            }
            endPicker.show();
        });
        //归属者选择
        mEtBelong.setOnClickListener(v -> {
            if (null == mPickerTeam) {
                onTeamSelectClicked();
            }else {
                mPickerTeam.show();
            }
        });
        //端选择
        mTvOs.setOnClickListener(v -> {
            if (null == mPopOSSelect){
                if (0 < mListOs.size()){
                    mPopOSSelect = new BottomCommonPop.Builder(ProjectUpdateActivity.this)
                            .create(mTvOs)
                            .init(mListOs, (value, pos) -> {
                                mOS = ((KeyValueEntity)mListOs.get(pos).getData()).getValue();
                                mTvOs.setText(((KeyValueEntity)mListOs.get(pos).getData()).getKey());
                            });
                }
            }
            mPopOSSelect.show();
        });
    }

    @Override
    protected void initData() {
        //构建底部弹窗
        String jsonOS = SPUtils.getInstance().getString(Constant.KeySPUtils.RATE_SP_OS);
        if (!TextUtils.isEmpty(jsonOS)){
            Type type = new TypeToken<List<KeyValueEntity>>(){}.getType();
            List<KeyValueEntity> teamUserEntities = GsonHelper.JSONToObj(jsonOS, type);
            if (0 < teamUserEntities.size()) {
                //去掉第一条数据--->不限
                teamUserEntities.remove(0);
                for (KeyValueEntity entity : teamUserEntities
                ) {
                    BottomPopEntity<KeyValueEntity> bottomPopEntity = new BottomPopEntity<>(entity.getKey());
                    bottomPopEntity.setData(entity);
                    mListOs.add(bottomPopEntity);
                }
            }
        }
    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    /**
     * 验证输入
     * @return
     */
    private boolean verifyInput(){
        if (TextUtils.isEmpty(mEtName.getText().toString().trim())){
            ToastUtils.showShort("请输入项目名");
            return false;
        }else if (TextUtils.isEmpty(mBelongId)){
            ToastUtils.showShort("请选择项目归属者");
            return false;
        }else if (TextUtils.isEmpty(mEtStartTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目开始时间");
            return false;
        }else if (TextUtils.isEmpty(mEtEndTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目结束时间");
            return false;
        }else if (TextUtils.isEmpty(mEtNoted.getText().toString().trim())){
            ToastUtils.showShort("请输入项目说明");
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }

    /**
     * 创建/更新    成功
     * @param code  1.创建    2.更新
     * @param msg
     */
    @Override
    public void onCommonSuccess(int code, String msg) {
        EventBusHelper.onSendCode(Constant.CodeEvent.RATE_PROJECTTEAMUPDATE);
        this.finish();
    }

    /**
     * 团队成员选择点击
     */
    private void onTeamSelectClicked(){
        if (null == mPickerTeam){
            mPickerTeam = new TeamPickerHelper()
                    .setActivity(this)
                    .callback((first, second, third) -> {
                        mBelongId = second.getValue();
                        mTeamId = first.getValue();
                        mEtBelong.setText(first.getLabel() + "-" + second.getLabel());
                    })
                    .init();
            mPickerTeam.show();
        }
    }

    /**
     * 弹出时间选择器
     * @param input 1.开始时间  2.结束时间
     */
    private DatePicker onTimeSelected(int input){
        Calendar calendar = Calendar.getInstance();
        return new DatePickerHelper()
                .startYear(calendar.get(Calendar.YEAR))
                .startMonth(calendar.get(Calendar.MONTH) + 1)
                .startDay(calendar.get(Calendar.DAY_OF_MONTH) + 1)
                .endYear(calendar.get(Calendar.YEAR) + 4)
                .endMonth(1)
                .endDay(1)
                .type(input)
                .title(1 == input ? "开始时间" : "结束时间")
                .callback((type, date) -> {
                    if (1 == type){
                        mEtStartTime.setText(date);
                    }else {
                        mEtEndTime.setText(date);
                    }
                })
                .init(this);
    }
}
