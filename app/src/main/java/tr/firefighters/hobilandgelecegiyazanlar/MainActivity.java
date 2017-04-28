package tr.firefighters.hobilandgelecegiyazanlar;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import tr.firefighters.hobilandgelecegiyazanlar.Fragments.FragmentDesignFactory;
import tr.firefighters.hobilandgelecegiyazanlar.Fragments.IFragmentStateListener;


public class MainActivity extends AppCompatActivity implements IFragmentStateListener {

    private FirebaseAuth mAuth;
    public Toolbar myToolbar;
    public ActionBar actionBar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        myToolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_titleTextColor));
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.nav_quit:
                        mAuth.signOut();
                        FTransaction(FragmentDesignFactory.LOGIN,null);
                        break;
                    case R.id.nav_hobbies:
                        FTransaction(FragmentDesignFactory.HOBBIES,null);
                        break;
                    case R.id.nav_comment:
                        FTransaction(FragmentDesignFactory.HSCOMMENTS,null);
                        break;
                    case R.id.nav_chat:
                        FTransaction(FragmentDesignFactory.CHAT,null);
                        break;
                    case R.id.nav_profile:
                        FTransaction(FragmentDesignFactory.PROFILE,null);
                        break;
                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        hideSoftKeyboard();
        if (user == null) {
            FTransaction(FragmentDesignFactory.LOGIN,null);
        } else {
            FTransaction(FragmentDesignFactory.MAIN,null);
        }
    }

    @Override
    public void onFragmentChange(String tag) {
        this.onFragmentChange(tag,null);
    }

    @Override
    public void onFragmentChange(String tag, Bundle bundle) {
        FTransaction(tag,bundle);
    }

    protected void FTransaction(String fragment,Bundle bundle)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentDesignFactory FDF = FragmentDesignFactory.getInstance();

        Fragment tFragment = FDF.getFragment(fragment,bundle);
        fragmentTransaction.replace(R.id.fragment_container, tFragment);

        fragmentTransaction.setCustomAnimations(R.anim.fadein,R.anim.fadeout);

        fragmentTransaction.commit();
        if (fragment.equalsIgnoreCase(FragmentDesignFactory.LOGIN) || fragment.equalsIgnoreCase(FragmentDesignFactory.REGISTER))
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            actionBar.hide();
        }
        else
        {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            actionBar.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}
