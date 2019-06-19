package pkkl.sample.bookstore;
import android.text.*;
import android.view.*;

import java.util.*;
import org.json.*;

public class MainActivity extends JSONFeedActivity
{
	//links နွင့္ titles ကိုက္ေအာင္ ထည့္ပါ။
	//000webhost
	/*
	 String[] links={
	 "https://phyoeko.000webhostapp.com/books1.html",
	 "https://phyoeko.000webhostapp.com/books2.html",
	 "https://phyoeko.000webhostapp.com/books3.html",
	 "https://phyoeko.000webhostapp.com/books4.html"
	 };
	 */

	//Blogger
	String[] links={
		"Books1",
		"Books2",
		"Books3",
		"Books4"
	};

	String[] titles={
		"Programming",
		"Graphics",
		"English",
		"Novel"
	};

	int current=0;

	@Override
	public void _Options_Menu_Click(MenuItem item)
	{
		int id=item.getItemId();
		if (id == R.id.menu_about)
		{
			MsgBox("About",
				   "This project is created by PhyoeKo"

				 );
		}
		else if (id == R.id.navigation_item_contact)
		{
			//open contact Activity
		}
		else if (id == R.id.navigation_item_facebook)
		{
			//open facebook
		}
		else
		{
			if (id == R.id.menu_programming)
			{
				current = 0;
			}
			else if (id == R.id.menu_graphics)
			{
				current = 1;
			}
			else if (id == R.id.menu_english)
			{
				current = 2;
			}
			else if (id == R.id.menu_novel)
			{
				current = 3;
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
		try
		{
			JSONObject jo=new JSONObject(input);
			JSONArray ja=jo.getJSONObject("feed").getJSONArray("entry");
			JSONObject jo2;
			for(int i=0;i<ja.length();i++){
				jo2=ja.getJSONObject(i);
				if(jo2.getJSONObject("title").getString("$t").equals(links[current])){
					String content=jo2.getJSONObject("content").getString("$t");

					JSONObject obj=new JSONObject(Html.fromHtml(content).toString());
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

			}
		}
		catch (JSONException e)
		{
			//Toast.makeText(this,e.toString(),1).show();
		}
	}

	@Override
	public boolean useGridLayout()
	{
		return true;
	}

	@Override
	public String getFeedAddress()
	{
		setTitle(titles[current]);
		//Blogger
		return "https://technologicaluniversitytoungoo.blogspot.com/feeds/posts/default?alt=json";

		//000webhost
		//return links[current];
	}
}
