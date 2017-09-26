package cn.shawn.baseproject.adapter;

import cn.shawn.baselibrary.view.recyclerview.adapter.CommonAdapter;
import cn.shawn.baselibrary.view.recyclerview.utils.ViewHolder;
import cn.shawn.baseproject.R;
import cn.shawn.baseproject.http.Repo;

/**
 * Created by daopeng on 2017/9/26.
 */

public class GitInfoAdapter extends CommonAdapter<Repo> {
    @Override
    public int getItemLayoutId() {
        return R.layout.item_main;
    }

    @Override
    public void convert(ViewHolder holder, int position, Repo info) {
        holder.setText(R.id.tv_item,String.valueOf(position));
    }
}
