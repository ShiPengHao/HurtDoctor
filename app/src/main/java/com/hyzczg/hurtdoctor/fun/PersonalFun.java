package com.hyzczg.hurtdoctor.fun;

/**
 * 个人中心页面功能接口
 */

public interface PersonalFun {
    /**
     * 切换编辑/阅读状态
     */
    void switchState();

    /**
     * 编辑之后提交信息
     */
    void submit();

    /**
     * 下载个人信息
     */
    void download();
}
