package cn.codingjc.wechat.design.responsibilitychain;

/**
 * @author coding_jc
 * @date 2021/11/7
 */
public class DepartmentApprover extends Approver{

    public DepartmentApprover(String name) {
        super(name);
    }

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getPrice() <= 5000) {
            System.out.println("请求编号id:" + request.getId() + "被" + this.name + "处理");
        } else {
            approver.processRequest(request);
        }

    }
}
