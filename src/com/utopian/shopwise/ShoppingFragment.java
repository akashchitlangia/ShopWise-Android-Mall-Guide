package com.utopian.shopwise;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.utopian.tools.DbBean;
import com.utopian.tools.DbHelper;

public class ShoppingFragment extends Fragment {
	
	protected DbHelper db;
	CheckBox cb;
	List<DbBean> list;
	MyAdapter adapt;

	View rootView ;
	
public ShoppingFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.activity_view_task, container, false);
        
        db = new DbHelper(getActivity());
		list = db.getAllTasks();
		adapt = new MyAdapter(getActivity(), R.layout.list_inner_view, list);
		ListView listTask = (ListView) rootView.findViewById(R.id.listView1);
		listTask.setAdapter(adapt);
		

		
		
		ImageButton add= (ImageButton)rootView.findViewById(R.id.button1);
		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub..
				
				
				EditText t = (EditText) rootView.findViewById(R.id.editText1);
				String s = t.getText().toString();
				if (s.equalsIgnoreCase("")) {
					Toast.makeText(getActivity(), "enter the task description first!!",
							Toast.LENGTH_LONG);
				} else {
					DbBean task = new DbBean(s, 0);
					db.addTask(task);
					Log.d("tasker", "data added");
					t.setText("");
					adapt.add(task);
					adapt.notifyDataSetChanged();
				}
				
			}
		});
		
		ActionBar actionbar= ((ActionBarActivity)getActivity()).getSupportActionBar();
		actionbar.setDisplayHomeAsUpEnabled(true);
         
   /*     Intent intent = new Intent(getActivity(),ViewShoppingList.class);
		startActivity(intent); */
        return rootView;
    }
	/*
	public void addTaskNow(View v) {
		EditText t = (EditText) v.findViewById(R.id.editText1);
		String s = t.getText().toString();
		if (s.equalsIgnoreCase("")) {
			Toast.makeText(getActivity(), "enter the task description first!!",
					Toast.LENGTH_LONG);
		} else {
			DbBean task = new DbBean(s, 0);
			db.addTask(task);
			Log.d("tasker", "data added");
			t.setText("");
			adapt.add(task);
			adapt.notifyDataSetChanged();
		}

	}

	*/

	private class MyAdapter extends ArrayAdapter<DbBean> {

		Context context;
		List<DbBean> taskList = new ArrayList<DbBean>();
		int layoutResourceId;

		public MyAdapter(Context context, int layoutResourceId,
				List<DbBean> objects) {
			super(context, layoutResourceId, objects);
			this.layoutResourceId = layoutResourceId;
			this.taskList = objects;
			this.context = context;
		}

		/**
		 * This method will DEFINe what the view inside the list view will
		 * finally look like Here we are going to code that the checkbox state
		 * is the status of task and check box text is the task name
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			CheckBox chk = null;
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_inner_view,
						parent, false);
				chk = (CheckBox) convertView.findViewById(R.id.chkStatus);
				convertView.setTag(chk);

				chk.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						cb = (CheckBox) v;
						DbBean changeTask = (DbBean) cb.getTag();
						changeTask.setStatus(cb.isChecked() == true ? 1 : 0);
						db.updateTask(changeTask);
						if (cb.isChecked()) {
							 cb.setPaintFlags(cb.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
		                                                    
		                } else  {
		                	cb.setPaintFlags(cb.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));  
		                }
					}

				});
			} else {
				chk = (CheckBox) convertView.getTag();
			}
			DbBean current = taskList.get(position);
			chk.setText(current.getTaskName());
			chk.setChecked(current.getStatus() == 1 ? true : false);
			chk.setTag(current);
			Log.d("listener", String.valueOf(current.getId()));
			return convertView;
		}

	}

	

}