package com.cartella;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.prototypes.CardSection;
import it.gmariotti.cardslib.library.prototypes.SectionedCardAdapter;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;
import it.gmariotti.cardslib.library.view.listener.UndoBarController;


public class CartellaActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartella);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.cartella, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        Map<Date,String[][]> calendario = new HashMap<Date, String[][]>();
        public PlaceholderFragment() {

            Date today = new Date();

            // TODO warning alle 12 del giorno prima
            today.setHours(today.getHours()-12);
            calendario.put((Date) today.clone(),new String[][]{
                    new String[]{"Folina","5mg","1","compressa","orale"},
            });
            today.setHours(6);
            // TODO alle 6
            calendario.put((Date) today.clone(),new String[][]{
                    new String[]{"Mepral","40mg","1","fiala","endovena"},
                    new String[]{"Targosid","400mg","1","fiala","endovena"},
            });
            today.setHours(8);
            // TODO alle 8
            calendario.put((Date) today.clone(),new String[][]{
                    new String[]{"Lioresal","10mg","1","compressa","orale"},
                    new String[]{"Keppra","1000mg","1","compressa","orale"},
            });
            today.setHours(20);
            // TODO alle 20
            calendario.put((Date) today.clone(),new String[][]{
                    new String[]{"Lioresal","10mg","1","compressa","orale"},
                    new String[]{"Keppra","1000mg","1","compressa","orale"},
            });
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM cccc, hh");
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.cardlist_undo, container, false);

            // CARD VIEW
//            CardView cardView = (CardView) rootView.findViewById(R.id.carddemo);
//            cardView.setCard(card);

            // CARD LIST
            ArrayList<Card> cards = new ArrayList<Card>();
            // Define your sections
            List<CardSection> sections =  new ArrayList<CardSection>();

            int position=0;
            for(Map.Entry<Date,String[][]> entry : calendario.entrySet()) {

                sections.add(new CardSection(position,dateFormat.format(entry.getKey())));
                position+=entry.getValue().length;

                for(String[] values : entry.getValue()) {
                    Card card = new Card(getActivity(), R.layout.carddemo_example_inner_content);

                    //Create a CardHeader
                    CardHeader header = new CardHeader(getActivity());
                    //Add Header to card
                    card.addCardHeader(header);
                    header.setTitle(values[0]);
                    card.setTitle(values[1]);

                    //Create thumbnail
                    CardThumbnail thumb = new CardThumbnail(getActivity());
                    thumb.setDrawableResource(Utils.getDrawableResource(values[3],R.class));
                    card.addCardThumbnail(thumb);

//                    card.setSwipeable(true);
                    card.setId("w");
                    cards.add(card);
                }
            }

            CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);
            mCardArrayAdapter.setEnableUndo(true);
//            mCardArrayAdapter.setUndoBarUIElements(new UndoBarController.UndoBarUIElements() {
//                @Override
//                public int getUndoBarId() {
//                    return R.id.list_card_undobar;
//                }
//
//                @Override
//                public int getUndoBarMessageId() {
//                    return R.id.list_card_undobar_message;
//                }
//
//                @Override
//                public int getUndoBarButtonId() {
//                    return R.id.list_card_undobar_button;
//                }
//
//                @Override
//                public String getMessageUndo(CardArrayAdapter cardArrayAdapter, String[] strings, int[] ints) {
//                    return "undo";
//                }
//            });

            //Define your Sectioned adapter
            SectionedCardAdapter mAdapter = new SectionedCardAdapter(getActivity(), mCardArrayAdapter);
            CardSection[] dummy = new CardSection[sections.size()];
            mAdapter.setCardSections(sections.toArray(dummy));


            CardListView listView = (CardListView) rootView.findViewById(R.id.myList);
//            listView.setAdapter(mCardArrayAdapter);
            listView.setExternalAdapter(mAdapter,mCardArrayAdapter);

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((CartellaActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
