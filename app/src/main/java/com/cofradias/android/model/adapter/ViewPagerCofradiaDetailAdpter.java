package com.cofradias.android.model.adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cofradias.android.R;
import com.cofradias.android.model.object.Cofradia;
import com.cofradias.android.view.DetailCofradiaFragment;
import com.cofradias.android.view.DetailDetalleFragment;
import com.cofradias.android.view.DetailGaleriaFragment;
import com.cofradias.android.view.DetailPasoFragment;

public class ViewPagerCofradiaDetailAdpter extends FragmentPagerAdapter {

    public Context context;
    private String tab1, tab2, tab3, tab4;
    private String[] tabtitlearray = new String[4];

    Cofradia[] cofradiaList;
    DetailCofradiaFragment.InterfaceDetailCofradia activity;

    public ViewPagerCofradiaDetailAdpter(DetailCofradiaFragment.InterfaceDetailCofradia activity, FragmentManager manager, Context context, Cofradia[] cofradiaList){
        super(manager);
        this.context = context;
        this.cofradiaList = cofradiaList;
        this.activity=activity;

        tab1 = context.getString(R.string.tab_detail_one);
        tab2 = context.getString(R.string.tab_detail_two);
        tab3 = context.getString(R.string.tab_detail_three);
        tab4 = context.getString(R.string.tab_detail_four);

        tabtitlearray[0]=tab1;
        tabtitlearray[1]=tab2;
        tabtitlearray[2]=tab3;
        tabtitlearray[3]=tab4;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0: {
                DetailCofradiaFragment detailCofradiaFragment = new DetailCofradiaFragment();
                detailCofradiaFragment.setCofradia(cofradiaList[position]);
                detailCofradiaFragment.setListener(activity);
                return detailCofradiaFragment;
            }
            case 1: {
                DetailDetalleFragment detailDetalleFragment = new DetailDetalleFragment();
                detailDetalleFragment.setCofradia(cofradiaList[0]);
                return detailDetalleFragment;
            }
            case 2: {
                DetailPasoFragment detailPasoFragment = new DetailPasoFragment();
                detailPasoFragment.setCofradia(cofradiaList[0]);
                return detailPasoFragment;
            }
            case 3: {
                DetailGaleriaFragment detailGaleriaFragment = new DetailGaleriaFragment();
                detailGaleriaFragment.setCofradia(cofradiaList[0]);
                return detailGaleriaFragment;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabtitlearray[position];
    }
}
