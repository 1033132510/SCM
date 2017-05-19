package com.zzc.modules.sysmgr.enums;

public enum ImageTypeEnum {

	PURCHASER_LICENCE(1, "purchaser_licence"), PURCHASER_CODE_LICENCE(2,
			"purchaser_code_licence"), SUPPLIER_BUSINESS_LICENCE(3,
			"supplier_business_licence"), SUPPLIER_ORG_CODE(4,
			"supplier_org_code"), SUPPLIER_LOGO(5, "supplier_logo"), SUPPLIER_HONOR(
			6, "supplier_honor"), BRAND_LOGO(7, "brand_logo"), BRAND_DESC(8,
			"brand_desc"), SUPPLIER_BUSINESS_LICENCE_YEAR(9,
			"supplier_business_licence_year"), TAX_REGISTRATION_CERTIFICATE(10,
			"tax_registration_certificate"), TRADEMARK_REGISTRATION_CERTIFICATE(
			11, "trademark_registration_certificate"), POWER_OF_ATTORNEY(12,
			"power_of_attorney"), SOLE_DISTRIBUTOR_PA(13, "sole_distributor_pa"), PRODUCT_IMAGE(
			14, "product_image"), PRODUCT_DESC(15, "product_desc"), PURCHASER_TAX_REGISTRATION(
			16, "purchaser_tax_registration"), PURCHASER_CONSTRUCTION_QUALIFICATION(
			17, "purchaser_construction_qualification");

	private int value;

	private String text;

	ImageTypeEnum(int value, String text) {
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public String getText() {
		return text;
	}

	public static ImageTypeEnum getImageTypeEnumByImageTypeValue(
			Integer imageTypeValue) {
		for (ImageTypeEnum imageTypeEnum : ImageTypeEnum.values()) {
			if (imageTypeValue == imageTypeEnum.getValue())
				return imageTypeEnum;
		}
		return null;

	}
}
