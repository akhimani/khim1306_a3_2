package ca.wlu.khim1306_a3_2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import ca.wlu.khim1306_a3_2.db.ChatDatabaseHelper;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";
    ListView chatView;
    EditText chatText;
    Button sendButton;
    ArrayList<String> chatMessages;
    ChatAdapter messageAdapter;
    ChatDatabaseHelper chatHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(ACTIVITY_NAME, "OnCreate Called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatView = findViewById(R.id.ChatView);
        chatText = findViewById(R.id.editChatText);
        sendButton = findViewById(R.id.sendButton);
        chatMessages = new ArrayList<String>();
        chatHelper = new ChatDatabaseHelper(getApplicationContext());

        db = chatHelper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME, new String[] {});
        c.moveToFirst();
        while(!c.isAfterLast()) {
            String chatMessage = c.getString(c.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            Log.i(ACTIVITY_NAME, "SQL MESSAGE: " + chatMessage);
            chatMessages.add(chatMessage);
            c.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor’s column count = " + c.getColumnCount());
        for(int columnIndex = 0; columnIndex < c.getColumnCount(); columnIndex++){
            Log.i(ACTIVITY_NAME, "Cursor’s column name = " + c.getColumnName(columnIndex) );
        }
        c.close();

        messageAdapter = new ChatAdapter( this );
        chatView.setAdapter (messageAdapter);
    }

    @Override
    protected void onDestroy() {
        Log.i(ACTIVITY_NAME, "OnDestroy Called");
        super.onDestroy();
        db.close(); // a call to close database
    }

    public void sendButton_onClick(View view) {
        Log.i(ACTIVITY_NAME, "User clicked Chat Send button");
        String chatMessage = chatText.getText().toString();
        chatMessages.add(chatMessage);

        ContentValues values = new ContentValues();
        values.put(ChatDatabaseHelper.KEY_MESSAGE, chatMessage);

        long insertId = db.insert(ChatDatabaseHelper.TABLE_NAME, null,
                values);

        messageAdapter.notifyDataSetChanged();
        chatText.setText("");
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return chatMessages.size();
        }

        public String getItem(int position) {
            return chatMessages.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            else
                result = inflater.inflate(R.layout.chat_row_incoming, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(   getItem(position)  ); // get the string at position

            return result;
        }
    }

}