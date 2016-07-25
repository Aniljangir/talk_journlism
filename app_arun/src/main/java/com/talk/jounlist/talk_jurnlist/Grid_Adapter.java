package com.talk.jounlist.talk_jurnlist;

import java.util.ArrayList;

import com.talk.jounlist.talk_jurnlist.R;
import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.RoundedImageView;
import com.talk.jounlist.talk_jurnlist.utils.grid_interface;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Grid_Adapter extends BaseAdapter
{
	LayoutInflater inf;
	ArrayList<Grid_Model> list = new ArrayList<>();
	Activity ctx;
	grid_interface listener;
	public Grid_Adapter(Activity ctx , ArrayList<Grid_Model> list,grid_interface lis)
	{
		// TODO Auto-generated constructor stub
		this.ctx = ctx;
		this.list = list;
		listener=lis;
		inf = LayoutInflater.from(this.ctx);
	}
	
	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Grid_Model getItem(int position)
	{
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		final MyViewHolder holder;
		
		if(convertView == null)
		{
			convertView = inf.inflate(R.layout.speaker_grid_item, parent , false);
			holder = new MyViewHolder(convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (MyViewHolder) convertView.getTag();
		}

		final Grid_Model model = list.get(position);
		holder.tv1.setText(model.getName());
		holder.tv2.setText(model.getDesc());

		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				ctx.runOnUiThread(new Runnable()
				{
					@Override
					public void run()
					{
						holder.iv.setImageResource(model.getImage());
					}
				});

			}
		}).start();
		holder.iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				listener.PopUp(position);
			}
		});
		return convertView;
	}

	private class MyViewHolder
	{
		TextView tv1;
		TextView tv2;
		RoundedImageView iv;
		
		public MyViewHolder(View v)
		{
			// TODO Auto-generated constructor stub
			tv1 = (TextView) v.findViewById(R.id.textView22);
			tv2 = (TextView) v.findViewById(R.id.textView23);
			iv = (RoundedImageView) v.findViewById(R.id.imageView5);
		}
	}
}