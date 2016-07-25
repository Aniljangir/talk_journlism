package com.talk.jounlist.talk_jurnlist;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.talk.jounlist.talk_jurnlist.utils.StaticData;

/**
 * Created by Arun on 6/27/2016.
 */
public class Programfragment extends Fragment
{

    ImageView imageView, imageView_secons, imageView_three;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.program_new, container, false);
        imageView = (ImageView) rootView.findViewById(R.id.imageView);
        imageView_secons = (ImageView) rootView.findViewById(R.id.imageView_secons);
        imageView_three = (ImageView) rootView.findViewById(R.id.imageView_three);

       /* if (StaticData.pref(getActivity()).getString(StaticData.firsttime, "false").equalsIgnoreCase("true"))
        {
            Log.e("aa", "come");
            CreateDialogAvinash();
            SharedPreferences pref = StaticData.pref(getActivity());

            SharedPreferences.Editor e = pref.edit();

            e.putString(StaticData.firsttime, "false");
            //
            e.commit();
        }*/
        imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent as = new Intent(getActivity(), Prgramcall.class);
                startActivity(as);
            }
        });
        imageView_secons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(getActivity(), Prgramcall.class);
                startActivity(as);
            }
        });
        imageView_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent as = new Intent(getActivity(), Prgramcall.class);
                startActivity(as);
            }
        });

        //tv_error=(TextView) rootView.findViewById(R.id.tv_error);

        return rootView;
    }


    public void CreateDialogAvinash()
    {
        Log.e("aa", "come2");
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.home_dailog, null);
        final Dialog deleteDialog = new Dialog(getActivity());

        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //deleteDialog.setView(deleteDialogView);
        deleteDialog.setContentView(deleteDialogView);
        deleteDialog.setCancelable(false);
        // final TextView textView1=(TextView) deleteDialogView.findViewById(R.id.textView1);
        final ImageView img_cross = (ImageView) deleteDialogView.findViewById(R.id.imageView13);
        TextView user_id_s = (TextView) deleteDialog.findViewById(R.id.user_id_s);

        user_id_s.setText(StaticData.pref(getActivity()).getString(StaticData.PREFKEY_USER_ID, "no key"));
        //fonts.setfont_textview_RalewayMedium(textView1, con);
        img_cross.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteDialog.dismiss();
            }

        });
        deleteDialog.show();
    }
}