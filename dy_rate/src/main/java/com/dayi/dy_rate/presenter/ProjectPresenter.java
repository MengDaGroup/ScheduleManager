package com.dayi.dy_rate.presenter;

import android.text.TextUtils;

import com.dayi.dy_rate.contract.UserContract;
import com.dayi.dy_rate.entity.ProjectEntity;
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
 * 描    述: 类        项目
 * 修订历史:
 * =========================================
 */
public class ProjectPresenter extends BasePresentImpl<UserContract.ProjectContract.View> implements UserContract.ProjectContract.Presenter {
    public ProjectPresenter(UserContract.ProjectContract.View mView) {
        super(mView);
    }

    /**
     *
     * @param entity        增
     */
    @Override
    public void create(ProjectEntity entity) {
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
    public void update(ProjectEntity entity) {
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
     *
     * @param entity        查
     */
    @Override
    public void search(String searchId, ProjectEntity entity) {
        if (null != entity){
            mView.showLoading();
            BmobQuery<ProjectEntity> query = new BmobQuery<ProjectEntity>();
            if (!TextUtils.isEmpty(searchId)) {
                query.addWhereEqualTo("projectTeamId", searchId);
            }
            query.findObjects(new FindListener<ProjectEntity>() {
                @Override
                public void done(List<ProjectEntity> list, BmobException e) {
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
    }
}
