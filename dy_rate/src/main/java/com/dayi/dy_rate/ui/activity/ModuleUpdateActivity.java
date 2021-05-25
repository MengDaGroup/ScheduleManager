package com.dayi.dy_rate.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.presenter.ModuleUpdatePresenter;
import com.dayi.dy_rate.utils.DatePickerHelper;
import com.dayi.dy_rate.utils.TeamPickerHelper;
import com.dayi35.qx_base.arouter.ARouterPath;
import com.dayi35.qx_base.base.state.BaseStateActivity;
import com.dayi35.qx_base.beans.EventBusEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.EventBusHelper;
import com.dayi35.qx_utils.common.DateUtil;
import com.dayi35.qx_utils.common.StatusBarUtil;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_utils.convert.RCaster;
import com.dayi35.qx_widget.pickers.picker.DatePicker;
import com.dayi35.qx_widget.pickers.picker.LinkagePicker;
import com.dayi35.qx_widget.titlebar.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/9 10:24
 * 描    述: 类        项目编辑 type 1.新增  2.修改(预览)
 * 修订历史:
 * =========================================
 */
@Route(path = ARouterPath.Rate.ModuleUpdate.PATH)
public class ModuleUpdateActivity extends BaseStateActivity<ModuleUpdatePresenter> implements UserContract.ModuleUpdateContract.View {
    @BindView(R2.id.rate_tb_project_edit) TitleBar mTbTitle;            //title
    @BindView(R2.id.rate_btn_save) Button mBtnAdd;                      //添加/修改
    @BindView(R2.id.rate_et_name) EditText mEtName;                     //项目名称
    @BindView(R2.id.rate_et_belong) TextView mEtBelong;                 //项目归属
    @BindView(R2.id.rate_et_starttime) TextView mEtStartTime;           //项目开始时间
    @BindView(R2.id.rate_et_endtime) TextView mEtEndTime;               //项目结束时间
    @BindView(R2.id.rate_et_note) EditText mEtNote;                     //项目备注

    @Autowired(name = ARouterPath.Rate.ModuleUpdate.Params.MODULEID)
    String mModuleId;                   //组件ID
    @Autowired(name = ARouterPath.Rate.ModuleUpdate.Params.PROJECTID)
    String mProjectId;                  //项目ID
    @Autowired(name = ARouterPath.Rate.ModuleUpdate.Params.STARTTIME)
    String mStartTime;                  //开始时间
    @Autowired(name = ARouterPath.Rate.ModuleUpdate.Params.ENDTIME)
    String mEndTime;                    //结束时间

    private LinkagePicker mPickerTeam;          //团队成员选择
    private String mBelongId;                   //归属者ID
    private DatePicker startPicker;             //开始选择器
    private DatePicker endPicker;               //结束选择器



    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_module_edit;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ModuleUpdatePresenter createPresent() {
        return new ModuleUpdatePresenter(this);
    }

    @Override
    protected RCaster createCaster() {
        return new RCaster(R.class, R2.class);
    }

