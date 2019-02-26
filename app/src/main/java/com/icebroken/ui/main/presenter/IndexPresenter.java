package com.icebroken.ui.main.presenter;

import com.icebroken.api.AppConstant;
import com.icebroken.ui.main.contract.IndexContract;
import rx.functions.Action1;

/**
 * Created by Dev on 2017/1/19.
 */

public class IndexPresenter extends IndexContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
        //监听返回顶部动作
        mRxManage.on(AppConstant.NEWS_LIST_TO_TOP, new Action1<Object>() {
            @Override
            public void call(Object o) {
                mView.scrolltoTop();
            }
        });
    }

    @Override
    public void lodeMaindataRequest(String body) {
//
//        mRxManage.add(mModel.GetBootdata(body).subscribe(new RxSubscriber<BootBean>(mContext, true) {
//            @Override
//            protected void _onNext(BootBean bootDetail) {
//                mView.returnMaindata(bootDetail);
//                if (null != bootDetail) {
//                    BaseUtil.headurl = bootDetail.getSetting_Bean().getUsercenter_api();//获取 头像默认拼接地址
//                }
//            }
//
//            @Override
//            protected void _onError(String message) {
//                mView.showErrorTip(message);
//            }
//        }));
    }

    @Override
    public void lodeTopicRequest(String body, final int position) {
//        LogUtils.logd(body.toString());
//        mRxManage.add(mModel.getTopicData(body).subscribe(new RxSubscriber<TopicAdvBean>(mContext, false) {
//            @Override
//            protected void _onNext(TopicAdvBean topicAdvBean) {
//                mView.returnTopicDtat(topicAdvBean, position);
//            }
//
//            @Override
//            protected void _onError(String message) {
//
//            }
//        }));
    }
}
