package com.dayi.dy_rate.utils;

import com.dayi.dy_rate.entity.TeamUserEntity;
import com.dayi35.qx_widget.pickers.picker.LinkagePicker;

import java.util.ArrayList;
import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/21 16:42
 * 描    述: 类    组 团队成员内容提供者
 * 修订历史:
 * =========================================
 */
public class TeamProvider implements LinkagePicker.DataProvider {
    //name
    private List<String> firstList = new ArrayList<>();
    private List<List<String>> secondList = new ArrayList<>();
    //value
    private List<String> areaIdfirstList = new ArrayList<>();
    private List<List<String>> areaIdsecondList = new ArrayList<>();

    public TeamProvider(List<TeamUserEntity> teamUserEntities) {
        parseData(teamUserEntities);
    }

    @Override
    public boolean isOnlyTwo() {
        return true;
    }

    @Override
    public List<String> provideFirstData() {
        return firstList;
    }

    @Override
    public List<String> provideFirstDataAreaId() {
        return areaIdfirstList;
    }

    @Override
    public List<String> provideSecondData(int firstIndex) {
        return secondList.get(firstIndex);
    }

    @Override
    public List<String> provideSecondDataAreaId(int firstIndex) {
        return areaIdsecondList.get(firstIndex);
    }

    @Override
    public List<String> provideThirdData(int firstIndex, int secondIndex) {
        return null;
    }

    @Override
    public List<String> provideThirdDataAreaId(int firstIndex, int secondIndex) {
        return null;
    }

    private void parseData(List<TeamUserEntity> data) {
        int count = data.size();
        //拆分第一层     --团队
        for (int i = 0; i < count; i++) {
            TeamUserEntity entity = data.get(i);
            firstList.add(entity.getLabel());
            areaIdfirstList.add(entity.getValue());
            List<TeamUserEntity.UserBean> listUser = entity.getUser();
            List<String> xSecondList = new ArrayList<>();
            List<String> xValueSecondList = new ArrayList<>();
            int userCount = listUser.size();
            //拆分第二层     --组员
            for (int j = 0; j < userCount; j++) {
                TeamUserEntity.UserBean userBean = listUser.get(j);
                xSecondList.add(userBean.getLabel());
                xValueSecondList.add(userBean.getValue());
            }
            secondList.add(xSecondList);
            areaIdsecondList.add(xValueSecondList);
        }


    }
}
