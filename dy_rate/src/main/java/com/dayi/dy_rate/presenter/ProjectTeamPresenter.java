package com.dayi.dy_rate.presenter;

import android.text.TextUtils;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
import com.dayi.dy_rate.entity.ProjectRateEntity;
import com.dayi.dy_rate.entity.ProjectTeamEntity;
import com.dayi35.qx_base.base.mvp.BasePresentImpl;
import com.dayi35.qx_utils.common.ToastUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/4/8 9:58
 * 描    述: 类        项目组
 * 修订历史:
 * =========================================
 */
public class ProjectTeamPresenter extends BasePresentImpl<UserContract.ProjectTeamContract.View> implements UserContract.ProjectTeamContract.Presenter {
    public ProjectTeamPresenter(UserContract.ProjectTeamContract.View mView) {
        super(mView);
    }

    /**
     *
     * @param entity        增
     */
    @Override
    public void create(ProjectTeamEntity entity) {
        if (null != entity){
            mView.showLoading();
            entity.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    mView.hideLoading();
                    if (null == e){
                        ToastUtils.showShort("添加成功");
                        mView.onDoSuccess(1, null);
                    }else {
                        ToastUtils.showShort("添加失败");
                        mView.onFailed(1, e.getMessage());
                    }
                }
            });
        }
    }

    /**
     *
     * @param entity        改
     */
    @Override
    public void update(ProjectTeamEntity entity) {
        if (null != entity){
            mView.showLoading();
            entity.update(entity.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    mView.hideLoading();
                    if (null == e){
                        ToastUtils.showShort("更新成功");
                        mView.onDoSuccess(2, null);
                    }else {
                        ToastUtils.showShort("更新失败");
                        mView.onFailed(2, e.getMessage());
                    }
                }
            });
        }
    }

    /**
     * 查询
     * @param objectId      项目归属ID
     * @param os            端1.Android  2.IOS
     * @param state         状态1.进行中 2.已结束   3.已逾期
     */
    @Override
    public void search(String objectId, int os, int state) {
        mView.showLoading();
        BmobQuery<ProjectTeamEntity> query = new BmobQuery<ProjectTeamEntity>();
//        if (!TextUtils.isEmpty(objectId)) {
//            query.addWhereEqualTo("projectRateId", objectId);
//        }
//        if (0 != os){
//            query.addWhereEqualTo("projectOS", os);
//        }
//        if (0 != state){
//            query.addWhereEqualTo("projectState", state);
//        }
        query.findObjects(new FindListener<ProjectTeamEntity>() {
            @Override
            public void done(List<ProjectTeamEntity> list, BmobException e) {
                mView.hideLoading();
                if (null == e){
                    mView.onDoSuccess(3, list);
                }else {
                    ToastUtils.showShort("查询失败");
                    mView.onFailed(3, e.getMessage());
                }
            }
        });
    }

    /**
     * 查询项目实体列表
     */
    @Override
    public void searchRate() {
        mView.showLoading();
        BmobQuery<ProjectRateEntity> query = new BmobQuery<ProjectRateEntity>();
        query.findObjects(new FindListener<ProjectRateEntity>() {
            @Override
            public void done(List<ProjectRateEntity> list, BmobException e) {
                mView.hideLoading();
                if (null == e){
                    mView.onGetRateSuccess(list);
                }else {
                    ToastUtils.showShort("查询失败");
                    mView.onFailed(3, e.getMessage());
                }
            }
        });
    }
}
