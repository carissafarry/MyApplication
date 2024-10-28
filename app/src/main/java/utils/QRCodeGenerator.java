//package utils;
//
//import android.graphics.Bitmap;
//import android.widget.ImageView;
//import com.journeyapps.barcodescanner.BarcodeEncoder;
//
//// Method to generate and display QR Code
//public void generateQRCode(String data, ImageView imageView) {
//    try {
//        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
//        Bitmap bitmap = barcodeEncoder.encodeBitmap(data, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400);
//        imageView.setImageBitmap(bitmap);
//    } catch(Exception e) {
//        e.printStackTrace();
//    }