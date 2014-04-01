package info.androidhive.StockEProject;

import java.io.IOException;

import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;


public class PortfolioSellFragment extends Fragment implements OnClickListener{
	
	public PortfolioSellFragment(){}
	public String YAHOO= "http://download.finance.yahoo.com/d/quotes.csv?s=";	

	
	public static double cash = 2000;
	public static double price;

	public static String[] symbol = new String[10];
	public static String[] quantity = new String[10];
	public static TextView[] label = new TextView[10];
	public static TextView textBox;
	public static TextView[] stock = new TextView[10];
	public static TextView message;
	public static EditText txtQuantity;
	public static CheckBox[] checkBox = new CheckBox[10];
	public static boolean checkBox0ON = false;
	public static boolean checkBox1ON = false;
	public static boolean checkBox2ON = false;
	public static boolean checkBox3ON = false;
	public static boolean checkBox4ON = false;
	public static boolean checkBox5ON = false;
	public static boolean checkBox6ON = false;
	public static boolean checkBox7ON = false;
	public static boolean checkBox8ON = false;
	public static boolean checkBox9ON = false;

    private Button bnSell = null;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_sell, container, false);



        textBox = (TextView) rootView.findViewById(R.id.edit_cash);
        textBox.setText(String.format("%.2f", cash));  

        txtQuantity = (EditText) rootView.findViewById(R.id.txt_quantity);

        message = (TextView) rootView.findViewById(R.id.lbl_message);
        
        checkBox[0] = (CheckBox) rootView.findViewById(R.id.radioStock1);
        checkBox[0].setEnabled(checkBox0ON);
        checkBox[1] = (CheckBox) rootView.findViewById(R.id.radioStock2);
        checkBox[1].setEnabled(checkBox1ON);
        checkBox[2] = (CheckBox) rootView.findViewById(R.id.radioStock3);
        checkBox[2].setEnabled(checkBox2ON);
        checkBox[3] = (CheckBox) rootView.findViewById(R.id.radioStock4);
        checkBox[3].setEnabled(checkBox3ON);
        checkBox[4] = (CheckBox) rootView.findViewById(R.id.radioStock5);
        checkBox[4].setEnabled(checkBox4ON);
        checkBox[5] = (CheckBox) rootView.findViewById(R.id.radioStock6);
        checkBox[5].setEnabled(checkBox5ON);
        checkBox[6] = (CheckBox) rootView.findViewById(R.id.radioStock7);
        checkBox[6].setEnabled(checkBox6ON);
        checkBox[7] = (CheckBox) rootView.findViewById(R.id.radioStock8);
        checkBox[7].setEnabled(checkBox7ON);
        checkBox[8] = (CheckBox) rootView.findViewById(R.id.radioStock9);
        checkBox[8].setEnabled(checkBox8ON);
        checkBox[9] = (CheckBox) rootView.findViewById(R.id.radioStock10);
        checkBox[9].setEnabled(checkBox9ON);
        
        stock[0] = (TextView) rootView.findViewById(R.id.stock_1);
        stock[0].setText(quantity[0]);
        stock[1] = (TextView) rootView.findViewById(R.id.stock_2);
        stock[1].setText(quantity[1]);
        stock[2] = (TextView) rootView.findViewById(R.id.stock_3);
        stock[2].setText(quantity[2]);
        stock[3] = (TextView) rootView.findViewById(R.id.stock_4);
        stock[3].setText(quantity[3]);
        stock[4] = (TextView) rootView.findViewById(R.id.stock_5);
        stock[4].setText(quantity[4]);

		label[0] = (TextView) rootView.findViewById(R.id.stock1Name);
        label[0].setText(symbol[0]);
		label[1] = (TextView) rootView.findViewById(R.id.stock2Name);
        label[1].setText(symbol[1]);
		label[2] = (TextView) rootView.findViewById(R.id.stock3Name);
        label[2].setText(symbol[2]);
		label[3] = (TextView) rootView.findViewById(R.id.stock4Name);
        label[3].setText(symbol[3]);
		label[4] = (TextView) rootView.findViewById(R.id.stock5Name);
        label[4].setText(symbol[4]);


		
		bnSell = (Button) rootView.findViewById(R.id.bn_sell);

		bnSell.setOnClickListener(new OnClickListener(){
		    @Override
		    
			public void onClick(View arg0){
			    AsyncTaskRunner runner = new AsyncTaskRunner(); 
			    runner.execute();
			    }});
		
        return rootView;
        
        
    }
	
	public String nameNo()
	{
		for (int x = 0; x<checkBox.length;x++)
		{
			if (checkBox[x].isChecked())
			{
				return label[x].getText().toString();
			}
		}
		return "";
	}
	public int stockNo()
	{
		int x;
		for (x = 0; x<checkBox.length;x++)
		{
			if (checkBox[x].isChecked())
			{
				return x;
			}
		}
		return x;
	}
	 private class AsyncTaskRunner extends AsyncTask<String, Void, String> {


		  @Override
		  protected String doInBackground(String... s) {
			  try{
				HttpURLConnection connection = null;
				String stock = nameNo();
				String site= YAHOO+stock+"&f=l1&e=.csv";
				
				try {
					connection = (HttpURLConnection) new URL(site).openConnection();
					if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
						InputStreamReader reader = new InputStreamReader(connection.getInputStream());
						StringBuilder sb = new StringBuilder();
						char[] buf = new char[1024];
						int read;
						while ((read = reader.read(buf)) != -1) {
							sb.append(buf, 0, read);
						}
						reader.close();
						
						return sb.toString();
						
						};			
				} catch (MalformedURLException e){
					textBox.setText("Error retrieving price: " +e.getMessage());
				} catch (IOException e) {
					textBox.setText("Error retrieving price: " +e.getMessage());
				}

				return "";
			  }
			  catch (Exception e)
			  {
				  
			  }
			  return "";
			}
		  

		  
		  
		  /*
		   * (non-Javadoc)
		   * 
		   * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		   */
		  @Override
		  protected void onPostExecute(String result) {
		   // execution of result of Long time consuming operation
			  try{
				price = Double.parseDouble(result);
				int chosenQuantity = Integer.parseInt(txtQuantity.getText().toString());
				int x = stockNo();
				int availableQuantity = Integer.parseInt(stock[x].getText().toString());
				if (chosenQuantity<=availableQuantity && chosenQuantity > 0)
				{
					chosenQuantity = Integer.parseInt(txtQuantity.getText().toString());
					setCash(cash+(price*chosenQuantity));
			        textBox.setText(String.format("%.2f", cash)); 
			        
					message.setText("Stock sold.");
					  
					if (availableQuantity-chosenQuantity == 0)
					{
						if (x==0)
						{
							setSymbol0("");
							setQuantity0("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn0False();
						}
						
						else if (x==1)
						{
							setSymbol1("");
							setQuantity1("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn1False();
						}
						
						else if (x==2)
						{
							setSymbol2("");
							setQuantity2("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn2False();
						}
						
						else if (x==3)
						{
							setSymbol3("");
							setQuantity3("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn3False();
						}
						
						else if (x==4)
						{
							setSymbol4("");
							setQuantity4("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn4False();
						}
						
						else if (x==5)
						{
							setSymbol5("");
							setQuantity5("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn5False();
						}
						
						else if (x==6)
						{
							setSymbol6("");
							setQuantity6("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn6False();
						}
						
						else if (x==7)
						{
							setSymbol7("");
							setQuantity7("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn7False();
						}
						
						else if (x==8)
						{
							setSymbol8("");
							setQuantity8("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn8False();
						}
						
						else if (x==9)
						{
							setSymbol9("");
							setQuantity9("");
							label[x].setText(symbol[x]);
							stock[x].setText(quantity[x]);
						setCheckBoxOn9False();
						}
					}
					else if (availableQuantity-chosenQuantity != 0)
					{
						int remaining;
						if (x==0)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity0(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==1)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity1(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==2)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity2(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==3)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity3(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==4)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity4(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==5)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity5(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==6)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity6(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==7)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity7(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==8)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity8(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
						else if (x==9)
						{
						remaining = availableQuantity-chosenQuantity;
						setQuantity9(Integer.toString(remaining));
						stock[x].setText(quantity[x]);
						}
					}
				}
				else
				{
					  message.setText("Cannot sell more than you own.");

				}
			  }
			  catch (Exception e)
			  {
				  message.setText("Choose a stock and specify how many.");
			  }
			  
		  }

		  /*
		   * (non-Javadoc)
		   * 
		   * @see android.os.AsyncTask#onPreExecute()
		   */
		  @Override
		  protected void onPreExecute() {
		   // Things to be done before execution of long running operation. For
		   // example showing ProgessDialog
		  }

		  /*
		   * (non-Javadoc)
		   * 
		   * @see android.os.AsyncTask#onProgressUpdate(Progress[])
		   */

		 }
	public static double getCash() {
	    return cash;
	}
	public static void setCash(double var) {
		cash = var;
		}
	
	// GET SYMBOLS
	public static String getSymbol0()
	{
		return symbol[0];
	}

	public static String getSymbol1()
	{
		return symbol[1];
	}
	
	public static String getSymbol2()
	{
		return symbol[2];
	}

	
	public static String getSymbol3()
	{
		return symbol[3];
	}
	
	public static String getSymbol4()
	{
		return symbol[4];
	}

	public static String getSymbol5()
	{
		return symbol[5];
	}
	
	public static String getSymbol6()
	{
		return symbol[6];
	}

	public static String getSymbol7()
	{
		return symbol[7];
	}
	
	public static String getSymbol8()
	{
		return symbol[8];
	}

	
	public static String getSymbol9()
	{
		return symbol[9];
	}


	
	// SET SYMBOLS
	public static void setSymbol0(String var) {
		symbol[0] = var;
		}
	public static void setSymbol1(String var) {
		symbol[1] = var;
		}
	
	public static void setSymbol2(String var) {
		symbol[2] = var;
		}
	public static void setSymbol3(String var) {
		symbol[3] = var;
		}	
	public static void setSymbol4(String var) {
		symbol[4] = var;
		}
	public static void setSymbol5(String var) {
		symbol[5] = var;
		}

	public static void setSymbol6(String var) {
		symbol[6] = var;
		}
	
	public static void setSymbol7(String var) {
		symbol[7] = var;
		}
	public static void setSymbol8(String var) {
		symbol[8] = var;
		}	
	public static void setSymbol9(String var) {
		symbol[9] = var;
		}

	
	// GET AND SET QUANTITIES
	public static String getQuantity0() {
		return quantity[0];
		}
	public static void setQuantity0(String var) {
		quantity[0] = var;
		}
	
	public static String getQuantity1() {
		return quantity[1];
		}
	public static void setQuantity1(String var) {
		quantity[1] = var;
		}
	
	public static String getQuantity2() {
		return quantity[2];
		}
	public static void setQuantity2(String var) {
		quantity[2] = var;
		}
	
	public static String getQuantity3() {
		return quantity[3];
		}
	public static void setQuantity3(String var) {
		quantity[3] = var;
		}
	
	public static String getQuantity4() {
		return quantity[4];
		}
	public static void setQuantity4(String var) {
		quantity[4] = var;
		}
	
	public static String getQuantity5() {
		return quantity[5];
		}
	public static void setQuantity5(String var) {
		quantity[5] = var;
		}
	
	public static String getQuantity6() {
		return quantity[6];
		}
	public static void setQuantity6(String var) {
		quantity[6] = var;
		}
	
	public static String getQuantity7() {
		return quantity[7];
		}
	public static void setQuantity7(String var) {
		quantity[7] = var;
		}
	
	public static String getQuantity8() {
		return quantity[8];
		}
	public static void setQuantity8(String var) {
		quantity[8] = var;
		}
	
	public static String getQuantity9() {
		return quantity[9];
		}
	public static void setQuantity9(String var) {
		quantity[9] = var;
		}
	

	// SET CHECKBOXES
	public static void setCheckBoxOn0True()
	{
		checkBox0ON = true;
	}
	public static void setCheckBoxOn0False()
	{
		checkBox0ON = false;
	}
	
	public static void setCheckBoxOn1True()
	{
		checkBox1ON = true;
	}
	public static void setCheckBoxOn1False()
	{
		checkBox1ON = false;
	}
	
	public static void setCheckBoxOn2True()
	{
		checkBox2ON = true;
	}
	public static void setCheckBoxOn2False()
	{
		checkBox2ON = false;
	}
	
	public static void setCheckBoxOn3True()
	{
		checkBox3ON = true;
	}
	public static void setCheckBoxOn3False()
	{
		checkBox3ON = false;
	}
	

	
	public static void setCheckBoxOn4True()
	{
		checkBox4ON = true;
	}
	public static void setCheckBoxOn4False()
	{
		checkBox4ON = false;
	}

	
	public static void setCheckBoxOn5True()
	{
		checkBox5ON = true;
	}
	public static void setCheckBoxOn5False()
	{
		checkBox5ON = false;
	}
	
	public static void setCheckBoxOn6True()
	{
		checkBox6ON = true;
	}
	public static void setCheckBoxOn6False()
	{
		checkBox6ON = false;
	}
	
	public static void setCheckBoxOn7True()
	{
		checkBox7ON = true;
	}
	public static void setCheckBoxOn7False()
	{
		checkBox7ON = false;
	}
	
	public static void setCheckBoxOn8True()
	{
		checkBox8ON = true;
	}
	public static void setCheckBoxOn8False()
	{
		checkBox8ON = false;
	}
	

	
	public static void setCheckBoxOn9True()
	{
		checkBox9ON = true;
	}
	public static void setCheckBoxOn9False()
	{
		checkBox9ON = false;
	}

	
	
	// check CHECKBOXES
	public static boolean checkBox0Enabled()
	{
		return checkBox[0].isEnabled();
	}
	
	public static boolean checkBox1Enabled()
	{
		return checkBox[1].isEnabled();
	}
	
	public static boolean checkBox2Enabled()
	{
		return checkBox[2].isEnabled();
	}
	
	public static boolean checkBox3Enabled()
	{
		return checkBox[3].isEnabled();
	}
	
	public static boolean checkBox4Enabled()
	{
		return checkBox[4].isEnabled();
	}
	
	public static boolean checkBox5Enabled()
	{
		return checkBox[5].isEnabled();
	}
	
	public static boolean checkBox6Enabled()
	{
		return checkBox[6].isEnabled();
	}
	
	public static boolean checkBox7Enabled()
	{
		return checkBox[7].isEnabled();
	}
	
	public static boolean checkBox8Enabled()
	{
		return checkBox[8].isEnabled();
	}
	
	public static boolean checkBox9Enabled()
	{
		return checkBox[9].isEnabled();
	}
	
	 @Override
	 public void onClick(View V)
	 {

	 }

}
