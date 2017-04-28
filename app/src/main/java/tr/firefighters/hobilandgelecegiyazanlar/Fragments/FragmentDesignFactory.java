package tr.firefighters.hobilandgelecegiyazanlar.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import static android.R.attr.fragment;

/**
 * Created by bthnorhan on 18.04.2017.
 */

public class FragmentDesignFactory {

    public static final String LOGIN = "fragment_login";
    public static final String MAIN = "fragment_main";
    public static final String REGISTER = "fragment_register";
    public static final String HOBBIES = "fragment_hobbies";
    public static final String HSCOMMENTS = "fragment_hscomments";
    public static final String UCOMMENTS = "fragment_ucomment";
    public static final String PROFILE = "fragment_profile";
    public static final String CHAT = "fragment_chat";
    public static final String NEWCHAT = "fragment_newchat";
    public static final String MESSAGING = "fragment_messaging";

    private static FragmentDesignFactory instance = null;

    private FragmentDesignFactory() { }

    public synchronized static FragmentDesignFactory getInstance() {
        if (instance == null) {
            instance = new FragmentDesignFactory();
        }
        return instance;
    }

    public Fragment getFragment(String fragment){
        return this.getFragment(fragment,null);
    }

    public Fragment getFragment(String fragment, Bundle bundle) {
        if (fragment == null) {
            return null;
        }
        if (fragment.equalsIgnoreCase(LOGIN)) {
            return new LoginFragment();
        } else if (fragment.equalsIgnoreCase(REGISTER)) {
            return new RegisterFragment();
        } else if (fragment.equalsIgnoreCase(MAIN)) {
            return new MainFragment();
        } else if (fragment.equalsIgnoreCase(HOBBIES)) {
            return new HobbiesFragment();
        } else if (fragment.equalsIgnoreCase(PROFILE)) {
            return new ProfileFragment();
        } else if (fragment.equalsIgnoreCase(CHAT)){
            return new ChatFragment();
        } else if(fragment.equalsIgnoreCase(NEWCHAT)) {
            return new newChatFragment();
        } else if(fragment.equalsIgnoreCase(MESSAGING)){
            return new MessagingFragment().newInstance(bundle);
        } else if(fragment.equalsIgnoreCase(HSCOMMENTS)){
            return new HSCommentsFragment();
        } else if(fragment.equalsIgnoreCase(UCOMMENTS)){
            return new UCommentFragment().newInstance(bundle);
        } else {
            return null;
        }
    }
}
