package com.dayi.dy_rate.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.dayi.dy_rate.R;
import com.dayi.dy_rate.R2;
import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.presenter.ComponentCreatePresenter;
import com.dayi.dy_rate.utils.DatePickerHelper;
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
import com.dayi35.qx_widget.titlebar.TitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/17 15:18
 * 描    述: 类 功能创建/更新界面
 * 修订历史:
 * =========================================
 */
@Route(path = ARouterPath.Rate.ComponentUpdate.PATH)
public class ComponentUpdateActivity extends BaseStateActivity<ComponentCreatePresenter> implements UserContract.ComponentUpdateContract.View {
    @BindView(R2.id.rate_tb_project_edit) TitleBar mTbTitle;            //title
    @BindView(R2.id.rate_btn_save) Button mBtnAdd;                      //添加/修改
    @BindView(R2.id.rate_tv_project) EditText mEtName;                  //功能名称
    @BindView(R2.id.rate_et_starttime) TextView mEtStartTime;           //项目开始时间
    @BindView(R2.id.rate_et_endtime) TextView mEtEndTime;               //项目结束时间
    @BindView(R2.id.rate_et_progress) EditText mEtProgress;             //项目进度
    @BindView(R2.id.rate_et_note) EditText mEtNote;                     //项目备注
    @BindView(R2.id.rate_ll_create_input) LinearLayout mLLInput;        //输入容器
    @BindView(R2.id.rate_ll_progress) LinearLayout mLLProgress;         //进度

    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.PROJECTID)
    String mProjectId;      //项目ID
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.MODULEID)
    String mModuleId;       //组件ID
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.COMPONENTID)
    String mComponentId;    //功能ID
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.COMPONENTPROGRESS)
    float mProgress;        //进度
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.COMPONENTNAME)
    String mComponentName;  //功能名
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.STARTTIME)
    String mStartTime;      //组件的开始时间
    @Autowired(name = ARouterPath.Rate.ComponentUpdate.Params.ENDTIME)
    String mEndTime;        //组件的结束时间

    private DatePicker startPicker;             //开始选择器
    private DatePicker endPicker;               //结束选择器


    @Override
    protected int getMainLayoutId() {
        return R.layout.rate_activity_component_edit;
    }

    @Override
    protected void initStateLayout() {
        getStateManager();
    }

    @Override
    protected ComponentCreatePresenter createPresent() {
        return new ComponentCreatePresenter(this);
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
        //mComponentId不为空 说明是更新功能
        if (!TextUtils.isEmpty(mComponentId)){
            mLLInput.setVisibility(View.GONE);
            mEtNote.setVisibility(View.VISIBLE);
            mEtNote.setHint("请添加" + mComponentName + "更新描述");
            mLLProgress.setVisibility(View.VISIBLE);
            mEtProgress.setHint("当前进度" + mProgress);
            mTbTitle.setCenterText(TextUtils.isEmpty(mComponentName) ? "更新功能" : mComponentName);
        }else {
            mLLInput.setVisibility(View.VISIBLE);
            mEtNote.setVisibility(View.VISIBLE);
            mLLProgress.setVisibility(View.GONE);
        }
        mBtnAdd.setOnClickListener(v -> {
            //mComponentId不为空 说明是更新功能 ---> 只需要提交更新进度即可
            if (!TextUtils.isEmpty(mComponentId)) {
                if (!TextUtils.isEmpty(mEtProgress.getText().toString()) && !TextUtils.isEmpty(mEtNote.getText().toString())) {
                    getP().componentUpdateProgress(mComponentId, Integer.valueOf(mEtProgress.getText().toString().trim()), mEtNote.getText().toString().trim());
                }
            } else {//否则是创建功能
                if (verifyInput()) {
                    getP().componentCreate(mComponentId, mEtName.getText().toString().trim(), mProjectId,
                            mEtStartTime.getText().toString().trim(), mEtEndTime.getText().toString().trim(),
                            mModuleId, mEtNote.getText().toString().trim());
                }
            }
        });

        //开始时间选择
        mEtStartTime.setOnClickListener(v -> {
            //未获取到组件界面传过来的时间，给出提示
            if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)) {
//                if (null == startPicker) {
                    //如果先选择好了结束时间，那开始时间选择的范围为 组件的开始时间（mStartTime） - 已选择的组件结束时间（即已填充到布局的时间）
                    if (!TextUtils.isEmpty(mEtEndTime.getText().toString().trim())) {
                        startPicker = onTimeSelected(1, mStartTime, mEtEndTime.getText().toString().trim() + " 00:00:00");
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
            //未获取到组件界面传过来的时间，给出提示
            if (!TextUtils.isEmpty(mStartTime) && !TextUtils.isEmpty(mEndTime)) {
//                if (null == endPicker) {
                    //判断是否已选择开始时间，如果选择了开始时间，得到的起始时间不能小于开始的起始时间 即范围为 已选择的开始时间-组件的结束时间
                    if (!TextUtils.isEmpty(mEtStartTime.getText().toString().trim())){
                        endPicker = onTimeSelected(2, mEtStartTime.getText().toString().trim() + " 00:00:00", mEndTime);
                    }else {
                        endPicker = onTimeSelected(2, mStartTime, mEndTime);
                    }
//                }
                endPicker.show();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected TitleBar titleBar() {
        return mTbTitle;
    }

    /**
     * @param type      类型  1.创建    2.更新    --->    创建/更新   功能成功
     * @param msg       msg
     */
    @Override
    public void onComponentCreated(int type, String msg) {
        EventBusHelper.onSendCode(Constant.CodeEvent.RATE_COMPONENTUPDATE);
        this.finish();
    }

    /**
     * @param msg   msg --->    更新进度成功
     */
    @Override
    public void onUpdateProgress(String msg) {
        EventBusHelper.onSendCode(Constant.CodeEvent.RATE_PROGRESSUPDATE);
        this.finish();
    }

    /**
     * 验证创建输入
     * @return
     */
    private boolean verifyInput(){
        if (TextUtils.isEmpty(mEtName.getText().toString().trim())){
            ToastUtils.showShort("请输入项目名称");
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

    /**
     * 弹出时间选择器
     * @param input 1.开始时间  2.结束时间
     */
    private DatePicker onTimeSelected(int input, String start, String end){
        Date dateStart = DateUtil.parseDate(start, DateUtil.mFormat_24);
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
