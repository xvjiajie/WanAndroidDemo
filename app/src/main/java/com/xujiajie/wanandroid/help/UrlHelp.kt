package com.xujiajie.wanandroid.help

/**
 * 创建日期 2019/11/20
 * 描述：链接
 */
object UrlHelp {
    const val CODE = "code"
    const val MSG = "msg"
    const val DATA = "data"
    const val SUB_CODE = "subCode"
    const val SUB_MSG = "subMsg"
    private const val API = ""
    private var BSSE_URL: String? = null
    var bsseUrl: String?
        get() = if (BSSE_URL == null) "https://www.wanandroid.com" else BSSE_URL
        set(bsseUrl) {
            BSSE_URL = bsseUrl
            H5url.HTML_HEAD = bsseUrl
        }

    object Api {
        private const val KEY = API + ""

        const val banner_json="/banner/json"
        const val project_tree="/project/tree/json"
        const val tree_json="/tree/json"
    }

    object Article {
        private const val KEY = "$API/article"

        const val top_json = "$KEY/top/json"


    }

    object Order {
        private const val KEY = API + "/order"

        /**
         * 我购买的订单列表
         */
        const val getMyPurchaseOrder = KEY + "/getMyPurchaseOrder/v1"

        /**
         * 买入订单详情
         */
        const val getMyPurchaseOrderDetail = KEY + "/getMyPurchaseOrderDetail/v1"

        /**
         * 单个卖出订单详情
         */
        const val getMySoldOrderDetail = KEY + "/getMySoldOrderDetail/v1"

        /**
         * 我卖出的订单列表
         */
        const val getMySoldOrder = KEY + "/getMySoldOrder/v1"

        /**
         * 买家取消待付款订单
         */
        const val cancelRePayOrder = KEY + "/cancelRePayOrder/v1"

        /**
         * 确认待收货订单
         */
        const val confirmReceiveOrder = KEY + "/confirmReceiveOrder/v1"

        /**
         * 卖家取消已售订单
         */
        const val cancelSoldOrder = KEY + "/cancelSoldOrder/v1"

        /**
         * 发货订单更新物流
         */
        const val updateSoldOrderLogistics = KEY + "/updateSoldOrderLogistics/v1"

        /**
         * 修改收货地址
         */
        const val updateReceiveAddress = KEY + "/updateReceiveAddress/v1"

        /**
         * 轮询
         */
        const val roll = KEY + "/pay/roll/v1"

        /**
         * 连连页面返回失败 或 用户取消 关闭支付中的订单
         */
        const val pay_return = KEY + "/pay/return/v1"
    }

    object App {
        private const val KEY = API + "/app"

        /**
         * 获取开屏页信息
         */
        const val launchImage = KEY + "/launch/v1"
        const val domain = KEY + "/domain/v1"

        /**
         * 获取商品发布配置
         */
        const val tab_entry = KEY + "/tab/entry/v1"

        /**
         * 获取首页模块信息
         */
        const val page = KEY + "/page/v1"

        /**
         * 首页商品列表
         */
        const val goods_list = KEY + "/goods/list/v1"

        /**
         * 商品详情
         */
        const val goods_detail = KEY + "/goods/detail/v1"

        /**
         * 版本更新
         */
        const val GET_UPGRADE_INFO = KEY + "/upgradeInfo/v1"

        /**
         * 首次打开app弹窗
         */
        const val get_pop = KEY + "/get/pop/v1"

        /**
         * 全部已读
         */
        const val PUSH_MARKED_ALL = KEY + "/push/markedAll/v1"

        /**
         * 图片上传
         */
        const val UPLOAD_IMAGE = KEY + "/upload/v1"

        /**
         * 多图片上传
         */
        const val UPLOAD_FILES_IMAGE = KEY + "/uploadFiles/v1"

        /**
         * 获取系统时间
         */
        const val system_time = KEY + "/get/system/time/v1"

