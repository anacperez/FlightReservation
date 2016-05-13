package com.example.anarts6.airplaneticketreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anarts6 on 5/12/16.
 */
public class CustomFlightsAdapter extends ArrayAdapter<Flight> {

    public CustomFlightsAdapter(Context context, ArrayList<Flight> flights) {
        super(context,0, flights);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Flight flight = getItem(position);

        if(convertView== null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.reserve_seat, parent, false);
        }
        TextView flightNo = (TextView) convertView.findViewById(R.id.flightNo);
        TextView departureID = (TextView) convertView.findViewById(R.id.departureID);
        TextView arrivalID = (TextView) convertView.findViewById(R.id.arrivalID);
        TextView departureTimeID = (TextView) convertView.findViewById(R.id.departureTimeID);
        TextView priceID = (TextView) convertView.findViewById(R.id.priceID);
        TextView capacityID = (TextView) convertView.findViewById(R.id.capacityID);


        flightNo.setText(flight.getFlightNumber());
        departureID.setText(flight.getDeparture());
        arrivalID.setText(flight.getArrival());
        departureTimeID.setText(flight.getDepartureTime());
        priceID.setText(String.valueOf(flight.getPrice()));
        capacityID.setText(String.valueOf(flight.getCapacity()));

        return convertView;
    }
}
