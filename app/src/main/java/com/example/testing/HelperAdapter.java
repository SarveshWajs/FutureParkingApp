package com.example.testing;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.CustomViewHolder> {

    List<FetchVehicleData> fetchVehicleDataList;

    public HelperAdapter(List<FetchVehicleData> fetchVehicleDataList) {
        this.fetchVehicleDataList = fetchVehicleDataList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_layout_activity,parent,false);


        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperAdapter.CustomViewHolder holder, int position) {


        FetchVehicleData fetchData = fetchVehicleDataList.get(position);
        holder.number.setText(fetchData.getVehicleNumber());
        holder.model.setText(fetchData.getModel());
        holder.description.setText(fetchData.getDescription());
        holder.compound.setText(fetchData.getCompound());
        holder.price.setText(fetchData.getPrice());


    }

    @Override
    public int getItemCount() {
        return fetchVehicleDataList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView number,model,description,compound,price;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.vehicleNum);
            model = itemView.findViewById(R.id.vehicleModel);
            description = itemView.findViewById(R.id.vehicleDes);
            compound = itemView.findViewById(R.id.dum);
            price = itemView.findViewById(R.id.dum);
        }
    }
}

