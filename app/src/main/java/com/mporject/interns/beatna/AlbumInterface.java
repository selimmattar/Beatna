package comm.example.emirc.beatnafirsttry;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class AlbumInterface extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    NodeJS myAPI;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_interface);
        Retrofit retrofit= RetrofitClient.getInstance();
        myAPI=retrofit.create(NodeJS.class);
        compositeDisposable.add(myAPI.loginUser("emirc","azerty")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) {

                    }
                }));
        ListView listALbum=(ListView) findViewById(R.id.listTracks);
        ArrayList<Song> album=new ArrayList<>();

        Song a1= new Song("1  Consideration");
        Song a2= new Song("2  James Joint");
        Song a3= new Song("3  Kiss It Better");
        Song a4= new Song("4  Same OI' Mistakes");
        Song a5= new Song("5  Desperado");
        Song a6= new Song("6  Youth");
        Song a7=new Song("7   Love on the Brain");
        Song a8=new Song("8   Go Go Go");

        album.add(a1);
        album.add(a2);
        album.add(a3);
        album.add(a4);
        album.add(a5);
        album.add(a6);
        album.add(a7);
        AlbumAdapter adapter = new AlbumAdapter(this, R.layout.list_album, album);
        listALbum.setAdapter(adapter);

        BottomNavigationView bottomNav=findViewById(R.id.bottomNavigationAlbum);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment=null;
            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    selectedFragment=new HomeFragment();
                    break;
                case R.id.nav_favs:
                    selectedFragment=new FavoritesFragment();
                    break;
                case R.id.nav_playlist:
                    selectedFragment=new PlaylistFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment=new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
            return true;
        }
    };
}
