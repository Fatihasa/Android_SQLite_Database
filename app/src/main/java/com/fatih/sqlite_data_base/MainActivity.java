  package com.fatih.sqlite_data_base;

  import android.os.Bundle;
  import android.view.View;
  import android.widget.AdapterView;
  import android.widget.ArrayAdapter;
  import android.widget.Button;
  import android.widget.EditText;
  import android.widget.ListView;
  import android.widget.Switch;
  import android.widget.Toast;

  import androidx.appcompat.app.AppCompatActivity;

  import java.util.List;

  public class MainActivity extends AppCompatActivity {

    Button btn_add, btn_viewAll;
    Switch sw_active;
    EditText et_name, et_age;
    ListView lv_customerList;
    ArrayAdapter customerArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btn_viewAll);
        sw_active = findViewById(R.id.sw_active);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        lv_customerList = findViewById(R.id.lv_customerList);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerModel customerModel;
                try{
                    customerModel = new CustomerModel(-1,et_name.getText().toString(),Integer.parseInt(et_age.getText().toString()),sw_active.isChecked());
                    Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_LONG).show();

                }catch (Exception e){
                    Toast.makeText(MainActivity.this, "Invalid age", Toast.LENGTH_SHORT).show();
                    customerModel = new CustomerModel(-1,"Error",0,false);


                }

                DataBase dataBase = new DataBase(MainActivity.this);

                boolean success = dataBase.addOne(customerModel);

                Toast.makeText(MainActivity.this, "Success "+ success, Toast.LENGTH_LONG).show();

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DataBase dataBase = new DataBase(MainActivity.this);
                List<CustomerModel> everyone = dataBase.getEveryOne();

                customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,everyone);

                lv_customerList.setAdapter(customerArrayAdapter);

                Toast.makeText(MainActivity.this, everyone.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataBase dataBase = new DataBase(MainActivity.this);
                CustomerModel clickedCustomer = (CustomerModel) parent.getItemAtPosition(position);
                dataBase.deleteOne(clickedCustomer);
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }
        });

    }
}