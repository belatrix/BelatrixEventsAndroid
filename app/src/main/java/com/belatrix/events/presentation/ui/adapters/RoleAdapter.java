package com.belatrix.events.presentation.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Role;

public class RoleAdapter extends ArrayAdapter<Role> {

    private LayoutInflater inflater;

    public RoleAdapter(@NonNull Context context) {
        super(context, R.layout.item_role);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        RoleViewHolder holder;
        if (convertView == null || convertView.getTag() instanceof RoleViewHolder) {
            convertView = inflater.inflate(R.layout.item_role, parent, false);
            holder = new RoleViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (RoleViewHolder) convertView.getTag();
        }
        Role role = getItem(position);
        if (role != null) {
            holder.tvRole.setText(role.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ExpandedRoleViewHolder holder;
        if (convertView == null || convertView.getTag() instanceof ExpandedRoleViewHolder) {
            convertView = inflater.inflate(R.layout.item_role_expanded, parent, false);
            holder = new ExpandedRoleViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ExpandedRoleViewHolder) convertView.getTag();
        }
        Role role = getItem(position);
        if (role != null) {
            holder.tvRole.setText(role.getName());
        }
        return convertView;
    }

    static class RoleViewHolder {
        TextView tvRole;

        RoleViewHolder(View itemView) {
            this.tvRole = itemView.findViewById(R.id.tv_role_title);
        }
    }

    static class ExpandedRoleViewHolder {
        TextView tvRole;

        ExpandedRoleViewHolder(View itemView) {
            this.tvRole = itemView.findViewById(R.id.tv_role_title);
        }
    }
}
