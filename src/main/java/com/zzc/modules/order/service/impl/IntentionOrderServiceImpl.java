package com.zzc.modules.order.service.impl;

import com.zzc.common.page.PageForShow;
import com.zzc.common.sms.SmsService;
import com.zzc.common.sms.SmsTemplateKeys;
import com.zzc.common.sms.SmsWithIndexPlaceholder;
import com.zzc.common.sms.SmsWithoutPlaceholder;
import com.zzc.common.util.ConcurrentDateUtil;
import com.zzc.common.util.ImagePathUtil;
import com.zzc.core.dao.BaseDao;
import com.zzc.core.enums.CommonStatusEnum;
import com.zzc.core.exceptions.BizException;
import com.zzc.core.service.impl.BaseCrudServiceImpl;
import com.zzc.core.utils.JsonUtils;
import com.zzc.modules.order.dao.IntentionOrderDao;
import com.zzc.modules.order.dao.IntentionOrderItemDao;
import com.zzc.modules.order.entity.IntentionOrder;
import com.zzc.modules.order.entity.IntentionOrderItem;
import com.zzc.modules.order.service.IntentionOrderItemService;
import com.zzc.modules.order.service.IntentionOrderService;
import com.zzc.modules.shop.cart.entity.Cart;
import com.zzc.modules.shop.cart.service.CartService;
import com.zzc.modules.sysmgr.common.entity.SequenceEntity;
import com.zzc.modules.sysmgr.common.service.SequenceEntityService;
import com.zzc.modules.sysmgr.common.util.NumberSequenceUtil;
import com.zzc.modules.sysmgr.constant.SystemConstants;
import com.zzc.modules.sysmgr.enums.ImageTypeEnum;
import com.zzc.modules.sysmgr.enums.IntentionOrderStatusEnum;
import com.zzc.modules.sysmgr.product.service.ProductSKUService;
import com.zzc.modules.sysmgr.product.service.vo.ProductSKUBussinessVO;
import com.zzc.modules.sysmgr.user.base.entity.Role;
import com.zzc.modules.sysmgr.user.base.service.RoleService;
import com.zzc.modules.sysmgr.user.employee.entity.Employee;
import com.zzc.modules.sysmgr.user.employee.service.EmployeeService;
import com.zzc.modules.sysmgr.user.purchaser.entity.PurchaserUser;
import com.zzc.modules.sysmgr.user.purchaser.service.PurchaserUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("intentionOrderService")
public class IntentionOrderServiceImpl extends
		BaseCrudServiceImpl<IntentionOrder> implements IntentionOrderService {

	private static Logger logger = LoggerFactory
			.getLogger(IntentionOrderServiceImpl.class);

	private IntentionOrderDao intentionOrderDao;

	private IntentionOrderItemDao intentionOrderItemDao;

	@Autowired
	private IntentionOrderItemService intentionOrderItemService;

	@Autowired
	private PurchaserUserService purchaserUserService;

	@Autowired
	private ProductSKUService productSKUService;

	@Autowired
	private SequenceEntityService sequenceEntityService;

	@Autowired
	private CartService cartService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private SmsService smsService;

	private static final String SEQUENCE_NAME = "IntentionOrder";

	private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";

	@Autowired
	public IntentionOrderServiceImpl(BaseDao<IntentionOrder> intentionOrderDao,
			BaseDao<IntentionOrderItem> intentionOrderItemDao) {
		super(intentionOrderDao);
		this.intentionOrderDao = (IntentionOrderDao) intentionOrderDao;
		this.intentionOrderItemDao = (IntentionOrderItemDao) intentionOrderItemDao;
	}

	/**
	 * 创建订单
	 * @param intentionOrder
	 * @param intentionOrderItems
	 */
	@Transactional
	@Override
	public void create(IntentionOrder intentionOrder,
			List<IntentionOrderItem> intentionOrderItems) {
		intentionOrder = intentionOrderDao.save(intentionOrder);
		for (IntentionOrderItem intentionOrderItem : intentionOrderItems) {
			intentionOrderItem.setIntentionOrder(intentionOrder);
			intentionOrderItem.setStatus(CommonStatusEnum.有效.getValue());
		}
		intentionOrderItemDao.save(intentionOrderItems);
	}

	/**
	 * 查询
	 * @param pageNumber
	 * @param pageSize
	 * @param orderStatus
	 * @param orderCodeOrOrgName
	 * @param isBoss
	 * @param employeeId
	 * @return
	 */
	@Override
	public Page<IntentionOrder> findByCondition(Integer pageNumber,
			Integer pageSize, final Integer orderStatus,
			final String orderCodeOrOrgName, final boolean isBoss,
			final String employeeId) {
		return intentionOrderDao.findAll(
				getWhereClause(orderStatus, orderCodeOrOrgName, isBoss,
						employeeId), new PageRequest(pageNumber - 1, pageSize));
	}

	private Specification<IntentionOrder> getWhereClause(
			final Integer orderStatus, final String orderCodeOrOrgName,
			final boolean isBoss, final String employeeId) {
		return new Specification<IntentionOrder>() {
			@Override
			public Predicate toPredicate(Root<IntentionOrder> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {

				List<Predicate> list = new ArrayList<Predicate>();
				if (orderStatus != null
						&& (orderStatus == 0 || orderStatus == 1) && isBoss) {
					list.add(builder.equal(root.get("orderStatus"), orderStatus));

				}
				if (!isBoss) {
					Join<IntentionOrder, Employee> leftJoinEmployee = root
							.join(root.getModel().getDeclaredSingularAttribute(
									"employee", Employee.class), JoinType.LEFT);
					list.add(builder.equal(leftJoinEmployee.get("id"),
							employeeId));
				}
				if (StringUtils.isNotBlank(orderCodeOrOrgName)) {
					Join<IntentionOrder, PurchaserUser> leftJoinPurchaserUser = root
							.join(root.getModel().getDeclaredSingularAttribute(
									"purchaserUser", PurchaserUser.class),
									JoinType.LEFT);
					Join<Object, Object> purchaserUserLeftJoinPurchaser = leftJoinPurchaserUser
							.join("purchaser", JoinType.LEFT);
					Predicate predicate1 = builder.like(root.get("orderCode")
							.as(String.class), "%" + orderCodeOrOrgName + "%");
					Predicate predicate2 = builder.like(
							purchaserUserLeftJoinPurchaser.get("orgName").as(
									String.class), "%" + orderCodeOrOrgName
									+ "%");
					list.add(builder.or(predicate1, predicate2));
				}
				query.orderBy(builder.desc(root.get("orderStatus")),
						builder.desc(root.get("createTime")));
				return builder.and(list.toArray(new Predicate[list.size()]));
			}
		};
	}

	/**
	 * 创建意向单
	 * @param intentionOrderJson
	 * @param intentionOrderItemsJson
	 * @param cartIds
	 * @param purchaserUserId
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean createIntentionOrder(String intentionOrderJson,
			String intentionOrderItemsJson, String cartIds,
			String purchaserUserId) throws Exception {
		long startTime = System.currentTimeMillis();
		logger.info("【创建意向单数据】" + intentionOrderJson);
		IntentionOrder intentionOrder = JsonUtils.toObject(intentionOrderJson,
				IntentionOrder.class);
		List<IntentionOrderItem> intentionOrderItems = JsonUtils.toList(
				intentionOrderItemsJson, ArrayList.class,
				IntentionOrderItem.class);

		if (intentionOrder == null
				|| CollectionUtils.isEmpty(intentionOrderItems)) {
			throw new BizException("不能保存空数据");
		}
		BigDecimal totalPrice = new BigDecimal(0);
		for (IntentionOrderItem intentionOrderItem : intentionOrderItems) {
			totalPrice = setIntentionOrderItem(intentionOrderItem,
					intentionOrder, totalPrice);
		}
		List<String> cartIdList = JsonUtils.toList(cartIds, ArrayList.class,
				String.class);

		if (CollectionUtils.isNotEmpty(cartIdList)) {
			deleteCart(cartIdList);
		}
		PurchaserUser purchaserUser = purchaserUserService
				.findByPK(purchaserUserId);
		setIntentionOrder(intentionOrder, purchaserUser, totalPrice,
				intentionOrderItems);
		intentionOrderDao.save(intentionOrder);
		intentionOrderItemDao.save(intentionOrderItems);
		long endTime = System.currentTimeMillis();
		logger.info("【创建订单执行时间为】" + (endTime - startTime) + "【毫秒】");
		sendSmsToOrderAdminstrator(purchaserUser.getPurchaser().getOrgName(),
				intentionOrder.getDemandDate());
		return Boolean.TRUE;
	}

	/**
	 * 向管理员发送邮件
	 * @param orgName
	 * @param demandDate
	 * @throws ParseException
	 */
	private void sendSmsToOrderAdminstrator(String orgName, Date demandDate)
			throws ParseException {
		Role cooRole = roleService
				.findByRoleCode(SystemConstants.ORDER_ADMINISTRATOR);
		if (cooRole != null) {
			List<Employee> employees = employeeService.findByRoleId(cooRole
					.getId());
			if (CollectionUtils.isNotEmpty(employees) && employees.size() == 1) {
				Employee coo = employees.get(0);
				SmsWithoutPlaceholder sms = new SmsWithIndexPlaceholder(
						coo.getMobile(),
						SmsTemplateKeys.CREATE_INTENTION_ORDER,
						ArrayUtils.toArray(orgName, ConcurrentDateUtil
								.formatDate(demandDate, DATE_FORMAT_PATTERN)));
				smsService.sendSms(sms);
			}
		}
	}

	private BigDecimal setIntentionOrderItem(
			IntentionOrderItem intentionOrderItem,
			IntentionOrder intentionOrder, BigDecimal totalPrice) {
		intentionOrderItem.setIntentionOrder(intentionOrder);
		intentionOrderItem.setStatus(CommonStatusEnum.有效.getValue());
		ProductSKUBussinessVO productSnapShot = productSKUService
				.getProductDetailShop(
						intentionOrderItem.getProductCategoryId(),
						intentionOrderItem.getProductSKUId());
		intentionOrderItem.setItemSnapShot(JsonUtils.toJson(productSnapShot));

		return calculateTotalPrice(intentionOrderItem.getPrice(),
				intentionOrderItem.getQuantity(), totalPrice);
	}

	/**
	 * 计算订单总价格
	 * 
	 * @param price
	 * @param quantity
	 * @param totalPrice
	 * @return
	 */
	private BigDecimal calculateTotalPrice(BigDecimal price, Integer quantity,
			BigDecimal totalPrice) {
		return price.multiply(new BigDecimal(quantity)).add(totalPrice);
	}

	private void setIntentionOrder(IntentionOrder intentionOrder,
			PurchaserUser purchaserUser, BigDecimal totalPrice,
			List<IntentionOrderItem> intentionOrderItems) {
		intentionOrder.setStatus(CommonStatusEnum.有效.getValue());
		intentionOrder.setOrderStatus(IntentionOrderStatusEnum.TO_BE_PAYED
				.getValue());
		intentionOrder.setCreateOrderName(purchaserUser.getName());
		intentionOrder.setCreateOrderMobile(purchaserUser.getMobile());
		intentionOrder.setPurchaserUser(purchaserUser);
		intentionOrder.setOrderCode(generateIntentionOrderCode(purchaserUser));
		intentionOrder.setTotalPrice(totalPrice.setScale(2));
		intentionOrder.setIntentionOrderItems(intentionOrderItems);
	}

	/**
	 * 创建订单项后，清楚对应购物车中得记录
	 * @param cartIds
	 */
	private void deleteCart(List<String> cartIds) {
		for (String cartId : cartIds) {
			Cart cart = cartService.findByPK(cartId);
			cart.setModifiedTime(new Date());
			cart.setStatus(CommonStatusEnum.无效.getValue());
			cartService.update(cart);
			logger.info("【提交订单删除购物车相应记录，购物车Id】" + cartId);
		}
	}

	/**
	 * 根据订单Id查询订单项详情,包括分页信息
	 * 
	 * @throws ParseException
	 */
	@Override
	public Map<String, Object> findIntentionOrderDetail(
			IntentionOrder intentionOrder, Integer pageNumber, Integer pageSize)
			throws ParseException {
		Page<IntentionOrderItem> pageOfItems = findItemsByOrderId(
				intentionOrder.getId(), pageNumber, pageSize);
		Map<String, Object> response = intenionOrderInfo(intentionOrder);
		logger.info("【订单详情页面中订单相关信息】" + response);
		response.put("data", intentionOrderItemsInfo(pageOfItems));
		response.put("pageNumber", pageNumber);
		response.put("totalPages", pageOfItems.getTotalPages());
		return response;
	}

	private void checkParam(String orderId) {
		if (StringUtils.isBlank(orderId)) {
			throw new BizException("订单Id不能为空");
		}
	}

	/**
	 * 根据订单Id查询订单项详情,包括分页信息(适用BSgrid)
	 */
	@Override
	public PageForShow<Map<String, Object>> findIntentionOrderDetailForSysmgr(
			String orderId, Integer pageNumber, Integer pageSize) {
		checkParam(orderId);
		Page<IntentionOrderItem> pageOfItems = findItemsByOrderId(orderId,
				pageNumber, pageSize);
		return PageForShow.newInstanceFromSpringPage(
				intentionOrderItemsInfo(pageOfItems), pageNumber,
				pageOfItems.getTotalElements());
	}

	private Map<String, Object> intentionOrderItemsInfo(
			IntentionOrderItem orderItem) {
		String itemSnapShot = orderItem.getItemSnapShot();
		if (StringUtils.isEmpty(itemSnapShot)) {
			return Collections.emptyMap();
		}
		ProductSKUBussinessVO productSKUBussinessVO = JsonUtils.toObject(
				itemSnapShot, ProductSKUBussinessVO.class);
		Map<String, Object> productInView = new HashMap<>();
		productInView
				.put("productCode", productSKUBussinessVO.getProductCode());
		productInView
				.put("productName", productSKUBussinessVO.getProductName());
		productInView.put("hasTax", productSKUBussinessVO.getHasTax() == null ? 1 : productSKUBussinessVO.getHasTax());
		productInView.put("hasTransportation", productSKUBussinessVO.getHasTransportation() == null ? 1 : productSKUBussinessVO.getHasTransportation());
		if (CollectionUtils
				.isNotEmpty(productSKUBussinessVO.getProductImages())) {
			productInView.put("productImage", ImagePathUtil.getImagePath(
					ImageTypeEnum.PRODUCT_IMAGE, productSKUBussinessVO
							.getProductImages().get(0)));
		}
		productInView.put("price", orderItem.getPrice());
		productInView.put("quantity", orderItem.getQuantity());
		productInView.put("itemTotalPrice", orderItem.getTotalPrice());
		productInView.put("itemId", orderItem.getId());
		return productInView;
	}

	private List<Map<String, Object>> intentionOrderItemsInfo(
			List<IntentionOrderItem> items) {
		if (CollectionUtils.isEmpty(items)) {
			return Collections.emptyList();
		}
		List<Map<String, Object>> products = new ArrayList<>();
		for (IntentionOrderItem orderItem : items) {
			products.add(intentionOrderItemsInfo(orderItem));
		}
		return products;
	}

	private List<Map<String, Object>> intentionOrderItemsInfo(
			Page<IntentionOrderItem> pageOfItems) {
		return intentionOrderItemsInfo(pageOfItems.getContent());
	}

	private Map<String, Object> intenionOrderInfo(IntentionOrder intentionOrder)
			throws ParseException {
		Map<String, Object> response = new HashMap<>();
		response.put("createTime", intentionOrder.getCreateTime());
		response.put("orgName", intentionOrder.getPurchaserUser()
				.getPurchaser().getOrgName());
		response.put("orderCode", intentionOrder.getOrderCode());
		response.put("name", intentionOrder.getCreateOrderName());
		response.put("mobile", intentionOrder.getCreateOrderMobile());
		response.put("projectName", intentionOrder.getProjectName());
		response.put("demandDate", ConcurrentDateUtil.formatDate(
				intentionOrder.getDemandDate(), DATE_FORMAT_PATTERN));
		response.put("projectDescription",
				intentionOrder.getProjectDescription());
		response.put("orderId", intentionOrder.getId());
		response.put("orderStatus", intentionOrder.getOrderStatus());
		if (intentionOrder.getEmployee() != null) {
			response.put("employeeName", intentionOrder.getEmployee()
					.getEmployeeName());
			response.put("employeeMobile", intentionOrder.getEmployee()
					.getMobile());
		}
		response.put("orderTotalPrice", intentionOrder.getTotalPrice());
		return response;
	}

	private Page<IntentionOrderItem> findItemsByOrderId(String orderId,
			Integer pageNumber, Integer pageSize) {
		pageNumber = (pageNumber == null) ? 1 : pageNumber;
		pageSize = (pageSize == null) ? 10 : pageSize;
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_intentionOrder.id", orderId);
		return intentionOrderItemService.findByParams(IntentionOrderItem.class,
				params, pageNumber, pageSize, "DESC", "createTime");
	}

	private String generateIntentionOrderCode(PurchaserUser purchaserUser) {
		SequenceEntity sequenceEntity = sequenceEntityService
				.findSequenceEntityBySequenceName(SEQUENCE_NAME);
		Integer sequenceNumber = 1;
		if (sequenceEntity == null) {
			sequenceEntity = new SequenceEntity();
			sequenceEntity.setSequenceName(SEQUENCE_NAME);
			sequenceEntity.setStatus(CommonStatusEnum.有效.getValue());
			sequenceEntity.setSequenceNumber(sequenceNumber);
		} else {
			sequenceNumber = sequenceEntity.getSequenceNumber() + 1;
			sequenceEntity.setSequenceNumber(sequenceNumber);
			sequenceEntity.setModifiedTime(new Date());
		}
		sequenceEntityService.create(sequenceEntity);
		String sequenceNumberStr = NumberSequenceUtil.appendPrefixChar(
				sequenceNumber, 4, '0');

		String purchaserCode = purchaserUser.getPurchaser().getOrgCode();
		String[] purchaserCodes = purchaserCode.trim().split("-");
		final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String orderCode = purchaserCodes[1]
				+ format.format(new Date(System.currentTimeMillis()))
				+ sequenceNumberStr;
		return orderCode;
	}

	@Override
	public List<Map<String, Object>> getIntentionOrderList(Integer pageNumber,
			Integer pageSize, Integer status, String currentUserId)
			throws ParseException {
		Map<String, Object> params = new HashMap<>();
		params.put("AND_EQ_purchaserUser.id", currentUserId);
		if (status != null && status != -1) {
			params.put("AND_EQ_orderStatus", status);
		}
		params.put("AND_EQ_status", CommonStatusEnum.有效.getValue());
		pageNumber = (pageNumber == null) ? 1 : pageNumber;
		pageSize = (pageSize == null) ? 10 : pageSize;
		Page<IntentionOrder> page = findByParams(IntentionOrder.class, params,
				pageNumber, pageSize, "DESC", "createTime");
		List<Map<String, Object>> response = new ArrayList<>();
		if (CollectionUtils.isEmpty(page.getContent())) {
			return Collections.emptyList();
		}
		for (IntentionOrder order : page.getContent()) {
			Map<String, Object> viewData = intenionOrderInfo(order);
			Integer itemCount = 0;
			List<Map<String, Object>> items = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(order.getIntentionOrderItems())) {
				IntentionOrderItem orderItem = null;
				int orderItemCount = order.getIntentionOrderItems().size();
				viewData.put("orderItemCount", orderItemCount);
				for (int i = 0; i < orderItemCount; i++) {
					orderItem = order.getIntentionOrderItems().get(i);
					if (orderItem == null) {
						continue;
					}
					itemCount += orderItem.getQuantity();
					if (i <= 1) {
						items.add(intentionOrderItemsInfo(orderItem));
					}
				}
			}
			viewData.put("items", items);
			viewData.put("totalPages", page.getTotalPages());
			response.add(viewData);
		}
		return response;
	}
}
