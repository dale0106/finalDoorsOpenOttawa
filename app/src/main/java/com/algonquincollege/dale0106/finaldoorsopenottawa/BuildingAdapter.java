package com.algonquincollege.dale0106.finaldoorsopenottawa;

/**
 * Created by crisdalessio on 2016-11-07.
 */

import java.util.List;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.algonquincollege.dale0106.finaldoorsopenottawa.model.Building;

import org.w3c.dom.Text;

public class BuildingAdapter extends ArrayAdapter<Building> {

    private Context context;
    private List<Building> buildingList;

    private LruCache<Integer, Bitmap> imageCache;

    public BuildingAdapter(Context context, int resource, List<Building> objects) {
        super(context, resource, objects);
        this.context = context;
        this.buildingList = objects;

        // TODO: instantiate the imageCache
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_building, parent, false);


        //Getting current building obj to access all of its info
        Building building = buildingList.get(position);

        //TODO: Display the name of the building
        //Creating a textView which will hold the building NAME
        TextView tv = (TextView) view.findViewById(R.id.textView1);
        //Inserting the text inside the building.getName()
        tv.setText(building.getName());


        //TODO: Display the address of the building
        //Creating a second textView which will hold the building ADDRESS
        TextView tv2 = (TextView) view.findViewById(R.id.textView2);
        //Inserting the text inside the building.getAddress()
        tv2.setText(building.getAddress());


        // TODO: Display building photo in ImageView widget
        //Get the image id
        Bitmap bitmap = imageCache.get(building.getBuildingId());
        if (bitmap != null) {
            //if bitmap is not  empty, create an ImageView - inserting the imageView property
            Log.i("BUILDINGS", building.getName() + "\tbitmap in cache");
            ImageView image = (ImageView) view.findViewById(R.id.imageView1);
            //Insert the image inside building.getBitmap();
            image.setImageBitmap(building.getBitmap());
        } else {
            Log.i("BUILDINGS", building.getName() + "\tfetching bitmap using AsyncTask");
            BuildingAndView container = new BuildingAndView();
            container.building = building;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return view;
    }

    // container for AsyncTask params
    private class BuildingAndView {
        public Building building;
        public View view;
        public Bitmap bitmap;

    }


    private class ImageLoader extends AsyncTask<BuildingAndView, Void, BuildingAndView> {

        @Override
        protected BuildingAndView doInBackground(BuildingAndView... params) {

            BuildingAndView container = params[0];
            Building building = container.building;

            try {
                String imageUrl = MainActivity.IMAGES_BASE_URL + building.getImage();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                building.setBitmap(bitmap);
                in.close();
                container.bitmap = bitmap;
                return container;
            } catch (Exception e) {
                System.err.println("IMAGE: " + building.getName());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(BuildingAndView result) {
            ImageView image = (ImageView) result.view.findViewById(R.id.imageView1);
            image.setImageBitmap(result.bitmap);
//            result.planet.setBitmap(result.bitmap);
            imageCache.put(result.building.getBuildingId(), result.bitmap);
        }
    }

}
