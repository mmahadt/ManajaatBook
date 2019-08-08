package com.bytexcite.manajaatulfaqeerbook;

import android.app.Activity;
//import android.database.Cursor;
//import android.net.Uri;
//import android.provider.OpenableColumns;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements OnPageChangeListener,OnLoadCompleteListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String SAMPLE_FILE = "duaBook.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pdfView= (PDFView)findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                // Current day is Sunday
                pageNumber = 55;
                break;
            case Calendar.MONDAY:
                pageNumber = 77;
                break;
            case Calendar.TUESDAY:
                pageNumber = 98;
                break;
            case Calendar.WEDNESDAY:
                pageNumber = 117;
                break;
            case Calendar.THURSDAY:
                pageNumber = 140;
                break;
                case Calendar.FRIDAY:
                pageNumber = 9;
                break;
            case Calendar.SATURDAY:
                pageNumber = 34;
                break;
        }

        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .enableSwipe(true)

                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

}
