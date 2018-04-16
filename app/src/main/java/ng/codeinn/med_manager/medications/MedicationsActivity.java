package ng.codeinn.med_manager.medications;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import ng.codeinn.med_manager.R;
import ng.codeinn.med_manager.data.source.room.MedicationsDatabase;
import ng.codeinn.med_manager.data.source.room.MedicationsLocalDataSource;
import ng.codeinn.med_manager.util.ActivityUtils;
import ng.codeinn.med_manager.util.AppExecutors;

public class MedicationsActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";

    private MedicationsPresenter mMedicationsPresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        MedicationsFragment medicationsFragment = (MedicationsFragment)
                getSupportFragmentManager().findFragmentById(R.id.medications_container);

        if (medicationsFragment == null){

            medicationsFragment = new MedicationsFragment();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    medicationsFragment, R.id.medications_container);
        }

        MedicationsDatabase database = MedicationsDatabase.getInstance(getApplicationContext());

        mMedicationsPresenter = new MedicationsPresenter(
                MedicationsLocalDataSource.getInstance(new AppExecutors(), database.medicationsDao())
                , medicationsFragment);

        if (savedInstanceState != null){
            MedicationsFilterType currentFilterType = (MedicationsFilterType)
                    savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            mMedicationsPresenter.setFiltering(currentFilterType);
        }
    }


    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.medications, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, mMedicationsPresenter.getFiltering());
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            drawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.list_navigation_menu_item:
                        //Do nothing
                        break;
                    case R.id.sign_out:
                        // TODO: 14/04/2018 Set Up sign out
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                drawer.closeDrawers();
                return true;
            }
        });
    }
}
