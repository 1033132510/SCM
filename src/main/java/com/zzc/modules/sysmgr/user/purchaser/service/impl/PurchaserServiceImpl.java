package com.zzc.modules.sysmgr.user.purchaser.service.impl;

import com.zzc.common.security.util.UserUtil;
import com.zzc.common.security.web.user.SessionUser;
import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.core.web.common.ResultData;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.file.dao.ImageDao;
import com.zzc.modules.sysmgr.file.entity.Image;
import com.zzc.modules.sysmgr.user.purchaser.dao.PurchaserDao;
import com.zzc.modules.sysmgr.user.purchaser.dto.PurchaserAndImageDTO;
import com.zzc.modules.sysmgr.user.purchaser.entity.Purchaser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("purchaserService")
public class PurchaserServiceImpl extends BaseCrudServiceImpl<Purchaser>
		implements PurchaserService {

	private PurchaserDao purchaserDao;

	private ImageDao imageDao;

	@Autowired
	public PurchaserServiceImpl(BaseDao<Purchaser> baseDao,
			BaseDao<Image> imageDao) {
		super(baseDao);
		this.purchaserDao = (PurchaserDao) baseDao;
		this.imageDao = (ImageDao) imageDao;
	}

	@Override
	public Page<Purchaser> findByPurchaserCodeOrCompany(String codeOrCompany,
			Integer pageNumber, Integer pageSize) {
		return purchaserDao.findByPurchaserCodeOrCompany(codeOrCompany,
				new PageRequest(pageNumber - 1, pageSize));
	}

	@Override
	public PurchaserAndImageDTO findPurchaserAndImageById(String id) {
		Purchaser purchaser = findByPK(id);
		if (purchaser == null) {
			return null;
		}
		return covertPurchaserToDTO(purchaser);
	}

	private PurchaserAndImageDTO covertPurchaserToDTO(Purchaser purchaser) {
		PurchaserAndImageDTO dto = new PurchaserAndImageDTO();
		dto.setId(purchaser.getId());
		dto.setOrgCode(purchaser.getOrgCode());
		dto.setLegalName(purchaser.getLegalName());
		dto.setLevel(purchaser.getLevel());
		dto.setTel(purchaser.getTel());
		dto.setOrgName(purchaser.getOrgName());
		if (purchaser.getParentPurchaser() != null) {
			dto.setParentId(purchaser.getParentPurchaser().getId());
		}
		List<Image> images = new ArrayList<>();
		List<Image> licenceImage = imageDao.findByRelationIdAndRelationType(
				purchaser.getId(), ImageTypeEnum.PURCHASER_LICENCE.getValue(),
				CommonStatusEnum.有效.getValue());
		List<Image> codeLicenceImage = imageDao
				.findByRelationIdAndRelationType(purchaser.getId(),
						ImageTypeEnum.PURCHASER_CODE_LICENCE.getValue(),
						CommonStatusEnum.有效.getValue());
		List<Image> taxRegistrationImage = imageDao
				.findByRelationIdAndRelationType(purchaser.getId(),
						ImageTypeEnum.PURCHASER_TAX_REGISTRATION.getValue(),
						CommonStatusEnum.有效.getValue());
		List<Image> constructionQualificationImage = imageDao
				.findByRelationIdAndRelationType(purchaser.getId(),
						ImageTypeEnum.PURCHASER_CONSTRUCTION_QUALIFICATION
								.getValue(), CommonStatusEnum.有效.getValue());
		images.addAll(licenceImage);
		images.addAll(codeLicenceImage);
		images.addAll(taxRegistrationImage);
		images.addAll(constructionQualificationImage);
		if (CollectionUtils.isNotEmpty(images)) {
			for (Image image : images) {
				if (ImageTypeEnum.PURCHASER_LICENCE.getValue() == image
						.getRelationType()) {
					dto.setLicencePath(ImagePathUtil.getImagePath(
							ImageTypeEnum.PURCHASER_LICENCE, image));
					dto.setLicenceId(image.getId());
					dto.setLicenceRelationType(image.getRelationType());
				} else if (ImageTypeEnum.PURCHASER_CODE_LICENCE.getValue() == image
						.getRelationType()) {
					dto.setCodeLicencePath(ImagePathUtil.getImagePath(
							ImageTypeEnum.PURCHASER_CODE_LICENCE, image));
					dto.setCodeLicenceId(image.getId());
					dto.setCodeLicenceRelationType(image.getRelationType());
				} else if (ImageTypeEnum.PURCHASER_TAX_REGISTRATION.getValue() == image
						.getRelationType()) {
					dto.setTaxRegistrationPath(ImagePathUtil.getImagePath(
							ImageTypeEnum.PURCHASER_TAX_REGISTRATION, image));
					dto.setTaxRegistrationId(image.getId());
					dto.setTaxRegistrationRelationType(image.getRelationType());
				} else if (ImageTypeEnum.PURCHASER_CONSTRUCTION_QUALIFICATION
						.getValue() == image.getRelationType()) {
					dto.setConstructionQualificationPath(ImagePathUtil
							.getImagePath(
									ImageTypeEnum.PURCHASER_CONSTRUCTION_QUALIFICATION,
									image));
					dto.setConstructionQualificationId(image.getId());
					dto.setConstructionQualificationRelationType(image
							.getRelationType());
				}
			}
		}
		return dto;
	}

	@Override
	public PurchaserAndImageDTO findPurchaserAndImageByOrgCode(String orgCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_orgCode", orgCode);
		List<Purchaser> purchasers = findAll(params, Purchaser.class);
		if (CollectionUtils.isEmpty(purchasers)) {
			return null;
		}

		return covertPurchaserToDTO(purchasers.get(0));
	}

	@Override
	public ResultData relateExistedPurchaser(String orgCode, String parentId) {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_orgCode", orgCode);
		List<Purchaser> purchasers = findAll(params, Purchaser.class);
		if (CollectionUtils.isEmpty(purchasers)) {
			throw new BizException("查询无数据，企业编码输入有误或企业编码不存在，请重新输入");
		}
		Purchaser current = findByPK(parentId);
		Purchaser child = purchasers.get(0);
		if (current.getId().equals(child.getId())) {
			throw new BizException("关联失败，不能本公司与本公司进行关联");
		}
		if (child.getParentPurchaser() != null) {
			throw new BizException("关联失败，此公司已存在父公司，一个公司不能同时关联两个父公司");
		}
		Purchaser parent = current.getParentPurchaser();
		while (parent != null && StringUtils.isNotBlank(parent.getId())) {
			if (parent.getId().equals(child.getId())) {
				throw new BizException("关联失败，不能关联本公司的父公司");
			} else {
				parent = parent.getParentPurchaser();
			}
		}
		child.setParentPurchaser(current);
		update(child);
		return new ResultData(true, "关联成功", null, null);
	}
	

	@Override
	public Purchaser save(Purchaser purchaser){
		return purchaserDao.save(purchaser);
	}
	
	@Override
	public Purchaser create(Purchaser purchaser){
		return purchaserDao.save(purchaser);
	}
	
	@Override
	public Purchaser update(Purchaser purchaser){
		return purchaserDao.save(purchaser);
	}
	
	
	@Override
	public String logInformation(Purchaser purchaser) {
		return JsonUtils.toJson(purchaser);
	}

	@Override
	public String userLog() {
		SessionUser sessionUser = UserUtil.getUserFromSession();
		return JsonUtils.toJson(sessionUser);
	}

}
