package littman.gil.appsheetexercise.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import littman.gil.appsheetexercise.DataClasses.UserDetails;
import littman.gil.appsheetexercise.R;

/**
 * Created by Gil on 09/01/2018.
 */

public class UsersAdapter extends ArrayAdapter<UserDetails> implements View.OnClickListener {
    private List<UserDetails> mUsers;

    public UsersAdapter(@NonNull Context context, int resource, List<UserDetails> users) {
        super(context, resource, users);
        mUsers = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(getContext());

            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_details_item, parent, false);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(this);
            viewHolder.nameTV = convertView.findViewById(R.id.user_details_name);
            viewHolder.ageTV = convertView.findViewById(R.id.user_details_age);
            viewHolder.phoneNumberTV = convertView.findViewById(R.id.user_details_phone_number);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final UserDetails user = mUsers.get(position);
        viewHolder.nameTV.setText(user.getName());
        viewHolder.ageTV.setText(String.format(getContext().getString(R.string.user_details_age), user.getAge()));
        viewHolder.phoneNumberTV.setText(user.getPhoneNumber());
        viewHolder.position = position;

        return convertView;
    }

    @Override
    public void onClick(View view) {
        UserDetails user = mUsers.get(((ViewHolder) view.getTag()).position);
        callNumber(user.getPhoneNumber());
    }

    private void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + number));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        }
    }

    class ViewHolder extends View {
        TextView nameTV;
        TextView ageTV;
        TextView phoneNumberTV;
        int position;

        public ViewHolder(Context context) {
            super(context);
        }
    }
}
