package com.dayi.dy_rate.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi35.qx_base.constant.Constant;
import com.dayi35.qx_base.utils.GsonHelper;
import com.dayi35.qx_utils.androidcodeutils.SPUtils;
import com.dayi35.qx_utils.common.ToastUtils;
import com.dayi35.qx_widget.pickers.entity.SimpleItemBean;
import com.dayi35.qx_widget.pickers.listeners.OnMoreItemPickListener;
import com.dayi35.qx_widget.pickers.picker.LinkagePicker;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/21 17:14
 * 描    述: 类    团队成员选择器辅助类
 * 修订历史:
 * =========================================
 */
public class TeamPickerHelper {

    private OnTeamPickerSelectedCallback callback;
    private Activity activity;

    public TeamPickerHelper callback(OnTeamPickerSelectedCallback callback){
        this.callback = callback;
        return this;
    }

    public TeamPickerHelper setActivity(Activity activity){
        this.activity = activity;
        return this;
    }

    public LinkagePicker init(){
        String json = SPUtils.getInstance().getString(Constant.KeySPUtils.RATE_SP_TEAM);
        if (!TextUtils.isEmpty(json)) {
            Type type = new TypeToken<List<TeamUserEntity>>(){}.getType();
            List<TeamUserEntity> teamUserEntities = GsonHelper.JSONToObj(json, type);
            if (verifyTeamData(teamUserEntities)) {
                TeamProvider provider = new TeamProvider(teamUserEntities);
                LinkagePicker picker = new LinkagePicker(activity, provider);
                picker.setCanLoop(false);
                picker.setLabel(null, null);
//        picker.setSelectedIndex(0, 8);
                picker.setOnMoreItemPickListener((OnMoreItemPickListener<SimpleItemBean>) (first, second, third) -> callback.onTeamSelected(first, second, third));
                return picker;
            }
        }
        return null;
    }

    /**
     * 判断工具的输入是否完善
     * @param teamUserEntities  团队数据
     * @return
     */
    private boolean verifyTeamData(List<TeamUserEntity> teamUserEntities){
        if (null == callback || null == activity){
            ToastUtils.showShort("数据设置不正确");
            return false;
        }else if (null == teamUserEntities || 0 ==teamUserEntities.size()){
            ToastUtils.showShort("为获取到团队数据");
            return false;
        }
        return true;
    }


    public interface OnTeamPickerSelectedCallback{
        void onTeamSelected(SimpleItemBean first, SimpleItemBean second, SimpleItemBean secondValue);
    }

}
