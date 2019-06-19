package pkkl.sample.bookstore;
import android.app.*;
import android.net.*;
import android.os.*;
import android.support.v7.app.*;
import android.view.*;
import android.webkit.*;
import android.widget.*;
import java.io.*;

public class ViewerActivity extends AppCompatActivity
{
	WebView wv;
	String link="",title,type;
	android.support.v7.widget.Toolbar tb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewer_layout);
		tb=(android.support.v7.widget.Toolbar)findViewById(R.id.nnl_toolbar3);
		setSupportActionBar(tb);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		link=getIntent().getStringExtra("link");
		title=getIntent().getStringExtra("title");
		type=getIntent().getStringExtra("type");
		
		setTitle(title);
		wv = (WebView)findViewById(R.id.wv);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient(){});
		wv.getSettings().setAllowFileAccess(true);
		
		wv.setDownloadListener(new DownloadListener() {
			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				if(link.contains("://drive.google.com"))
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
				String mFilePath = "file://" + mBaseFolderPath + "/" + title + "."+ type;
				request.setDestinationUri(Uri.parse(mFilePath));
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
				DownloadManager dm=(DownloadManager)getSystemService(DOWNLOAD_SERVICE);
				dm.enqueue(request);
				Toast.makeText(getApplicationContext(),"Download started.",1).show();
			}
		});
		
		
		if(link.contains("://drive.google.com"))
			wv.loadUrl(link);
		else
			wv.loadUrl("http://docs.google.com/viewer?url="+link);
		
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(item.getItemId()==android.R.id.home){
			finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}
