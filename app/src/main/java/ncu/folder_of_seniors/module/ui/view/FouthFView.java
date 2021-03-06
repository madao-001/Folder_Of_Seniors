package ncu.folder_of_seniors.module.ui.view;

import ncu.folder_of_seniors.base.BaseView;

public interface FouthFView extends BaseView {

    /**
     * 显示数据
     * @param num1 关注数
     * @param num2 粉丝数
     * @param num3 积分数
     */
    void showData(int num1,int num2,int num3);
    /**
     * 显示头像
     * @param picPath 图片地址
     */
    void showIcon(String picPath);
    /**
     * 当数据请求异常，调用此接口提示
     */
    void showErrorMessage(String msg);
    /**
     * 获取图片地址
     * @param picPath 图片地址
     */
    void getPicPath(String picPath);
}
