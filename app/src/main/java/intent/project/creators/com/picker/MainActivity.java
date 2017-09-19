package intent.project.creators.com.picker;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import intent.project.creators.com.picker.model.SetJob;

/**
 * Created by ngohaihue on 9/19/17.
 */


public class MainActivity extends Activity {
    private TextView txtSetDate, txtSetTime;
    private EditText edtJob, edtContent;
    private Button btnSetDate, btnSetTime, btnSave;
    private ArrayList<SetJob> arrJob = new ArrayList<SetJob>();
    private ArrayAdapter<SetJob> adapter = null;
    private ListView listViewJob;
    private Calendar cal;
    private Date dateFinish;
    private Date hourFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSetDate = (TextView) findViewById(R.id.text_display_date);
        txtSetTime = (TextView) findViewById(R.id.text_display_hour);
        edtJob = (EditText) findViewById(R.id.edt_job);
        edtContent = (EditText) findViewById(R.id.edt_content);
        btnSetDate = (Button) findViewById(R.id.btn_set_date);
        btnSetTime = (Button) findViewById(R.id.btn_set_hour);
        btnSave = (Button) findViewById(R.id.btn_save);
        listViewJob = (ListView) findViewById(R.id.list_job);

        // add du lieu
        adapter = new ArrayAdapter<SetJob>(this, android.R.layout.simple_list_item_1, arrJob);
        listViewJob.setAdapter(adapter);
        getDefaultInfor();

        // bắt sự kiện button set date
        btnSetDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();

            }
        });
        // bắt sự kiện button set giờ
        btnSetTime.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog();

            }
        });
        // bắt sự kiện nút save
        btnSave.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                processAddJob();

            }
        });
        // sủ lý click cái ittem trong list view
        listViewJob.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,
                        arrJob.get(i).getmDiscription(),
                        Toast.LENGTH_LONG).show();

            }
        });
        listViewJob.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                arrJob.remove(i);
                adapter.notifyDataSetChanged();
                return false;


            }
        });

    }

    /**
     * Hàm lấy các thông số mặc định khi lần đầu tiền chạy ứng dụng
     */
    public void getDefaultInfor() {
        cal = Calendar.getInstance();
        // Hiển thị ngày  hệ thống
        SimpleDateFormat dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String strDate = dft.format(cal.getTime());
        txtSetDate.setText(strDate);

        //Hiển thị giờ hệ thống
        dft = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String strTime = dft.format(cal.getTime());
        txtSetTime.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtSetTime.setTag(dft.format(cal.getTime()));

        edtJob.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish = cal.getTime();
        hourFinish = cal.getTime();
    }


    /**
     * hàm set ngày
     */
    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener myDate = new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear,
                                  int dayOfMonth) {

                txtSetDate.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/" + year);

                //Lưu  lại biến ngày hoàn thành
                cal.set(year, monthOfYear, dayOfMonth);
                dateFinish = cal.getTime();
            }
        };
        String s = txtSetDate.getText() + "";
        String strArrtmp[] = s.split("/");
        int ngay = Integer.parseInt(strArrtmp[0]);
        int thang = Integer.parseInt(strArrtmp[1]) - 1;
        int nam = Integer.parseInt(strArrtmp[2]);
        DatePickerDialog pic = new DatePickerDialog(MainActivity.this, myDate, nam, thang, ngay);
        pic.setTitle("Chọn ngày hoàn thành");
        pic.show();
    }

    /**
     * hàm set thời gian
     */
    public void showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener myTime = new OnTimeSetListener() {
            public void onTimeSet(TimePicker view,
                                  int hourOfDay, int minute) {
                //Xử lý lưu giờ và AM,PM
                String s = hourOfDay + ":" + minute;
                int hourTam = hourOfDay;
                if (hourTam > 12)
                    hourTam = hourTam - 12;
                txtSetTime.setText
                        (hourTam + ":" + minute + (hourOfDay > 12 ? " PM" : " AM"));
                //lưu giờ thực vào tag
                txtSetTime.setTag(s);
                //lưu vết lại giờ vào hourFinish
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                hourFinish = cal.getTime();
            }
        };
        String s = txtSetTime.getTag() + "";
        String strArr[] = s.split(":");
        int gio = Integer.parseInt(strArr[0]);
        int phut = Integer.parseInt(strArr[1]);
        TimePickerDialog time = new TimePickerDialog(
                MainActivity.this,
                myTime, gio, phut, true);
        time.setTitle("Chọn giờ hoàn thành");
        time.show();
    }

    /**
     * Hàm save công việc
     */
    public void processAddJob() {
        String title = edtJob.getText() + "";
        String description = edtContent.getText() + "";
        SetJob job = new SetJob(title, description, dateFinish, hourFinish);
        arrJob.add(job);
        adapter.notifyDataSetChanged();
        //sau khi cập nhật thì reset dữ liệu và cho focus tới edtJob
        edtJob.setText("");
        edtContent.setText("");
        edtJob.requestFocus();
    }

}
