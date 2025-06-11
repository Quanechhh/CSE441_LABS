package com.example.lab_14_02;

import android.app.TabActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends TabActivity {

    private EditText edtTimKiem;
    private ListView lv1, lv2, lv3;

    private List<Song> allSongs; // Master list of all songs
    private List<Song> tab1Songs; // Songs displayed in Tab 1 (filtered)
    private List<Song> tab2Songs; // Songs displayed in Tab 2
    private List<Song> tab3Songs; // Songs displayed in Tab 3

    private SongAdapter adapter1, adapter2, adapter3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        // Initialize TabHost and add tabs
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Tab_TimKiem");
        spec1.setIndicator("Tìm kiếm"); // Tab text
        spec1.setContent(R.id.tab1);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab_BaiHatYeuThich");
        spec2.setIndicator("Bài hát yêu thích"); // Tab text
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab_BaiHatMoi");
        spec3.setIndicator("Bài hát mới"); // Tab text
        spec3.setContent(R.id.tab3);
        tabHost.addTab(spec3);

        tabHost.setCurrentTab(0); // Set default tab

        // Initialize UI elements from included layouts
        edtTimKiem = findViewById(R.id.edtTimKiem);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        lv3 = findViewById(R.id.lv3);

        // Prepare sample data
        allSongs = new ArrayList<>();
        allSongs.add(new Song("52300", "Em là ai Tôi là ai"));
        allSongs.add(new Song("52600", "Bài ca đất Phương Nam"));
        allSongs.add(new Song("52567", "Buồn của Anh"));
        allSongs.add(new Song("57236", "Gói em ở cuối sông hồng"));
        allSongs.add(new Song("51548", "Quê hương tuổi thơ tôi"));
        allSongs.add(new Song("51748", "Em gì ơi"));
        allSongs.add(new Song("57689", "Hát với dòng sông"));
        allSongs.add(new Song("58716", "Say tình _ Remix"));
        allSongs.add(new Song("58916", "Người hãy quên em đi"));
        allSongs.add(new Song("50001", "Bài hát test 1"));
        allSongs.add(new Song("50002", "Bài hát test 2"));
        allSongs.add(new Song("50003", "Bài hát test 3"));


        // Initialize lists for each tab (initially, Tab 1 shows all songs)
        tab1Songs = new ArrayList<>(allSongs); // Copy all songs to Tab 1 initially
        tab2Songs = new ArrayList<>(); // Tab 2 will only show liked songs
        tab3Songs = new ArrayList<>(allSongs.subList(0, Math.min(5, allSongs.size()))); // Example: Tab 3 shows first 5 songs as "new"


        // Setup Adapters
        adapter1 = new SongAdapter(this, R.layout.listitem, tab1Songs);
        lv1.setAdapter(adapter1);

        adapter2 = new SongAdapter(this, R.layout.listitem, tab2Songs);
        lv2.setAdapter(adapter2);

        adapter3 = new SongAdapter(this, R.layout.listitem, tab3Songs);
        lv3.setAdapter(adapter3);


        // --- Search functionality for Tab 1 ---
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterSongs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // --- Tab Change Listener to update contents ---
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("Tab_BaiHatYeuThich".equals(tabId)) {
                    updateLikedSongsTab();
                } else if ("Tab_TimKiem".equals(tabId)) {
                    // When returning to search tab, re-filter based on current text
                    filterSongs(edtTimKiem.getText().toString());
                }
                // adapter1.notifyDataSetChanged(); // Ensure all adapters are up-to-date
                // adapter2.notifyDataSetChanged();
                // adapter3.notifyDataSetChanged();
            }
        });

        // Initial update for Tab 2
        updateLikedSongsTab();
    }

    private void filterSongs(String query) {
        tab1Songs.clear();
        if (query.isEmpty()) {
            tab1Songs.addAll(allSongs); // Show all songs if search query is empty
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            for (Song song : allSongs) {
                if (song.getTieuDe().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery) ||
                        song.getMaSo().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    tab1Songs.add(song);
                }
            }
        }
        adapter1.notifyDataSetChanged();
    }

    // This method needs to be called when a song's liked status changes
    // and when Tab 2 is selected.
    public void updateLikedSongsTab() {
        tab2Songs.clear();
        for (Song song : allSongs) {
            if (song.isLiked()) {
                tab2Songs.add(song);
            }
        }
        adapter2.notifyDataSetChanged();
    }
}