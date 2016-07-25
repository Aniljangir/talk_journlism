package com.talk.jounlist.talk_jurnlist;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


import com.talk.jounlist.talk_jurnlist.utils.Logger;
import com.talk.jounlist.talk_jurnlist.utils.RoundedImageView;
import com.talk.jounlist.talk_jurnlist.utils.grid_interface;

import java.util.ArrayList;

/**
 * Created by Arun on 6/27/2016.
 */
public class Speaker extends Fragment
{

    grid_interface listener=new grid_interface() {
        @Override
        public void PopUp(int i) {
            CreateDialogAvinash(i);
        }
    };
    ArrayList<Grid_Model> list;
    GridView gView;
    String[] nameList  = {"Rahul srivastava" , "Suhasini Haider" , "Ayaz Memon","Shivam vij","Rahul dev" ,"Ravish Kumar","Raju Narisetti","Dr. David Dunkley",
            "Sanjay Pugalia","Nikhil Pawha","Paronjoy thakurta","Dilip Cherian","Bharat Bhushan","Rajesh Raina","Diksha Madhok","Jairaj Singh","Om saini","Vineet Kumar"
            ,"Nick Davies","Gerard Ryle","Sourish Bhattcharyya","Arun Shourie","Sameer Kulkarni","Govind rajan","Nikhil Kumar","Durga Ragunath","Nikhil Waghle"
            ,"Hartosh Singh Bal","Shankarshan Thakur","Arijit Sen","Sachin Pilot","Ram Madhav","Kumar vishwas","Lakshyaraj Singh Mewar","Dr. C P Joshi"};

    String[] nameDesc = {"Political Editor, NDTV","Diplomatic Editor, The Hindu" ,"Consulting Editor, CNN IBN" ,"Contributing Editor, Huffpost"
            ,"Senior journalist ","Executive Editor,  NDTV India","Senior VP , Strategy ,News Corp","Uni of  Westminster","The Quint","Founder, Medianama"
            ,"Editor, Economic and Political Weekly","Perfect Relations","Editor, Catch News","ETV Group Editor","Editor, Quartz","Daily O","Senior Journalist Jaipur"
            ,"Delhi University","The Guardian","Head, ICIJ","Food columnist","Former Union Minister","Co Founder Scroll","founder India Spend","Time Magzine","Juggernaut books"
            ,"Senior journalist","Caravan","The Telegraph","Amnesty International","Congress Chief, Rajasthan","General secretary BJP","National Executive of AAP","HRH Hotels","Indian Politician"};

