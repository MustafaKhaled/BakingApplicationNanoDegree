package com.example.bakingapplicationnanodegree.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.bakingapplicationnanodegree.R;
import com.example.bakingapplicationnanodegree.interfaces.StepClickListener;
import com.example.bakingapplicationnanodegree.models.StepsItem;

public class RecipeStepViewHolder extends RecyclerView.ViewHolder {
    TextView stepHeader;
    TextView stepDetails;
    public RecipeStepViewHolder(@NonNull View itemView) {
        super(itemView);
        stepHeader = itemView.findViewById(R.id.step_header);
        stepDetails = itemView.findViewById(R.id.step_details);
    }

    public void bind(StepsItem stepsItem, final StepClickListener stepClickListener){
        stepHeader.setText(stepsItem.getShortDescription());
        stepDetails.setText(stepsItem.getDescription());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stepClickListener.StepClick(itemView,getAdapterPosition());
            }
        });
    }
}
