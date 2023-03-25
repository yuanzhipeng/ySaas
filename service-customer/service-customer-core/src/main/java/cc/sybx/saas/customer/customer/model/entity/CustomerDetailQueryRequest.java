package cc.sybx.saas.customer.customer.model.entity;

import cc.sybx.saas.common.base.BaseQueryRequest;
import cc.sybx.saas.common.util.XssUtils;
import cc.sybx.saas.customer.bean.enums.CustomerStatus;
import cc.sybx.saas.customer.customer.model.root.Customer;
import cc.sybx.saas.customer.detail.model.root.CustomerDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailQueryRequest extends BaseQueryRequest {

    /**
     * 会员详细信息标识UUID
     */
    private String customerDetailId;

    /**
     * 批量多个会员详细信息标识UUID
     */
    private List<String> customerDetailIds;

    /**
     * 客户ID
     */
    private String customerId;

    /**
     * 账户
     */
    private String customerAccount;

    /**
     * 客户IDs
     */
    private List<String> customerIds;

    /**
     * 客户等级ID
     */
    private Long customerLevelId;

    /**
     * 会员名称
     */
    private String customerName;

    /**
     * 省
     */
    private Long provinceId;

    /**
     * 市
     */
    private Long cityId;

    /**
     * 区
     */
    private Long areaId;

    /**
     * 账号状态 0：启用中  1：禁用中
     */
    private CustomerStatus customerStatus;

    /**
     * 删除标志 0未删除 1已删除
     */
    private Integer delFlag;

    /**
     * 审核状态 0：待审核 1：已审核 2：审核未通过
     */
    private Integer checkState;

    /**
     * 负责业务员
     */
    private String employeeId;

    /**
     * 精确查询-账户
     */
    private String equalCustomerAccount;

    /**
     * 精确查找-商家下的客户
     */
    private Long companyInfoId;

    /**
     * 禁用原因
     */
    private String forbidReason;

    private Long storeId;


    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<CustomerDetail> getWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            //联查
            Join<Customer, CustomerDetail> customerDetailJoin = root.join("customer");

            if (StringUtils.isNotBlank(equalCustomerAccount)) {
                predicates.add(cbuild.equal(customerDetailJoin.get("customerAccount"), equalCustomerAccount.trim()));
            }
            if (storeId != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("storeId"), storeId));
            }

            if (Objects.nonNull(customerAccount) && StringUtils.isNotEmpty(customerAccount.trim())) {
                predicates.add(cbuild.like(customerDetailJoin.get("customerAccount"), buildLike(customerAccount)));
            }
            if (customerLevelId != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("customerLevelId"), customerLevelId));
            }
            if (checkState != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("checkState"), checkState));
            }

            if (CollectionUtils.isNotEmpty(customerIds)) {
                predicates.add(root.get("customerId").in(customerIds));
//                predicates.add(root.get("customer").get("customerId").in(customerIds));
            }
            if (Objects.nonNull(customerName) && StringUtils.isNotEmpty(customerName.trim())) {
                predicates.add(cbuild.like(root.get("customerName"), buildLike(customerName)));
            }
            if (Objects.nonNull(employeeId) && StringUtils.isNotEmpty(employeeId.trim())) {
                predicates.add(cbuild.equal(root.get("employeeId"), employeeId));
            }
            if (Objects.nonNull(customerDetailId) && StringUtils.isNotEmpty(customerDetailId.trim())) {
                predicates.add(cbuild.equal(root.get("customerDetailId"), customerDetailId));
            }
            if (Objects.nonNull(customerId) && StringUtils.isNotEmpty(customerId.trim())) {
                predicates.add(cbuild.equal(root.get("customer").get("customerId"), customerId));
            }
            if (provinceId != null) {
                predicates.add(cbuild.equal(root.get("provinceId"), provinceId));
            }
            if (cityId != null) {
                predicates.add(cbuild.equal(root.get("cityId"), cityId));
            }
            if (areaId != null) {
                predicates.add(cbuild.equal(root.get("areaId"), areaId));
            }
            if (customerStatus != null) {
                predicates.add(cbuild.equal(root.get("customerStatus"), customerStatus));
            }
            //删除标记
            predicates.add(cbuild.equal(customerDetailJoin.get("delFlag"), delFlag));
            predicates.add(cbuild.equal(root.get("delFlag"), delFlag));
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    /**
     * 封装公共条件
     *
     * @return
     */
    public Specification<CustomerDetail> getAnyWhereCriteria() {
        return (root, cquery, cbuild) -> {
            List<Predicate> predicates = new ArrayList<>();

            //联查
            Join<Customer, CustomerDetail> customerDetailJoin = root.join("customer");

            if (StringUtils.isNotBlank(equalCustomerAccount)) {
                predicates.add(cbuild.equal(customerDetailJoin.get("customerAccount"), equalCustomerAccount.trim()));
            }

            if (Objects.nonNull(customerAccount) && StringUtils.isNotEmpty(customerAccount.trim())) {
                predicates.add(cbuild.like(customerDetailJoin.get("customerAccount"), buildLike(customerAccount)));
            }
            if (customerLevelId != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("customerLevelId"), customerLevelId));
            }
            if (checkState != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("checkState"), checkState));
            }

            if (CollectionUtils.isNotEmpty(customerIds)) {
                predicates.add(root.get("customerId").in(customerIds));
//                predicates.add(root.get("customer").get("customerId").in(customerIds));
            }
            if (Objects.nonNull(customerName) && StringUtils.isNotEmpty(customerName.trim())) {
                predicates.add(cbuild.like(root.get("customerName"), buildLike(customerName)));
            }
            if (Objects.nonNull(employeeId) && StringUtils.isNotEmpty(employeeId.trim())) {
                predicates.add(cbuild.equal(root.get("employeeId"), employeeId));
            }
            if (Objects.nonNull(customerDetailId) && StringUtils.isNotEmpty(customerDetailId.trim())) {
                predicates.add(cbuild.equal(root.get("customerDetailId"), customerDetailId));
            }

            if (CollectionUtils.isNotEmpty(customerDetailIds)) {
                predicates.add(root.get("customerDetailId").in(customerDetailIds));
            }

            if (Objects.nonNull(customerId) && StringUtils.isNotEmpty(customerId.trim())) {
                predicates.add(cbuild.equal(root.get("customer").get("customerId"), customerId));
            }
            if (provinceId != null) {
                predicates.add(cbuild.equal(root.get("provinceId"), provinceId));
            }
            if (cityId != null) {
                predicates.add(cbuild.equal(root.get("cityId"), cityId));
            }
            if (areaId != null) {
                predicates.add(cbuild.equal(root.get("areaId"), areaId));
            }
            if (customerStatus != null) {
                predicates.add(cbuild.equal(root.get("customerStatus"), customerStatus));
            }
            //删除标记
            if (delFlag != null) {
                predicates.add(cbuild.equal(customerDetailJoin.get("delFlag"), delFlag));
                predicates.add(cbuild.equal(root.get("delFlag"), delFlag));
            }
            Predicate[] p = predicates.toArray(new Predicate[predicates.size()]);
            return p.length == 0 ? null : p.length == 1 ? p[0] : cbuild.and(p);
        };
    }

    private static String buildLike(String field) {
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append('%').append(XssUtils.replaceLikeWildcard(field)).append('%').toString();
    }
}
