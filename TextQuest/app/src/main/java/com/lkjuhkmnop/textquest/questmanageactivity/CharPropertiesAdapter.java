package com.lkjuhkmnop.textquest.questmanageactivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lkjuhkmnop.textquest.R;

import java.util.HashMap;
import java.util.LinkedList;

public class CharPropertiesAdapter extends RecyclerView.Adapter<CharPropertiesAdapter.ViewHolder> {
    private LinkedList<String[]> charProps;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView charPropertyValueLabel;
        EditText charPropertyName, charPropertyValue;
        ImageView charPropDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            charPropertyName = itemView.findViewById(R.id.char_prop_name);
            charPropertyValueLabel = itemView.findViewById(R.id.char_prop_value_label);
            charPropertyValue = itemView.findViewById(R.id.char_prop_value);
            charPropDelete = itemView.findViewById(R.id.char_prop_delete);
        }

        public void setCharPropertyNameText(String text) {
            charPropertyName.setText(text);
        }

        public View getItemView() {
            return itemView;
        }
    }

    public CharPropertiesAdapter(LinkedList<String[]> charProps) {
        this.charProps = charProps;
    }

    @NonNull
    @Override
    public CharPropertiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_character_property_item, parent, false);
        return new CharPropertiesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharPropertiesAdapter.ViewHolder holder, int position) {
//        Set character property name
        holder.setCharPropertyNameText(charProps.get(position)[0]);
//        Save character property value when it's EditText loose focus
        EditText charPropValueET = holder.getItemView().findViewById(R.id.char_prop_value);
        charPropValueET.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                charProps.get(position)[1] = charPropValueET.getText().toString();
            }
        });
    }

    @Override
    public int getItemCount() {
        return charProps == null ? 0 : charProps.size();
    }
}

