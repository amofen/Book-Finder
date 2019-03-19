package com.example.bookfinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookfinder.data.Book;
import com.example.bookfinder.data.BookInfoRetrieveAsyncTask;
import com.example.bookfinder.data.DataRetrievedListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.journeyapps.barcodescanner.DefaultDecoderFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CodeScanActivity extends AppCompatActivity implements DataRetrievedListener {
    private DecoratedBarcodeView barcodeView;
    private TextView txtISBN;
    private TextView lblIsbn;
    private ProgressBar progressBar;
    private Button btnFind;
    private static final int CAMERA_PER_CODE_REQUEST=100;
    private String codeValue;//The value of the ISBN to be used to fetch book infos
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_scan);
        codeValue="";
        fetchViews();
        askForCameraPermission();
        initBarcodeView();
    }

    private void initBarcodeView() {
        Collection<BarcodeFormat> formats = Arrays.asList(BarcodeFormat.QR_CODE, BarcodeFormat.CODE_39);
        this.barcodeView.getBarcodeView().setDecoderFactory(new DefaultDecoderFactory(formats));
        this.barcodeView.initializeFromIntent(getIntent());
        this.barcodeView.decodeContinuous(getBarcodeCallBack());
    }

    private void fetchViews() {
        this.lblIsbn = findViewById(R.id.lbl_isbn);
        this.lblIsbn.setVisibility(View.INVISIBLE);
        this.txtISBN = findViewById(R.id.txt_code);
        this.txtISBN.setVisibility(View.INVISIBLE);
        this.barcodeView =findViewById(R.id.barcode_view);
        this.progressBar = findViewById(R.id.scanProgressBar);
        this.progressBar.setVisibility(View.GONE);
        this.btnFind = findViewById(R.id.btn_find);
    }

    private void askForCameraPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PER_CODE_REQUEST);
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.barcodeView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.barcodeView.resume();
    }

    private BarcodeCallback getBarcodeCallBack(){
        return new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if(result.getText() == null || result.getText().equals(codeValue) ) {
                    return;
                }
                barcodeView.setStatusText(getString(R.string.msg_got_isbn));
                txtISBN.setText(result.getText());
                txtISBN.setVisibility(View.VISIBLE);
                codeValue = result.getText();
                lblIsbn.setVisibility(View.VISIBLE);
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {
            }
        };
    }

    public void find(View view){
        if(codeValue.isEmpty()){
            Toast.makeText(this,getString(R.string.msg_scan_first),Toast.LENGTH_LONG).show();
        }else{
            this.progressBar.setVisibility(View.VISIBLE);
            this.btnFind.setVisibility(View.INVISIBLE);
            BookInfoRetrieveAsyncTask task = new BookInfoRetrieveAsyncTask(this);
            task.execute(codeValue);

        }


    }

    @Override
    public void onCompletedTask(Book book) {
        if(book!=null){
            Intent intent = new Intent(this,BookDetailsActivity.class);
            intent.putExtra("book",book);
            this.btnFind.setVisibility(View.VISIBLE);
            this.progressBar.setVisibility(View.GONE);
            this.codeValue="";
            barcodeView.setStatusText("");
            txtISBN.setText("");
            startActivity(intent);
        }
        else{
            Toast.makeText(this,"No Book Found !!!",Toast.LENGTH_SHORT);
        }

    }
}
