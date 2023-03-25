package cc.sybx.saas.customer.api.constant;

public final class EmployeeErrorCode {

    /**
     * 该角色已存在
     */
    public final static String ROLE_ALREADY_EXIST = "K-040003";

    /**
     * 该角色已存在
     */
    public final static String REGEDIT_EXIST = "K-040004";

    /**
     * 手机号码已注册
     */
    public final static String ALREADY_EXIST = "K-040005";

    /**
     * 员工已被锁定
     */
    public final static String ACCOUNT_LOCKED = "K-040007";

    /**
     * 员工已被禁用
     */
    public final static String ACCOUNT_DISABLED = "K-040019";

    /**
     * 连续输错密码5次，请{0}分钟后重试！
     */
    public final static String LOCKED_MINUTES = "K-040009";

    /**
     * 账号或密码错误
     */
    public final static String ACCOUNT_PASSWORD_ERROR = "K-040008";

    /**
     * 手机号码不符合要求
     */
    public final static String MOBILE_FORMAT_ERROR = "K-040011";

    /**
     * 员工姓名已存在
     */
    public final static String ACCOUNT_NAME_EXIST = "K-040013";

    /**
     * 此账号不允许操作
     */
    public final static String ACCOUNT_NOT_SET = "K-040014";

    /**
     * 最多只能新增20个员工角色
     */
    public final static String ROLE_ADD_LIMIT = "K-040015";

    /**
     * 角名名称不允许为系统管理员
     */
    public final static String NAME_NOT_ADMIN = "K-040016";

    /**
     * 银行账号已存在
     */
    public final static String BANK_ACCOUNT_EXIST = "K-040017";

    /**
     * 不存在
     */
    public final static String NOT_EXIST = "K-040018";

    /**
     * 操作频繁，请稍后重试！
     */
    public final static String FREQUENT_OPERATION = "K-040020";

    /**
     * 账号已禁用
     */
    public final static String DISABLED = "K-040021";

    /**
     * 不存在需要改变的
     */
    public final static String NONE_NEED_CHANGE = "K-040022";

    /**
     * 暂无权限访问
     */
    public final static String ACCESS_DENIED = "K-040023";
    /**
     * 暂无权限访问
     */
    public final static String ACCESS_LACK_ROLE= "K-040031";

    /**
     * 请{0}分钟后重试！
     */
    public final static String TRY_AGAIN = "K-040024";
    /**
     * 手机号不存在
     */
    public final static String MOBILE_NOT_EXIST = "K-040025";
    /**
     * 手机号未注册
     */
    public final static String MOBILE_UNREGISTERED = "K-040026";

    /**
     * 会员账户已存在
     */
    public final static String ACCOUNT_EXIST = "K-040027";

    /**
     * 员工已离职
     */
    public final static String ACCOUNT_DISMISSION = "K-040028";

    /**
     * 员工导入模版未配置
     */
    public final static String TEMPLATE_NOT_SETTING = "K-040029";

    /**
     * 工号已存在
     */
    public final static String JOB_NO_EXIST = "K-040030";

    /**
     * 手机号未注册
     */
    public final static String ACCOUNT_ALREADY_EXIST = "K-040027";

    private EmployeeErrorCode() {
    }
}
