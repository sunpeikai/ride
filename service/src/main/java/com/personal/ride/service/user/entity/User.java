package com.personal.ride.service.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author sunpeikai
 * @since 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("r_user")
@ApiModel(value="User对象", description="用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "登录账号（昵称：正式姓名+后缀）")
    private String userName;

    @ApiModelProperty(value = "用户真实姓名")
    private String trueName;

    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "头像路径")
    private String avatar;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "盐加密")
    private String salt;

    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "删除标志（0代表存在 1代表删除）")
    private String delFlag;

    @ApiModelProperty(value = "最后登陆ip")
    private String loginIp;

    @ApiModelProperty(value = "最后登陆时间")
    private LocalDateTime loginDate;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "备注")
    private String remark;


}
