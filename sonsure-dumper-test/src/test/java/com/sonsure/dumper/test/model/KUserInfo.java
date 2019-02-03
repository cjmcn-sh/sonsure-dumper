package com.sonsure.dumper.test.model;

import com.sonsure.commons.model.Model;
import com.sonsure.dumper.core.annotation.Column;
import com.sonsure.dumper.core.annotation.Id;
import com.sonsure.dumper.core.annotation.Table;

import java.util.Date;

/**
 * 用户
 * <p>
 * UserInfo: liyd
 * Date: Wed Dec 24 16:46:48 CST 2014
 */
@Table("ktx_user_info")
public class KUserInfo extends Model {

    private static final long serialVersionUID = 8166785520231287816L;

    /**
     * 用户id
     */
    @Id
    @Column("USER_INFO_ID_")
    private Long userInfoId;

    /**
     * 登录名
     */
    @Column("LOGIN_NAME_")
    private String loginName;

    /**
     * 密码
     */
    @Column("PASSWORD_")
    private String password;

    /**
     * 年龄
     */
    @Column("USER_AGE_")
    private Integer userAge;

    /**
     * 创建时间
     */
    @Column("GMT_CREATE_")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column("GMT_MODIFY_")
    private Date gmtModify;

    public Long getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(Long userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

}