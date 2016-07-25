package com.talk.jounlist.talk_jurnlist;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.talk.jounlist.talk_jurnlist.utils.RoundedImageView;
import com.talk.jounlist.talk_jurnlist.utils.StaticData;

/**
 * Created by Arun on 6/24/2016.
 */
public class Homefragment extends Fragment
{
    Button avinash , jameel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.founders, container, false);
        avinash = (Button)rootView.findViewById(R.id.avinash);
        jameel = (Button)rootView.findViewById(R.id.jameel);

        avinash.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateDialogAvinash();
            }
        });

        jameel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CreateDialogJameel();
            }
        });

        if (StaticData.pref(getActivity()).getString(StaticData.firsttime, "false").equalsIgnoreCase("true"))
        {
            Log.e("aa", "come");
            showUserId();
            SharedPreferences pref = StaticData.pref(getActivity());
            SharedPreferences.Editor e = pref.edit();

            e.putString(StaticData.firsttime, "false");
            e.commit();
        }

        return rootView;
    }

    private void showUserId()
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


    public void CreateDialogAvinash()
    {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.avinash_popup, null);
        final Dialog deleteDialog = new Dialog(getActivity());

        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //deleteDialog.setView(deleteDialogView);
        deleteDialog.setContentView(deleteDialogView);
        deleteDialog.setCancelable(false);
        final TextView tv_name=(TextView) deleteDialogView.findViewById(R.id.textView23);
        final TextView tv_desc=(TextView) deleteDialogView.findViewById(R.id.textView25);
        final TextView textView26=(TextView) deleteDialogView.findViewById(R.id.textView26);
        final TextView textView24=(TextView) deleteDialogView.findViewById(R.id.textView24);
        final RoundedImageView imageView14=(RoundedImageView) deleteDialogView.findViewById(R.id.imageView14);
        final ImageView img_cross=(ImageView) deleteDialogView.findViewById(R.id.imageView13);

        textView26.setVisibility(View.GONE);
        tv_name.setText("AVINASH KALLA");
        textView24.setText("FOUNDER");
        tv_desc.setText("From writing page long news features for almost a decade to telling stories in 140 characters, Avinash has made a transition from print to online.");
        imageView14.setImageResource(R.mipmap.home_sn_av2x);
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


    public void CreateDialogJameel()
    {
        LayoutInflater factory = LayoutInflater.from(getActivity());
        final View deleteDialogView = factory.inflate(R.layout.avinash_popup, null);
        final Dialog deleteDialog = new Dialog(getActivity());

        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //deleteDialog.setView(deleteDialogView);
        deleteDialog.setContentView(deleteDialogView);
        deleteDialog.setCancelable(false);
        final TextView tv_name=(TextView) deleteDialogView.findViewById(R.id.textView23);
        final TextView tv_desc=(TextView) deleteDialogView.findViewById(R.id.textView25);
        final TextView textView26=(TextView) deleteDialogView.findViewById(R.id.textView26);
        final TextView textView24=(TextView) deleteDialogView.findViewById(R.id.textView24);
        final RoundedImageView imageView14=(RoundedImageView) deleteDialogView.findViewById(R.id.imageView14);
        final ImageView img_cross=(ImageView) deleteDialogView.findViewById(R.id.imageView13);

        textView26.setVisibility(View.GONE);
        tv_name.setText("JAMEEL KHAN");
        textView24.setText("Founder");
        tv_desc.setText("Jameel lives by the book. Holding a masters degree in Education, this director of WeCan Schools thinks nothing beyond teaching. ");
        imageView14.setImageResource(R.mipmap.home_sn_jk2x);
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


