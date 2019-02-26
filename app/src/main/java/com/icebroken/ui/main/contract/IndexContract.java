package com.icebroken.ui.main.contract;

import com.mocuz.common.base.BaseModel;
import com.mocuz.common.base.BasePresenter;
import com.mocuz.common.base.BaseView;

/**
 * Created by Dev on 2017/1/17.
 */

public interface IndexContract {
    interface Model extends BaseModel {
//        Observable<BootBean> GetBootdata(String body);

//        Observable<TopicAdvBean> getTopicData(String body);
    }

    interface View extends BaseView {
//        void returnMaindata(BootBean newsChannelsMine);

//        void returnTopicDtat(TopicAdvBean bean,int position);

        //返回顶部
        void scrolltoTop();
    }

    abstract static class Presenter extends BasePresenter<IndexContract.View, IndexContract.Model> {
        public abstract void lodeMaindataRequest(String body);

        public abstract void lodeTopicRequest(String body,int position);
    }
}
