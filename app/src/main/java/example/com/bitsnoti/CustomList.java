package example.com.bitsnoti;

/**
 * Created by handsome on 21/6/17.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {
    private String[] titles;
    private String[] descriptions;
    private String[] dates;
    private String[] times;
    private Activity context;

    public CustomList(Activity context, String[] titles, String[] descriptions, String[] dates, String[] times) {
        super(context, R.layout.list_event, titles);
        this.context = context;
        this.titles = titles;
        this.descriptions = descriptions;
        this.dates = dates;
        this.times = times;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_event, null, true);
        TextView textViewtitle = (TextView) listViewItem.findViewById(R.id.textViewtitle);
        TextView textViewdescription = (TextView) listViewItem.findViewById(R.id.textViewdescription);
        TextView textViewdate = (TextView) listViewItem.findViewById(R.id.textViewdate);
        TextView textViewtime = (TextView) listViewItem.findViewById(R.id.textViewtime);

        textViewtitle.setText(titles[position]);
        textViewdescription.setText(descriptions[position]);
        textViewdate.setText(dates[position]);
        textViewtime.setText(times[position]);

        return listViewItem;
    }
}

