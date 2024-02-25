package com.example.test4;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.io.File;
import java.util.HashMap;
import java.util.List;

public class MyAdptor extends RecyclerView.Adapter<MyAdptor.VH> {

  private List<HashMap<String,Object>> list;
  private OnClickItem item;

  public MyAdptor(List<HashMap<String,Object>> list, OnClickItem item) {
    this.list = list;
    this.item = item;
  }

  @Override
  public int getItemCount() {
    return list.size();
  }

  @Override
  public VH onCreateViewHolder(ViewGroup viewgroup, int viewType) {
    View view =
        LayoutInflater.from(viewgroup.getContext()).inflate(R.layout.layout_test, viewgroup, false);
    return new VH(view);
  }

  @Override
  public void onBindViewHolder(VH holder, int itempos) {
    File file = new File(list.get(itempos).get("path").toString());
    holder.tv.setText(file.getName());
    if(file.isDirectory()) {
    	//add icon dir
    }else{
      //add icon file
    }
    holder.tv.setOnClickListener(v -> item.onItemClick(itempos));
    holder.tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    holder.tv.setMarqueeRepeatLimit(-1);
    holder.tv.setSingleLine(true);
    holder.tv.setSelected(true);
    holder.tv.setOnLongClickListener(( x) ->{
      holder.card.setChecked(!holder.card.isChecked());
        return false;
    });
  }

  public class VH extends RecyclerView.ViewHolder {
    TextView tv;
    MaterialCardView card;

    public VH(View view) {
      super(view);
      tv = view.findViewById(R.id.tvs);
      card = view.findViewById(R.id.card);
    }
  }

  public interface OnClickItem {
    public void onItemClick(int pos);
  }
}
