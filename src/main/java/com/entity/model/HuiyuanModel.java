package com.entity.model;

import com.entity.HuiyuanEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 会员
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class HuiyuanModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 会员编号
     */
    private String huiyuanUuidNumber;


    /**
     * 会员姓名
     */
    private String huiyuanName;


    /**
     * 会员手机号
     */
    private String huiyuanPhone;


    /**
     * 会员身份证号
     */
    private String huiyuanIdNumber;


    /**
     * 会员头像
     */
    private String huiyuanPhoto;


    /**
     * 性别
     */
    private Integer sexTypes;


    /**
     * 余额
     */
    private Double newMoney;


    /**
     * 会员邮箱
     */
    private String huiyuanEmail;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：会员编号
	 */
    public String getHuiyuanUuidNumber() {
        return huiyuanUuidNumber;
    }


    /**
	 * 设置：会员编号
	 */
    public void setHuiyuanUuidNumber(String huiyuanUuidNumber) {
        this.huiyuanUuidNumber = huiyuanUuidNumber;
    }
    /**
	 * 获取：会员姓名
	 */
    public String getHuiyuanName() {
        return huiyuanName;
    }


    /**
	 * 设置：会员姓名
	 */
    public void setHuiyuanName(String huiyuanName) {
        this.huiyuanName = huiyuanName;
    }
    /**
	 * 获取：会员手机号
	 */
    public String getHuiyuanPhone() {
        return huiyuanPhone;
    }


    /**
	 * 设置：会员手机号
	 */
    public void setHuiyuanPhone(String huiyuanPhone) {
        this.huiyuanPhone = huiyuanPhone;
    }
    /**
	 * 获取：会员身份证号
	 */
    public String getHuiyuanIdNumber() {
        return huiyuanIdNumber;
    }


    /**
	 * 设置：会员身份证号
	 */
    public void setHuiyuanIdNumber(String huiyuanIdNumber) {
        this.huiyuanIdNumber = huiyuanIdNumber;
    }
    /**
	 * 获取：会员头像
	 */
    public String getHuiyuanPhoto() {
        return huiyuanPhoto;
    }


    /**
	 * 设置：会员头像
	 */
    public void setHuiyuanPhoto(String huiyuanPhoto) {
        this.huiyuanPhoto = huiyuanPhoto;
    }
    /**
	 * 获取：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 设置：性别
	 */
    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 获取：余额
	 */
    public Double getNewMoney() {
        return newMoney;
    }


    /**
	 * 设置：余额
	 */
    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }
    /**
	 * 获取：会员邮箱
	 */
    public String getHuiyuanEmail() {
        return huiyuanEmail;
    }


    /**
	 * 设置：会员邮箱
	 */
    public void setHuiyuanEmail(String huiyuanEmail) {
        this.huiyuanEmail = huiyuanEmail;
    }
    /**
	 * 获取：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
