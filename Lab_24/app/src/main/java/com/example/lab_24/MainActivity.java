package com.example.lab_24;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView lvTygia;
    private TextView txtDate;
    private MyArrayAdapter myadapter;
    private ArrayList<Tygia> dstygia;

    // TODO: THAY THẾ CHUỖI NÀY BẰNG API KEY THẬT CỦA BẠN SAU KHI ĐĂNG KÝ exchangerate.host
    private static final String API_ACCESS_KEY = "YOUR_ACTUAL_API_KEY_HERE"; // <<< THAY THẾ CHỖ NÀY

    // URL của API tỉ giá mới với placeholder cho API Key
    private static final String EXCHANGE_RATE_BASE_URL = "https://api.exchangerate.host/latest";


    // Map để lưu ID của các icon cờ (bạn cần thêm các icon này vào thư mục drawable của bạn)
    private Map<String, Integer> currencyIcons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTygia = findViewById(R.id.lvTygia);
        txtDate = findViewById(R.id.txtDate);

        updateDateTextView();

        // Khởi tạo map icon cờ
        initCurrencyIcons();

        dstygia = new ArrayList<>();
        myadapter = new MyArrayAdapter(this, R.layout.layout_listview, dstygia);
        lvTygia.setAdapter(myadapter);

        // Xây dựng URL cuối cùng với API Key
        String finalApiUrl = EXCHANGE_RATE_BASE_URL + "?access_key=" + API_ACCESS_KEY + "&base=USD"; // Lấy tỉ giá so với USD

        new TygiaTask().execute(finalApiUrl); // Truyền URL đã có API Key
    }

    private void updateDateTextView() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDateTime = simpleDateFormat.format(calendar.getTime());
        txtDate.setText("Hôm Nay: " + currentDateTime);
    }

    // Khởi tạo các icon tiền tệ. Bạn cần thêm các file ảnh cờ vào thư mục res/drawable
    // Ví dụ: usd.png, eur.png, jpy.png, gbp.png, cad.png, vnd.png
    private void initCurrencyIcons() {
        currencyIcons = new HashMap<>();
        // Bạn cần tự thêm các icon cờ vào thư mục res/drawable
        // Ví dụ:
        currencyIcons.put("USD", R.drawable.usd); // Đảm bảo có usd.png trong drawable
        currencyIcons.put("EUR", R.drawable.eur); // Đảm bảo có eur.png trong drawable
        currencyIcons.put("JPY", R.drawable.jpy); // Đảm bảo có jpy.png trong drawable
        currencyIcons.put("GBP", R.drawable.gbp); // Đảm bảo có gbp.png trong drawable
        currencyIcons.put("CAD", R.drawable.cad); // Đảm bảo có cad.png trong drawable
        currencyIcons.put("VND", R.drawable.vnd); // Đảm bảo có vnd.png trong drawable
        // Thêm các loại tiền tệ khác nếu cần
    }


    private class TygiaTask extends AsyncTask<String, Void, ArrayList<Tygia>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dstygia.clear();
            myadapter.notifyDataSetChanged();
            Toast.makeText(MainActivity.this, "Đang tải tỉ giá...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected ArrayList<Tygia> doInBackground(String... urls) {
            ArrayList<Tygia> result = new ArrayList<>();
            String jsonString = "";
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(30000);
                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        builder.append(line);
                    }
                    jsonString = builder.toString();
                    Log.d(TAG, "Full Content from URL: " + jsonString);

                    if (jsonString.startsWith("{")) { // Kiểm tra xem có phải JSON object không
                        JSONObject jsonObject = new JSONObject(jsonString);

                        boolean success = jsonObject.optBoolean("success", false);
                        if (!success) {
                            String errorInfo = jsonObject.optJSONObject("error") != null ?
                                    jsonObject.optJSONObject("error").toString() : "Unknown error";
                            Log.e(TAG, "API call was not successful: " + errorInfo);
                            // Cập nhật UI trên Main Thread để hiển thị lỗi
                            runOnUiThread(() -> Toast.makeText(MainActivity.this, "Lỗi API: " + errorInfo, Toast.LENGTH_LONG).show());
                            return null;
                        }

                        JSONObject rates = jsonObject.getJSONObject("rates");

                        // Lấy tên tiền tệ gốc (base currency) - thường là USD trong trường hợp này
                        String baseCurrency = jsonObject.optString("base", "USD");

                        // Duyệt qua tất cả các tỉ giá trong đối tượng "rates"
                        Iterator<String> keys = rates.keys();
                        while(keys.hasNext()) {
                            String currencyCode = keys.next();
                            // Chỉ lấy các loại tiền tệ bạn muốn hiển thị
                            if (currencyCode.equals("VND") ||
                                    currencyCode.equals("EUR") ||
                                    currencyCode.equals("JPY") ||
                                    currencyCode.equals("GBP") ||
                                    currencyCode.equals("CAD") ||
                                    currencyCode.equals("USD")) { // Thêm USD vào danh sách nếu bạn muốn hiển thị nó

                                double rateValue = rates.optDouble(currencyCode, 0.0);
                                String rateString = String.format(Locale.US, "%.2f", rateValue); // Định dạng 2 chữ số thập phân

                                // Lấy icon cờ nếu có
                                Integer iconId = currencyIcons.get(currencyCode);
                                Bitmap bitmap = null;
                                if (iconId != null) {
                                    bitmap = BitmapFactory.decodeResource(getResources(), iconId);
                                } else {
                                    Log.w(TAG, "No icon found for currency: " + currencyCode);
                                }

                                // Tạo đối tượng Tygia
                                // imageurl không có, nên để trống.
                                // Các giá trị mua/bán sẽ được gán bằng giá trị tỉ giá lấy được.
                                result.add(new Tygia(currencyCode, "", bitmap, rateString, rateString, rateString, rateString));
                            }
                        }

                    } else {
                        Log.e(TAG, "Content is not valid JSON. Received non-JSON content.");
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Lỗi: Nội dung nhận được không phải JSON hợp lệ.", Toast.LENGTH_LONG).show());
                        return null;
                    }

                } else {
                    Log.e(TAG, "HTTP Error: " + responseCode + " for URL: " + urls[0]);
                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "Lỗi HTTP: " + responseCode + ". Không thể tải dữ liệu.", Toast.LENGTH_LONG).show());
                    return null;
                }

            } catch (IOException e) {
                Log.e(TAG, "Network or IO error: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Lỗi mạng hoặc IO. Vui lòng kiểm tra kết nối.", Toast.LENGTH_LONG).show());
                return null;
            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Lỗi phân tích dữ liệu tỉ giá. Định dạng JSON không khớp.", Toast.LENGTH_LONG).show());
                return null;
            } catch (Exception e) {
                Log.e(TAG, "Generic error in doInBackground: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Đã xảy ra lỗi không mong muốn.", Toast.LENGTH_LONG).show());
                return null;
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing reader: " + e.getMessage());
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<Tygia> result) {
            super.onPostExecute(result);
            if (result != null && !result.isEmpty()) {
                dstygia.addAll(result);
                myadapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Tải tỉ giá thành công!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Finished loading tygia. Total items: " + dstygia.size());
            } else {
                Toast.makeText(MainActivity.this, "Không thể tải tỉ giá hoặc không có dữ liệu hợp lệ. Vui lòng kiểm tra lại nguồn API và API Key.", Toast.LENGTH_LONG).show();
                Log.w(TAG, "No tygia items parsed or result is null/empty.");
            }
        }
    }
}