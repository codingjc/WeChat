package cn.codingjc.wechat.design.responsibilitychain;

/**
 * @author coding_jc
 * @date 2021/11/7
 */
public class CollegeApprover extends Approver{

    public CollegeApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        float price = request.getPrice();
        if (price > 5000 && price <= 10000) {
            System.out.println("请求编号id:" + request.getId() + "被" + this.name + "处理");
        } else {
            approver.processRequest(request);
        }
    }
}
