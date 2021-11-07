package cn.codingjc.wechat.design.responsibilitychain;

/**
 * @author coding_jc
 * @date 2021/11/7
 */
public class Client {
    public static void main(String[] args) {
        PurchaseRequest purchaseRequest = new PurchaseRequest(1, 30000, 1);

        DepartmentApprover departmentApprover = new DepartmentApprover("系主任");
        CollegeApprover collegeApprover = new CollegeApprover("院长");
        ViceSchoolApprover viceSchoolApprover = new ViceSchoolApprover("副校长");
        SchoolMasterApprover schoolMasterApprover = new SchoolMasterApprover("校长");

        departmentApprover.setApprover(collegeApprover);
        collegeApprover.setApprover(viceSchoolApprover);
        viceSchoolApprover.setApprover(schoolMasterApprover);

        departmentApprover.processRequest(purchaseRequest);
    }
}
