package example.com.bitsnoti;

/**
 * Created by handsome on 21/6/17.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Belal on 9/22/2015.
 */
public class ParseJSON {
    public static String[] titles;
    public static String[] descriptions;
    public static String[] dates;
    public static String[] times;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_DATE = "date";
    public static final String KEY_TIME = "time";

    private JSONArray users = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            users = jsonObject.getJSONArray(JSON_ARRAY);

            titles = new String[users.length()];
            descriptions = new String[users.length()];
            dates = new String[users.length()];
            times = new String[users.length()];

            for(int i=0;i<users.length();i++){
                JSONObject jo = users.getJSONObject(i);
                titles[i] = jo.getString(KEY_TITLE);
                descriptions[i] = jo.getString(KEY_DESCRIPTION);
                dates[i] = jo.getString(KEY_DATE);
                times[i]= jo.getString(KEY_TIME);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
