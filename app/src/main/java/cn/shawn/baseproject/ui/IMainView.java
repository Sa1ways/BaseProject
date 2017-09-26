package cn.shawn.baseproject.ui;

import java.util.List;

import cn.shawn.baselibrary.base.IBaseView;
import cn.shawn.baseproject.http.Repo;

/**
 * Created by daopeng on 2017/9/26.
 */

public interface IMainView extends IBaseView {
    void onGotInfo(List<Repo> data);

    void onGotMoreInfo(List<Repo> data);
}
