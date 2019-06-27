package pkkl.sample.bookstore;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.text.*;
import android.view.*;
import android.widget.TextView;

import java.util.*;

import org.json.*;

public class MainActivity extends JSONFeedActivity {

//Adapter mAdapter;
//
// //use this method to return recycler adapter item count..
// private int recyclerCount(){
//    int count = 0;
//    if (mAdapter != null) {
//       count = mAdapter.getItemCount();
//    }
//    return count;
// }






    //Blogger
    String[] links = {
            "Books1",
            "Books2",
            "Books3",
            "Books4",
            "Books5",
            "Books6",
            "Books7",
            "Books8"
    };

    String[] titles = {
            "Programming",
            "Poem",
            "English",
            "Novel",
            "Health",
            "History",
            "Magazine",
            "Comedy"
    };

    @Override
    public boolean onSupportNavigateUp() {
        //This method is called when the up button is pressed. Just the pop back stack.
        getSupportFragmentManager().popBackStack();
        return true;
    }

    int current = 0;

    @Override
    public void _Options_Menu_Click(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_about) {
            MsgBox("About",
                    "This project is created by Kaung Htet & Myo Thawtar Lin."

            );
        } else {
            if (id == R.id.menu_programming) {
                current = 0;
            } else if (id == R.id.menu_poem) {
                current = 1;
            } else if (id == R.id.menu_english) {
                current = 2;
            } else if (id == R.id.menu_novel) {
                current = 3;
            } else if (id == R.id.menu_health) {
                current = 4;
            }else if (id == R.id.menu_history){
                current =5;
            }else if(id == R.id.menu_magazine){
                current =6;
            }else if(id == R.id.menu_comedy){
                current =7;
            }

            refresh();
        }
    }

    //000webhost
	/*
	 public void processJson(String inputJson){
	 posts = new ArrayList<PostItem>();
	 String input="";
	 switch(currentFont){
	 case FONT_ZAWGYI:
	 input=FontConverter.uni2zg(inputJson);
	 break;
	 case FONT_UNI:
	 input=FontConverter.zg2uni(inputJson);
	 break;
	 default:
	 input=inputJson;
	 break;
	 }
	 input=Html.fromHtml(input).toString();
	 if(input.indexOf("<body>")>=0)
	 input=input.substring(input.indexOf("<body>")+6);
	 if(input.indexOf("</body>")>=0)
	 input=input.substring(0,input.indexOf("</body>"));
	 try
	 {

	 JSONObject obj=new JSONObject(input);
	 JSONArray jarr=obj.getJSONArray("books");
	 for(int j=0;j<jarr.length();j++){
	 PostItem p=new PostItem();
	 p.title=(jarr.getJSONObject(j).getString("title"));
	 p.type=(jarr.getJSONObject(j).getString("type"));
	 p.link=(jarr.getJSONObject(j).getString("link"));
	 p.thumbnailUrl=(jarr.getJSONObject(j).getString("image"));
	 p.desc=(jarr.getJSONObject(j).getString("desc"));
	 addItem(p);
	 }
	 }
	 catch (JSONException e)
	 {
	 //Toast.makeText(this,e.toString(),1).show();
	 }
	 }
	 */

    //Blogger
    public void processJson(String inputJson) {
        posts = new ArrayList<PostItem>();
        String input = "";
        switch (currentFont) {
            case FONT_ZAWGYI:
                input = FontConverter.uni2zg(inputJson);
                break;
            case FONT_UNI:
                input = FontConverter.zg2uni(inputJson);
                break;
            default:
                input = inputJson;
                break;
        }
        try {
            JSONObject jo = new JSONObject(input);
            JSONArray ja = jo.getJSONObject("feed").getJSONArray("entry");
            JSONObject jo2;
            for (int i = 0; i < ja.length(); i++) {
                jo2 = ja.getJSONObject(i);
                if (jo2.getJSONObject("title").getString("$t").equals(links[current])) {
                    String content = jo2.getJSONObject("content").getString("$t");

                    JSONObject obj = new JSONObject(Html.fromHtml(content).toString());
                    JSONArray jarr = obj.getJSONArray("books");
                    for (int j = 0; j < jarr.length(); j++) {
                        PostItem p = new PostItem();
                        p.title = (jarr.getJSONObject(j).getString("title"));
                        p.type = (jarr.getJSONObject(j).getString("type"));
                        p.link = (jarr.getJSONObject(j).getString("link"));
                        p.thumbnailUrl = (jarr.getJSONObject(j).getString("image"));
                        p.desc = (jarr.getJSONObject(j).getString("desc"));
                        addItem(p);
                    }

                }

            }
        } catch (JSONException e) {
            //Toast.makeText(this,e.toString(),1).show();
        }
    }

    @Override
    public boolean useGridLayout() {
        return true;
    }

    @Override
    public String getFeedAddress() {
        setTitle(titles[current]);
        //Blogger
        return "https://mybookstore12345.blogspot.com/feeds/posts/default?alt=json";

        //000webhost
        //return links[current];
    }
}
