package ca.wlu.khim1306_a3_2;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.snackbar.Snackbar;

import ca.wlu.khim1306_a3_2.databinding.ActivityTestToolbarBinding;

public class TestToolbar extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTestToolbarBinding binding;
    private String action_one_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTestToolbarBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        action_one_message = getString(R.string.action_one_message);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.mail_button_message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_one:
                Log.d("Toolbar","Option 1 selected");
                View rootView = this.getWindow().getDecorView().findViewById(android.R.id.content);
                Snackbar.make(rootView, action_one_message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case R.id.action_two:
                Log.d("Toolbar","Option 2 selected");
                AlertDialog.Builder action_two_builder = new AlertDialog.Builder(this);
                action_two_builder.setTitle(R.string.action_two_message);
                action_two_builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                action_two_builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog action_two_dialog = action_two_builder.create();
                action_two_dialog.show();
                break;
            case R.id.action_three:
                Log.d("Toolbar","Option 3 selected");
                AlertDialog.Builder action_three_builder = new AlertDialog.Builder(this);
                LayoutInflater action_three_inflater = this.getLayoutInflater();
                View content = action_three_inflater.inflate(R.layout.custom_dialog,null);
                action_three_builder.setView(content);
                EditText dialogText = (EditText) content.findViewById(R.id.dialog_snack_text);
                dialogText.setText(action_one_message);
                action_three_builder.setTitle(R.string.action_two_dialog_title);
                action_three_builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        action_one_message = dialogText.getText().toString();
                    }
                });
                action_three_builder.setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog action_three_dialog = action_three_builder.create();
                action_three_dialog.show();
                break;
            case R.id.about_menu:
                Log.d("Toolbar","About menu selected");
                Toast.makeText(getApplicationContext(), R.string.about_toast_text,Toast.LENGTH_LONG).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        
        return true;
    }

    //    @Override
  //  public boolean onSupportNavigateUp() {
    //    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_test_toolbar);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//               || super.onSupportNavigateUp();
//    }
}