    String[] nameLongDesc = {
            "Rahul Srivastava, Political Editor, NDTV"
            ,"Suhasini Haidar,Diplomatic Editor at The Hindu. Presenter of WorldView,formerly Foreign Ed,CNN-IBN,& Correspondent CNN Intl"
            ,"Ayaz Memon is an Indian sports writer, journalist, columnist, author and lawyer. He was born in Bridgeport, Connecticut."
            ,"Shivam Vij is Contributing Editor, Huffington Post India, based in New Delhi.He reports on news and politics from India for a variety of local and international publications.He tweets as @DilliDurAst, the handle means \"Delhi is still far.\""
            ,"Journalist, Indian Languages activist. Trustee, Samyak Foundation."
            ,"Ravish Kumar (born 5 December 1974) is an Indian TV anchor,writer and journalist who covers topics pertaining to Indian politics and society.He is the senior executive editor at NDTV India."
            ,"Raju Narisetti is a Senior Vice-President, Strategy at for News Corp. Prior to that, since February 2012, Raju was Managing Editor of The Wall Street Journal Digital Network and a Deputy Managing Editor of The Wall Street Journal."
            ,"Dr David Dunkley Gyimah is an award-winning innovator in digital, journalism and video, behind 8 Days, Obama 100 Days made for the Southbank Centre and Adana on the Syrian border."
            ,"Sanjay Pugalia is a veteran Indian political and business journalist with print and broadcast experience, president and editorial director of both The Quint and Bloomberg Quint."
            ,"Nikhil Pahwa is an Indian entrepreneur, journalist, publisher and founder and owner of MediaNama, an India-based mobile and digital news portal."
            ,"Paranjoy Guha Thakurta is an Indian journalist, political commentator, author and a documentary film maker. His works have appeared in print,radio, television and documentaries"
            ,"Dilip Cherian is a communications consultant, a political campaign advisor and a practicing political & policy professional. He is also involved with political lobbying and bureaucracy decision making."
            ,"Editor of Catch News, Bharat has been a hack for 25 years. He has been the founding Editor of Mail Today, Executive Editor of the Hindustan Times, Editor of The Telegraph in Delhi, Editor of the Express News Service, Washington Correspondent of the Indian Express and an Assistant Editor with The Times of India."
            ,"Journalist by profession (Present : Group Editor-ETV News Network), Obsessed with Urdu language, Blessed to be born in India.Proud to be a Kashmiri Pandit."
            ,"Diksha Madhok is the India Editor at Quartz. She likes writing about gender, popular culture and business. Before coming to Quartz,Diksha worked with Thomson Reuters in New Delhi and studied journalism at Columbia University in New York City. She also has an interest in startups and has worked with Startup Village, a Kerala-based nonprofit that promotes entrepreneurship in India."
            ,""
            ,""
            ,""
            ,"Nicholas Davies is a British investigative journalist, writer and documentary maker. Davies has written extensively as a freelancer, as well as for The Guardian and The Observer, and been named Reporter Of the year."
            ,"Gerard Ryle is an Irish-Australian investigative reporter who has written on subjects including politics, financial and medical scandals, and police corruption."
            ,"Blogger. Food and wine writer. Founder member, Delhi Gourmet Club. Columnist and restaurant reviewer for Mail Today."
            ,"Arun Shourie (born 2 November 1941) is an Indian journalist, author and politician. He has worked as an economist with the World Bank, a consultant to the Planning Commission of India, editor of the Indian Express and The Times of India and a minister in the government of India (1998–2004)."
            ,""
            ,"He is a television & print journalist and also Founder of Boom News, a next generation television news initiative. He also anchors seasonal shows on Indian news television – the current one being the weekly `Policy Watch on public service broadcaster Rajya Sabha or RS TV. And most recent being BottomLine on Headlines Today – and writes for newspapers like Business Standard."
            ,"South Asia bureau chief for TIME"
            ,"Durga Raghunath Co-founder and CEO @Juggernautbooks, former vice president (growth) at restaurant search service Zomato."
            ,"Nikhil Wagle started his media career in 1977 as a freelance reporter. He later joined Dinank, a Marathi newsweekly, in Mumbai. In 1979, when the editor of Dinank resigned, the publisher asked 19-year-old Wagle to become the managing editor. Wagle later became the editor-in-chief of Dinank. Subsequently, he went to Pune, and joined Kirloskar Group, which owned a couple of magazines at that time. However, within one month, he quit his new job and came back to Mumbai."
            ,"Hartosh Singh Bal is the political editor at The Caravan, and is the author of Waters Close Over Us: A Journey Along the Narmada. He was formerly the political editor at Open magazine."
            ,"Sankarshan Thakur is an Indian print journalist.He was born in Bihar in 1962. His work seems deeply inspired by M.J. Akbar, under whom Thakur apprenticed as a journalist for many years."
            ,""
            ,"Sachin Pilot is a politician and a member of the Indian National Congress. He represented Ajmer constituency of Rajasthan at member of Indian Parliament in the 15th Lok Sabha."
            ,"Ram Madhav is an Indian politician, writer and journalist. He serves as the National General Secretary of the Bharatiya Janata Party.He is a former member of the National Executive of the Rashtriya Swayamsevak Sangh and has authored several books."
            ,"Kumar Vishwas is a Hindi-language performance poet and an Indian politician and National Executive of Aam Aadmi Party."
            ,"Lakshyaraj Singh Mewar is a scion of the 1500 year-old House of Mewar in Udaipur. Born on the 28th of January, 1985, he is the only son of Shriji Arvind Singh Mewar and Smt. Vijayraj Kumari Mewar of Udaipur."
            ,"C. P. Joshi (born 29 July 1950) is an Indian Politician. He was the Member of Parliament of India from Bhilwara in the 15th Lok Sabha.He contested the 2014 Lok Sabha elections from Jaipur(Rural) seat."};

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.speakers, container, false);

        /*Log.e("nameList",nameList.length+"");
        Log.e("nameDesc",nameDesc.length+"");
        Log.e("nameLongDesc",nameLongDesc.length+"");*/
        gView = (GridView)rootView.findViewById(R.id.gridView1);
        gView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.e("aa","clicked");
                CreateDialogAvinash(i);
            }
        });
        gView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Logger.e("aa","clicked");
                CreateDialogAvinash(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        list = new ArrayList<Grid_Model>();

        for(int i = 0 ; i <35 ; i++)
        {
            Grid_Model m = new Grid_Model();
            m.setName(nameList[i]);
            m.setDesc(nameDesc[i]);
            m.setLongdesc(nameLongDesc[i]);

            switch (i)
            {


                case 0 : m.setImage(R.mipmap.img1);
                        break;

                case 1 : m.setImage(R.mipmap.img2);
                        break;

                case 2 : m.setImage(R.mipmap.img3);
                        break;

                case 3 : m.setImage(R.mipmap.img4);
                        break;

                case 4 : m.setImage(R.mipmap.img5);
                        break;

                case 5 : m.setImage(R.mipmap.img6);
                        break;

                case 6 : m.setImage(R.mipmap.img7);
                        break;

                case 7 : m.setImage(R.mipmap.img8);
                        break;

                case 8 : m.setImage(R.mipmap.img9);
                        break;

                case 9 : m.setImage(R.mipmap.img10);
                        break;

                case 10 : m.setImage(R.mipmap.img11);
                        break;

                case 11 : m.setImage(R.mipmap.img12);
                        break;

                case 12 : m.setImage(R.mipmap.img13);
                        break;

                case 13 : m.setImage(R.mipmap.img14);
                        break;

                case 14 : m.setImage(R.mipmap.img15);
                        break;

                case 15 : m.setImage(R.mipmap.img16);
                        break;

                case 16 : m.setImage(R.mipmap.img17);
                        break;

                case 17 : m.setImage(R.mipmap.img18);
                        break;

                case 18 : m.setImage(R.mipmap.img19);
                        break;

                case 19 : m.setImage(R.mipmap.img20);
                        break;

                case 20 : m.setImage(R.mipmap.img21);
                        break;

                case 21 : m.setImage(R.mipmap.img22);
                        break;

                case 22 : m.setImage(R.mipmap.img23);
                        break;

                case 23 : m.setImage(R.mipmap.img24);
                        break;

                case 24 : m.setImage(R.mipmap.img25);
                        break;

                case 25 : m.setImage(R.mipmap.img26);
                        break;

                case 26 : m.setImage(R.mipmap.img27);
                        break;

                case 27 : m.setImage(R.mipmap.img28);
                        break;

                case 28 : m.setImage(R.mipmap.img29);
                        break;

                case 29 : m.setImage(R.mipmap.img30);
                        break;

                case 30: m.setImage(R.mipmap.img31);
                        break;

                case 31 : m.setImage(R.mipmap.img32);
                        break;

                case 32 : m.setImage(R.mipmap.img33);
                        break;

                case 33 : m.setImage(R.mipmap.img34);
                        break;

                case 34 : m.setImage(R.mipmap.img35);
                        break;


            }
            //m.setImage((i+1)+"");

            list.add(m);
        }

        gView.setAdapter(new Grid_Adapter(getActivity(), list,listener));
        return rootView;
    }


    public void CreateDialogAvinash(int i)
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
        tv_name.setText(list.get(i).getName());
        textView24.setText(list.get(i).getDesc());
        tv_desc.setText(list.get(i).getLongdesc());
        imageView14.setImageResource(list.get(i).getImage());
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
