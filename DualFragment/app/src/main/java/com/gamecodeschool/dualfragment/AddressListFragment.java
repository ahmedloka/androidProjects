package com.gamecodeschool.dualfragment;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class AddressListFragment extends ListFragment {
    private ActivityComs mActivityComs;
    private ArrayList<NameAndAddress>mNamesAndAddresses ;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        /*
activity is a ActivityComs but must
still be explicitly cast to the type
*/
        mActivityComs = (ActivityComs)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mActivityComs = null ;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        // pass the position to MainActivity
        mActivityComs.onListItemSelected(position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNamesAndAddresses = AddressBook.getInstance().getBook();
        AddressListAdapter adapter = new AddressListAdapter(mNamesAndAddresses);
        setListAdapter(adapter);
    }
    private class AddressListAdapter extends ArrayAdapter<NameAndAddress> {
        /*
        This simple constructor lets the ArrayAdapter
        super class
        know what data to use. Notice we didn't need to bind
        the data in onCreate.
        Neither do we need to override getCount,
        getItem or getItemId
        It is all handled by ArrayAdapter because it is a more
        specialized version of Base Adapter
        */
        public AddressListAdapter(ArrayList<NameAndAddress>
                                          namesAndAddresses) {
            super(getActivity(), R.layout.list_item, namesAndAddresses);
        }

        @Override
        public View getView(int whichItem, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getLayoutInflater();
                view = inflater.inflate(R.layout.list_item, null);
            }
// We also have this super-handy getItem method
            NameAndAddress tempNameAndAddress = getItem(whichItem);
            TextView txtName = (TextView) view.findViewById(R.id.txtName);
            txtName.setText(tempNameAndAddress.getName());
            return view;
        }
    }
}
