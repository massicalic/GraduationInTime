package com.example.graduationintime;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.os.SystemClock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class RunnableCreatePdf implements Runnable{

    private Document document;
    private String file;
    private AppCompatActivity activity;
    private boolean check1, check2, check3, check4, check5, check6, check7, check8, check9;
    private User user;
    private Image imagePdf;
    private File myFile;
    private String pdfname;
    private static final int REQUEST_CODE = 555;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static Font small = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    public RunnableCreatePdf(AppCompatActivity activity, boolean check1, boolean check2, boolean check3, boolean check4,
                             boolean check5, boolean check6, boolean check7, boolean check8, boolean check9, User user, Image imagePdf) {
        this.activity = activity;
        this.check1 = check1;
        this.check2 = check2;
        this.check3 = check3;
        this.check4 = check4;
        this.check5 = check5;
        this.check6 = check6;
        this.check7 = check7;
        this.check8 = check8;
        this.check9 = check9;
        this.user = user;
        this.imagePdf = imagePdf;
    }

    @Override
    public void run() {
        try {
            createPdf(check1, check2, check3, check4, check5, check6, check7, check8, check9);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public void createPdf(boolean check1, boolean check2, boolean check3, boolean check4, boolean check5, boolean check6,
                          boolean check7, boolean check8, boolean check9) throws FileNotFoundException, DocumentException {

        document = new Document();
        File pdfFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!pdfFolder.exists()) {
            pdfFolder.mkdir();
        }

        //Create time stamp
        Date date = new Date() ;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);

        pdfname = "CV" + timeStamp + ".pdf";

        myFile = new File(pdfFolder.getAbsolutePath(), pdfname);
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            scheduleNotificationProgress(getNotificationProgress(1500), 1500);
            //File write logic here
            PdfWriter.getInstance(document, new FileOutputStream(myFile));
            document.open();

            Paragraph title = new Paragraph();
            title.add(new Paragraph(user.getName().toUpperCase()+" "+user.getSurname().toUpperCase(), catFont));
            title.add(new Paragraph("CURRICULUM VITAE", catFont));
            addEmptyLine(title, 2);
            document.add(title);

            Paragraph personal = new Paragraph();
            personal.add(new Paragraph(activity.getResources().getString(R.string.personal_info), subFont));
            addEmptyLine(personal, 1);
            String s = activity.getResources().getString(R.string.born_on)+": ";
            Paragraph p = new Paragraph(s, small);
            s = (user.getDay()<10 ? "0"+user.getDay()+"/" : user.getDay()+"/") +
                    ((user.getMonth()+1)<10?"0"+(user.getMonth()+1)+"/" : (user.getMonth()+1)+"/") + (user.getYear())+"   ";
            p.add(new Chunk(s, smallBold));
            Calendar c = Calendar.getInstance();
            Calendar dateCalendar = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());
            long diffInMilli = c.getTimeInMillis() - dateCalendar.getTimeInMillis();
            diffInMilli = diffInMilli/1000/60/60/24/365;
            s = activity.getResources().getString(R.string.age)+": ";
            p.add(new Chunk(s, small));
            s = String.valueOf(diffInMilli);
            p.add(new Chunk(s, smallBold));
            personal.add(p);

            if (check1) {
                if (user.getCurriculum().getPlace() != null) {
                    s = activity.getResources().getString(R.string.birth_place)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getPlace();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCitizenship() != null) {
                    s = activity.getResources().getString(R.string.citizenship)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCitizenship();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCf() != null) {
                    s = activity.getResources().getString(R.string.cf)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCf();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getAddress() != null) {
                    s = activity.getResources().getString(R.string.address)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getAddress();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getLicens() != null) {
                    s = activity.getResources().getString(R.string.driving_license)+": ";
                }
                if (user.getCurriculum().isWithCar() != null) {
                    if (user.getCurriculum().isWithCar()) {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        s = s+"   "+activity.getResources().getString(R.string.with_car);
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    } else {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        s = s+"   "+activity.getResources().getString(R.string.without_car);
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    }
                    personal.add(p);
                } else {
                    if (user.getCurriculum().getLicens() != null) {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    }
                }
                if (user.getCurriculum().getEmail() != null) {
                    s = activity.getResources().getString(R.string.email)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getEmail();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCellphone() != null) {
                    s = activity.getResources().getString(R.string.cell)+": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCellphone();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getLinks()!=null) {
                    s = activity.getResources().getString(R.string.links)+":  ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getLinks();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                addEmptyLine(personal, 1);
                document.add(personal);
            }else {
                addEmptyLine(personal, 1);
                document.add(personal);
            }
            if (check2) {
                imagePdf.scaleToFit(120f, 120f);
                imagePdf.setAbsolutePosition(400f, 680f);
                document.add(imagePdf);
            }
            if (check3) {
                if (user.getCurriculum().getGoal()!=null) {
                    Paragraph goal = new Paragraph();
                    goal.add(new Paragraph(activity.getResources().getString(R.string.professional_goal), subFont));
                    addEmptyLine(personal, 1);
                    goal.add(new Paragraph(user.getCurriculum().getGoal(), small));
                    addEmptyLine(goal, 1);
                    document.add(goal);
                }
            }
            if (check4) {
                if (user.getCurriculum().getSkills().size()!=0) {
                    ArrayList<Integer> sk = user.getCurriculum().getSkills();
                    Paragraph skill = new Paragraph();
                    skill.add(new Paragraph(activity.getResources().getString(R.string.soft_skill), subFont));
                    addEmptyLine(skill, 1);
                    String str = activity.getResources().getString(R.string.autonomy)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(0)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.self_confidence)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(1)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.stress)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(2)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.organize)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(3)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.accuracy)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(4)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.learn)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(5)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.goals)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(6)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.manage_info)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(7)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.initiative)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(8)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.comunication_skills)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(9)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.problem_solving)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(10)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.team_work)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(11)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.leadership)+" ";
                    p = new Paragraph(str, small);
                    str = sk.get(12)+"/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    addEmptyLine(skill, 1);
                    document.add(skill);
                }
            }
            if (check5) {
                if (user.getCurriculum().getExperiences().size()!=0) {
                    ArrayList<String> exp = user.getCurriculum().getExperiences();
                    Paragraph experiences = new Paragraph();
                    experiences.add(new Paragraph(activity.getResources().getString(R.string.experiences), subFont));
                    addEmptyLine(experiences, 1);
                    for (int i=0; i<exp.size(); i++) {
                        experiences.add(new Paragraph(exp.get(i), small));
                        addEmptyLine(experiences, 1);
                    }
                    document.add(experiences);
                }
            }
            if (check6) {
                if (user.getCurriculum().getEducation().size()!=0) {
                    ArrayList<String> edu = user.getCurriculum().getEducation();
                    ArrayList<Integer> tipeEdu = user.getCurriculum().getTipeEducation();
                    Paragraph education = new Paragraph();
                    education.add(new Paragraph(activity.getResources().getString(R.string.education), subFont));
                    addEmptyLine(education, 1);
                    for (int i=0; i<edu.size(); i++) {
                        if (tipeEdu.get(i)==0) {
                            String str = activity.getResources().getString(R.string.school)+": ";
                            p = new Paragraph();
                            p.add(new Chunk(str, smallBold));
                        }else {
                            String str = activity.getResources().getString(R.string.university)+": ";
                            p = new Paragraph();
                            p.add(new Chunk(str, smallBold));
                        }
                        p.add(new Chunk(edu.get(i), small));
                        education.add(p);
                    }
                    addEmptyLine(education, 1);
                    document.add(education);
                }
            }
            if (check7) {
                Paragraph languages = new Paragraph();
                String langNative = user.getCurriculum().getLanguagesNative();
                languages.add(new Paragraph(activity.getResources().getString(R.string.language_skills), subFont));
                addEmptyLine(languages, 1);
                if (user.getCurriculum().getLanguagesNative()!=null) {
                    String str = activity.getResources().getString(R.string.native_language)+ ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    p.add(new Chunk(langNative, small));
                    languages.add(p);
                }
                if (user.getCurriculum().getLanguagesKnow().size()!=0) {
                    ArrayList<String> langKnow = user.getCurriculum().getLanguagesKnow();
                    ArrayList<ArrayList<String>> know = user.getCurriculum().getKnowledge();
                    String str = activity.getResources().getString(R.string.known_languages);
                    languages.add(new Paragraph(str, smallBold));

                    for (int i=0; i<langKnow.size(); i++) {
                        str = langKnow.get(i)+":  "+activity.getResources().getString(R.string.listening)+" "+ know.get(i).get(0)+"  "+
                                activity.getResources().getString(R.string.reading)+" "+ know.get(i).get(1)+"  "+
                                activity.getResources().getString(R.string.comunication)+ " "+know.get(i).get(2)+"  "+
                                activity.getResources().getString(R.string.writing)+ " "+know.get(i).get(3);
                        languages.add(new Paragraph(str, small));
                    }
                }
                addEmptyLine(languages, 1);
                document.add(languages);
            }
            if (check8) {
                if (user.getCurriculum().getIt().size()!=0) {
                    ArrayList<String> it = user.getCurriculum().getIt();
                    Paragraph infoSkills = new Paragraph();
                    infoSkills.add(new Paragraph(activity.getResources().getString(R.string.it_skills), subFont));
                    addEmptyLine(infoSkills, 1);
                    for (int i=0; i<it.size(); i++) {
                        infoSkills.add(new Paragraph(it.get(i), small));
                        addEmptyLine(infoSkills, 1);
                    }
                    document.add(infoSkills);
                }
            }
            if (check9) {
                Paragraph distance = new Paragraph();
                distance.add(new Paragraph(activity.getResources().getString(R.string.distance_workplace), subFont));
                addEmptyLine(distance, 1);
                if (user.getCurriculum().getProvince()!=null) {
                    String str = activity.getResources().getString(R.string.favorite_provinces)+ ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    p.add(new Chunk(user.getCurriculum().getProvince(), small));
                    distance.add(p);
                }
                if (user.getCurriculum().getMove().size()!=0) {
                    ArrayList<Integer> mov = user.getCurriculum().getMove();
                    String str = activity.getResources().getString(R.string.willingness_buisness_trip)+ ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    switch (mov.get(0)) {
                        case 0:
                            str = activity.getResources().getString(R.string.home_transfers);
                            break;
                        case 1:
                            str = activity.getResources().getString(R.string.frequent_transfers);
                            break;
                        case 2:
                            str = activity.getResources().getString(R.string.limited_transfers);
                            break;
                        case 3:
                            str = activity.getResources().getString(R.string.no);
                            break;
                    }
                    p.add(new Chunk(str, small));
                    distance.add(p);

                    str = activity.getResources().getString(R.string.willingness_abroad_transfers)+ ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    switch (mov.get(1)) {
                        case 0:
                            str = activity.getResources().getString(R.string.only_europe);
                            break;
                        case 1:
                            str = activity.getResources().getString(R.string.non_european);
                            break;
                        case 2:
                            str = activity.getResources().getString(R.string.no);
                            break;
                    }
                    p.add(new Chunk(str, small));
                    distance.add(p);
                }
                document.add(distance);
            }
            document.close();
        }else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

            scheduleNotificationProgress(getNotificationProgress(1500), 1500);

            PdfWriter.getInstance(document, new FileOutputStream(myFile));
            document.open();

            Paragraph title = new Paragraph();
            title.add(new Paragraph(user.getName().toUpperCase() + " " + user.getSurname().toUpperCase(), catFont));
            title.add(new Paragraph("CURRICULUM VITAE", catFont));
            addEmptyLine(title, 2);
            document.add(title);

            Paragraph personal = new Paragraph();
            personal.add(new Paragraph(activity.getResources().getString(R.string.personal_info), subFont));
            addEmptyLine(personal, 1);
            String s = activity.getResources().getString(R.string.born_on) + ": ";
            Paragraph p = new Paragraph(s, small);
            s = (user.getDay() < 10 ? "0" + user.getDay() + "/" : user.getDay() + "/") +
                    ((user.getMonth() + 1) < 10 ? "0" + (user.getMonth() + 1) + "/" : (user.getMonth() + 1) + "/") + (user.getYear()) + "   ";
            p.add(new Chunk(s, smallBold));
            Calendar c = Calendar.getInstance();
            Calendar dateCalendar = new GregorianCalendar(user.getYear(), user.getMonth(), user.getDay());
            long diffInMilli = c.getTimeInMillis() - dateCalendar.getTimeInMillis();
            diffInMilli = diffInMilli / 1000 / 60 / 60 / 24 / 365;
            s = activity.getResources().getString(R.string.age) + ": ";
            p.add(new Chunk(s, small));
            s = String.valueOf(diffInMilli);
            p.add(new Chunk(s, smallBold));
            personal.add(p);

            if (check1) {
                if (user.getCurriculum().getPlace() != null) {
                    s = activity.getResources().getString(R.string.birth_place) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getPlace();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCitizenship() != null) {
                    s = activity.getResources().getString(R.string.citizenship) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCitizenship();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCf() != null) {
                    s = activity.getResources().getString(R.string.cf) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCf();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getAddress() != null) {
                    s = activity.getResources().getString(R.string.address) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getAddress();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getLicens() != null) {
                    s = activity.getResources().getString(R.string.driving_license) + ": ";
                }
                if (user.getCurriculum().isWithCar() != null) {
                    if (user.getCurriculum().isWithCar()) {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        s = s + "   " + activity.getResources().getString(R.string.with_car);
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    } else {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        s = s + "   " + activity.getResources().getString(R.string.without_car);
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    }
                    personal.add(p);
                } else {
                    if (user.getCurriculum().getLicens() != null) {
                        p = new Paragraph(s, small);
                        s = user.getCurriculum().getLicens();
                        p.add(new Chunk(s, smallBold));
                        personal.add(p);
                    }
                }
                if (user.getCurriculum().getEmail() != null) {
                    s = activity.getResources().getString(R.string.email) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getEmail();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getCellphone() != null) {
                    s = activity.getResources().getString(R.string.cell) + ": ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getCellphone();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                if (user.getCurriculum().getLinks() != null) {
                    s = activity.getResources().getString(R.string.links) + ":  ";
                    p = new Paragraph(s, small);
                    s = user.getCurriculum().getLinks();
                    p.add(new Chunk(s, smallBold));
                    personal.add(p);
                }
                addEmptyLine(personal, 1);
                document.add(personal);
            } else {
                addEmptyLine(personal, 1);
                document.add(personal);
            }
            if (check2) {
                imagePdf.scaleToFit(120f, 120f);
                imagePdf.setAbsolutePosition(400f, 680f);
                document.add(imagePdf);
            }
            if (check3) {
                if (user.getCurriculum().getGoal() != null) {
                    Paragraph goal = new Paragraph();
                    goal.add(new Paragraph(activity.getResources().getString(R.string.professional_goal), subFont));
                    addEmptyLine(personal, 1);
                    goal.add(new Paragraph(user.getCurriculum().getGoal(), small));
                    addEmptyLine(goal, 1);
                    document.add(goal);
                }
            }
            if (check4) {
                if (user.getCurriculum().getSkills().size() != 0) {
                    ArrayList<Integer> sk = user.getCurriculum().getSkills();
                    Paragraph skill = new Paragraph();
                    skill.add(new Paragraph(activity.getResources().getString(R.string.soft_skill), subFont));
                    addEmptyLine(skill, 1);
                    String str = activity.getResources().getString(R.string.autonomy) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(0) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.self_confidence) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(1) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.stress) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(2) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.organize) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(3) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.accuracy) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(4) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.learn) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(5) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.goals) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(6) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.manage_info) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(7) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.initiative) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(8) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.comunication_skills) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(9) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.problem_solving) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(10) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.team_work) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(11) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    str = activity.getResources().getString(R.string.leadership) + " ";
                    p = new Paragraph(str, small);
                    str = sk.get(12) + "/10";
                    p.add(new Chunk(str, smallBold));
                    skill.add(p);
                    addEmptyLine(skill, 1);
                    document.add(skill);
                }
            }
            if (check5) {
                if (user.getCurriculum().getExperiences().size() != 0) {
                    ArrayList<String> exp = user.getCurriculum().getExperiences();
                    Paragraph experiences = new Paragraph();
                    experiences.add(new Paragraph(activity.getResources().getString(R.string.experiences), subFont));
                    addEmptyLine(experiences, 1);
                    for (int i = 0; i < exp.size(); i++) {
                        experiences.add(new Paragraph(exp.get(i), small));
                        addEmptyLine(experiences, 1);
                    }
                    document.add(experiences);
                }
            }
            if (check6) {
                if (user.getCurriculum().getEducation().size() != 0) {
                    ArrayList<String> edu = user.getCurriculum().getEducation();
                    ArrayList<Integer> tipeEdu = user.getCurriculum().getTipeEducation();
                    Paragraph education = new Paragraph();
                    education.add(new Paragraph(activity.getResources().getString(R.string.education), subFont));
                    addEmptyLine(education, 1);
                    for (int i = 0; i < edu.size(); i++) {
                        if (tipeEdu.get(i) == 0) {
                            String str = activity.getResources().getString(R.string.school) + ": ";
                            p = new Paragraph();
                            p.add(new Chunk(str, smallBold));
                        } else {
                            String str = activity.getResources().getString(R.string.university) + ": ";
                            p = new Paragraph();
                            p.add(new Chunk(str, smallBold));
                        }
                        p.add(new Chunk(edu.get(i), small));
                        education.add(p);
                    }
                    addEmptyLine(education, 1);
                    document.add(education);
                }
            }
            if (check7) {
                Paragraph languages = new Paragraph();
                String langNative = user.getCurriculum().getLanguagesNative();
                languages.add(new Paragraph(activity.getResources().getString(R.string.language_skills), subFont));
                addEmptyLine(languages, 1);
                if (user.getCurriculum().getLanguagesNative() != null) {
                    String str = activity.getResources().getString(R.string.native_language) + ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    p.add(new Chunk(langNative, small));
                    languages.add(p);
                }
                if (user.getCurriculum().getLanguagesKnow().size() != 0) {
                    ArrayList<String> langKnow = user.getCurriculum().getLanguagesKnow();
                    ArrayList<ArrayList<String>> know = user.getCurriculum().getKnowledge();
                    String str = activity.getResources().getString(R.string.known_languages);
                    languages.add(new Paragraph(str, smallBold));

                    for (int i = 0; i < langKnow.size(); i++) {
                        str = langKnow.get(i) + ":  " + activity.getResources().getString(R.string.listening) + " " + know.get(i).get(0) + "  " +
                                activity.getResources().getString(R.string.reading) + " " + know.get(i).get(1) + "  " +
                                activity.getResources().getString(R.string.comunication) + " " + know.get(i).get(2) + "  " +
                                activity.getResources().getString(R.string.writing) + " " + know.get(i).get(3);
                        languages.add(new Paragraph(str, small));
                    }
                }
                addEmptyLine(languages, 1);
                document.add(languages);
            }
            if (check8) {
                if (user.getCurriculum().getIt().size() != 0) {
                    ArrayList<String> it = user.getCurriculum().getIt();
                    Paragraph infoSkills = new Paragraph();
                    infoSkills.add(new Paragraph(activity.getResources().getString(R.string.it_skills), subFont));
                    addEmptyLine(infoSkills, 1);
                    for (int i = 0; i < it.size(); i++) {
                        infoSkills.add(new Paragraph(it.get(i), small));
                        addEmptyLine(infoSkills, 1);
                    }
                    document.add(infoSkills);
                }
            }
            if (check9) {
                Paragraph distance = new Paragraph();
                distance.add(new Paragraph(activity.getResources().getString(R.string.distance_workplace), subFont));
                addEmptyLine(distance, 1);
                if (user.getCurriculum().getProvince() != null) {
                    String str = activity.getResources().getString(R.string.favorite_provinces) + ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    p.add(new Chunk(user.getCurriculum().getProvince(), small));
                    distance.add(p);
                }
                if (user.getCurriculum().getMove().size() != 0) {
                    ArrayList<Integer> mov = user.getCurriculum().getMove();
                    String str = activity.getResources().getString(R.string.willingness_buisness_trip) + ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    switch (mov.get(0)) {
                        case 0:
                            str = activity.getResources().getString(R.string.home_transfers);
                            break;
                        case 1:
                            str = activity.getResources().getString(R.string.frequent_transfers);
                            break;
                        case 2:
                            str = activity.getResources().getString(R.string.limited_transfers);
                            break;
                        case 3:
                            str = activity.getResources().getString(R.string.no);
                            break;
                    }
                    p.add(new Chunk(str, small));
                    distance.add(p);

                    str = activity.getResources().getString(R.string.willingness_abroad_transfers) + ": ";
                    p = new Paragraph();
                    p.add(new Chunk(str, smallBold));
                    switch (mov.get(1)) {
                        case 0:
                            str = activity.getResources().getString(R.string.only_europe);
                            break;
                        case 1:
                            str = activity.getResources().getString(R.string.non_european);
                            break;
                        case 2:
                            str = activity.getResources().getString(R.string.no);
                            break;
                    }
                    p.add(new Chunk(str, small));
                    distance.add(p);
                }
                document.add(distance);
            }
            document.close();

        }
        scheduleNotification(getNotification(pdfname, 1000), 1000);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) activity.getSystemService(ns);
        nMgr.cancel(1500);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private void scheduleNotification(Notification notification, int notificationid) {
        Intent notificationIntent = new Intent(activity, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationid);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, notificationid, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime();
        AlarmManager alarmManager = (AlarmManager)activity.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Documents/"+ pdfname);
        Uri photoURI = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", myFile);
        intent.setDataAndType(photoURI, "application/pdf");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, default_notification_channel_id);
        builder.setContentTitle(activity.getResources().getString(R.string.completed_pdf));
        builder.setContentText(content);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_create_pdf);
        builder.setContentIntent(pendingIntent);
        return builder.build();
    }

    private void scheduleNotificationProgress(Notification notification, int notificationid) {

        NotificationManager notificationManager = (NotificationManager) activity.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10101" , "NOTIFICATION_CHANNEL_NAM" , importance);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel);
        }
        assert notificationManager != null;
        notificationManager.notify(notificationid, notification);
    }

    private Notification getNotificationProgress(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        PendingIntent pendingIntent = PendingIntent.getActivity(activity, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, default_notification_channel_id);
        builder.setSmallIcon(R.drawable.ic_create_pdf);
        builder.setContentTitle(activity.getResources().getString(R.string.creation_PDF));
        builder.setContentText(activity.getResources().getString(R.string.creation_progress));
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setProgress(0, 0, true);
        return builder.build();
    }
}