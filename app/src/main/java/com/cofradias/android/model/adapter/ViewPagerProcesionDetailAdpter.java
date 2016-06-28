package com.cofradias.android.model.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cofradias.android.R;
import com.cofradias.android.model.object.Procesion;
import com.cofradias.android.view.DetailIndicacionesFragment;
import com.cofradias.android.view.DetailMapsFragment;
import com.cofradias.android.view.DetailProcesionFragment;

public class ViewPagerProcesionDetailAdpter extends FragmentPagerAdapter {

    private final String LOG_TAG = ViewPagerProcesionDetailAdpter.class.getSimpleName();

    public Context context;
    public Procesion procesion;

    private String tab1, tab2, tab3, tab4;
    private String[] tabtitlearray = new String[3];

    public ViewPagerProcesionDetailAdpter(FragmentManager manager, Context context, Procesion procesion){
        super(manager);

        this.context = context;
        this.procesion = procesion;

        tab1 = context.getString(R.string.tab_maps_one);
        tab2 = context.getString(R.string.tab_maps_three);
        tab3 = context.getString(R.string.tab_maps_four);

        tabtitlearray[0]=tab1;
        tabtitlearray[1]=tab2;
        tabtitlearray[2]=tab3;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0: {
                DetailProcesionFragment detailProcesionFragment = new DetailProcesionFragment();
                Bundle args = new Bundle();
                args.putSerializable("PROCESION_KEY", procesion);
                detailProcesionFragment.setArguments(args);
                return detailProcesionFragment;
            }

            case 1: {
                DetailIndicacionesFragment detailIndicacionesFragment = new DetailIndicacionesFragment();
                Bundle args = new Bundle();
                args.putSerializable("PROCESION_KEY", procesion);
                detailIndicacionesFragment.setArguments(args);
                return detailIndicacionesFragment;
            }

            case 2: {
                DetailMapsFragment detailMapsFragment = new DetailMapsFragment();
                Bundle args = new Bundle();
                args.putSerializable("PROCESION_KEY", procesion);
                detailMapsFragment.setArguments(args);
                return detailMapsFragment;
            }
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabtitlearray[position];
    }
}