        /**
         * 我的-用户信息
         */
        const val mine_info = KEY + "/mine/info/v1"

        /**
         * 我的-我的交易
         */
        const val mine_business = KEY + "/mine/business/v1"

        /**
         *
         */
        const val goods_category_list = KEY + "/goods/category/list/v1"
    }

    object User {
        private const val KEY = API + "/user"

        /**
         * 获取图形验证码
         */
        const val GET_IMAGE_CODE = KEY + "/login/getImageCode/v1"

        /**
         * 获取短信验证码
         */
        const val GET_SMS_CODE = KEY + "/login/getSmsCode/v1"

        /**
         * 新增用户收藏
         */
        const val addusercol = KEY + "/addusercol/v1"

        /**
         * 删除藏品
         */
        const val delusercol = KEY + "/delusercol/v1"

        /**
         * 登录接口
         */
        const val PHONE_LOGIN = KEY + "/login/phoneLogin/v1"

        /**
         * 添加用户地址
         */
        const val ADD_ADDRESS = KEY + "/adduseraddress/v1"

        /**
         * 修改用户地址
         */
        const val MODIFY_ADDRESS = KEY + "/updateuseraddress/v1"

        /**
         * 查询地址列表
         */
        const val GET_USER_ADDRESSES = KEY + "/getuseraddress/v1"

        /**
         * 删除地址信息
         */
        const val DELETE_ADDRESS = KEY + "/deluseraddress/v1"

        /**
         *
         */
        const val DEFAULT_ADDRESS = KEY + "/address/default/v1"

        /**
         * 查看某个用户所有收藏商品（会返回是否已上架、收藏该商品的人数）
         */
        const val getusercol = KEY + "/getusercol/v1"

        /**
         * 查询未使用的优惠券列表（点击合并优惠券数量）
         */
        const val noUse_coupons = KEY + "/get/noUse/coupons/v1"

        /**
         * 确认订单https://tapi.damoshopn.com/api/order/confirm/v1
         */
        const val CONFIRM_ORDER = KEY + "/confirm/v1"

        /**
         * 二次签约绑卡发送短信
         */
        const val BANK_SECOND = KEY + "/bank/second/bind"

        /**
         * 是否设置支付密码
         */
        const val IS_SET_PAY_PWD = KEY + "/paypwd/isSet/v1"
        const val PAYPWD_ISSET = KEY + "/paypwd/isSet/v1"

        /**
         * 我的-设置
         */
        const val setUp = KEY + "/updateMineInfo/v1"

        /**
         * 获取我的相关信息 （个人信息页面）
         */
        const val getMineInfo = KEY + "/getMineInfo/v1"

        /**
         *
         */
        const val COUPON_ORDER_LIST = KEY + "/coupon/order/list/v1"

        /**
         * 商品订单列表
         */
        const val ORDER_LIST = KEY + "/order/list/v2"

        /**
         * POST
         * 商品订单详情 -（goodsType = 1 | 2）
         */
        const val ORDER_DETAIL = KEY + "/order/detail/v1"

        /**
         * 我的-账户信息-资金明细
         */
        const val GET_ACCOUNT_CAPITAL = KEY + "/getAccountCapital/v1"
    }

    object H5url {
        internal var HTML_HEAD = bsseUrl
        const val registAg = "/h5/registAg" //用户注册协议-金融
        const val registAg1 = "/h5/registAg" //用户注册协议-电商
        const val serviceAg = "/h5/serviceAg" //银行卡服务协议
        const val ABOUT_US = "/h5/aboutUs" //关于我们
        const val PRIVACY_INFO = "/h5/privacyInfo" //非审核-隐私政策
        const val PRIVACY_INFO1 = "/h5/privacyInfo" //审核-隐私政策
        const val MSG_LIST = "/h5/newIndex" //消息列表
        const val evaluation = "/h5/evaluation" //填写估价信息

        object Home {
            const val newIndex = "/h5/newIndex.html" //消息首页
        }
    }
}