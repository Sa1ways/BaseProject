package cn.shawn.baselibrary.view.recyclerview.project;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.shawn.baselibrary.R;
import cn.shawn.baselibrary.view.recyclerview.iinterface.RefreshViewCreator;
import cn.shawn.baselibrary.view.recyclerview.view.FunctionRecyclerView;

/**
 * Created by root on 17-6-24.
 */

public class RlRecyclerView extends FunctionRecyclerView {

    public RlRecyclerView(Context context) {
        super(context);
    }

    public RlRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RlRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public int getEmptyViewLayoutId() {
        return R.layout.layout_empty;
    }

    @Override
    public int getErrorViewLayoutId() {
        return R.layout.layout_error;
    }

    @Override
    public int getLoadMoreViewLayoutId() {
        return R.layout.layout_loading;
    }

    @Override
    public int getNoMoreViewLayoutId() {
        return R.layout.layout_no_more;
    }

    @Override
    public int getNetworkRefreshButtonId() {
        return R.id.tv_refresh;
    }

    @Override
    public RefreshViewCreator getRefreshViewCreator() {
        return new RlRefreshViewCreator();
    }


}
