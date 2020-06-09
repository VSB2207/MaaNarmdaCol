package com.example.college;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class adapterForPdf extends ArrayAdapter<modelClassOfPdf>
{
    int resource;
    Context context;
    List<modelClassOfPdf> dataList;

    public adapterForPdf(@NonNull Context context, int resource, List<modelClassOfPdf> dataList) {
        super(context, resource, dataList);
        this.resource = resource;
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view =  layoutInflater.inflate(resource,null);

        final modelClassOfPdf pdf=dataList.get(position);
        TextView pdfTitle,read,description;
        pdfTitle=view.findViewById(R.id.pdfTitle);
        read=view.findViewById(R.id.TV_readPdf);
        description=view.findViewById(R.id.pdfDescription);

        description.setText(pdf.pdfDescription);
        pdfTitle.setText(pdf.getPdfname());

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,readPdf.class);
                intent.putExtra("url",pdf.getPdfUrl());
                context.startActivity(intent);

            }
        });
      return view;
    }
}
