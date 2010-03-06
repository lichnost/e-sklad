package org.stablylab.webui.client.widget;

import java.util.Map;

import org.stablylab.webui.client.service.LoginServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.button.StatusButtonBar;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;

/**
 * @author Pavel Semyonov <-- vizbor84 [AT] list [DOT] ru -->
 *
 */
public class LoginDialog extends Dialog {

	protected LoginServiceAsync loginService = (LoginServiceAsync) Registry.get("loginService");
	protected StatusButtonBar buttonBar;
	protected TextField<String> organisation;
	protected TextField<String> userName;
	protected TextField<String> password;
	protected TextField<String> company;
	protected TextField<String> phone;
	protected TextField<String> email;
	protected TextField<String> serial;
	protected TextField<String> key;
	protected Button demo;
	protected Button reset;
	protected Button login;
	protected Map<String, String> regInfo;
	protected Boolean tr = new Boolean(true);
	protected Boolean fl = new Boolean(false);
	
	private String[] ad = {
			"e807f1fcf82d132f9bb018ca6738a19f",	"f9bccff7b7d5ea82dfabe6f469b3461a", "fbdf8f080185880b66bc4568341a1423",
			"ee498d5c94fa1f3209bdf4ce05380d9b", "a06c3b6ddb0f4375096b46359e691906", "6e8f3cd6eb2ea4ddc2dd2ce5770b6550",
			"8479522dec8ec07b126308c83ed2e465", "0310a42114069b23d6d9d35655b40087", "0e2635df044d8215dbe038fb900437ff"};

	public LoginDialog() 
	{
		createLoginForm();
	}

	protected void createRegisterForm()
	{
		
		this.setLayout(new FlowLayout());
		
		HTML head = new HTML("<b>e.Склад</b></br></br>");
		this.add(head);
		
		LayoutContainer form = new LayoutContainer();
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(140);
		layout.setDefaultWidth(200);
		form.setLayout(layout);
		
		this.setButtons("");
		this.setHeading("Регистрация");
		this.setModal(true);
		this.setBodyBorder(true);
		this.setBodyStyle("padding: 8px;");
		this.setWidth(410);
		this.setResizable(false);	

	    company = new TextField<String>();
	    company.setFieldLabel("Название организации");
	    form.add(company);
		
	    phone = new TextField<String>();
	    phone.setFieldLabel("Телефон");
	    form.add(phone);

	    email = new TextField<String>();
	    email.setFieldLabel("E-mail");
		email.setValidator(new Validator<String, TextField<String>>() {
		public String validate(TextField<String> field, String value) {
			if (!value.toUpperCase().matches("[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}")) return "Необходимо ввести e-mail адрес";
				return null;
			}
		});
	    form.add(email);

	    serial = new TextField<String>();
	    serial.setFieldLabel("Серийный номер");
	    form.add(serial);
	    
	    key = new TextField<String>();
	    key.setFieldLabel("Ключ продукта");
	    form.add(key);
	    this.add(form);
	    this.setFocusWidget(company);

	    buttonBar = new StatusButtonBar();
	    createRegisterButtons();
	    this.setButtonBar(buttonBar);
	    this.show();
	}

	protected void createLoginForm()
	{
		
		this.setLayout(new FlowLayout());
		HTML head = new HTML("<b>e.Склад</b></br>");
		this.add(head);
		
		LayoutContainer form = new LayoutContainer();
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		form.setLayout(layout);
		
		this.setButtons("");
		this.setHeading("Вход");
		this.setModal(true);
		this.setBodyBorder(true);
		this.setBodyStyle("padding: 8px;");
		this.setWidth(310);
		this.setResizable(false);	

	    organisation = new TextField<String>();
	    organisation.setFieldLabel("Организация");
	    form.add(organisation);
		
	    userName = new TextField<String>();
	    userName.setFieldLabel("Пользователь");
	    form.add(userName);

	    password = new TextField<String>();
	    password.setMinLength(4);
	    password.setPassword(true);
	    password.setFieldLabel("Пароль");
	    form.add(password);
	    
	    //для сайта раскомментировть
//	    userName.setValue("admin");
//	    password.setValue("admin");
	    
	    this.add(form);
	    this.setFocusWidget(organisation);

	    buttonBar = new StatusButtonBar();
	    createLoginButtons();
	    this.setButtonBar(buttonBar);
	    this.show();
	}

