package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bthnorhan on 18.04.2017.
 */

public abstract class BaseFragment extends Fragment {

    protected IFragmentStateListener listener;


    protected abstract int getFID();

    protected abstract void init();

    protected abstract void handlers();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        handlers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getFID(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (IFragmentStateListener) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

}
