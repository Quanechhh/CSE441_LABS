package com.example.lab_23;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner; // Import cho Scanner để đọc InputStream

public class XMLParser {

    private static final String TAG = "XMLParser"; // Tag cho Logcat

    /**
     * Tải nội dung XML từ một URL.
     * Sử dụng HttpURLConnection để tải dữ liệu.
     * @param urlString URL của RSS feed.
     * @return Chuỗi XML hoặc null nếu có lỗi.
     */
    public String getXmlFromUrl(String urlString) {
        String xml = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000); // 10 giây timeout kết nối
            connection.setReadTimeout(30000);    // 15 giây timeout đọc dữ liệu
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                // Đọc toàn bộ InputStream thành một chuỗi
                Scanner s = new Scanner(inputStream).useDelimiter("\\A");
                xml = s.hasNext() ? s.next() : "";
                Log.d(TAG, "XML Loaded successfully. Length: " + xml.length());
                Log.d(TAG, "FULL XML CONTENT:\n" + xml); // <--- DÒNG MỚI ĐƯỢC THÊM VÀO LẦN NÀY ĐỂ DEBUG
            } else {
                Log.e(TAG, "HTTP Error: " + responseCode + " for URL: " + urlString);
            }
        } catch (IOException e) {
            Log.e(TAG, "IOException in getXmlFromUrl: " + e.getMessage(), e);
            xml = null; // Đặt về null nếu có lỗi IO
        } catch (Exception e) { // Bắt các lỗi khác
            Log.e(TAG, "Generic Error in getXmlFromUrl: " + e.getMessage(), e);
            xml = null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing InputStream: " + e.getMessage(), e);
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return xml;
    }

    /**
     * Phân tích chuỗi XML để trích xuất các item tin tức.
     * @param xml Chuỗi XML cần phân tích.
     * @return ArrayList chứa các đối tượng List đã được phân tích.
     */
    public ArrayList<List> parseXml(String xml) {
        ArrayList<List> mylist = new ArrayList<>();
        if (xml == null || xml.isEmpty()) {
            Log.e(TAG, "Cannot parse null or empty XML string.");
            return mylist; // Trả về danh sách rỗng nếu XML không hợp lệ
        }

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xml));

            int eventType = parser.getEventType();
            String nodeName = null;
            String title = null;
            String link = null;
            String description = null;
            Bitmap imageBitmap = null;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(TAG, "Start XML Document Parsing");
                        break;
                    case XmlPullParser.START_TAG:
                        nodeName = parser.getName();
                        if (nodeName.equals("item")) {
                            // Reset các biến cho mỗi item mới
                            title = null;
                            link = null;
                            description = null;
                            imageBitmap = null;
                        } else if (nodeName.equals("title")) {
                            title = parser.nextText();
                        } else if (nodeName.equals("link")) {
                            link = parser.nextText();
                        } else if (nodeName.equals("description")) {
                            String descriptionRaw = parser.nextText(); // Đọc nội dung thô của description
                            Log.d(TAG, "Raw Description content from XmlPullParser: " + descriptionRaw); // Log nội dung thô

                            // *** ĐIỂM SỬA CHỮA CHÍNH: UNESCAPE HTML ENTITIES TRƯỚC KHI TRUYỀN CHO JSOUP ***
                            // Jsoup.parse() sẽ tự động unescape các ký tự như &lt;, &gt;, &amp;
                            Document tempDoc = Jsoup.parse(descriptionRaw);
                            String descriptionHtmlCleaned = tempDoc.html(); // Lấy lại HTML đã được unescape

                            Log.d(TAG, "Unescaped HTML Description content for Jsoup: " + descriptionHtmlCleaned); // Log nội dung đã unescape

                            // Lưu nội dung description thuần túy cho đối tượng List
                            // Dùng Jsoup.parse(descriptionHtmlCleaned).text() để loại bỏ các thẻ HTML và lấy nội dung text thuần túy
                            description = Jsoup.parse(descriptionHtmlCleaned).text();

                            // TRUYỀN CHUỖI HTML ĐÃ UNESCAPE VÀO extractImageUrl
                            String imgUrl = extractImageUrl(descriptionHtmlCleaned);

                            if (imgUrl != null && !imgUrl.isEmpty()) {
                                Log.d(TAG, "Attempting to load image from: " + imgUrl);
                                try {
                                    URL newurl = new URL(imgUrl);
                                    HttpURLConnection connection = (HttpURLConnection) newurl.openConnection();
                                    connection.setDoInput(true);
                                    connection.connect();
                                    InputStream input = connection.getInputStream();
                                    imageBitmap = BitmapFactory.decodeStream(input);
                                    if (imageBitmap == null) {
                                        Log.w(TAG, "BitmapFactory.decodeStream returned null for: " + imgUrl);
                                    }
                                    if (input != null) input.close(); // Đóng InputStream sau khi dùng
                                    if (connection != null) connection.disconnect();
                                } catch (IOException e) {
                                    Log.e(TAG, "Error loading image from " + imgUrl + ": " + e.getMessage(), e);
                                    imageBitmap = null; // Đảm bảo là null nếu có lỗi
                                }
                            } else {
                                Log.d(TAG, "No valid image URL found in description.");
                                imageBitmap = null; // Đảm bảo là null nếu không có URL ảnh
                            }
                        }
                        // Bạn có thể thêm các thẻ khác như "pubDate", "guid" nếu muốn lấy thêm thông tin
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            // Chỉ thêm vào danh sách nếu có đủ dữ liệu cần thiết (title, link, description)
                            // Nếu bạn muốn yêu cầu phải có ảnh, thêm && imageBitmap != null vào điều kiện
                            if (title != null && link != null && description != null) {
                                mylist.add(new List(imageBitmap, title, description, link));
                                Log.d(TAG, "Added item: " + title + " (Image: " + (imageBitmap != null ? "Present" : "Null") + ")");
                            } else {
                                Log.w(TAG, "Skipped item due to missing essential data. Title=" + title + ", Link=" + link + ", Description=" + description);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
            Log.d(TAG, "Parsed " + mylist.size() + " items from XML.");
        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML: " + e.getMessage(), e);
            mylist.clear(); // Xóa bất kỳ item nào đã thêm trước khi lỗi xảy ra
        }
        return mylist;
    }

    /**
     * Hàm helper để trích xuất URL ảnh từ chuỗi HTML của description.
     * Sử dụng thư viện Jsoup để phân tích HTML.
     * @param html Chuỗi HTML từ thẻ <description>.
     * @return URL của ảnh hoặc null nếu không tìm thấy.
     */
    private String extractImageUrl(String html) {
        if (html == null || html.isEmpty()) {
            Log.d(TAG, "HTML description is null or empty."); // Log thêm để rõ ràng
            return null;
        }
        Log.d(TAG, "HTML Description content received by extractImageUrl: " + html); // Dòng này giờ sẽ in ra HTML đã unescape
        try {
            Document doc = Jsoup.parse(html);
            Element img = doc.select("img").first(); // Tìm thẻ <img> đầu tiên
            if (img != null) {
                String imageUrl = img.absUrl("src"); // Lấy URL tuyệt đối của thuộc tính 'src'
                if (!imageUrl.isEmpty()) {
                    Log.d(TAG, "Found image URL: " + imageUrl); // Log URL tìm thấy
                    return imageUrl;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error extracting image URL from HTML: " + e.getMessage(), e);
        }
        Log.d(TAG, "No image element or valid URL found in HTML after Jsoup parsing."); // Sửa lại log này cho rõ nghĩa hơn
        return null; // Trả về null nếu không tìm thấy hoặc có lỗi
    }
}