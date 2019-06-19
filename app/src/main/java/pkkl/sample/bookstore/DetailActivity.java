package pkkl.sample.bookstore;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.widget.*;
import com.squareup.picasso.*;
import java.io.*;

public class DetailActivity extends AppCompatActivity
{
	TextView tv;
	ImageView iv;
	String title,type,link,image,desc;
	android.support.v7.widget.Toolbar tb;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_layout);
		tb=(android.support.v7.widget.Toolbar)findViewById(R.id.nnl_toolbar2);
		setSupportActionBar(tb);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		tv=(TextView)findViewById(R.id.tvDetail);
		iv=(ImageView)findViewById(R.id.ivDetail);
		Intent intent=getIntent();
		title=intent.getStringExtra("title");
		type=intent.getStringExtra("type");
		link=intent.getStringExtra("link");
		image=intent.getStringExtra("image");
		desc=intent.getStringExtra("desc");
		setTitle(title);
		Picasso.with(this)
			.load(image)
			.into(iv);
		tv.setText(title+"\n\n"+desc);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
    public void downloadClick(View v) {
        downloadFromUrl();
    }
	
	public void readClick(View v) {
        Intent intent=new Intent(this,ViewerActivity.class);
		intent.putExtra("title",title);
		intent.putExtra("type",type);
		intent.putExtra("link",link);
		startActivity(intent);
    }
	
    private void downloadFromUrl(){
		String url=link;
		if(url.contains("://drive.google.com"))
			url=getGoogleDriveDownloadLinkFromUrl(link);
		DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(title);
		String mBaseFolderPath = android.os.Environment
			.getExternalStorageDirectory()
			+ File.separator
			+ "BookStore" + File.separator;
		if (!new File(mBaseFolderPath).exists()) {
			new File(mBaseFolderPath).mkdir();
		}
		
		String mFilePath = "file://" + mBaseFolderPath + "/" + title + "."+type;
		request.setDestinationUri(Uri.parse(mFilePath));
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
		dm.enqueue(request);
		Toast.makeText(this,"Download started.",1).show();
    }
	
	public static String getGoogleDriveDownloadLinkFromUrl( String url )
    {
        int index = url.indexOf( "id=" );
        int closingIndex=0;
        if( index >= 0 )
        {
            index += 3;
            closingIndex = url.indexOf( "&", index );
            if( closingIndex < 0 )
                closingIndex = url.length();
        }
        else
        {
            index = url.indexOf( "file/d/" );
            if( index < 0 ) // url is not in any of the supported forms
                return url;

            index += 7;

            closingIndex = url.indexOf( "/", index );
            if( closingIndex < 0 )
            {
                closingIndex = url.indexOf( "?", index );
                if( closingIndex < 0 )
                    closingIndex = url.length();
            }
        }
		String id=url.substring( index, closingIndex);
        return ("https://drive.google.com/uc?id="+id+"&export=download");
    }
	

}

