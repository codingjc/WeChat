package cn.codingjc.wechat.design.responsibilitychain;

/**
 * 审批类
 * @author coding_jc
 * @date 2021/11/7
 */
public abstract class Approver {

    // 下一个处理者
    Approver approver;
    //名称
    String name;

    public Approver(String name) {
        this.name = name;
    }

    public void setApprover(Approver approver) {
        this.approver = approver;
    }

    /**
     * 处理审批请求的方法，得到一个请求，处理是子类完成
     * @param request
     */
    public abstract void processRequest(PurchaseRequest request);
}