	protected void createLoginButtons()
	{
		reset = new Button("Сбросить");
		reset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				organisation.reset();
				userName.reset();
				password.reset();
				LoginDialog.this.setFocusWidget(organisation);
			}

		});

		login = new Button("Войти");
		login.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(isValidLoginForm())
					onLogin();
			}
		});

		buttonBar.add(reset);
		buttonBar.add(login);
	}

	protected void createRegisterButtons()
	{
		demo = new Button("Демо");
		demo.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onDemo();
			}

		});
		
		reset = new Button("Сбросить");
		reset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				organisation.reset();
				userName.reset();
				password.reset();
				LoginDialog.this.setFocusWidget(organisation);
			}

		});

		login = new Button("Активировать");
		login.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				if(isValidRegisterForm())
					onRegister();
			}
		});
		
		buttonBar.add(demo);
		buttonBar.add(reset);
		buttonBar.add(login);
	}
	
	protected void onDemo() {
		Registry.register("d", true);
	    loginService.tryLogin("first.org", "admin", "admin", new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {

				buttonBar.enable();
				buttonBar.getStatusBar().clear();
				
		    	AppInfo.display("Ошибка!", "Ошибка соединения.");
			}

			public void onSuccess(Boolean logged) {
				buttonBar.enable();
				buttonBar.getStatusBar().clear();
				// GWT.log(logged.toString(), null);
				if (logged == null) {
					
				} else if (logged.booleanValue()) {
					LoginDialog.this.hide();
				} else if (!logged.booleanValue()) {
			    	AppInfo.display("Ошибка!","Неверные данные");
				}
			}
			}
	    );
	}

	protected void onRegister()
	{
		buttonBar.disable();
		buttonBar.getStatusBar().showBusy();
		
		regInfo.put("key", key.getValue());
		regInfo.put("serial", serial.getValue());
		regInfo.put("company", company.getValue());
		regInfo.put("phone", phone.getValue());
		regInfo.put("email", email.getValue());
		
		loginService.setRegisterData(regInfo, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable arg0) {
				AppInfo.display("Ошибка!", "Ошибка соединения.");
			}

			public void onSuccess(Boolean ok) {
				if(ok){
					Window.open(GWT.getModuleBaseURL().replace(GWT.getModuleName()+"/", ""), "_self", "web-Склад");
				}
				else
					AppInfo.display("Ошибка!","Ошибка при сохранении данных");
			}
		});
