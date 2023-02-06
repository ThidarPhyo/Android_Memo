package jp.ac.jec.cm0129.android109memo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private ArrayList<String> todoList;
    private TextView txtMemo;
    private TextView dateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.btnSave);
        button.setOnClickListener(new SaveClickListenerImple());

        Button btnLoad = findViewById(R.id.btnLoad);
        btnLoad.setOnClickListener(new LoadClickListenerImple());

        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new AddClickListenerImple());

        Button btnClear = findViewById(R.id.btnAc);
        btnClear.setOnClickListener(new ClearClickListenerImple());

        todoList = new ArrayList<>();

        txtMemo = findViewById(R.id.txtMemoList);
        dateTime = (TextView) findViewById(R.id.txtUpdateData);

    }
    class SaveClickListenerImple implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //EditText text = findViewById(R.id.edtMemo);


            String txt = toCanma(txtMemo.getText().toString());
            String date = dateTime.getText().toString();
            if (txt.equals("") || date.equals("")) {
                Toast.makeText(MainActivity.this,"Please Enter Data",Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences sp = getSharedPreferences("android109",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Memo",txt);
                editor.putString("DATE",date);
                editor.apply();
                System.out.println("memo string--->"+txt);
                System.out.println("memo Date--->"+date);
                Toast.makeText(MainActivity.this,"保存しました",Toast.LENGTH_SHORT).show();
            }

        }
    }
    class LoadClickListenerImple implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //EditText text = findViewById(R.id.edtMemo);
            SharedPreferences sp = getSharedPreferences("android109",MODE_PRIVATE);
            String memo = sp.getString("Memo","");
            String d = sp.getString("DATE","");
            String text = toKaigyou(memo);
            txtMemo.setText(text);
            dateTime.setText(d);


        }
    }
    class AddClickListenerImple implements View.OnClickListener {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onClick(View view) {
            EditText input = findViewById(R.id.edtMemo);


            String itemText = input.getText().toString();
            System.out.println("item text--->"+itemText);
            String toPrint = "";

            if (!(itemText.equals(""))){

                //blah
                todoList.add(itemText);
                System.out.println("array list todolist--->"+todoList.toString());

                for(String str: todoList){ //iterate element by element in a list

                    toPrint += str;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        toPrint = String.join("\n", todoList);

                    }
                    System.out.println(str);

                }
                LocalDate localDate = LocalDate.now();

                DateTimeFormatter japanDateFormat = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
                        .withChronology(IsoChronology.INSTANCE)
                        .withLocale(Locale.JAPAN);
                System.out.println(localDate.format(japanDateFormat));
                dateTime.setText("最終保存日時："+localDate.format(japanDateFormat));

                String memoStr = toPrint;
                txtMemo.setText(memoStr);

                input.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Please enter text!!!", Toast.LENGTH_SHORT)
                        .show();

            }

        }
    }
    private String toCanma(String str){
        String[] sArray = str.split("\n");
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<= (sArray.length - 1);i++){
            sb.append(sArray[i]+",");
        }
        return sb.toString();
    }
    private String toKaigyou(String str){
        String[] sArray = str.split(",");
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i<= (sArray.length - 1);i++){
            sb.append(sArray[i]+"\n");
        }
        return sb.toString();
    }
    class ClearClickListenerImple implements View.OnClickListener {

        @Override
        public void onClick(View view) {
//            //EditText text = findViewById(R.id.edtMemo);
//
//            SharedPreferences sp = getSharedPreferences("android109",MODE_PRIVATE);
//            String memo = sp.getString("Memo","");
//            String date = sp.getString("DATE","");
//            String txt = toKaigyou(memo);
            txtMemo.setText("");
            dateTime.setText("");

        }
    }
}