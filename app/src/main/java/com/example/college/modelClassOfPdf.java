package com.example.college;

public class modelClassOfPdf
{
    String pdfname,pdfUrl,pdfDescription;

    public modelClassOfPdf(String pdfname, String pdfUrl,String pdfDescription)
    {
        this.pdfDescription=pdfDescription;
        this.pdfname = pdfname;
        this.pdfUrl = pdfUrl;
    }

    public String getPdfname() {
        return pdfname;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public String getPdfDescription() {
        return pdfDescription;
    }
}
