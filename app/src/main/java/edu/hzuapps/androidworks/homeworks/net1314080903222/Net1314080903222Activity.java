
package edu.hzuapps.androidworks.homeworks.net1314080903222;
import android.R.layout;
import android.os.Bundle; 
import android.app.Activity; 
import android.view.KeyEvent; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.view.Window; 
import android.webkit.WebChromeClient; 
import android.webkit.WebSettings; 
import android.webkit.WebView; 
import android.webkit.WebViewClient; 
import android.widget.Button; 
import android.widget.EditText; 
public class Net1314080903222Activity extends Activity implements OnClickListener { 
private String url = null; 
private WebView webView; 
private EditText et_url; 
private Button btn_login; 
private Button btn_back; 
private Button btn_exit; 
private Button btn_forward; 
private Button btn_menu; 
@Override
protected void onCreate(Bundle savedInstanceState) { 
super.onCreate(savedInstanceState); 
// ���ڽ����� 
requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
setContentView(R.layout.net1314080903222); 
setProgressBarIndeterminate(true); 
webView = (WebView) findViewById(R.id.webView); 
et_url = (EditText) findViewById(R.id.et_url); 
btn_login = (Button) findViewById(R.id.btn_login); 
btn_back = (Button) findViewById(R.id.btn_back); 
btn_exit = (Button) findViewById(R.id.btn_exit); 
btn_forward = (Button) findViewById(R.id.btn_forward); 
btn_menu = (Button) findViewById(R.id.btn_menu); 
// �������ť��ӵ�������¼� 
btn_login.setOnClickListener(this); 
btn_back.setOnClickListener(this); 
btn_exit.setOnClickListener(this); 
btn_forward.setOnClickListener(this); 
btn_menu.setOnClickListener(this); 
} 
private void setContentView(Class<layout> class1) {
	// TODO Auto-generated method stub
	
}
// btn_login�Ĵ����¼� ����� webView��ʼ��ȡurl 
protected void startReadUrl(String url) { 
// TODO Auto-generated method stub 
// webView����web��Դ 
webView.loadUrl(url); 
// ����webViewĬ��ͨ��ϵͳ���ߵ��������������ҳ����Ϊ 
// ���Ϊfalse����ϵͳ���ߵ��������������ҳ����Ϊ 
webView.setWebViewClient(new WebViewClient() { 
@Override
public boolean shouldOverrideUrlLoading(WebView view, String url) { 
// TODO Auto-generated method stub 
// webView����web��Դ 
view.loadUrl(url); 
return true; 
} 
}); 
// ����֧��javascript 
WebSettings settings = webView.getSettings(); 
settings.setJavaScriptEnabled(true); 
// web����ҳ������ʹ�û������ 
settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); 
// ����ҳ��ʱ ��ʾ������ ҳ�����ȫʱ ���ؽ����� 
webView.setWebChromeClient(new WebChromeClient() { 
@Override
public void onProgressChanged(WebView view, int newProgress) { 
// TODO Auto-generated method stub 
setTitle("��ҳ���Ѽ���" + newProgress + "%"); 
if (newProgress == 100) { 
closeProgressBar(); 
} else { 
openProgressBar(newProgress); 
} 
super.onProgressChanged(view, newProgress); 
} 
}); 
} 
// �򿪽����� 
protected void openProgressBar(int x) { 
// TODO Auto-generated method stub 
setProgressBarIndeterminateVisibility(true); 
setProgress(x); 
} 
// �رս����� 
protected void closeProgressBar() { 
// TODO Auto-generated method stub 
setProgressBarIndeterminateVisibility(false); 
} 
// ��д������ ���ؼ����߼� 
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) { 
// TODO Auto-generated method stub 
if (keyCode == KeyEvent.KEYCODE_BACK) { 
if (webView.canGoBack()) { 
// ������һҳ�� 
webView.goBack(); 
return true; 
} else { 
// �˳����� 
finish(); 
} 
} 
return super.onKeyDown(keyCode, event); 
} 
// �԰�ť�¼��Ĵ��� 
@Override
public void onClick(View v) { 
// TODO Auto-generated method stub 
switch (v.getId()) { 
case R.id.btn_login: 
url = "http://" + et_url.getText().toString(); 
url = url.replace(" ", ""); 
startReadUrl(url); 
break; 
case R.id.btn_back: 
if (webView.canGoBack()) { 
webView.goBack(); 
} else { 
finish(); 
} 
break; 
case R.id.btn_forward: 
if (webView.canGoForward()) { 
webView.goForward(); 
} 
break; 
case R.id.btn_exit: 
finish(); 
break; 
case R.id.btn_menu: 
startReadUrl("http://www.baidu.com"); 
break; 
} 
} 
}