    @Override
    protected void initView() {
        EventBusHelper.register(this);
        StatusBarUtil.transparent(this);
        StatusBarUtil.setDarkMode(this);
        mBtnAdd.setOnClickListener(v -> {
            //验证输入是否有误
            if (verifyInput()){
                getP().moduleCreate(mModuleId,
                        mEtName.getText().toString().trim(),
                        mBelongId,
                        mEtStartTime.getText().toString().trim(),
                        mEtEndTime.getText().toString().trim(), mProjectId,
                        mEtNote.getText().toString().trim());
            }
        });
        //开始时间选择
        mEtStartTime.setOnClickListener(v -> {
            //未获取到项目界面传过来的时间，给出提示
            if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)) {
//                if (null == startPicker) {
                    //如果先选择好了结束时间，那开始时间选择的范围为 项目的开始时间（mStartTime） - 已选择的项目结束时间（即已填充到布局的时间）
                    if (!TextUtils.isEmpty(mEtEndTime.getText().toString().trim())) {
                        startPicker = onTimeSelected(1, mStartTime, mEtEndTime.getText().toString().trim());
                    }else {
                        startPicker = onTimeSelected(1, mStartTime, mEndTime);
                    }
//                }
                startPicker.show();
            }else {
                ToastUtils.showShort("为获取到项目的起始时间");
            }
        });
        //结束时间选择
        mEtEndTime.setOnClickListener(v -> {
            //未获取到项目界面传过来的时间，给出提示
            if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)) {
//                if (null == endPicker) {
                    //判断是否已选择开始时间，如果选择了开始时间，得到的起始时间不能小于开始的起始时间 即范围为 已选择的开始时间-项目的结束时间
                    if (!TextUtils.isEmpty(mEtStartTime.getText().toString().trim())){
                        endPicker = onTimeSelected(2, mEtStartTime.getText().toString().trim(), mEndTime);
                    }else {
                        endPicker = onTimeSelected(2, mStartTime, mEndTime);
                    }
//                }
                endPicker.show();
            }
        });
        //归属者选择
        mEtBelong.setOnClickListener(v -> {
            if (null == mPickerTeam) {
                onTeamSelectClicked();
            }else {
                mPickerTeam.show();
            }
        });
    }

    @Override
    protected void initData() {
        //更新(预览)
        if (!TextUtils.isEmpty(mModuleId)) {
            mEtName.setEnabled(false);
            mEtBelong.setEnabled(false);
            mEtStartTime.setEnabled(false);
            mEtEndTime.setEnabled(false);
            mBtnAdd.setVisibility(View.GONE);
        }else {//添加
            mEtName.setEnabled(true);
            mEtBelong.setEnabled(true);
            mEtStartTime.setEnabled(true);
            mEtEndTime.setEnabled(true);
            mBtnAdd.setVisibility(View.VISIBLE);
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
            ToastUtils.showShort("请输入项目名称");
            return false;
        }else if (TextUtils.isEmpty(mEtBelong.getText().toString().trim())){
            ToastUtils.showShort("请输入项目归属者");
            return false;
        }else if (TextUtils.isEmpty(mEtStartTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目开始时间");
            return false;
        }else if (TextUtils.isEmpty(mEtEndTime.getText().toString().trim())){
            ToastUtils.showShort("请输入项目结束时间");
            return false;
        }else if (TextUtils.isEmpty(mEtNote.getText().toString().trim())){
            ToastUtils.showShort("请输入提交说明");
            return false;
        }
        return true;
    }

    @Override
    public void onDoSuccess(int type, String msg) {
        EventBusHelper.onSendCode(Constant.CodeEvent.RATE_MODULEUPDATE);
        this.finish();
    }

    /**
     * 团队成员选择点击
     */
    private void onTeamSelectClicked(){
        if (null == mPickerTeam){
            mPickerTeam = new TeamPickerHelper()
                    .setActivity(this)
                    .callback((first, second, thrid) -> {
                        mBelongId = second.getValue();
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
    private DatePicker onTimeSelected(int input, String start, String end){
        Date dateStart = DateUtil.parseDate(start, DateUtil.mFormat_date_24);
        String[] strStart = new SimpleDateFormat(DateUtil.mFormat_date_24).format(dateStart).split("-");
        Date dateEnd = DateUtil.parseDate(end, DateUtil.mFormat_date_24);
        String[] strEnd = new SimpleDateFormat(DateUtil.mFormat_date_24).format(dateEnd).split("-");
        return new DatePickerHelper()
                .startYear(Integer.valueOf(strStart[0]))
                .startMonth(Integer.valueOf(strStart[1]))
                .startDay(Integer.valueOf(strStart[2]))
                .endYear(Integer.valueOf(strEnd[0]))
                .endMonth(Integer.valueOf(strEnd[1]))
                .endDay(Integer.valueOf(strEnd[2]))
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventSubcriber(EventBusEntity entity) {
        switch (entity.getEventCode()){

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }
}
