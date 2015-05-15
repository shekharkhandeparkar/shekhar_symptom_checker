package hsd.symptom.checker.navigation.drawer;

import hsd.symptom.checker.ProfileEditActivity;
import hsd.symptom.checker.ProfileViewActivity;
import hsd.symptom.checker.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

	/**
	 * Remember the position of the selected item.
	 */
	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	/**
	 * A pointer to the current callbacks instance (the Activity).
	 */
	private NavigationDrawerCallbacks mCallbacks;

	public ActionBarDrawerToggle getDrawerToggle() {
		return mDrawerToggle;
	}

	/**
	 * Helper component that ties the action bar to the navigation drawer.
	 */
	private ActionBarDrawerToggle mDrawerToggle;

	private DrawerLayout mDrawerLayout;
	private View mDrawerView;
	private ListView mDrawerListView;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 0;

	public NavigationDrawerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Indicate that this fragment would like to influence the set of
		// actions in the action bar.
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mDrawerView = inflater.inflate(R.layout.fragment_navigation_drawer,
				container, false);
		mDrawerListView = (ListView) mDrawerView
				.findViewById(R.id.listView_menu);
		mDrawerListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						selectItem(position);
					}
				});

		ArrayList<NavDrawerItem> drawerItems = new ArrayList<>();
		drawerItems.add(new NavDrawerItem(R.drawable.ic_symptom_checker,
				"Symptom Checker"));
		drawerItems.add(new NavDrawerItem(R.drawable.ic_bmi_calculator,
				"BMI Calculator"));
		drawerItems.add(new NavDrawerItem(R.drawable.ic_setting, "Settings"));
		drawerItems.add(new NavDrawerItem(R.drawable.ic_logout, "Logout"));

		mDrawerListView.setAdapter(new DrawerAdapter(this.getActivity(),
				drawerItems));
		mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

		mDrawerView.findViewById(R.id.linearLayout_profile_view)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "View Profile",
								Toast.LENGTH_LONG).show();
						startActivity(new Intent(getActivity(),
								ProfileViewActivity.class));
					}
				});

		mDrawerView.findViewById(R.id.textView_about_healthserve)
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(getActivity(), "About Health Serve",
								Toast.LENGTH_LONG).show();
					}
				});

		return mDrawerView;
	}

	/**
	 * Users of this fragment must call this method to set up the navigation
	 * drawer interactions.
	 *
	 * @param fragmentId
	 *            The android:id of this fragment in its activity's layout.
	 * @param drawerLayout
	 *            The DrawerLayout containing this fragment's UI.
	 */
	public void setUp(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		// between the navigation drawer and the action bar app icon.
		mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		toolbar, /* nav drawer image to replace 'Up' caret */
		R.string.open_drawer, /* "open drawer" description for accessibility */
		R.string.close_drawer /* "close drawer" description for accessibility */
		) {
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if (!isAdded()) {
					return;
				}
				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if (!isAdded()) {
					return;
				}
				getActivity().supportInvalidateOptionsMenu(); // calls
																// onPrepareOptionsMenu()
			}
		};
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

	private void selectItem(int position) {
		mCurrentSelectedPosition = position;
		if (mDrawerListView != null) {
			mDrawerListView.setItemChecked(position, true);
		}
		if (mDrawerLayout != null) {
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null) {
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Forward the new configuration the drawer toggle component.
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Callbacks interface that all activities using this fragment must
	 * implement.
	 */
	public static interface NavigationDrawerCallbacks {
		/**
		 * Called when an item in the navigation drawer is selected.
		 */
		void onNavigationDrawerItemSelected(int position);
	}
}