//		LoginDialog.this.hide();
	}
	
	protected void onLogin()
	{
		  
		buttonBar.disable();
		buttonBar.getStatusBar().showBusy();
	    
//		LoginDialog.this.hide();
// tryLogin(organisation.getValue()...
		Registry.register("d", false);
	    loginService.tryLogin(organisation.getValue(), userName.getValue(), password.getValue(), new AsyncCallback<Boolean>() {

			public void onFailure(Throwable caught) {

				buttonBar.enable();
				buttonBar.getStatusBar().clear();
				
		    	AppInfo.display("Ошибка!", "Ошибка соединения.");
			}

			public void onSuccess(Boolean logged) {
				buttonBar.enable();
				buttonBar.getStatusBar().clear();
				// GWT.log(logged.toString(), null);
				if (logged == null) {
					
				} else if (logged.booleanValue()) {
					LoginDialog.this.hide();
				} else if (!logged.booleanValue()) {
			    	AppInfo.display("Ошибка!","Неверные данные");
				}
			}
			}
	    );
	  }
	  
	private boolean isValidLoginForm()
	{
		if(userName.getValue()!="" && userName.getValue()!=null && password.getValue()!="" && password.getValue()!=null)
			return true;
		else {
			if(userName.getValue()=="" || userName.getValue()==null) userName.markInvalid(null);
			if(password.getValue()=="" || password.getValue()==null) password.markInvalid(null);
			AppInfo.display("Нет данных", "Заполните необходимые поля");
			return false;
		}
	}
	
	private boolean isValidRegisterForm()
	{
		if(company.getValue()!="" && company.getValue()!=null
				&& phone.getValue()!="" && phone.getValue()!=null
				&& email.getValue()!="" && email.getValue()!=null
				&& serial.getValue()!="" && serial.getValue()!=null
				&& key.getValue()!="" && key.getValue()!=null)
			return true;
		else {
			if(company.getValue()=="" || company.getValue()==null) company.markInvalid(null);
			if(phone.getValue()=="" || phone.getValue()==null) phone.markInvalid(null);
			if(email.getValue()=="" || email.getValue()==null) email.markInvalid(null);
			if(serial.getValue()=="" || serial.getValue()==null) serial.markInvalid(null);
			if(key.getValue()=="" || key.getValue()==null) key.markInvalid(null);
			AppInfo.display("Нет данных", "Заполните необходимые поля");
			return false;
		}
	}
	
	private boolean isValidRegisterData(Map<String, String> map)
	{
		if(map.get("serial")==null || map.get("company")==null || map.get("key")==null)
			return false;
		String md5 = cryptMD5("/"+map.get("serial").toUpperCase()+"//"+map.get("company").toLowerCase()+"/");
		for(int i = 1; i < ad.length ;i++)
			if(md5.equalsIgnoreCase(ad[i]+"e-Sklad"))
				new Boolean(true);
			else new Boolean(false);
		if(cryptMD5(md5+"//e-Sklad").equalsIgnoreCase(map.get("key")))
			return tr;
		if(!cryptMD5(md5+"//e-Sklad").equalsIgnoreCase(map.get("key")))
			return fl;
		return false;
	}
	
	public native String cryptMD5(String string)
	/*-{
		function RotateLeft(lValue, iShiftBits) {
			return (lValue<<iShiftBits) | (lValue>>>(32-iShiftBits));
		}
 
		function AddUnsigned(lX,lY) {
			var lX4,lY4,lX8,lY8,lResult;
			lX8 = (lX & 0x80000000);
			lY8 = (lY & 0x80000000);
			lX4 = (lX & 0x40000000);
			lY4 = (lY & 0x40000000);
			lResult = (lX & 0x3FFFFFFF)+(lY & 0x3FFFFFFF);
			if (lX4 & lY4) {
				return (lResult ^ 0x80000000 ^ lX8 ^ lY8);
			}
			if (lX4 | lY4) {
				if (lResult & 0x40000000) {
					return (lResult ^ 0xC0000000 ^ lX8 ^ lY8);
				} else {
					return (lResult ^ 0x40000000 ^ lX8 ^ lY8);
				}
			} else {
				return (lResult ^ lX8 ^ lY8);
			}
 		}
 
 		function F(x,y,z) { return (x & y) | ((~x) & z); }
 		function G(x,y,z) { return (x & z) | (y & (~z)); }
 		function H(x,y,z) { return (x ^ y ^ z); }
		function I(x,y,z) { return (y ^ (x | (~z))); }
 
		function FF(a,b,c,d,x,s,ac) {
			a = AddUnsigned(a, AddUnsigned(AddUnsigned(F(b, c, d), x), ac));
			return AddUnsigned(RotateLeft(a, s), b);
		};
 
		function GG(a,b,c,d,x,s,ac) {
			a = AddUnsigned(a, AddUnsigned(AddUnsigned(G(b, c, d), x), ac));
			return AddUnsigned(RotateLeft(a, s), b);
		};
 
		function HH(a,b,c,d,x,s,ac) {
			a = AddUnsigned(a, AddUnsigned(AddUnsigned(H(b, c, d), x), ac));
			return AddUnsigned(RotateLeft(a, s), b);
		};
 
		function II(a,b,c,d,x,s,ac) {
			a = AddUnsigned(a, AddUnsigned(AddUnsigned(I(b, c, d), x), ac));
			return AddUnsigned(RotateLeft(a, s), b);
		};
 
		function ConvertToWordArray(string) {
			var lWordCount;
			var lMessageLength = string.length;
			var lNumberOfWords_temp1=lMessageLength + 8;
			var lNumberOfWords_temp2=(lNumberOfWords_temp1-(lNumberOfWords_temp1 % 64))/64;
			var lNumberOfWords = (lNumberOfWords_temp2+1)*16;
			var lWordArray=Array(lNumberOfWords-1);
			var lBytePosition = 0;
			var lByteCount = 0;
			while ( lByteCount < lMessageLength ) {
				lWordCount = (lByteCount-(lByteCount % 4))/4;
				lBytePosition = (lByteCount % 4)*8;
				lWordArray[lWordCount] = (lWordArray[lWordCount] | (string.charCodeAt(lByteCount)<<lBytePosition));
				lByteCount++;
			}
			lWordCount = (lByteCount-(lByteCount % 4))/4;
			lBytePosition = (lByteCount % 4)*8;
			lWordArray[lWordCount] = lWordArray[lWordCount] | (0x80<<lBytePosition);
			lWordArray[lNumberOfWords-2] = lMessageLength<<3;
			lWordArray[lNumberOfWords-1] = lMessageLength>>>29;
			return lWordArray;
		};
 
		function WordToHex(lValue) {
			var WordToHexValue="",WordToHexValue_temp="",lByte,lCount;
			for (lCount = 0;lCount<=3;lCount++) {
				lByte = (lValue>>>(lCount*8)) & 255;
				WordToHexValue_temp = "0" + lByte.toString(16);
				WordToHexValue = WordToHexValue + WordToHexValue_temp.substr(WordToHexValue_temp.length-2,2);
			}
			return WordToHexValue;
		};
 
		function Utf8Encode(string) {
			string = string.replace(/\r\n/g,"\n");
			var utftext = "";
 
			for (var n = 0; n < string.length; n++) {
 
				var c = string.charCodeAt(n);
 
				if (c < 128) {
					utftext += String.fromCharCode(c);
				}
				else if((c > 127) && (c < 2048)) {
					utftext += String.fromCharCode((c >> 6) | 192);
					utftext += String.fromCharCode((c & 63) | 128);
				}
				else {
					utftext += String.fromCharCode((c >> 12) | 224);
					utftext += String.fromCharCode(((c >> 6) & 63) | 128);
					utftext += String.fromCharCode((c & 63) | 128);
				}
 
			}
 
			return utftext;
		};
 
		var x=Array();
		var k,AA,BB,CC,DD,a,b,c,d;
		var S11=7, S12=12, S13=17, S14=22;
		var S21=5, S22=9 , S23=14, S24=20;
		var S31=4, S32=11, S33=16, S34=23;
		var S41=6, S42=10, S43=15, S44=21;
 
		string = Utf8Encode(string);
 
		x = ConvertToWordArray(string);
 
		a = 0x67452301; b = 0xEFCDAB89; c = 0x98BADCFE; d = 0x10325476;
 
		for (k=0;k<x.length;k+=16) {
			AA=a; BB=b; CC=c; DD=d;
			a=FF(a,b,c,d,x[k+0], S11,0xD76AA478);
			d=FF(d,a,b,c,x[k+1], S12,0xE8C7B756);
			c=FF(c,d,a,b,x[k+2], S13,0x242070DB);
			b=FF(b,c,d,a,x[k+3], S14,0xC1BDCEEE);
			a=FF(a,b,c,d,x[k+4], S11,0xF57C0FAF);
			d=FF(d,a,b,c,x[k+5], S12,0x4787C62A);
			c=FF(c,d,a,b,x[k+6], S13,0xA8304613);
			b=FF(b,c,d,a,x[k+7], S14,0xFD469501);
			a=FF(a,b,c,d,x[k+8], S11,0x698098D8);
			d=FF(d,a,b,c,x[k+9], S12,0x8B44F7AF);
			c=FF(c,d,a,b,x[k+10],S13,0xFFFF5BB1);
			b=FF(b,c,d,a,x[k+11],S14,0x895CD7BE);
			a=FF(a,b,c,d,x[k+12],S11,0x6B901122);
			d=FF(d,a,b,c,x[k+13],S12,0xFD987193);
			c=FF(c,d,a,b,x[k+14],S13,0xA679438E);
			b=FF(b,c,d,a,x[k+15],S14,0x49B40821);
			a=GG(a,b,c,d,x[k+1], S21,0xF61E2562);
			d=GG(d,a,b,c,x[k+6], S22,0xC040B340);
			c=GG(c,d,a,b,x[k+11],S23,0x265E5A51);
			b=GG(b,c,d,a,x[k+0], S24,0xE9B6C7AA);
			a=GG(a,b,c,d,x[k+5], S21,0xD62F105D);
			d=GG(d,a,b,c,x[k+10],S22,0x2441453);
			c=GG(c,d,a,b,x[k+15],S23,0xD8A1E681);
			b=GG(b,c,d,a,x[k+4], S24,0xE7D3FBC8);
			a=GG(a,b,c,d,x[k+9], S21,0x21E1CDE6);
			d=GG(d,a,b,c,x[k+14],S22,0xC33707D6);
			c=GG(c,d,a,b,x[k+3], S23,0xF4D50D87);
			b=GG(b,c,d,a,x[k+8], S24,0x455A14ED);
			a=GG(a,b,c,d,x[k+13],S21,0xA9E3E905);
			d=GG(d,a,b,c,x[k+2], S22,0xFCEFA3F8);
			c=GG(c,d,a,b,x[k+7], S23,0x676F02D9);
			b=GG(b,c,d,a,x[k+12],S24,0x8D2A4C8A);
			a=HH(a,b,c,d,x[k+5], S31,0xFFFA3942);
			d=HH(d,a,b,c,x[k+8], S32,0x8771F681);
			c=HH(c,d,a,b,x[k+11],S33,0x6D9D6122);
			b=HH(b,c,d,a,x[k+14],S34,0xFDE5380C);
			a=HH(a,b,c,d,x[k+1], S31,0xA4BEEA44);
			d=HH(d,a,b,c,x[k+4], S32,0x4BDECFA9);
			c=HH(c,d,a,b,x[k+7], S33,0xF6BB4B60);
			b=HH(b,c,d,a,x[k+10],S34,0xBEBFBC70);
			a=HH(a,b,c,d,x[k+13],S31,0x289B7EC6);
			d=HH(d,a,b,c,x[k+0], S32,0xEAA127FA);
			c=HH(c,d,a,b,x[k+3], S33,0xD4EF3085);
			b=HH(b,c,d,a,x[k+6], S34,0x4881D05);
			a=HH(a,b,c,d,x[k+9], S31,0xD9D4D039);
			d=HH(d,a,b,c,x[k+12],S32,0xE6DB99E5);
			c=HH(c,d,a,b,x[k+15],S33,0x1FA27CF8);
			b=HH(b,c,d,a,x[k+2], S34,0xC4AC5665);
			a=II(a,b,c,d,x[k+0], S41,0xF4292244);
			d=II(d,a,b,c,x[k+7], S42,0x432AFF97);
			c=II(c,d,a,b,x[k+14],S43,0xAB9423A7);
			b=II(b,c,d,a,x[k+5], S44,0xFC93A039);
			a=II(a,b,c,d,x[k+12],S41,0x655B59C3);
			d=II(d,a,b,c,x[k+3], S42,0x8F0CCC92);
			c=II(c,d,a,b,x[k+10],S43,0xFFEFF47D);
			b=II(b,c,d,a,x[k+1], S44,0x85845DD1);
			a=II(a,b,c,d,x[k+8], S41,0x6FA87E4F);
			d=II(d,a,b,c,x[k+15],S42,0xFE2CE6E0);
			c=II(c,d,a,b,x[k+6], S43,0xA3014314);
			b=II(b,c,d,a,x[k+13],S44,0x4E0811A1);
			a=II(a,b,c,d,x[k+4], S41,0xF7537E82);
			d=II(d,a,b,c,x[k+11],S42,0xBD3AF235);
			c=II(c,d,a,b,x[k+2], S43,0x2AD7D2BB);
			b=II(b,c,d,a,x[k+9], S44,0xEB86D391);
			a=AddUnsigned(a,AA);
			b=AddUnsigned(b,BB);
			c=AddUnsigned(c,CC);
			d=AddUnsigned(d,DD);
		}
	 
		var temp = WordToHex(a)+WordToHex(b)+WordToHex(c)+WordToHex(d);
	 
		return temp.toLowerCase();

	}-*/;
}