package tmall.bean;

import java.util.Date;
import java.util.List;

public class Order {

    private int id;
    private String orderCode;// 订单号
    private String address;// 收货地址
    private String post;// 邮编
    private String receiver;// 收件人
    private String mobile;// 手机号码
    private String userMessage;// 用户备注
    private Date createDate;// 订单创建时间
    private Date payDate;// 支付时间
    private Date deliveryDate;// 发货时间
    private Date confirmDate;// 确认收货时间
    private User user;
    private String status;

    private List<OrderItem> orderItemList;
    private float total;
    private int totalNumber;

    public String getStatusDesc() {
        String desc = "未知";
        switch (status) {
//          case OrderDAO.waitPay:
//              desc="待付款";
//              break;
//          case OrderDAO.waitDelivery:
//              desc="待发货";
//              break;
//          case OrderDAO.waitConfirm:
//              desc="待收货";
//              break;
//          case OrderDAO.waitReview:
//              desc="等评价";
//              break;
//          case OrderDAO.finish:
//              desc="完成";
//              break;
//          case OrderDAO.delete:
//              desc="刪除";
//              break;
//          default:
//              desc="未知";
        }
        return desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }
}
