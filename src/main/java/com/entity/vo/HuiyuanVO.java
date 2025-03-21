package com.entity.vo;

import com.entity.HuiyuanEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 会员
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("huiyuan")
public class HuiyuanVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 会员编号
     */

    @TableField(value = "huiyuan_uuid_number")
    private String huiyuanUuidNumber;


    /**
     * 会员姓名
     */

    @TableField(value = "huiyuan_name")
    private String huiyuanName;


    /**
     * 会员手机号
     */

    @TableField(value = "huiyuan_phone")
    private String huiyuanPhone;


    /**
     * 会员身份证号
     */

    @TableField(value = "huiyuan_id_number")
    private String huiyuanIdNumber;


    /**
     * 会员头像
     */

    @TableField(value = "huiyuan_photo")
    private String huiyuanPhoto;


    /**
     * 性别
     */

    @TableField(value = "sex_types")
    private Integer sexTypes;


    /**
     * 余额
     */

    @TableField(value = "new_money")
    private Double newMoney;


    /**
     * 会员邮箱
     */

    @TableField(value = "huiyuan_email")
    private String huiyuanEmail;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：会员编号
	 */
    public String getHuiyuanUuidNumber() {
        return huiyuanUuidNumber;
    }


    /**
	 * 获取：会员编号
	 */

    public void setHuiyuanUuidNumber(String huiyuanUuidNumber) {
        this.huiyuanUuidNumber = huiyuanUuidNumber;
    }
    /**
	 * 设置：会员姓名
	 */
    public String getHuiyuanName() {
        return huiyuanName;
    }


    /**
	 * 获取：会员姓名
	 */

    public void setHuiyuanName(String huiyuanName) {
        this.huiyuanName = huiyuanName;
    }
    /**
	 * 设置：会员手机号
	 */
    public String getHuiyuanPhone() {
        return huiyuanPhone;
    }


    /**
	 * 获取：会员手机号
	 */

    public void setHuiyuanPhone(String huiyuanPhone) {
        this.huiyuanPhone = huiyuanPhone;
    }
    /**
	 * 设置：会员身份证号
	 */
    public String getHuiyuanIdNumber() {
        return huiyuanIdNumber;
    }


    /**
	 * 获取：会员身份证号
	 */

    public void setHuiyuanIdNumber(String huiyuanIdNumber) {
        this.huiyuanIdNumber = huiyuanIdNumber;
    }
    /**
	 * 设置：会员头像
	 */
    public String getHuiyuanPhoto() {
        return huiyuanPhoto;
    }


    /**
	 * 获取：会员头像
	 */

    public void setHuiyuanPhoto(String huiyuanPhoto) {
        this.huiyuanPhoto = huiyuanPhoto;
    }
    /**
	 * 设置：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 获取：性别
	 */

    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 设置：余额
	 */
    public Double getNewMoney() {
        return newMoney;
    }


    /**
	 * 获取：余额
	 */

    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }
    /**
	 * 设置：会员邮箱
	 */
    public String getHuiyuanEmail() {
        return huiyuanEmail;
    }


    /**
	 * 获取：会员邮箱
	 */

    public void setHuiyuanEmail(String huiyuanEmail) {
        this.huiyuanEmail = huiyuanEmail;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
