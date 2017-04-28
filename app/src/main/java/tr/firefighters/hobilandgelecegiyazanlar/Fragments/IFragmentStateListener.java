package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.os.Bundle;

/**
 * Created by bthnorhan on 18.04.2017.
 */

public interface IFragmentStateListener {
    void onFragmentChange(String tag);
    void onFragmentChange(String tag, Bundle bundle);
}
