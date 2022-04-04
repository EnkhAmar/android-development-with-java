package mn.amra.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.TaskViewHolder> {

    private Context context;
    public Activity activity;
    private ArrayList <String> task_id, task_task, task_status, task_due_date;

    public final static String LOG_TAG = CustomAdapter.class.getSimpleName();
    public final static String EXTRA_TASK_ID = "mn.amra.todoapp.extra.TASK_ID";
    public final static String EXTRA_TASK_TASK = "mn.amra.todoapp.extra.TASK_TASK";
    public final static String EXTRA_TASK_STATUS = "mn.amra.todoapp.extra.TASK_STATUS";
    public final static String EXTRA_TASK_DUE_DATE = "mn.amra.todoapp.extra.TASK_DUE_DATE";
    public final static String NO_DUE_DATE = "No due date";
    public final static int REQUEST_CODE = 1;

    public CustomAdapter(Activity activity, Context context, ArrayList task_id, ArrayList task_task, ArrayList task_status, ArrayList task_due_date) {
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_task = task_task;
        this.task_status = task_status;
        this.task_due_date = task_due_date;
    }

    @NonNull
    @Override
    public CustomAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.TaskViewHolder holder, int position) {
        boolean completed = task_status.get(position).equals("1");
        Date date = null;
        String date_msg = NO_DUE_DATE;
        try {
            date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy").parse(String.valueOf(task_due_date.get(position)));
            date_msg = new SimpleDateFormat("E, dd MMM").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.task_id_txt.setText(String.valueOf(task_id.get(position)));
        holder.task_task_txt.setText(String.valueOf(task_task.get(position)));
        holder.task_status_txt.setText(handleTaskStatusValue(completed));
        if (date != null) {
            holder.task_due_date_txt.setText(date_msg);
            Date now = new Date();
            Log.d(LOG_TAG, now.toString() + " --- " +  date.toString());
            if (date.before(now)) {
                holder.task_due_date_txt.setTextColor(ContextCompat.getColor(context, R.color.warning));
            }
        } else {
            holder.task_due_date_txt.setText(NO_DUE_DATE);
            holder.task_due_date_txt.setTextColor(ContextCompat.getColor(context, R.color.gray));
        }
        if (completed) {
            holder.task_due_date_txt.setTextColor(ContextCompat.getColor(context, R.color.completed));
        }
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            int currentPosition = holder.getAdapterPosition();
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra(EXTRA_TASK_ID, String.valueOf(task_id.get(currentPosition)));
                intent.putExtra(EXTRA_TASK_TASK, String.valueOf(task_task.get(currentPosition)));
                intent.putExtra(EXTRA_TASK_STATUS, completed);
                intent.putExtra(EXTRA_TASK_DUE_DATE, String.valueOf(task_due_date.get(currentPosition)));
                activity.startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView task_id_txt, task_task_txt, task_status_txt, task_due_date_txt;
        LinearLayout mainLayout;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt = itemView.findViewById(R.id.task_id_text);
            task_task_txt = itemView.findViewById(R.id.task_task_text);
            task_status_txt = itemView.findViewById(R.id.task_status_text);
            task_due_date_txt = itemView.findViewById(R.id.task_due_date_text);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }

    private String handleTaskStatusValue (Boolean completed) {
        if (completed)
            return "Completed";
        else
            return "Not completed";
    }
}
