package com.zzc.modules.sysmgr.user.purchaser.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.zzc.modules.sysmgr.user.purchaser.validator.PurchaserUserSaveGroup;
import com.zzc.modules.sysmgr.user.purchaser.validator.PurchaserUserUpdateGroup;

public class SavePurchaserUserRequestDTO {

	@NotBlank(message = "邮箱不能为空", groups = { PurchaserUserSaveGroup.class,
			PurchaserUserUpdateGroup.class })
	@Email(message = "邮箱格式不正确", groups = { PurchaserUserSaveGroup.class,
			PurchaserUserUpdateGroup.class })
	private String email;

	@NotBlank(message = "姓名不能为空", groups = { PurchaserUserSaveGroup.class,
			PurchaserUserUpdateGroup.class })
	private String name;

	@NotBlank(message = "手机不能为空", groups = { PurchaserUserSaveGroup.class,
			PurchaserUserUpdateGroup.class })
	@Pattern(regexp = "^1[3|4|5|7|8]\\d{9}$", message = "手机号格式不正确", groups = {
			PurchaserUserSaveGroup.class, PurchaserUserUpdateGroup.class })
	private String mobile;

	@NotBlank(message = "身份证号不能为空", groups = { PurchaserUserSaveGroup.class,
			PurchaserUserUpdateGroup.class })
	@Pattern(regexp = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$", message = "身份证号格式不正确", groups = {
			PurchaserUserSaveGroup.class, PurchaserUserUpdateGroup.class })
	private String identityNumber;

	@NotBlank(message = "密码不能为空", groups = { PurchaserUserSaveGroup.class })
	@Size.List({
			@Size(min = 6, message = "密码最小长度为{min}位", groups = { PurchaserUserSaveGroup.class }),
			@Size(max = 16, message = "密码最大长度为{max}位", groups = { PurchaserUserSaveGroup.class }) })
	@Pattern(regexp = "^[0-9A-Za-z]{6,16}$", message = "密码格式不正确", groups = { PurchaserUserSaveGroup.class })
	private String userPwd;

	private String department;

	private String position;

	private String id;

	private String purchaserId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPurchaserId() {
		return purchaserId;
	}

	public void setPurchaserId(String purchaserId) {
		this.purchaserId = purchaserId;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

}
