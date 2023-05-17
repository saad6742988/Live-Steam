package com.example.livestreaming.PdfEditor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.example.livestreaming.R;



public class PdfReaderAct extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);
        imageView = findViewById(R.id.imgView);
//        genrateAndSavePdf();
        laodAndSavePdf();
    }

    private void laodAndSavePdf() {
        File pdfFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/certificate.pdf");
        PdfDocument pdfDocument = new PdfDocument();
        Paint paint = new Paint();

// Step 2: Load the PDF using PdfRenderer
        PdfRenderer pdfRenderer = null;
        try {
            pdfRenderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));
        } catch (IOException e) {
            e.printStackTrace();
        }

// Step 3: Render the desired page from PdfRenderer to a Bitmap
        int pageIndex = 0; // Index of the page you want to render
        PdfRenderer.Page pdfRendererPage = pdfRenderer.openPage(pageIndex);

        // Create a bitmap to hold the rendered page
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);

        // Render the page to the bitmap
        pdfRendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        // Close the PdfRenderer.Page after rendering
        pdfRendererPage.close();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100, 100, 1).create();
        PdfDocument.Page pdfDocumentPage = pdfDocument.startPage(pageInfo);
        Canvas canvas = pdfDocumentPage.getCanvas();
        canvas.drawBitmap(bitmap, 0, 0, null);
//        Canvas canvas = page.getCanvas();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        canvas.drawText("I am Saad",100/2,100/2,paint);

        // Step 5: Finish the PdfDocument
        pdfDocument.finishPage(pdfDocumentPage);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/hello1.pdf");
        try {
            pdfDocument.writeTo(new FileOutputStream(file));
        }catch (IOException e)
        {
            Toast.makeText(this, "Exception", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        // Step 6: Save or do further operations with the PdfDocument
        // ...

        // Step 7: Close the PdfRenderer and PdfDocument
        pdfRenderer.close();
        pdfDocument.close();
    }

    private void genrateAndSavePdf() {
        PdfDocument pdf = new PdfDocument();
        Paint paint = new Paint();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(100,
                100,1).create();
        PdfDocument.Page page = pdf.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(15);
        canvas.drawText("I am groot",100/2,100/2,paint);

        pdf.finishPage(page);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"/hello0.pdf");
//        try {
//            FileOutputStream outputStream = new FileOutputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
        try {
            pdf.writeTo(new FileOutputStream(file));
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}