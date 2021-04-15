package com.lkjuhkmnop.textquest.questmanageactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lkjuhkmnop.textquest.R;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class CharPropertiesAdapter extends RecyclerView.Adapter<CharPropertiesAdapter.ViewHolder> {
    private QuestManageActivity questManageActivity;
    private LinkedHashMap<String, String> charProps;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView charPropertyName, charPropertyValueLabel;
        EditText charPropertyValue;
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

        public void setCharPropertyValueText(String text) {
            charPropertyValue.setText(text);
        }

        public boolean requestValueFocus() {
            return charPropertyValue.requestFocus();
        }

        public View getItemView() {
            return itemView;
        }
    }

    public CharPropertiesAdapter(QuestManageActivity questManageActivity, LinkedHashMap<String, String> charProps) {
        this.questManageActivity = questManageActivity;
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
//        if (position == charProps.size()) {
//            return;
//        }
//        Set character property name
        String charPropName = charProps.keySet().toArray()[position].toString();
        holder.setCharPropertyNameText(charPropName);
//        Set char. prop. value (empty string in new property)
        holder.setCharPropertyValueText(charProps.get(charPropName));

//        Save character property value when it's EditText loose focus
        EditText charPropValueET = holder.getItemView().findViewById(R.id.char_prop_value);
        charPropValueET.setOnFocusChangeListener((v, hasFocus) -> {
            if (position < charProps.size() && !hasFocus) {
                String changeCharPropName = charProps.keySet().toArray()[position].toString();
                charProps.replace(changeCharPropName, charPropValueET.getText().toString());
            }
        });

//        Delete char. prop. on corresponding button click
        ImageView charPropDelete = holder.getItemView().findViewById(R.id.char_prop_delete);
        charPropDelete.setOnClickListener(v -> {
            Context context = holder.getItemView().getContext();
            String deleteCharPropName = charProps.keySet().toArray()[position].toString();
            Toast.makeText(context, context.getText(R.string.char_prop_delete_msg) + " " + charProps.get(deleteCharPropName), Toast.LENGTH_SHORT).show();
            charProps.remove(deleteCharPropName);
            notifyDataSetChanged();
        });

        if (position == getItemCount()-1) {
            holder.requestValueFocus();
        }
    }

    @Override
    public int getItemCount() {
        return charProps == null ? 0 : charProps.size();
    }


}

