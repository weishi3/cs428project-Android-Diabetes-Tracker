package com.cs428.dit.diabetestracker;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader;
    private HashMap<String, List<String>> _listDataChild;

    /**
     * The ExpandableListAdapter constructor,saves the android context,listDataHeader and listChildData
     *
     * @param context        the android context
     * @param listDataHeader a string array of the expandable list headers
     * @param listChildData  a HashMap from String to String list, which  corresponds to the expandable list header and its child elements
     */
    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    /**
     * Get the the childPosition-th child element under header with specified index groupPosition
     *
     * @param groupPosition  the index of the expandable list header
     * @param childPosititon the index of the child element under the header specified by groupPosition
     * @return the child element specified
     */
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    /**
     * Get the child element id given the header index groupPosition and child element index ChildPosition
     *
     * @param groupPosition index for the header we inquire about
     * @param childPosition index for the child element we want the id of
     * @return the child id
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Add a view for the child element with header index goupPosition and child index of childPosition given a view convertView,if convertView is not null
     * then set the text for that child under convertView otherwise create the view and edit the text
     *
     * @param groupPosition header index
     * @param childPosition child index
     * @param isLastChild   doesn't do anything here
     * @param convertView   A view that contains the child view
     * @param parent        doesn't do anything here
     * @return A view that contains the child view
     */
    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(childText);
        return convertView;
    }

    /**
     * Get the count of child elements under the groupPosition-th header
     *
     * @param groupPosition the index of the header we want to know about its child elements count
     * @return the number of child under this header
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    /**
     * Get the header name in index groupPosition
     *
     * @param groupPosition the header index
     * @return the name of the header
     */
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    /**
     * Get the number of headers
     *
     * @return the number of headers
     */
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    /**
     * Get the id of the header given its index gourpPosition
     *
     * @param groupPosition the header index
     * @return the id for the header
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Creates and returns the root view for the header with index groupPosition, if covertView is not null simply set the text in the view then retur convertView
     * otherwise inflate a new view then set and returns it
     *
     * @param groupPosition the index of the header
     * @param isExpanded    it doesn't do anything here
     * @param convertView   the view passed in
     * @param parent        it doesn't do anything here
     * @return the root view of the header view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    /**
     * Returns false
     *
     * @return false(never has stable ID)
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets wether the childPosition-th child under header with inde groupPosition is selectable
     *
     * @param groupPosition header index
     * @param childPosition child index
     * @return true(all child selectable)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
