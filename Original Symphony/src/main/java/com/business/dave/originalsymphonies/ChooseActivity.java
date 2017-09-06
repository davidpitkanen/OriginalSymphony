package com.business.dave.originalsymphonies;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ChooseActivity extends ListActivity {

    private File previousDir;
    private File currentDir;
    private FileArrayAdapter adapter;
    static final String queryString = "FIND_STRING";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        //System.getenv(System.getenv("SDCard"));
        File f_exts = new File("./");
        // here find the root of the app

        while (f_exts.getParent() != null)
        {
           File f_2exts =  new File(f_exts.getParent());
            f_exts = f_2exts;
        }
        fill(f_exts);

    }

    public void upDirectory(View view)
    {
        String parentName = currentDir.getParent();
        if (parentName != null)
        {
            currentDir = new File(parentName);
            fill(currentDir);
        }
        else
            fill(new File("./"));

    }


 /*   @Override
    public void onBackPressed() {
        //Log.d("CDA", "onBackPressed Called");
        System.out.println("back button pressed");
    }
*/


    private void fill(File f)
    {
        // this is where the magic happens -- so pass in a directory
        // all of the
        File[] dirs = f.listFiles();
        this.setTitle("Current Dir:" + f.getName());
        List<Option> dir = new ArrayList<Option>();
        List<Option> fls = new ArrayList<Option>();

        // to test this just create a bunch of options

        // add them to the  lists and then try to display them


        try
        {
            // add the ability to click on the files or directory
            // put files in the right folder

            // actually I think only directories in the base folder are listed.

            // should have a button also that is up one level

            // create a list of files and a separate list of directories
            for(File ff: dirs)
            {

            // dir.add(new Option("Second From top", "Folder", "Not Top"));
            // there's really no way this should work.
                if (ff.isDirectory())// && ff.getAbsolutePath().contains("sdcard"))
                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
                else// if (ff.getAbsolutePath().contains("sdcard"))
                {
                    fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
                }
            }

        }
        catch(Exception e)
        {
            e.toString();
        }

        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"));
            dir.add(0, new Option("..", "Parent Directory", f.getParent()));

        adapter = new FileArrayAdapter(ChooseActivity.this,R.layout.file_view,dir);
        this.setListAdapter(adapter);
    }

    /*
    private void fill(File f)
    {
        File[] dirs = f.listFiles();
        this.setTitle("Current Dir:" + f.getName());
        List<Option> dir = new ArrayList<Option>();
        List<Option> fls = new ArrayList<Option>();

        // to test this just create a bunch of options

        // add them to the  lists and then try to display them
*/
       /* dir.add(new Option("At the top" ,"Folder","Top Path"));
        dir.add(new Option("Second From top", "Folder", "Not Top"));
        dir.add(new Option("Third from the top", "Folder" , "Bottom" ));

        fls.add(new Option("Best File","File Size", "Best Path"));

        fls.add(new Option("Second Best File", "File Size", "Second Best"));
        fls.add(new Option("File Lile", "File Size", "MEEbee"));
        */
/*
        try
        {
            // add the ability to click on the files or directory
            // put files in the right folder

            // actually I think only directories in the base folder are listed.

            // should have a button also that is up one level

            // create a list of files and a separate list of directories
            for(File ff: dirs)
            {

                // dir.add(new Option("Second From top", "Folder", "Not Top"));
                // there's really no way this should work.
                if (ff.isDirectory() && ff.getAbsolutePath().contains("sdcard") && 1 == 0)
                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
                else if (ff.getAbsolutePath().contains("sdcard") && ff.getAbsolutePath().contains("m4a"))
                {
                    fls.add(new Option(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
                }
            }

        }
        catch(Exception e)
        {
            e.toString();
        }

        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if(!f.getName().equalsIgnoreCase("sdcard"));
        dir.add(0, new Option("..", "Parent Directory", f.getParent()));

        adapter = new FileArrayAdapter(ChooseActivity.this,R.layout.file_view,dir);
        this.setListAdapter(adapter);
    }
*/
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory"))
        {
            previousDir = currentDir;
            currentDir = new File(o.getPath());
            fill(currentDir);
        }
        else
        {
                onFileClick(o);
        }
    }

    private void onFileClick(Option o)
    {
        String nullStr = null;

        this.getIntent().putExtra(queryString, o.getPath());//suggest a query and then a string. o.getPath(), nullStr);
        setResult(Activity.RESULT_OK, this.getIntent());
        finish();

        //File interestingFile = new File(o.getPath());
        //Uri newt = Uri.fromFile(interestingFile);
        //String nullStr = null;
        //this.getIntent().putExtra(o.getPath(),nullStr);//,EXTRA_TEXT);, o.getPath());//getData().parse("android.resource://"+o.getPath());//o.getPath());//"//o.getName());
       //Toast.makeText(this, "File Clicked: " + this.getIntent().getData().toString(), Toast.LENGTH_SHORT).show();
       // this.finish();
        // not quite done but send this information back to the
        // ahahaha so it works finally I have won.
        // have to use startActivityForResult
    }
}
