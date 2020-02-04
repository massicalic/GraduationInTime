package com.example.graduationintime;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ItActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editText;
    private LinearLayout linearIt;
    private ArrayList<String> it;
    private DatabaseReference mDatabase;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private String mUserId;
    private boolean isEdit = false;
    private int pos;

    private static final String itKEY = "it_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it);

        it = getIntent().getStringArrayListExtra(itKEY);
        builder = new AlertDialog.Builder(this);

        mUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        toolbar = findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        editText = findViewById(R.id.EditText);
        linearIt = findViewById(R.id.linearLayout_it);

        setView();
    }

    private void setView() {
        for (int i=0; i<it.size(); i++) {
            final int j = i;
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            linearLayout.setLayoutParams(params);
            TextView text = new TextView(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40, 0, 0, 30);
            text.setLayoutParams(params);
            final float scale = getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale + 0.5f);
            text.setMaxWidth(pixels);
            text.setText(it.get(i));
            linearLayout.addView(text);

            Button button = new Button(this);
            pixels = (int) (38 * scale + 0.5f);
            int pix = (int) (47 * scale + 0.5f);
            params = new LinearLayout.LayoutParams(pix, pixels);
            params.setMargins(5, 0, 0, 0);
            button.setLayoutParams(params);
            Drawable d = getResources().getDrawable(R.drawable.ic_delete_black);
            button.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            pixels = (int) (1 * scale + 0.5f);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    builder.setMessage(R.string.sure_delete);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            it.remove(j);
                            mDatabase.child("users").child(mUserId).child("curriculum").child("it").setValue(it);
                            linearIt.removeAllViews();
                            setView();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog = builder.create();
                    dialog.show();
                }
            });
            linearLayout.addView(button);
            Button buttonEdit = new Button(this);
            pixels = (int) (38 * scale + 0.5f);
            pix = (int) (47 * scale + 0.5f);
            params = new LinearLayout.LayoutParams(pix, pixels);
            params.setMargins(5, 0, 0, 0);
            buttonEdit.setLayoutParams(params);
            d = getResources().getDrawable(R.drawable.ic_edit_black);
            buttonEdit.setCompoundDrawablesWithIntrinsicBounds(d, null, null, null);
            pixels = (int) (1 * scale + 0.5f);
            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setText(it.get(j));
                    isEdit = true;
                    pos = j;
                }
            });
            linearLayout.addView(buttonEdit);
            linearIt.addView(linearLayout);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.save:
                if (!editText.getText().toString().equals("")) {
                    if (isEdit) {
                        it.set(pos, editText.getText().toString());
                        mDatabase.child("users").child(mUserId).child("curriculum").child("it").setValue(it);
                    }else {
                        it.add(editText.getText().toString());
                        mDatabase.child("users").child(mUserId).child("curriculum").child("it").setValue(it);
                    }
                }
                /*Intent resultIntent = new Intent();
                resultIntent.putExtra(goalKEY, strGoal);
                setResult(Activity.RESULT_OK, resultIntent);*/
                finish();
                break;
            case android.R.id.home:
                builder.setMessage(R.string.areYouSure);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        builder.setMessage(R.string.areYouSure);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
    }
}
