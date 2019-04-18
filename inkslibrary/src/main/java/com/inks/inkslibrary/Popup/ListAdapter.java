package com.inks.inkslibrary.Popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inks.inkslibrary.Utils.GetResId;
import com.inks.inkslibrary.Utils.L;

import java.util.List;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class ListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<SelectListDataBean> datas;
    private Context context;
    private SelectSettings selectSettings;
//
//    public interface Callback {
//        public void click(int position, int view);
//    }


    //MyAdapter需要一个Context，通过Context获得Layout.inflater，然后通过inflater加载item的布局
    public ListAdapter(Context context,SelectSettings selectSettings,List<SelectListDataBean> selectListDataBeans) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.datas = selectListDataBeans;
        this.selectSettings = selectSettings;
    }

    public void setData (List<SelectListDataBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(GetResId.getId(context, "layout", "popup_select_list"), parent, false); //加载布局
               holder = new ViewHolder();
            holder.linearLayout = convertView.findViewById(GetResId.getId(context, "id", "popup_select_list"));
            holder.textView = convertView.findViewById(GetResId.getId(context, "id", "select_list_text"));
            holder.imageView = convertView.findViewById(GetResId.getId(context, "id", "select_list_select"));
            holder.iconView = convertView.findViewById(GetResId.getId(context, "id", "select_list_icon"));
            convertView.setTag(holder);
        } else {
             holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(datas.get(position).getText());
        holder.textView.setTextSize(selectSettings.getListTextSize());
        holder.textView.setPadding(selectSettings.getListTextPaddings()[0],
                selectSettings.getListTextPaddings()[1],
                selectSettings.getListTextPaddings()[2],
                selectSettings.getListTextPaddings()[3]);
        holder.textView.setGravity(selectSettings.getListTextGravity());
        if(datas.get(position).isChoosed()){
            holder.textView.setTextColor(selectSettings.getListTextSelectColor());
        }else{
            holder.textView.setTextColor(selectSettings.getListTextColor());
        }

        if(selectSettings.isShowListSelectImage()){
            holder.imageView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.imageView.getLayoutParams();
            linearParams.width = selectSettings.getListSelectImageWidth();
            linearParams.height = selectSettings.getListSelectImageHeight();
            holder.imageView.setLayoutParams(linearParams);
            holder.imageView.setPadding(selectSettings.getListSelectImagePaddings()[0],
                    selectSettings.getListSelectImagePaddings()[1],
                    selectSettings.getListSelectImagePaddings()[2],
                    selectSettings.getListSelectImagePaddings()[3]);
            if(datas.get(position).isChoosed()){
                if(selectSettings.getListSelectImageOn()!=null){
                    holder.imageView.setImageDrawable(selectSettings.getListSelectImageOn());
                }else{
                    holder.imageView.setImageResource(GetResId.getId(context, "drawable", "select_on"));
                }
            }else{
                if(selectSettings.getListSelectImageOff()!=null){
                    holder.imageView.setImageDrawable(selectSettings.getListSelectImageOff());
                }else{
                    holder.imageView.setImageResource(GetResId.getId(context, "drawable", "select_off"));
                }
            }
        }else{
            holder.imageView.setVisibility(View.GONE);
        }

        if(selectSettings.isShowListIcon() &&datas.get(position).getIcon()!=null){
            holder.iconView.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.iconView.getLayoutParams();
            linearParams.width = selectSettings.getListIconWidth();
            linearParams.height = selectSettings.getListIconHeight();
            holder.iconView.setLayoutParams(linearParams);
            holder.iconView.setPadding(selectSettings.getListIconPaddings()[0],
                    selectSettings.getListIconPaddings()[1],
                    selectSettings.getListIconPaddings()[2],
                    selectSettings.getListIconPaddings()[3]);
            holder.iconView.setImageDrawable(datas.get(position).getIcon());
        }else{
            holder.iconView.setVisibility(View.GONE);
        }
        //holder.linearLayout.setTag(position);
        return convertView;
    }
    private class ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        ImageView imageView;
        ImageView iconView;
    }
}
