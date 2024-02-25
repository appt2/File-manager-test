package com.example.test4;

import android.Manifest;
import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import android.os.Environment;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  RecyclerView rv;
  MyAdptor ad;
  String folders = "/sdcard/";
  List<HashMap<String, Object>> test = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Memoryaccess();
    rv = findViewById(R.id.rv_main);
    ad =
        new MyAdptor(
            test,
            new MyAdptor.OnClickItem() {

              @Override
              public void onItemClick(int pos) {
                File file = new File(test.get(pos).get("path").toString());
                if (file.isDirectory()) {
                  folders = file.getAbsolutePath();
                  listFilez();
                  rv.getAdapter().notifyDataSetChanged();
                }
              }
            });
    listFilez();
  }

  @Override
  @MainThread
  public void onBackPressed() {
    super.onBackPressed();
    if (folders.equals(Environment.getExternalStorageDirectory().getAbsolutePath())) {
      
    } else {
      folders = folders.substring(0, folders.lastIndexOf("/"));
      listFilez();
      // rv.getAdapter().notifyDataSetChanged();
    }
  }

  protected void listFilez() {
    List<String> files = new ArrayList<>();
    files.clear();
    listDir(folders, files);
    test.clear();
    for (int i = 0; i < files.size(); ++i) {
      HashMap<String, Object> map = new HashMap<>();
      map.put("path", files.get(i));
      test.add(map);
    }
    rv.setAdapter(ad);
    rv.setLayoutManager(new GridLayoutManager(this, 3));
  }

  private void Memoryaccess() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_DENIED) {
      ActivityCompat.requestPermissions(
          this,
          new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
          },
          1000);
    }
  }

  public void listDir(String path, List<String> list) {
    File dir = new File(path);
    if (!dir.exists() || dir.isFile()) return;

    File[] listFiles = dir.listFiles();
    if (listFiles == null || listFiles.length <= 0) return;

    if (list == null) return;
    list.clear();
    for (File file : listFiles) {
      list.add(file.getAbsolutePath());
    }
  }
}
