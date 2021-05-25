package com.dayi.dy_rate.entity;

import java.util.List;

/**
 * =========================================
 * 作    者: Akee
 * 创建日期: 2021/5/21 16:27
 * 描    述: 类    组  团队成员
 * 修订历史:
 * =========================================
 */
public class TeamUserEntity {

    /**
     * label : 雀喜项目组
     * value : 1
     * user : [{"label":"孔浩源","value":"016001329d0055a9b0"},{"label":"蔡健","value":"016001329d0055a9b1"}]
     */

    private String label;
    private String value;
    private List<UserBean> user;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * label : 孔浩源
         * value : 016001329d0055a9b0
         */

        private String label;
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
